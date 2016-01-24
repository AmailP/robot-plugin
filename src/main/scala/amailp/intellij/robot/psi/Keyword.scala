package amailp.intellij.robot.psi

import com.intellij.psi._
import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import amailp.intellij.robot.findUsage.UsageFindable
import amailp.intellij.robot.psi.reference.KeywordToDefinitionReference
import amailp.intellij.robot.psi.utils.RobotPsiUtils


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
  def setNewName(name: String): PsiElement = {
    val dummyKeyword = createKeyword(name)
    this.getNode.getTreeParent.replaceChild(this.getNode, dummyKeyword.getNode)
    this
  }

  def getType: String = "Keyword"
  def getDescriptiveName: String = getNode.getText
}

object Keyword {
  val ignoredPrefixes = List("Given", "When", "Then", "And")
}



