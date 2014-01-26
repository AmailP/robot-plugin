import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

package object psi {
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseTitle(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordTitle (node: ASTNode) extends ASTWrapperPsiElement(node)
}
