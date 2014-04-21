package amailp.intellij.robot.extensions

import com.intellij.openapi.fileTypes.FileTypeConsumer
import amailp.intellij.robot.file.FileType

class FileTypeFactory extends com.intellij.openapi.fileTypes.FileTypeFactory {
   def createFileTypes (consumer: FileTypeConsumer) = consumer.consume(FileType, "robot")
}


