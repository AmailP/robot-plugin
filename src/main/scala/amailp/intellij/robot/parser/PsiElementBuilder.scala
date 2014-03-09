package amailp.intellij.robot.parser

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.intellij.robot.elements.RobotTokenTypes.Ellipsis

class PsiElementBuilder(node: ASTNode) {
  def build(): PsiElement = {
    node.getElementType match {
      case Ellipsis => new amailp.intellij.robot.psi.Ellipsis(node)
      case SettingsTable => new amailp.intellij.robot.psi.Settings(node)
      case SettingName | ResourceName => new amailp.intellij.robot.psi.SettingName(node)
      case ResourceValue => new amailp.intellij.robot.psi.ResourceValue(node)
      case TestCaseName => new amailp.intellij.robot.psi.TestCaseName(node)
      case KeywordName => new amailp.intellij.robot.psi.KeywordName(node)
      case Keyword => new amailp.intellij.robot.psi.Keyword(node)
      case _ => new ASTWrapperPsiElement(node)
    }
  }
}
