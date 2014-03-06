package amailp.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import amailp.idea.RobotFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.ide.actions.CreateFileFromTemplateDialog.Builder

class NewRobotFileAction
  extends CreateFileFromTemplateAction("Robot File", "Creates a Robot file from the specified template", RobotFileType.getIcon) {
  override def getActionName(directory: PsiDirectory, newName: String, templateName: String): String =
    s"Create Robot file $newName"

  override def buildDialog(project: Project, directory: PsiDirectory, builder: Builder): Unit =
    builder
      .setTitle("New Robot file")
      .addKind("Robot test suite", RobotFileType.getIcon, "Robot Test Suite")
      .addKind("Robot keywords", RobotFileType.getIcon, "Robot Keywords")
}