package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._
import net.liftmodules.widgets.gravatar.Gravatar

import net.liftweb.http.js._
import JsCmds._

import _root_.net.liftweb.http._
import S._
import SHtml._
import js._
import js.jquery._
import JqJsCmds._
import JsCmds._
import util._
import Helpers._
import _root_.scala.xml.{Text, NodeSeq}


class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  // replace the contents of the element with id "time" with the date
  def howdy = "#time *" #> date.map(_.toString)

  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */

  def myface = Gravatar("liujiuwu@gmail.com")

  var cnt = 0
  val spanName: String = S.attr("id_name") openOr "cnt_id"

  def doClicker(text: NodeSeq) =
    a(() => {
      cnt = cnt + 1;
      SetHtml(spanName, Text(cnt.toString))
    }, text)

  def ajaxTest(xhtml: NodeSeq): NodeSeq = {
    // bind the view to the functionality
    bind("ajax", xhtml,"clicker" -> doClicker _)
  }
}
