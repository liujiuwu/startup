package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.Req
import net.liftweb.http.GetRequest
import net.liftweb.json.JsonAST.JString
import net.liftweb.http.S

object ApplicationRest extends RestHelper {

  serve {
    case Req("api" :: "static" :: _, "xml", GetRequest) => <b>Static</b>
    case Req("api" :: "static" :: _, "json", GetRequest) => JString("Static")
    case Get("user" :: "verifyemail" :: _, _) => <b>{S.param("code") ?~ "email error" ~> "请输入Email地址"}</b>
  }
}