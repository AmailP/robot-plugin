package parser

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.elements.RobotASTTypes._
import amailp.elements.RobotTokenTypes._
import amailp.psi.impl.SettingsImpl

class PsiElementBuilder(node: ASTNode) {
  def build(): PsiElement = {
    val elementType = node.getElementType
    if(elementType == Table) {
      val header = node.getChildren(HeaderTokens)
      assert(header.size <= 1)
      if(header.size == 1 && header(0).getElementType == SettingsHeader)
        return new SettingsImpl(node)
    }
    new ASTWrapperPsiElement(node)
  }
}
