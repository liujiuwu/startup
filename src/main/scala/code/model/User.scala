package code.model

import net.liftweb.mapper.MegaProtoUser
import net.liftweb.mapper.MetaMegaProtoUser
import net.liftweb.mapper.MappedDateTime
import net.liftweb.mapper.MappedString
import net.liftweb.http.S
import net.liftweb.mapper.CreatedUpdated

class User extends MegaProtoUser[User] with CreatedUpdated{
  def getSingleton = User


  override lazy val firstName = new MyFirstName(this, 32) {
    println("I am doing something different")
  }
}

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"
  override def fieldOrder = List(id, email, locale, timezone, password)
  
  override def signupFields = List(email, password)
}
