package code.snippet

import code.model.User
import net.liftweb.common.{Box, Full, Empty, Failure, ParamFailure}
import net.liftweb.http._
import net.liftweb.mapper.By
import net.liftweb.util.Helpers._
import scala.xml.Text
import code.lib.CommonUtils._
import net.liftmodules.widgets.gravatar.Gravatar
import net.liftweb.util._

class LoginOps {

  private object emailVar extends RequestVar[String]("liujiuwu@gmail.com")

  private object passwordVar extends RequestVar[String]("821024")

  def userFace = User.currentUser match {
    case Full(user) => Gravatar(user.email.is, 80)
    case _ => Text("")
  }

  def userStatus = User.currentUser match {
    case Full(user) =>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <div id="login-face">
            {Gravatar(user.email.is, 16)}
          </div>{user.name}<b class='caret'></b>
        </a>
        <ul class="dropdown-menu">
          <li>
            <a href="/user/">用户中心</a>
          </li>{if (User.superUser_?)
          <li>
            <a href="/admin/">后台管理</a>
          </li>}<li>
          <a href="/user/profile">账户设置</a>
        </li>
          <li>
            <a href="/user/favorites">我的关注</a>
          </li>
          <li class="last">
            <a href="/user/sign_out">退出</a>
          </li>
        </ul>
      </li>
    case _ =>
      <li class="sign_up">
        <a href="/sign_up" class="btn-nav">注册</a>
      </li>
        <li class="sign_in">
          <a href="/sign_in" class="btn-nav">登录</a>
        </li>
  }

  def render = {
    "name=email" #> SHtml.text(emailVar.is, emailVar.set(_)) &
      "name=password" #> SHtml.password(passwordVar.is, passwordVar.set(_)) &
      "type=submit" #> SHtml.onSubmitUnit(process)
  }

  private def process() = {
    if (emailVar.is.isEmpty) formError("email", "请输入注册的邮箱地址")
    else {
      User.find(By(User.email, emailVar.is)) match {
        case Full(user) => {
          if (passwordVar.is.isEmpty) formError("password", "请输入你的密码")
          else {
            if (user.password.match_?(passwordVar.is)) {
              //User.logUserIn(user, () => if (User.superUser_?) S.redirectTo("/admin/") else S.redirectTo("/user/"))
              user.lastLoginTime(user.loginTime.get)
              user.loginTime(TimeHelpers.now)
              user.save()
              User.logUserIn(user, () => S.redirectTo("/user"))
            } else
              formError("password", "密码与注册邮箱不匹配，请仔细想想")
          }
        }
        case _ => formError("email", "此邮箱尚未注册，请先注册")
      }
    }
  }
}