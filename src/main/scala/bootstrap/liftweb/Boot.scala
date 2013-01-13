package bootstrap.liftweb

import code.model.{Project, MyDBVendor, User}
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.mapper.DB
import net.liftweb.mapper.DefaultConnectionIdentifier
import net.liftweb.mapper.Schemifier
import net.liftweb.sitemap._
import code.lib.MailHelper
import code.rest.ApplicationRest
import net.liftmodules.FoBo


class Boot {
  def boot {
    LiftRules.addToPackages("code")
    DB.defineConnectionManager(DefaultConnectionIdentifier, MyDBVendor)

    FoBo.InitParam.JQuery = FoBo.JQuery182
    FoBo.InitParam.ToolKit = FoBo.Bootstrap222
    FoBo.InitParam.ToolKit = FoBo.FontAwesome300
    FoBo.init()

    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    LiftRules.dispatch.append(ApplicationRest) // stateful -- associated with a servlet container session

    MailHelper.init
    //MailHelper.sendEMail("liujiuwu@gmail.com", "923933533@qq.com", "liujiuwu@gmail.com", "邮件测试", <b>邮件内容</b>)

    Schemifier.schemify(true, Schemifier.infoF _, User, Project)
    LiftRules.setSiteMapFunc(() => User.sitemapMutator(MenuInfo.sitemap))

    //Rewrite
    LiftRules.statelessRewrite.append {
      case RewriteRequest(ParsePath("user_mgt" :: "login" :: Nil, _, _, _), _, _) =>
        RewriteResponse("sign_in" :: Nil)
      case RewriteRequest(ParsePath("user" :: "sign_out" :: Nil, _, _, _), _, _) =>
        RewriteResponse("user_mgt" :: "logout" :: Nil)
      case RewriteRequest(ParsePath("project" :: Nil, _, _, _), _, _) =>
        RewriteResponse("project" :: "index" :: Nil)
      case RewriteRequest(ParsePath("wenda" :: Nil, _, _, _), _, _) =>
        RewriteResponse("wenda" :: "index" :: Nil)
      case RewriteRequest(ParsePath("story" :: Nil, _, _, _), _, _) =>
        RewriteResponse("story" :: "index" :: Nil)
      case RewriteRequest(ParsePath("user" :: Nil, _, _, _), _, _) =>
        RewriteResponse("user" :: "index" :: Nil)
    }
  }
}

object MenuInfo {

  import Loc._
  import scala.xml._

  val MustBeLoggedIn = If(() => User.loggedIn_?, "")

  def userLinkText = User.currentUser.map(_.shortName).openOr("not logged in").toString

  val IfUserLoggedIn = If(() => User.loggedIn_?, () => RedirectResponse("/sign_in"))
  val IfAdminLoggedIn = If(() => User.loggedIn_? && User.superUser_?, () => RedirectResponse("/sign_in"))
  val HiddenSign = Unless(() => User.loggedIn_?, () => RedirectResponse("/user/"))

  val bootstrap210Doc = Menu(Loc("Bootstrap-2.1.0", Link(List("bootstrap-2.1.0"), true, "/bootstrap-2.1.0/index"), S.loc("Bootstrap-2.1.0", Text("Bootstrap-2.1.0"))))
  val bootstrap220Doc = Menu(Loc("Bootstrap-2.2.0", Link(List("bootstrap-2.2.0"), true, "/bootstrap-2.2.0/index"), S.loc("Bootstrap-2.2.0", Text("Bootstrap-2.2.0")), LocGroup("nldemo1")))
  val bootstrap222Doc = Menu(Loc("Bootstrap-2.2.2", Link(List("bootstrap-2.2.2"), true, "/bootstrap-2.2.2/index"), S.loc("Bootstrap-2.2.2", Text("Bootstrap-2.2.2")), LocGroup("nldemo1")))
  val foboApiDoc = Menu(Loc("FoBoAPI", Link(List("foboapi"), true, "/foboapi/#net.liftmodules.FoBo.package"), S.loc("FoBoAPI", Text("FoBo API")), LocGroup("liboTop2", "mdemo2", "nldemo1")))

  val nlHelp = Menu.i("NLHelp") / "helpindex"
  val menus = List(
    Menu("首页") / "index" >> LocGroup("main"),
    Menu("项目") / "project" / ** >> LocGroup("main"),
    Menu("问答") / "wenda" / ** >> LocGroup("main"),
    Menu("日志") / "story" / ** >> LocGroup("main"),

    Menu("注册") / "sign_up" >> HiddenSign >> LocGroup("sign"),
    Menu("邮箱验证") / "sign_up_end" >> HiddenSign >> LocGroup("sign"),
    Menu("登录") / "sign_in" >> HiddenSign >> LocGroup("sign"),

    Menu("帮助") / "help" / ** >> IfUserLoggedIn >> LocGroup("help"),

    Menu.i("更多") / "more" >> LocGroup("main") >> PlaceHolder submenus(
      bootstrap210Doc,
      bootstrap220Doc,
      bootstrap222Doc,
      Menu("divider1") / "divider1" >> FoBo.TBLocInfo.Divider,
      foboApiDoc
      ),

    Menu.i("用户中心") / "userCenterHeader" >> LocGroup("loginUser") >> FoBo.TBLocInfo.NavHeader,
    Menu("首页") / "user" / "index" >> IfUserLoggedIn >> LocGroup("loginUser"),
    Menu("账户设置") / "user" / "profile" / ** >> IfUserLoggedIn >> LocGroup("loginUser"),
    Menu("我的项目") / "user" / "project" / ** >> IfUserLoggedIn >> LocGroup("loginUser"),
    Menu("我的问答") / "user" / "wenda" / ** >> IfUserLoggedIn >> LocGroup("loginUser"),
    Menu("我的日志") / "user" / "story" / ** >> IfUserLoggedIn >> LocGroup("loginUser"),
    Menu("userDivider1") / "userDivider1" >> LocGroup("loginUser") >> FoBo.TBLocInfo.Divider,
    Menu("退出") / "user" / "sign_out" >> IfUserLoggedIn >> LocGroup("loginUser")
  )

  def sitemap() = SiteMap(menus: _*)
}


