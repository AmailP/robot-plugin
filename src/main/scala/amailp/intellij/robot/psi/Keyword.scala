package amailp.intellij.robot.psi

import com.intellij.psi.{PsiReference, AbstractElementManipulator}
import com.intellij.openapi.util.TextRange
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}


/**
 * An instance of a keyword when is used
 */
case class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override lazy val getReference: PsiReference = new KeywordReference(this)

  lazy val getTextStrippedFromIgnored = (for {
    prefix <- Keyword.ignoredPrefixes
    loweredPrefix = prefix.toLowerCase
    if getText.toLowerCase.startsWith(loweredPrefix)
    stripped = getText.toLowerCase.replaceFirst(loweredPrefix, "").trim
  } yield stripped).headOption
}

object Keyword {
  val ignoredPrefixes = List("Given", "When", "Then", "And")
}

class KeywordReference(element: Keyword) extends RobotReferenceBase[Keyword](element) {

  private def findInFilesMatchingKeywordDefs(files: Stream[RobotPsiFile], original: String = element.getText) = {
    for {
      file <- files
      keywordDefinition <- file.getKeywordDefinitions
      if keywordDefinition matches original
    } yield keywordDefinition
  }

  override def resolve() = {
    findInFilesMatchingKeywordDefs(currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles).headOption match {
      case Some(keyword) => keyword
      case None => element.getTextStrippedFromIgnored match {
        case Some(strippedKeywordName) => findInFilesMatchingKeywordDefs(currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles, strippedKeywordName).headOption match {
          case Some(keyword) => keyword
          case None => null
        }
        case None => null
      }
    }
  }

  override def getVariants = {
    val externalKeywordDefinitions: Set[KeywordDefinition] = for {
      robotFile: RobotPsiFile <- currentRobotFile.getRecursivelyImportedRobotFiles.toSet
      externalKeywordName: KeywordDefinition <- robotFile.getKeywordDefinitions
    } yield externalKeywordName

    val keywordNames = (externalKeywordDefinitions | currentRobotFile.getKeywordDefinitions).map(_.name)

    val prefixedKeywords = for {
      keyword <- keywordNames
      prefix <- Keyword.ignoredPrefixes
    } yield s"$prefix $keyword"

    for (
      keyword <- (keywordNames | prefixedKeywords).toArray
    ) yield LookupElementBuilder.create(keyword)
      .withCaseSensitivity(false)
      .withTypeText("Keyword", true)
      .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      .asInstanceOf[AnyRef]
  }
}

class KeywordManipulator extends AbstractElementManipulator[Keyword] {
  override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
}