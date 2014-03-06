package amailp.idea

import amailp.file.Icons
import com.intellij.openapi.fileTypes.{FileTypeFactory, LanguageFileType, FileTypeConsumer}
import javax.swing._

object RobotFileType extends LanguageFileType(RobotLanguage) {
  val getName: String = "Robot file"
  val getDescription: String = "Robotframework language file"
  val getDefaultExtension: String = "robot"
  val getIcon: Icon = Icons.FILE
}

class RobotFileTypeFactory extends FileTypeFactory {
   def createFileTypes (consumer: FileTypeConsumer) = consumer.consume(RobotFileType, "robot")
}


