package code.snippet

import net.liftweb.http.S
import net.liftweb.http.SHtml
import net.liftweb.mapper.Descending
import net.liftweb.mapper.OrderBy
import net.liftweb.util.Helpers.strToCssBindPromoter
import net.liftweb._
import http._
import common._
import util.Helpers._
import js._
import JsCmds._
import JE._
import scala.xml.NodeSeq

class IndexOps {
  def currentActiveLink = {
    ("li class=" + (S.uri match {
      case "/" => "home"
      case "/admin/" => "admin_index"
      case "/user/" => "user_index"
      case url => url.replaceAll("^/|/$", "").replaceAll("/", "_")
    }) + " [class+]") #> "active"
  }



  def ajaxForm = {
    // state
    var name = ""
    var age = "0"
    val whence = S.referer openOr "/"

    // our process method returns a
    // JsCmd which will be sent back to the browser
    // as part of the response
    def process(): JsCmd = {
      // sleep for 400 millis to allow the user to
      // see the spinning icon
      Thread.sleep(400)

      // do the matching
      asInt(age) match {
        // display an error and otherwise do nothing
        case Full(a) if a < 13 =>
          S.error("age", "Too young!"); Noop

        case Full(a) => {
          RedirectTo(whence, () => {
            S.notice("Name: " + name)
            S.notice("Age: " + a)
          })
        }

        case _ => S.error("age", "Age doesn't parse as a number"); Noop
      }
    }

    // binding looks normal
    "name=name" #> SHtml.text(name, name = _, "id" -> "the_name") &
      "name=age" #> (SHtml.text(age, age = _) ++ SHtml.hidden(process))
  }
  
  def test = {
    <div>This snippet evaluated on 
    {
      Thread.currentThread.getName()
    } 
    </div>
  }
}