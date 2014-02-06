package amailp.parser

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import RobotASTTypes._

class PsiElementBuilder(node: ASTNode) {
  def build(): PsiElement = {
    node.getElementType match {
      case Ellipsis => new amailp.psi.Ellipsis(node)
      case SettingsTable => new amailp.psi.Settings(node)
      case SettingName => new amailp.psi.SettingName(node)
      case TestCaseTitle => new amailp.psi.TestCaseTitle(node)
      case KeywordTitle => new amailp.psi.KeywordTitle(node)
      case _ => new ASTWrapperPsiElement(node)
    }
  }
}
