package code.lib

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: jiuwu
 * Date: 13-1-6
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
object TimeUtils {
  def format(format: String,date: Date = new Date) = {
    new SimpleDateFormat(format).format(date)
  }
}

