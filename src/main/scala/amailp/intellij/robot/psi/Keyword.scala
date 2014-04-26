package amailp.intellij.robot.psi

import com.intellij.psi._
import com.intellij.openapi.util.TextRange
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi.util.PsiTreeUtil
import scala.collection.JavaConversions._
import amailp.intellij.robot.file.FileType
import amailp.intellij.robot.findUsage.UsageFindable


/**
 * An instance of a keyword when is used
 */
class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils with UsageFindable {

  override def getReference = new KeywordToDefinitionReference(this)

  def getTextStrippedFromIgnored = {
    for {
      prefix <- Keyword.ignoredPrefixes
      loweredPrefix = prefix.toLowerCase
      if getText.toLowerCase.startsWith(loweredPrefix)
      stripped = getText.toLowerCase.replaceFirst(loweredPrefix, "").trim
    } yield stripped
  }.headOption
  override val element: PsiElement = this
  def setNewName(name: String): PsiElement = {
    //TODO factorize with KeyDef one
    val fileContent =
      s"""
          |*** Keywords ***
          |KeyDef
          |    $name
        """.stripMargin
    val dummyFile = PsiFileFactory.getInstance(getProject).createFileFromText("dummy", FileType, fileContent).asInstanceOf[RobotPsiFile]
    val dummyKeyword= PsiTreeUtil.findChildrenOfType(dummyFile.getNode.getPsi, classOf[Keyword]).head
    this.getNode.getTreeParent.replaceChild(this.getNode, dummyKeyword.getNode)
    this
  }

  def getType: String = "Keyword"
  def getDescriptiveName: String = getNode.getText
}

object Keyword {
  val ignoredPrefixes = List("Given", "When", "Then", "And")
}

class KeywordToDefinitionReference(keyword: Keyword)
  extends PsiPolyVariantReferenceBase[Keyword](keyword) with ExtRobotPsiUtils {

  override def handleElementRename(newElementName: String): PsiElement = getElement.setNewName(newElementName)

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

  def utilsPsiElement: PsiElement = getElement
}

class KeywordManipulator extends AbstractElementManipulator[Keyword] {
  override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
}