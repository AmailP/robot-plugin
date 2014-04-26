package amailp.intellij.robot.psi.manipulator

import com.intellij.psi.{PsiElement, AbstractElementManipulator}
import com.intellij.openapi.util.TextRange
import amailp.intellij.robot.psi
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils

class ResourceValue extends AbstractElementManipulator[psi.ResourceValue] {
  override def handleContentChange(element: psi.ResourceValue, range: TextRange, newContent: String): psi.ResourceValue = {
    val newElement = new ExtRobotPsiUtils {
      def utilsPsiElement: PsiElement = element
    }.createResourceValue(newContent)
    element.getNode.getTreeParent.replaceChild(element.getNode, newElement.getNode)
    element
  }
}
