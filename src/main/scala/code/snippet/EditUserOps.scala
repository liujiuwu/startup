package code.snippet

import net.liftweb.common.Full
import code.model.User
import net.liftweb.http.{S, SHtml, RequestVar}
import net.liftweb.util.Helpers._
import code.lib.CommonUtils._
import code.lib.Location
import net.liftweb.mapper.Genders


class EditUserOps {

  object userVar extends RequestVar[User](User.currentUser.get)

  var oldPassword = ""
  var newPassword = ""

  def edit = {
    val user = userVar.is
    "#hidden" #> SHtml.hidden(() => userVar(user)) &
      "#name" #> SHtml.text(userVar.is.name.is, userVar.is.name(_)) &
      "#gender" #> SHtml.selectObj[Genders.Value](
        Genders.values.toList.map(v => (v, v.toString)),
        Full(user.gender.get),
        userVar.is.gender(_)
      ) &
      "#location" #> SHtml.selectObj[Location.Value](
        Location.values.toList.map(v => (v, v.toString)),
        Full(user.location.get),
        userVar.is.location(_)
      ) &
      "#signature" #> SHtml.text(userVar.is.signature.is, userVar.is.signature(_)) &
      "#introduction" #> SHtml.textarea(userVar.is.introduction.is, userVar.is.introduction(_)) &
      "type=submit" #> SHtml.onSubmitUnit(processSubmit)
  }


  private def processSubmit() {
    userVar.get.validate match {
      case Nil => {
        userVar.get.save
        S.notice("user Saved")
        S.seeOther("/user/profile/")
      }
      case errors => errors.map(e => formError(e.field.uniqueFieldId.get.replaceAll(User.dbTableName + "_", ""), e.msg.text))
    }
  }

  def updatePassword = User.currentUser match {
    case Full(user) =>
      "#old_password" #> SHtml.password("", oldPassword = _) &
        "#new_password" #> SHtml.password("", newPassword = _) &
        "type=submit" #> SHtml.onSubmitUnit(doUpdatePassword)
    case _ => xml.NodeSeq.Empty
  }

  private def doUpdatePassword() {
    if (oldPassword.isEmpty || oldPassword.length < 6 || oldPassword.length > 15) {
      formError("old_password", "旧密码不能为空且密码长度介于6~15个字符")
      return
    }

    User.currentUser match {
      case Full(user) =>
        if (!user.password.match_?(oldPassword)) {
          formError("old_password", "旧密码错误，请输入正确的密码！")
          return
        }
    }

    if (newPassword.isEmpty || newPassword.length < 6 || newPassword.length > 15) {
      formError("new_password", "新密码不能为空且密码长度介于6~15个字符")
      return
    }
    /* if (user.password.match_?(oldPassword) && !newPassword.isEmpty) {
       user.password(newPassword)
       user.save()
     }*/
  }


}
