package code.model

import net.liftweb.mapper.{MappedString, MegaProtoUser, MetaMegaProtoUser, CreatedUpdated}

class User extends MegaProtoUser[User] with CreatedUpdated {
  def getSingleton = User

  object name extends MappedString(this, 20)

}

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"

  override def fieldOrder = List(id, email, name, locale, timezone, password)

  override def signupFields = List(email, password)
}
