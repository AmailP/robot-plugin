package amailp.intellij.robot.psi.manipulator

import amailp.intellij.robot.psi
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator


class Library extends AbstractElementManipulator[psi.Library] {
  override def handleContentChange(element: psi.Library, range: TextRange, newContent: String): psi.Library = null
}
