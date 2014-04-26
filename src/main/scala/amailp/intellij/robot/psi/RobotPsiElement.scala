package amailp.intellij.robot.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.TokenSet
import scala.collection.JavaConversions._
import com.intellij.navigation.ItemPresentation
import javax.swing.Icon
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.ide.structureView.StructureViewTreeElement
import amailp.intellij.robot.lexer.RobotIElementType

abstract class RobotPsiElement(node: ASTNode) extends ASTWrapperPsiElement(node) {

  def structureViewText: String
  def structureViewIcon: Icon
  def structureViewChildrenTokenTypes: List[RobotIElementType]

  val structureTreeElement: StructureViewTreeElement = new StructureViewTreeElement {
    def getChildren: Array[TreeElement] =
      findChildrenByType[RobotPsiElement](TokenSet.create(structureViewChildrenTokenTypes: _*)).map(_.structureTreeElement).toArray
    def getPresentation: ItemPresentation = new ItemPresentation {
      def getPresentableText: String = structureViewText
      def getLocationString: String = s"AAA $structureViewText location string"
      def getIcon(unused: Boolean): Icon = structureViewIcon
    }
    def canNavigateToSource: Boolean = false
    def canNavigate: Boolean = false
    def navigate(requestFocus: Boolean): Unit = ()
    def getValue: AnyRef = RobotPsiElement.this
  }
}
