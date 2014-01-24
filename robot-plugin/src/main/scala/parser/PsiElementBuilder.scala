package parser

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.elements.RobotASTTypes._
import amailp.elements.RobotTokenTypes._
import amailp.psi.impl.SettingsImpl

class PsiElementBuilder(node: ASTNode) {
  def build(): PsiElement = {
    node match {
      case SettingsTable => new SettingsImpl(node)
      case _ => new ASTWrapperPsiElement(node)
    }
  }
}
