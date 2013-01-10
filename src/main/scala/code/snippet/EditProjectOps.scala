package code.snippet

import net.liftweb.http.{S, LiftScreen}

/**
 * Created with IntelliJ IDEA.
 * User: jiuwu
 * Date: 13-1-11
 * Time: 上午12:16
 * To change this template use File | Settings | File Templates.
 */
class EditProjectOps extends LiftScreen{
  val project = Project.find(By(Project.title, S.param("jobName").open_!)).open_!
  val projectName = field("项目名称: ", project.title.is, trim)

  def EntoStr(cur:String): Utils.States.Value ={
    for(i <- 1 to 51){
      if(cur == Utils.getStateList()(i).toString)
        return Utils.States(i)
    }
    return null;
  }
  def PotoStre(cur:String): Utils.Positions.Value={
    for(i <- 1 to 33){
      if(cur == Utils.getPositionList()(i).toString)
        return Utils.Positions(i)
    }
    return null;
  }
  protected def finish() {
    val save = pre.CreatedUser(User.currentUser).chas(chas.is).city(city.is).companyName(companyName.is).title(title.is).position(PotoStre(position.is.toString)).duration(duration.is).req(req.is).respons(respons.is).location(EntoStr(location.is.toString)).save
    S.notice("工作已经修改")
  }
}
