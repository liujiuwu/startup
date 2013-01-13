package code.model

import net.liftweb.mapper._
import code.lib.{Location, ProjectStatus}


class Project extends LongKeyedMapper[Project] with IdPK with CreatedUpdated {
  def getSingleton = Project

  object name extends MappedString(this, 50) with ValidateLength {
    override def validations = valMinLen(2, "项目名称必填，不少于2个字。") _ ::
      super.validations
  }

  object status extends MappedEnum(this, ProjectStatus)

  object location extends MappedEnum(this, Location)

  object descn extends MappedTextarea(this, 255) with ValidateLength {
    override def validations = valMinLen(10, "项目描述必填，不少于10个字。") _ :: valMaxLen(300, "项目描述太长，不多于300个字。") _ ::
      super.validations
  }

}

object Project extends Project with LongKeyedMetaMapper[Project] {
  override def dbTableName = "projects"

  override def fieldOrder = List(id, name, status)
}