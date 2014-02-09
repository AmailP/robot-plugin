package amailp.psi

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.{PsiReference, AbstractElementManipulator}
import com.intellij.openapi.util.TextRange
import scala.collection.JavaConversions._
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement

case class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override lazy val getReference: PsiReference = new KeywordReference(this)
}

class KeywordReference(element: Keyword) extends RobotReferenceBase[Keyword](element) {

  def sameTextAsKeyword(keywordName: KeywordName) = keywordName.getText == element.getText

  override def resolve() = {
    fileDefinedKeywordNames find sameTextAsKeyword match {
      case Some(keyword) => keyword.getParent
      case None => currentRobotFile.getRecursivelyImportedRobotFiles
          .flatMap(_.getDefinedKeywordNames)
          .find(sameTextAsKeyword) match {
          case Some(keyword) => keyword.getParent
          case None => null
        }
    }
  }

  override def getVariants: Array[AnyRef] = fileDefinedKeywordNames.map(_.getText).toArray

  private def fileDefinedKeywordNames = {
    PsiTreeUtil findChildrenOfType(currentRobotFile, classOf[KeywordName])
  }
}

class KeywordManipulator extends AbstractElementManipulator[Keyword] {
  override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
}