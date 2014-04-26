package amailp.intellij.robot.file

import com.intellij.openapi.util.IconLoader
import com.intellij.ui.LayeredIcon
import com.intellij.icons.AllIcons

object Icons {
  val file = IconLoader.getIcon("/icons/robot_file_16.png")
  val robot = IconLoader.getIcon("/icons/robot_16.png")
  val robotTest = new LayeredIcon(robot, AllIcons.Nodes.JunitTestMark)
  val keywords = IconLoader.getIcon("/icons/keywords_16.png")
  val keyword = IconLoader.getIcon("/icons/keyword_16.png")
  val variables = IconLoader.getIcon("/icons/variables_16.png")
  val variable = IconLoader.getIcon("/icons/variable_16.png")
}
