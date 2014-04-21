package amailp.intellij.robot.structureView

import amailp.intellij.robot.psi.RobotPsiFile
import com.intellij.ide.structureView.{StructureViewTreeElement, StructureViewModel, TreeBasedStructureViewBuilder}
import com.intellij.openapi.editor.Editor
import amailp.intellij.robot.psi

class RobotTreeBasedStructureViewBuilder(psiFile: RobotPsiFile) extends TreeBasedStructureViewBuilder {
   override def createStructureViewModel(editor: Editor): StructureViewModel = {
     val element: StructureViewTreeElement =  psiFile.findChildByClass(classOf[psi.Tables]).structureViewTreeElement
     new RobotStructureViewModel(psiFile, editor, element)
   }
 }
