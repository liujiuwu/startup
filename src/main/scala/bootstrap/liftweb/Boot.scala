package bootstrap.liftweb

import code.model.MyDBVendor
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.mapper.DB
import net.liftweb.mapper.DefaultConnectionIdentifier
import net.liftweb.mapper.Schemifier
import net.liftweb.sitemap._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery.JQueryArtifacts
import code.model.User
import code.lib.MailHelper
import code.rest.ApplicationRest


class Boot {
  def boot {
    LiftRules.addToPackages("code")
    DB.defineConnectionManager(DefaultConnectionIdentifier, MyDBVendor)

    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery182
    JQueryModule.init()

    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    LiftRules.dispatch.append(ApplicationRest) // stateful -- associated with a servlet container session

    MailHelper.init
    //MailHelper.sendEMail("liujiuwu@gmail.com", "923933533@qq.com", "liujiuwu@gmail.com", "邮件测试", <b>邮件内容</b>)

    Schemifier.schemify(true, Schemifier.infoF _, User)
    LiftRules.setSiteMapFunc(() => MenuInfo.sitemap)

    //Rewrite
    LiftRules.statelessRewrite.append {
      case RewriteRequest(ParsePath("user_mgt" :: "login" :: Nil, _, _, _), _, _) =>
        RewriteResponse("sign_in" :: Nil)
      case RewriteRequest(ParsePath("user" :: "sign_out" :: Nil, _, _, _), _, _) =>
        RewriteResponse("user_mgt" :: "logout" :: Nil)
    }
  }
}

object MenuInfo {
  import Loc._

  val IfUserLoggedIn = If(() => User.loggedIn_?, () => RedirectResponse("/sign_in"))
  val IfAdminLoggedIn = If(() => User.loggedIn_? && User.superUser_?, () => RedirectResponse("/sign_in"))
  val HiddenSign = Unless(() => User.loggedIn_?, () => RedirectResponse("/user/"))


  val menus = List(
    Menu("首页") / "index",
    Menu("注册") / "sign_up" >> HiddenSign >> LocGroup("sign"),
    Menu("注册2") / "sign_up_end" >> HiddenSign >> LocGroup("sign"),
    Menu("登录") / "sign_in" >> HiddenSign >> LocGroup("sign"),
    Menu("项目") / "project" >> HiddenSign >> LocGroup("project"))

  def sitemap() = SiteMap(menus: _*)
}


