package amailp.intellij.robot.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.openapi.util.TextRange
import java.util.regex.Pattern
import amailp.intellij.robot.psi.utils.RobotPsiUtils

class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils {
  def getDefinition: KeywordDefinition = element.getParent.asInstanceOf[KeywordDefinition]
  def variables = getNode.getChildren(TokenSet.create(Variable))

  def textCaseInsensitiveExcludingVariables = {
    val text = this.currentRobotFile.getText
    def quoteRange(range: TextRange): String = Pattern.quote(range.substring(text))
    val result = new StringBuilder("(?i)")
    var doneOffset = getTextRange.getStartOffset
    for ( variable <- variables.sortWith((v1, v2) => v1.getTextRange.getStartOffset < v2.getTextRange.getStartOffset)) {
      val variableRange = variable.getTextRange
      result.append(quoteRange(new TextRange(doneOffset, variableRange.getStartOffset)))
      result.append(".*")
      doneOffset = variableRange.getEndOffset
    }
    result.append(quoteRange(new TextRange(doneOffset, getTextRange.getEndOffset))).toString()
  }
  def matches(string: String) = string matches textCaseInsensitiveExcludingVariables
  val element: PsiElement = this
}
