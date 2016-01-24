package amailp.intellij.robot

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import javax.swing.Icon
import com.intellij.icons.AllIcons
import amailp.intellij.robot.structureView.InStructureView
import amailp.intellij.robot.file.Icons
import amailp.intellij.robot.psi.utils.RobotPsiUtils

package object psi {
  class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)

  trait Library {
    def getText: String
  }
  class LibraryValue(node: ASTNode) extends ASTWrapperPsiElement(node) with Library  with RobotPsiUtils
  object BuiltInLibrary extends Library {
    def getText: String = "BuiltIn"
  }

  class Tables(node: ASTNode) extends RobotPsiElement(node) with InStructureView {
    def structureViewText: String = "AAA Tables structure view text"
    def structureViewIcon: Icon = null
    def structureViewChildrenTokenTypes = List(ast.TestCasesTable, ast.KeywordsTable, ast.VariablesTable)
  }

  class TestCases(node: ASTNode) extends RobotPsiElement(node) with InStructureView {
    def structureViewText = "Test Cases"
    def structureViewIcon = AllIcons.Nodes.TestSourceFolder
    def structureViewChildrenTokenTypes = List(ast.TestCaseDefinition)
  }

  class Keywords(node: ASTNode) extends RobotPsiElement(node) with InStructureView {
    def structureViewText = "Keywords"
    def structureViewIcon = Icons.keywords
    def structureViewChildrenTokenTypes = List(ast.KeywordDefinition)
  }

  class Variables(node: ASTNode) extends RobotPsiElement(node) with InStructureView {
    override def structureViewText = "Variables"
    override def structureViewIcon: Icon = Icons.variables
    override def structureViewChildrenTokenTypes = List(ast.VariableDefinition)
  }
}











