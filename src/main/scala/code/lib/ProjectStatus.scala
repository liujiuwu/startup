package code.lib

/**
 * Created with IntelliJ IDEA.
 * User: jiuwu
 * Date: 13-1-12
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
object ProjectStatus extends Enumeration {
  val running = Value(1, "运营中")
  val closed = Value(2, "已停运")
}
