package amailp.intellij.robot

import amailp.intellij.robot.file.Icons
import amailp.intellij.robot.structureView.InStructureView
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.psi.util.QualifiedName
import javax.swing.Icon

package object psi {
  class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  class Variable(node: ASTNode) extends RobotPsiElement(node)

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

  trait Library {
    def getName: String
    def getRobotName: String
    def getQualifiedName: QualifiedName
  }

  object BuiltInLibrary extends Library {
    def getName: String = "BuiltIn"
    def getRobotName: String = getName
    def getQualifiedName: QualifiedName = QualifiedName.fromDottedString(getName)
  }
}
