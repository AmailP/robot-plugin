package amailp.intellij.robot.parser

import amailp.intellij.robot.psi.LibraryValue
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.intellij.robot.elements.RobotTokenTypes.Ellipsis
import amailp.intellij.robot.{ast, psi}

class PsiElementBuilder(node: ASTNode) {
  def build(): PsiElement = {
    node.getElementType match {
      case ast.Tables => new psi.Tables(node)
      case Ellipsis => new psi.Ellipsis(node)
      case ast.SettingsTable => new psi.Settings(node)
      case ast.SettingName | ast.ResourceKey | ast.LibraryKey => new psi.SettingName(node)
      case ast.ResourceValue => new psi.ResourceValue(node)
      case ast.LibraryValue => new LibraryValue(node)
      case ast.TestCasesTable => new psi.TestCases(node)
      case ast.TestCaseDefinition => new psi.TestCaseDefinition(node)
      case ast.TestCaseName => new psi.TestCaseName(node)
      case ast.KeywordsTable => new psi.Keywords(node)
      case ast.KeywordDefinition => new psi.KeywordDefinition(node)
      case ast.KeywordName => new psi.KeywordName(node)
      case ast.Keyword => new psi.Keyword(node)
      case ast.VariablesTable => new psi.Variables(node)
      case ast.VariableDefinition => new psi.VariableDefinition(node)
      case _ => new ASTWrapperPsiElement(node)
    }
  }
}
