package amailp.intellij.robot.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import javax.swing.Icon
import amailp.intellij.robot.ast
import com.intellij.icons.AllIcons
import amailp.intellij.robot.structureView.InStructureView
import amailp.intellij.robot.file.Icons

class TestCaseDefinition(node: ASTNode) extends RobotPsiElement(node) with InStructureView {
  override def getName = testCaseName.getText
  def testCaseName = getNode.findChildByType(ast.TestCaseName).getPsi(classOf[TestCaseName])

  def structureViewText: String = getName
  def structureViewIcon = Icons.robotTest
  def structureViewChildrenTokenTypes = Nil
}

class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
