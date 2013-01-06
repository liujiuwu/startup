package code.snippet

import net.liftweb.http.RequestVar
import code.model.User
import net.liftweb.http.SHtml
import code.lib.CommonUtils._
import code.lib.MailHelper
import code.lib.TimeUtils
import scala.xml.NodeSeq
import net.liftweb.util.Helpers._
import net.liftweb.http.S


class RegisterOps {

  private object emailVar extends RequestVar[String]("")

  private object passwordVar extends RequestVar[String]("")

  private object passwordConfirmationVar extends RequestVar[String]("")

  def render = {
    "name=email" #> (SHtml.text(emailVar.is, emailVar.set(_))) &
      "name=password" #> (SHtml.password(passwordVar.is, passwordVar.set(_))) &
      //"name=passwordConfirmation" #> SHtml.password(passwordConfirmationVar.is, passwordConfirmationVar.set(_)) &
      "type=submit" #> SHtml.onSubmitUnit(process)
  }

  private def process() = {
    val user = new User
    user.email(emailVar.is)
   user.password(passwordVar.is)
    user.validate match {
      case Nil => {
        //user.validated.set(true)
        //user.save
        //MailHelper.sendEMail("liujiuwu@gmail.com", user.email.get, "liujiuwu@gmail.com", "注册创业去邮箱验证", mailContent(user))
        emailVar.remove()
        passwordVar.remove()

      }

      case errors => errors.map(e => formError(e.field.uniqueFieldId.get.replaceAll("users_", ""), e.msg.text))
    }
    S.redirectTo("/sign_up_end")
  }

  private def mailContent(user: User): NodeSeq = {
    <p>
      您于{TimeUtils.format("yyyy年MM月dd日 HH:mm")}申请了邮箱验证。请点击以下链接，即可完成安全验证。
      <br/>
       <a href="http://www.qq.com">去验证邮箱，激活创业去注册帐户</a>
      <br/>
      该链接有效时间为24小时 ， 点击访问后自动失效 ！
      <br/>
      如果链接无法点击 ， 请粘贴到浏览器窗口地址栏中打开 。
      <br/>
      <br/>
      感谢您对周五的支持 ， 再次希望您在周五收获更多的旅行回忆 。
      <br/>
      若您没有申请过邮箱验证 ， 请您忽略此邮件 ， 由此给您带来的不便请谅解 。
      <br/>
      <br/>

      周五 http :// www.zhouwu.com
      <br/>
      (这是一封系统自动发送的email ， 请勿回复 。)
      <br/>
    </p>
  }
}