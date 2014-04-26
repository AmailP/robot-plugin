package amailp.intellij.robot.psi.manipulator

import com.intellij.psi.AbstractElementManipulator
import com.intellij.openapi.util.TextRange
import amailp.intellij.robot.psi

class Keyword extends AbstractElementManipulator[psi.Keyword] {
  override def handleContentChange(element: psi.Keyword, range: TextRange, newContent: String): psi.Keyword = null
}
