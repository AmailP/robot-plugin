package amailp

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.util.PsiTreeUtil
import scala.collection.JavaConversions._
import com.intellij.openapi.util.TextRange
import scala.Some

package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node)

  case class Keyword (node: ASTNode) extends ASTWrapperPsiElement(node) {
    override lazy val getReference: PsiReference = new KeywordReference(this)
  }

  class KeywordReference(element: Keyword) extends PsiReferenceBase[Keyword](element: Keyword){
    override def resolve(): PsiElement = {
      val textToBeFound = element.getText
      fileDefinedKeywordNames find ( _.getText == textToBeFound ) match {
        case Some(keyword) => keyword.getParent
        case None => null
      }
    }

    override def getVariants: Array[AnyRef] = fileDefinedKeywordNames.map(_.getText).toArray

    private def fileDefinedKeywordNames = {
      val currentFile: RobotFile = PsiTreeUtil.getParentOfType(element, classOf[RobotFile])
      PsiTreeUtil findChildrenOfType (currentFile, classOf[KeywordName])
    }
  }

  class KeywordManipulator extends AbstractElementManipulator[Keyword]{
    override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
  }

  object Settings {
    val names = Set[String]("Library", "Resource", "Variables", "Documentation", "Metadata", "Suite Setup",
      "Suite Teardown", "Suite Precondition", "Suite Postcondition", "Force Tags", "Default Tags", "Test Setup",
      "Test Teardown", "Test Precondition", "Test Postcondition", "Test Template", "Test Timeout")
  }
}
