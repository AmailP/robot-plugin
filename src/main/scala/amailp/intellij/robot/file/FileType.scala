package amailp.intellij.robot.file

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon
import amailp.intellij.robot.lang.RobotLanguage

object FileType extends LanguageFileType(RobotLanguage) {
  val getName: String = "Robot file"
  val getDescription: String = "Robotframework language file"
  val getDefaultExtension: String = "robot"
  val getIcon: Icon = Icons.file
}
