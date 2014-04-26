package amailp.intellij.robot.structureView

import com.intellij.navigation.ItemPresentation
import javax.swing.Icon
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.ide.structureView.StructureViewTreeElement
import amailp.intellij.robot.lexer.RobotIElementType
import amailp.intellij.robot.psi.RobotPsiElement

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
      def getLocationString: String = s"AAA $structureViewText location string"
      def getIcon(unused: Boolean): Icon = structureViewIcon
    }
    def canNavigateToSource: Boolean = false
    def canNavigate: Boolean = false
    def navigate(requestFocus: Boolean): Unit = ()
    def getValue: AnyRef = InStructureView.this
  }
}
