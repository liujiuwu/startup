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

  def edit = {
    val user = userVar.is
    "#hidden" #> SHtml.hidden(() => userVar(user)) &
      "name=name" #> SHtml.text(userVar.is.name.is, userVar.is.name(_)) &
      "name=gender" #> SHtml.selectObj[Genders.Value](
        Genders.values.toList.map(v => (v, v.toString)),
        Full(user.gender.get),
        userVar.is.gender(_)
      ) &
      "name=location" #> SHtml.selectObj[Location.Value](
        Location.values.toList.map(v => (v, v.toString)),
        Full(user.location.get),
        userVar.is.location(_)
      ) &
      "name=signature" #> SHtml.text(userVar.is.signature.is, userVar.is.signature(_)) &
      "name=introduction" #> SHtml.textarea(userVar.is.introduction.is, userVar.is.introduction(_)) &
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


}
