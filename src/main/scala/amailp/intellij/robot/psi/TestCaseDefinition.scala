package amailp.intellij.robot.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.ide.structureView
import com.intellij.navigation.ItemPresentation
import javax.swing.Icon
import com.intellij.ide.util.treeView.smartTree.TreeElement
import amailp.intellij.robot.ast
import com.intellij.icons.AllIcons
import com.intellij.ide.util.PsiNavigationSupport
import com.intellij.psi.tree.TokenSet

class TestCaseDefinition(node: ASTNode) extends RobotPsiElement(node) {
  override def getName = testCaseName.getText
  def testCaseName = getNode.findChildByType(ast.TestCaseName).getPsi(classOf[TestCaseName])

  def structureViewText: String = getName
  def structureViewIcon: Icon = AllIcons.Nodes.Method
  def structureViewChildrenTokenTypes = Nil
}

case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
