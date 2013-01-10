package code.model

import net.liftweb.mapper._

class User extends MegaProtoUser[User] with CreatedUpdated {
  def getSingleton = User

  object name extends MappedString(this, 20)

  object lastLoginTime extends MappedDateTime(this)

  object loginTime extends MappedDateTime(this)

}

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"

  override def fieldOrder = List(id, email, name, locale, timezone, password)

  override def signupFields = List(email, password)
}
