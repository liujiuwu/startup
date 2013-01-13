package code.snippet

import code.model.User
import xml.Text
import net.liftweb.util.Helpers._
import net.liftweb.common.Full
import code.lib.TimeUtils

/**
 * Created with IntelliJ IDEA.
 * User: jiuwu
 * Date: 13-1-10
 * Time: 下午9:08
 * To change this template use File | Settings | File Templates.
 */
class UserCenterOps {
  def userBaseInfo = User.currentUser match {
    case Full(user) => "#user_name" #> user.name.get & "#user_login_time *" #> TimeUtils.format("yyyy-MM-dd HH:mm", user.loginTime.get) & "#user_last_login_time *" #> TimeUtils.format("yyyy-MM-dd HH:mm", user.lastLoginTime.get) & "#user_signature *" #> user.signature.get
    case _ => Text("")
  }
}
