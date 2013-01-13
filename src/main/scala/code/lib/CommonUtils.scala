package code.lib

import net.liftweb.http.S
import net.liftweb.http.js.JsCmds
import net.liftweb.http._
import net.liftweb.util.Helpers._
import scala.xml.XML

object CommonUtils extends App {
  def formErrors(errors: Map[String, String]) {
    for ((fieldName, errorMsg) <- errors) formError(fieldName, errorMsg)
  }

  def formError(fieldName: String, msg: String) {
    println(fieldName+"|"+msg)
    S.appendJs(JsCmds.Run("$('#group_%s').removeClass('success,error,warning');$('#group_%1$s').addClass('error');$('#error_%1$s').text('%s')" format (fieldName, msg)))
  }

  

}