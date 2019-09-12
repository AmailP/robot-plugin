package amailp.intellij.robot.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.ide.actions.CreateFileFromTemplateDialog.Builder
import amailp.intellij.robot.file.FileType

class NewRobotFileAction
    extends CreateFileFromTemplateAction("Robot File",
                                         "Creates a Robot file from the specified template",
                                         FileType.getIcon) {
  override def getActionName(directory: PsiDirectory, newName: String, templateName: String): String =
    s"Create Robot file $newName"

  override def buildDialog(project: Project, directory: PsiDirectory, builder: Builder): Unit =
    builder
      .setTitle("New Robot file")
      .addKind("Robot test suite", FileType.getIcon, "Robot Test Suite")
      .addKind("Robot keywords", FileType.getIcon, "Robot Keywords")
}
