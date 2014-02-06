package amailp

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseTitle(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordTitle (node: ASTNode) extends ASTWrapperPsiElement(node)

  object Ellipsis {
    val string = "..."
  }

  object Settings {
    val names = Set("Library", "Resource", "Variables", "Documentation", "Metadata", "Suite Setup",
      "Suite Teardown", "Suite Precondition", "Suite Postcondition", "Force Tags", "Default Tags", "Test Setup",
      "Test Teardown", "Test Precondition", "Test Postcondition", "Test Template", "Test Timeout")
  }
}
