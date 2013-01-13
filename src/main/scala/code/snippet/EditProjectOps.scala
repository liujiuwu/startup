package code.snippet

import net.liftweb.common.{Full, Box, Empty}
import code.model.Project
import net.liftweb.http.{S, SHtml, RequestVar}
import net.liftweb.util.Helpers._
import code.lib.CommonUtils._
import code.lib.ProjectStatus


class EditProjectOps {

  object projectVar extends RequestVar[Project](Project.create)

  def create = {
    val project = projectVar.is
    "#hidden" #> SHtml.hidden(() => projectVar(project)) &
      "name=name" #> SHtml.text(projectVar.is.name.is, projectVar.is.name(_)) &
      "name=status" #> SHtml.selectObj[ProjectStatus.Value](
        ProjectStatus.values.toList.map(v => (v, v.toString)),
        Full(ProjectStatus.running),
        projectVar.is.status(_)
      ) &
      "name=descn" #> SHtml.textarea(projectVar.is.descn.is, projectVar.is.descn(_)) &
      "type=submit" #> SHtml.onSubmitUnit(processSubmit)
  }


  private def processSubmit() {
    projectVar.get.validate match {
      case Nil => {
        projectVar.get.save
        S.notice("Project Saved")
      }
      case errors => errors.map(e => formError(e.field.uniqueFieldId.get.replaceAll("nodes_", ""), e.msg.text))
    }
  }

  def form = {
    <span id="hidden"></span>
      <div class="control-group" id="group_name">
        <label class="control-label" for="name">项目名称</label>
        <div class="controls">
          <input id="name" name="name" size="50" type="text"/> <span id="error_name" class="help-inline"></span>
        </div>
      </div>
      <div class="control-group" id="group_status">
        <label class="control-label" for="descn">项目状态</label>
        <div class="controls">
          <textarea id="status" name="status"></textarea> <span id="error_status" class="help-inline"></span>
        </div>
      </div>
      <div class="control-group" id="group_descn">
        <label class="control-label" for="descn">项目描述</label>
        <div class="controls">
          <textarea id="descn" name="descn"></textarea> <span id="error_descn" class="help-inline"></span>
        </div>
      </div>
      <div class="form-actions">
        <input class="btn btn-primary" name="commit" type="submit" value="确定"/>
      </div>
  }

}
