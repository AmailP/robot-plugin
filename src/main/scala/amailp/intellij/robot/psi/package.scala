package amailp.intellij.robot

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes
import scala.collection.JavaConversions._
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.impl.source.tree.LeafPsiElement
import amailp.intellij.robot.file.FileType
import amailp.intellij.robot.findUsage.UsageFindable
import javax.swing.Icon
import com.intellij.icons.AllIcons
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.ide.structureView.StructureViewTreeElement
import amailp.intellij.robot.lexer.RobotIElementType

//TODO delete or anyway reduce package object
package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node) {
    def matches(string: String) = string equalsIgnoreCase getText
    val element: PsiElement = this
  }
//  case class VariableKeywordName (node: ASTNode) extends LeafPsiElement(ast.KeywordName, node.getText) with RobotPsiUtils {
//    def utilsPsiElement = this
//    def textCaseInsensitiveExcludingVariables = {
//      val offset = getTextRange.getStartOffset
//      var result = getText
//      for {
//        variable <- variables.sortWith((v1, v2) => v1.getTextRange.getStartOffset > v2.getTextRange.getStartOffset)
//        relativeTextRange = variable.getTextRange.shiftRight(-offset)
//      } result = relativeTextRange.replace(result, ".*")
//      s"(?i)$result"
//    }
//    def variables = getNode.getChildren(TokenSet.create(RobotTokenTypes.Variable))
//    def matches(string: String) = string matches textCaseInsensitiveExcludingVariables
//  }

  case class KeywordDefinition (node: ASTNode) extends RobotPsiElement(node) with PsiNamedElement with UsageFindable {
    private def keywordName = getNode.findChildByType(ast.KeywordName).getPsi(classOf[KeywordName])
    override def getNodeText(useFullName: Boolean) = getName
    override def getName: String = keywordName.getText
    override def setName(name: String): PsiElement = {
      val fileContent =
        s"""
          |*** Keywords ***
          |$name
        """.stripMargin
      val dummyFile = PsiFileFactory.getInstance(getProject).createFileFromText("dummy", FileType, fileContent).asInstanceOf[RobotPsiFile]
      val dummyKeyword = KeywordDefinition.findInFile(dummyFile).head
      this.getNode.replaceChild(keywordName.getNode, dummyKeyword.keywordName.getNode)
      this
    }
    override val element: PsiElement = this

    def getType: String = "Keyword definition"
    def getDescriptiveName: String = getName

    def structureViewText = getName
    def structureViewIcon = AllIcons.Nodes.TestSourceFolder
    def structureViewChildrenTokenTypes = Nil
  }

  object KeywordDefinition {
    def findMatchingInFiles(files: Stream[RobotPsiFile], reference: String) = {
      for {
        keywordDefinition <- findInFiles(files)
        if keywordDefinition.keywordName matches reference
      } yield keywordDefinition
    }

    def findInFiles(files: Stream[RobotPsiFile]) = {
      for {
        file <- files
        keywordDefinition <- findInFile(file)
      } yield keywordDefinition
    }

    def findInFile(file: RobotPsiFile) = PsiTreeUtil.findChildrenOfType(file.getNode.getPsi, classOf[KeywordDefinition]).toSet
  }

  trait ExtRobotPsiUtils {
    def utilsPsiElement: PsiElement
    def currentRobotFile = PsiTreeUtil.getParentOfType(utilsPsiElement, classOf[RobotPsiFile])
    def currentFile = currentRobotFile.getVirtualFile
    def currentDirectory = currentFile.getParent
    def psiManager = PsiManager.getInstance(utilsPsiElement.getProject)
  }

  trait RobotPsiUtils extends PsiElement with ExtRobotPsiUtils {
    def utilsPsiElement = this
  }

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


  class Tables(node: ASTNode) extends RobotPsiElement(node) {
    def structureViewText: String = "AAA Tables structure view text"
    def structureViewIcon: Icon = null
    def structureViewChildrenTokenTypes = List(ast.TestCasesTable, ast.KeywordsTable)
  }

  class TestCases(node: ASTNode) extends RobotPsiElement(node) {
    def structureViewText = "Test Cases"
    def structureViewIcon = AllIcons.Nodes.TestSourceFolder
    def structureViewChildrenTokenTypes = List(ast.TestCaseDefinition)
  }

  class Keywords(node: ASTNode) extends RobotPsiElement(node) {
    def structureViewText = "Keywords"
    def structureViewIcon = AllIcons.Nodes.TestSourceFolder
    def structureViewChildrenTokenTypes = List(ast.KeywordDefinition)
  }
}
