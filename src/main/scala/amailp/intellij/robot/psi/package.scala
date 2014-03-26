package amailp.intellij.robot


import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes
import scala.collection.JavaConversions._

package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node) {
    def textCaseInsensitiveExcludingVariables = {
      val offset = getTextRange.getStartOffset
      var result = getText
      for {
        variable <- variables.sortWith((v1, v2) => v1.getTextRange.getStartOffset > v2.getTextRange.getStartOffset)
        relativeTextRange = variable.getTextRange.shiftRight(-offset)
      } result = relativeTextRange.replace(result, ".*")
      s"(?i)$result"
    }
    def variables = getNode.getChildren(TokenSet.create(RobotTokenTypes.Variable))
    def matches(string: String) = string matches textCaseInsensitiveExcludingVariables
  }
  case class KeywordDefinition (node: ASTNode) extends ASTWrapperPsiElement(node) with PsiNamedElement {
    override def getName: String = keywordName.getText
    def keywordName = getNode.findChildByType(parser.KeywordName).getPsi(classOf[KeywordName])
    def setName(name: String): PsiElement = ???
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

  trait RobotPsiUtils {
    def utilsPsiElement: PsiElement
    def currentRobotFile = PsiTreeUtil.getParentOfType(utilsPsiElement, classOf[RobotPsiFile])
    def currentFile = currentRobotFile.getVirtualFile
    def currentDirectory = currentFile.getParent
    def psiManager = PsiManager.getInstance(utilsPsiElement.getProject)
  }
}
