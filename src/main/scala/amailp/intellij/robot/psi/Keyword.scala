package amailp.intellij.robot.psi

import com.intellij.psi.{PsiElement, PsiReferenceBase, PsiReference, AbstractElementManipulator}
import com.intellij.openapi.util.TextRange
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi.util.PsiTreeUtil


/**
 * An instance of a keyword when is used
 */
case class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils {
  override def utilsPsiElement: PsiElement = this

  override def getReferences: Array[PsiReference] = {
    (for {
      keywordName <- List(getText) ++ getTextStrippedFromIgnored.toList
      keywordDefinition <- findInFilesMatchingKeywordDefs(currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles, keywordName)
    } yield new KeywordReference(this, keywordDefinition).asInstanceOf[PsiReference]).toArray
  }

  private def findInFilesMatchingKeywordDefs(files: Stream[RobotPsiFile], original: String) = {
    for {
      file <- files
      keywordDefinition <- file.getKeywordDefinitions
      if keywordDefinition matches original
    } yield keywordDefinition
  }

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

class KeywordReference(keyword: Keyword, val definition: KeywordDefinition) extends PsiReferenceBase[Keyword](keyword) with RobotPsiUtils {

  override def resolve() = definition

  override def utilsPsiElement: PsiElement = getElement

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