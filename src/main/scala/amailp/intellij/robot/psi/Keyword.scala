package amailp.intellij.robot.psi

import com.intellij.psi._
import com.intellij.openapi.util.TextRange
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}


/**
 * An instance of a keyword when is used
 */
case class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils {
  override def utilsPsiElement: PsiElement = this

  override def getReference = new KeywordToDefinitionReference(this)

  def getTextStrippedFromIgnored = {
    for {
      prefix <- Keyword.ignoredPrefixes
      loweredPrefix = prefix.toLowerCase
      if getText.toLowerCase.startsWith(loweredPrefix)
      stripped = getText.toLowerCase.replaceFirst(loweredPrefix, "").trim
    } yield stripped
  }.headOption
}

object Keyword {
  val ignoredPrefixes = List("Given", "When", "Then", "And")
}

class KeywordToDefinitionReference(keyword: Keyword)
  extends PsiPolyVariantReferenceBase[Keyword](keyword)
  with RobotPsiUtils {

  override def utilsPsiElement: PsiElement = getElement

  override def getVariants = {
    val externalKeywordDefinitions = KeywordDefinition.findInFiles(currentRobotFile.getRecursivelyImportedRobotFiles).toSet

    val keywordNames = (externalKeywordDefinitions | KeywordDefinition.findInFile(currentRobotFile)).map(_.getName)

    val prefixedKeywords = for {
      keyword <- keywordNames
      prefix <- Keyword.ignoredPrefixes
    } yield s"$prefix $keyword"

    (
      for (
        keyword <- keywordNames | prefixedKeywords
      ) yield LookupElementBuilder.create(keyword)
        .withCaseSensitivity(false)
        .withTypeText("Keyword", true)
        .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
        .asInstanceOf[AnyRef]
    ).toArray
  }

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      keywordName <- List(getElement.getText) ++ getElement.getTextStrippedFromIgnored.toList
      keywordDefinition <- KeywordDefinition.findMatchingInFiles(currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles, keywordName)
    } yield new PsiElementResolveResult(keywordDefinition)
  }.toArray
}

class KeywordManipulator extends AbstractElementManipulator[Keyword] {
  override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
}