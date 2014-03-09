package amailp.intellij.robot.idea

import com.intellij.openapi.fileTypes.{FileTypeFactory, LanguageFileType, FileTypeConsumer}
import javax.swing._
import com.intellij.openapi.util.IconLoader

object Icons {
  val file = IconLoader.getIcon("/icons/robot_file_16.png")
}

object RobotFileType extends LanguageFileType(RobotLanguage) {
  val getName: String = "Robot file"
  val getDescription: String = "Robotframework language file"
  val getDefaultExtension: String = "robot"
  val getIcon: Icon = Icons.file
}

class RobotFileTypeFactory extends FileTypeFactory {
   def createFileTypes (consumer: FileTypeConsumer) = consumer.consume(RobotFileType, "robot")
}


