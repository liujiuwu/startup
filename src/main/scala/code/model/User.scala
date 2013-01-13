package code.model

import net.liftweb.mapper._
import code.lib.Location

class User extends MegaProtoUser[User] with CreatedUpdated {
  def getSingleton = User

  object name extends MappedString(this, 20) with ValidateLength {
    override def validations = valMinLen(2, "真实姓名或昵称，不少于2个字。") _ ::
      super.validations
  }

  object gender extends MappedGender(this)

  object signature extends MappedString(this, 30) with ValidateLength {
    override def validations = valMaxLen(30, "签名太长了，不多于30个字。") _ ::
      super.validations
  }

  object introduction extends MappedString(this, 200) with ValidateLength {
    override def validations = valMaxLen(200, "自我介绍太长了，不多于200个字。") _ ::
      super.validations
  }

  object location extends MappedEnum(this, Location)

  object lastLoginTime extends MappedDateTime(this)

  object loginTime extends MappedDateTime(this)

}

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"

  override def fieldOrder = List(id, email, name, locale, timezone, password)

  override def signupFields = List(email, password)
}
