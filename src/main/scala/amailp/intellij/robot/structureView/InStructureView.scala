package amailp.intellij.robot.structureView

import com.intellij.navigation.ItemPresentation
import javax.swing.Icon
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.ide.structureView.StructureViewTreeElement
import amailp.intellij.robot.lexer.RobotIElementType
import amailp.intellij.robot.psi.RobotPsiElement
import com.intellij.ide.util.PsiNavigationSupport
import com.intellij.pom.Navigatable

trait InStructureView extends RobotPsiElement {
  def structureViewText: String
  def structureViewIcon: Icon
  def structureViewChildrenTokenTypes: List[RobotIElementType]

  val structureTreeElement: StructureViewTreeElement = new StructureViewTreeElement {
    def getChildren: Array[TreeElement] = {
      for {
        //TODO Use PsiTreeUtil and get rid of intermediate class RobotPsiElement
        child <- findChildrenByType[InStructureView](structureViewChildrenTokenTypes)
      } yield child.structureTreeElement
    }.toArray
    def getPresentation: ItemPresentation = new ItemPresentation {
      def getPresentableText: String = structureViewText
      def getLocationString: String = null
      def getIcon(unused: Boolean): Icon = structureViewIcon
    }
    def canNavigateToSource: Boolean = true
    def canNavigate: Boolean = getNavigatable.canNavigate
    def navigate(requestFocus: Boolean): Unit = getNavigatable.navigate(requestFocus)
    private def getNavigatable: Navigatable = PsiNavigationSupport.getInstance().getDescriptor(InStructureView.this)

    def getValue: AnyRef = InStructureView.this
  }
}
