package amailp.intellij.robot.psi

import com.intellij.psi._
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.intellij.robot.findUsage.UsageFindable
import amailp.intellij.robot.psi.reference.KeywordToDefinitionReference
import amailp.intellij.robot.psi.utils.RobotPsiUtils
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

/**
 * An instance of a keyword when is used
 */
class Keyword(node: ASTNode)
    extends ASTWrapperPsiElement(node)
    with RobotPsiUtils
    with UsageFindable
    with PsiNameIdentifierOwner {

  override def getReferences: Array[PsiReference] =
    Array[PsiReference](new KeywordToDefinitionReference(this)) ++
      ReferenceProvidersRegistry.getReferencesFromProviders(this)

  def getTextStrippedFromIgnoredPrefixes = {
    val textLowerCase = getText.toLowerCase
    for {
      prefix <- Keyword.lowerCaseIgnoredPrefixes
      if textLowerCase.startsWith(prefix)
      stripped = textLowerCase.replaceFirst(prefix, "").trim
    } yield stripped
  } ensuring { _.size < 2 }

  override def setName(name: String): PsiElement = {
    val dummyKeyword = createKeyword(name)
    this.getNode.getTreeParent.replaceChild(this.getNode, dummyKeyword.getNode)
    this
  }

  def getType: String = "Keyword"
  def getDescriptiveName: String = getNode.getText
  override def getNameIdentifier: PsiElement = this
}

object Keyword {
  val ignoredPrefixes = List("Given", "When", "Then", "And")
  val lowerCaseIgnoredPrefixes = ignoredPrefixes.map(_.toLowerCase)
}
