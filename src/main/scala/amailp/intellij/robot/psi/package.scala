package amailp.intellij.robot

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import javax.swing.Icon
import com.intellij.icons.AllIcons
import amailp.intellij.robot.structureView.InStructureView
import amailp.intellij.robot.file.Icons

package object psi {
  class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)

  class Tables(node: ASTNode) extends RobotPsiElement(node) with InStructureView {
    def structureViewText: String = "AAA Tables structure view text"
    def structureViewIcon: Icon = null
    def structureViewChildrenTokenTypes = List(ast.TestCasesTable, ast.KeywordsTable)
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
}
