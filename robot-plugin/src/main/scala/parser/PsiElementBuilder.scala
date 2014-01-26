package parser

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.elements.RobotASTTypes._

class PsiElementBuilder(node: ASTNode) {
  def build(): PsiElement = {
    node.getElementType match {
      case SettingsTable => new psi.Settings(node)
      case TestCaseTitle => new psi.TestCaseTitle(node)
      case KeywordTitle => new psi.KeywordTitle(node)
      case _ => new ASTWrapperPsiElement(node)
    }
  }
}
