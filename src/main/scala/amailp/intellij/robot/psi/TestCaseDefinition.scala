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

class TestCaseDefinition(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override def getName = testCaseName.getText
  def testCaseName = getNode.findChildByType(ast.TestCaseName).getPsi(classOf[TestCaseName])
  val structureViewTreeElement = new StructureViewTreeElement
  class StructureViewTreeElement extends structureView.StructureViewTreeElement {
    def getValue: AnyRef = TestCaseDefinition.this
    def getPresentation: ItemPresentation = new ItemPresentation {
      def getPresentableText: String = TestCaseDefinition.this.getName
      def getLocationString: String = null
      def getIcon(unused: Boolean): Icon = AllIcons.Nodes.Method
    }
    def getChildren: Array[TreeElement] = TreeElement.EMPTY_ARRAY
    def canNavigateToSource: Boolean = canNavigate
    def canNavigate: Boolean = PsiNavigationSupport.getInstance().canNavigate(TestCaseDefinition.this)
    def navigate(requestFocus: Boolean): Unit = PsiNavigationSupport.getInstance().getDescriptor(TestCaseDefinition.this).navigate(requestFocus)
  }
}

case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
