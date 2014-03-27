package amailp.intellij.robot.idea

import com.intellij.lang.PsiStructureViewFactory
import com.intellij.psi.PsiFile
import com.intellij.ide.structureView._
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import amailp.intellij.robot.psi.RobotPsiFile
import com.intellij.openapi.editor.Editor
import amailp.intellij.robot.psi

class RobotPsiStructureViewFactory extends PsiStructureViewFactory {
  def getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder = new StructureViewBuilder {
    def createStructureView(fileEditor: FileEditor, project: Project): StructureView =
      new RobotTreeBasedStructureViewBuilder(psiFile.asInstanceOf[RobotPsiFile]).createStructureView(fileEditor, project)
  }
}

class RobotTreeBasedStructureViewBuilder(psiFile: RobotPsiFile) extends TreeBasedStructureViewBuilder {
  override def createStructureViewModel(editor: Editor): StructureViewModel = {
    val element: StructureViewTreeElement =  psiFile.findChildByClass(classOf[psi.Tables]).structureViewTreeElement
    new RobotStructureViewModel(psiFile, editor, element)
  }
}

class RobotStructureViewModel(file: RobotPsiFile, editor: Editor, element: StructureViewTreeElement)
  extends StructureViewModelBase(file, editor, element)
