package code.model

import net.liftweb.mapper._


class Project extends LongKeyedMapper[Project] with IdPK with CreatedUpdated {
  def getSingleton = Project
}

object Project extends Project with LongKeyedMetaMapper[Project] with CRUDify[Long, Project] {
  override def dbTableName = "projects"
}