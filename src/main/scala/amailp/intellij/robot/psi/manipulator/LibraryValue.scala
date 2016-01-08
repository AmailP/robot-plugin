package amailp.intellij.robot.psi.manipulator

import amailp.intellij.robot.psi
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator


class LibraryValue extends AbstractElementManipulator[psi.LibraryValue] {
  override def handleContentChange(element: psi.LibraryValue, range: TextRange, newContent: String): psi.LibraryValue = null
}
