package amailp.intellij.robot.extensions

import com.intellij.lang.PsiStructureViewFactory
import com.intellij.psi.PsiFile
import com.intellij.ide.structureView._
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import amailp.intellij.robot.psi.RobotPsiFile
import amailp.intellij.robot.structureView.RobotTreeBasedStructureViewBuilder

class RobotPsiStructureViewFactory extends PsiStructureViewFactory {
  def getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder = new StructureViewBuilder {
    def createStructureView(fileEditor: FileEditor, project: Project): StructureView =
      new RobotTreeBasedStructureViewBuilder(psiFile.asInstanceOf[RobotPsiFile]).createStructureView(fileEditor, project)
  }
}




