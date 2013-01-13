package code.model

import net.liftweb.mapper._
import code.lib.ProjectStatus


class Project extends LongKeyedMapper[Project] with IdPK with CreatedUpdated {
  def getSingleton = Project

  object name extends MappedString(this, 50)

  object status extends MappedEnum(this, ProjectStatus)

  object descn extends MappedTextarea(this, 255)

}

object Project extends Project with LongKeyedMetaMapper[Project] {
  override def dbTableName = "projects"
}