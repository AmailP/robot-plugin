package amailp.intellij.robot.structureView

import amailp.intellij.robot.psi.RobotPsiFile
import com.intellij.openapi.editor.Editor
import com.intellij.ide.structureView.{StructureViewModelBase, StructureViewTreeElement}

class RobotStructureViewModel(file: RobotPsiFile, editor: Editor, element: StructureViewTreeElement)
    extends StructureViewModelBase(file, editor, element)
