package amailp

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.{PsiReferenceBase, PsiElement, PsiReference}
import com.intellij.openapi.util.TextRange

package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Keyword (node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node)

  object Ellipsis {
    val string = "..."
  }

  object Settings {
    val names = Set[String]("Library", "Resource", "Variables", "Documentation", "Metadata", "Suite Setup",
      "Suite Teardown", "Suite Precondition", "Suite Postcondition", "Force Tags", "Default Tags", "Test Setup",
      "Test Teardown", "Test Precondition", "Test Postcondition", "Test Template", "Test Timeout")
  }
}
