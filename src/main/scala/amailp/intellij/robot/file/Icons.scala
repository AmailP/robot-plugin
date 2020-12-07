package amailp.intellij.robot.file

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.LayeredIcon

object Icons {
  val file = IconLoader.getIcon("/icons/robot_file_16.png", Icons.getClass)
  val robot = IconLoader.getIcon("/icons/robot_16.png", Icons.getClass)
  val robotTest = new LayeredIcon(robot, AllIcons.Nodes.JunitTestMark)
  val keywords = IconLoader.getIcon("/icons/keywords_16.png", Icons.getClass)
  val keyword = IconLoader.getIcon("/icons/keyword_16.png", Icons.getClass)
  val variables = IconLoader.getIcon("/icons/variables_16.png", Icons.getClass)
  val variable = IconLoader.getIcon("/icons/variable_16.png", Icons.getClass)
}
