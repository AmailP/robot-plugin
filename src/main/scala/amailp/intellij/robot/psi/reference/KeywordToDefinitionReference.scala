package amailp.intellij.robot.psi.reference

import com.intellij.psi.{PsiElementResolveResult, ResolveResult, PsiElement, PsiPolyVariantReferenceBase}
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import amailp.intellij.robot.psi.{Keyword, KeywordDefinition}
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import amailp.intellij.robot.file.Icons

class KeywordToDefinitionReference(keyword: Keyword)
    extends PsiPolyVariantReferenceBase[Keyword](keyword)
    with ExtRobotPsiUtils {

  override def handleElementRename(newElementName: String): PsiElement = getElement.setName(newElementName)

  override def getVariants = {
    val externalKeywordDefinitions =
      KeywordDefinition.findInFiles(currentRobotFile.getRecursivelyImportedRobotFiles).toSet

    val keywordNames = (externalKeywordDefinitions | KeywordDefinition.findInFile(currentRobotFile)).map(_.getName)

    val prefixedKeywords = for {
      keyword <- keywordNames
      prefix <- Keyword.ignoredPrefixes
    } yield s"$prefix $keyword"

    (
        for (keyword <- keywordNames | prefixedKeywords)
          yield LookupElementBuilder
            .create(keyword)
            .withCaseSensitivity(false)
            .withTypeText("Keyword", true)
            .withIcon(Icons.keyword)
            .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
            .asInstanceOf[AnyRef]
    ).toArray
  }

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      keywordName <- getElement.getText :: getElement.getTextStrippedFromIgnoredPrefixes
      keywordDefinition <- KeywordDefinition.findMatchingInFiles(
          currentRobotFile #:: currentRobotFile.getRecursivelyImportedRobotFiles,
          keywordName
      )
    } yield new PsiElementResolveResult(keywordDefinition)
  }.toArray

  def utilsPsiElement: PsiElement = getElement
}
