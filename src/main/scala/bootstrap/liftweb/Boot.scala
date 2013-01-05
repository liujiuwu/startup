package bootstrap.liftweb

import code.model.MyDBVendor
import net.liftweb._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.mapper.DB
import net.liftweb.mapper.DefaultConnectionIdentifier
import net.liftweb.mapper.Schemifier
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery.JQueryArtifacts
import code.model.User



class Boot {
  def boot {
    LiftRules.addToPackages("code")
    DB.defineConnectionManager(DefaultConnectionIdentifier, MyDBVendor)

    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery182
    JQueryModule.init()

    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))  
    
    Schemifier.schemify(true, Schemifier.infoF _, User)
    LiftRules.setSiteMapFunc(() => MenuInfo.sitemap)
  }
}

object MenuInfo {
  import Loc._

  val adminMenus = Menu("管理首页") / "admin" / "index" >> Hidden submenus (
    Menu("分类管理") / "admin" / "sections" / "index" >> LocGroup("admin"),
    Menu("新建分类") / "admin" / "sections" / "add" >> LocGroup("admin"),
    Menu("修改分类") / "admin" / "sections" / "edit" >> Hidden,
    Menu("删除分类") / "admin" / "sections" / "delete" >> Hidden,

    Menu("讨论节点管理") / "admin" / "nodes" / "index" >> LocGroup("admin"),
    Menu("新建讨论节点") / "admin" / "nodes" / "add" >> LocGroup("admin"),
    Menu("修改讨论节点") / "admin" / "nodes" / "edit" >> Hidden,
    Menu("删除讨论节点") / "admin" / "nodes" / "delete" >> Hidden)

  def sitemap() = SiteMap(
    Menu("Home") / "index",
    Menu("Signup") / "sign_up",
    adminMenus,
    Menu(Loc("help", ("help" :: Nil) -> true, "帮助")))
}
