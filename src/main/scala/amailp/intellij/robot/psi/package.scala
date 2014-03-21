package amailp.intellij.robot


import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes

package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node) {
    def regExp = {
      val offset = getTextRange.getStartOffset
      var result = getText
      for {
        variable <- variables.sortWith((v1, v2) => v1.getTextRange.getStartOffset > v2.getTextRange.getStartOffset)
        relativeTextRange = variable.getTextRange.shiftRight(-offset)
      } result = relativeTextRange.replace(result, ".*")
      result
    }
    def variables = getNode.getChildren(TokenSet.create(RobotTokenTypes.Variable))
    def matches(string: String) = string matches regExp
  }
  case class KeywordDefinition (node: ASTNode) extends ASTWrapperPsiElement(node) {
    def name: String = keywordName.getText
    def matches(string: String) = keywordName matches string

    private def keywordName = getNode.findChildByType(parser.KeywordName).getPsi(classOf[KeywordName])
  }

  trait RobotPsiUtils {
    def utilsPsiElement: PsiElement
    def currentRobotFile = PsiTreeUtil.getParentOfType(utilsPsiElement, classOf[RobotPsiFile])
    def currentFile = currentRobotFile.getVirtualFile
    def currentDirectory = currentFile.getParent
    def psiManager = PsiManager.getInstance(utilsPsiElement.getProject)
  }
}
