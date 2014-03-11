package amailp.intellij.robot.psi

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.{PsiElement, PsiReference, AbstractElementManipulator}
import com.intellij.openapi.util.TextRange
import scala.collection.JavaConversions._
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement

/**
 * An instance of a keyword when is used
 */
case class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override lazy val getReference: PsiReference = new KeywordReference(this)

  lazy val getTextStrippedFromIgnored = (for {
    prefix <- List("given", "when", "then", "and")
    if getText.toLowerCase.startsWith(prefix)
    stripped = getText.toLowerCase.replaceFirst(prefix, "").trim
  } yield stripped).headOption
}

class KeywordReference(element: Keyword) extends RobotReferenceBase[Keyword](element) {

  def sameTextAsKeyword(keywordName: KeywordName) = keywordName.getText equalsIgnoreCase element.getText

  private def findKeywordNamesInRobotFiles(files: Stream[RobotPsiFile], original: String = element.getText) = {
    for {
      file <- files
      fileKeywordName <- file.getDefinedKeywordNames
      if fileKeywordName.getText equalsIgnoreCase original
    } yield fileKeywordName
  }

  override def resolve() = {
    findKeywordNamesInRobotFiles(currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles).headOption match {
      case Some(keyword) => keyword
      case None => element.getTextStrippedFromIgnored match {
        case Some(strippedKeywordName) => findKeywordNamesInRobotFiles(currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles, strippedKeywordName).headOption match {
          case Some(keyword) => keyword
          case None => null
        }
        case None => null
      }
    }
  }

  override def getVariants: Array[AnyRef] = {
    val externalKeywordNames: Set[KeywordName] = for {
      robotFile: RobotPsiFile <- currentRobotFile.getRecursivelyImportedRobotFiles.toSet
      externalKeywordName: KeywordName <- robotFile.getDefinedKeywordNames
    } yield externalKeywordName

    for {
      keywordName <- (externalKeywordNames | fileDefinedKeywordNames.toSet).toArray
    } yield keywordName.getText
  }

  private def fileDefinedKeywordNames = {
    findKeywordNamesInRobotFiles(Stream(currentRobotFile))
  }
}

class KeywordManipulator extends AbstractElementManipulator[Keyword] {
  override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
}