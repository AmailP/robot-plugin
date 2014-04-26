package amailp.intellij.robot.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes._

class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node) {
  def getDefinition: KeywordDefinition = element.getParent.asInstanceOf[KeywordDefinition]
  def variables = getNode.getChildren(TokenSet.create(Variable))
  def textCaseInsensitiveExcludingVariables = {
    val offset = getTextRange.getStartOffset
    var result = getText
    for {
      variable <- variables.sortWith((v1, v2) => v1.getTextRange.getStartOffset > v2.getTextRange.getStartOffset)
      relativeTextRange = variable.getTextRange.shiftRight(-offset)
    } result = relativeTextRange.replace(result, ".*")
    s"(?i)$result"
  }
  def matches(string: String) = string matches textCaseInsensitiveExcludingVariables
  val element: PsiElement = this
}
