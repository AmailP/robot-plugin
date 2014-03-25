package amailp.intellij.robot.idea

import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.lang.cacheBuilder.{DefaultWordsScanner, WordsScanner}
import com.intellij.psi.PsiElement
import amailp.intellij.robot.lexer.RobotLexer
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi.{KeywordName, KeywordDefinition, Keyword}

class RobotFindUsagesProvider extends FindUsagesProvider {
  override def getNodeText(element: PsiElement, useFullName: Boolean): String = element.asInstanceOf[UsageFindable].getNodeText(useFullName)
  override def getDescriptiveName(element: PsiElement): String = "AAA Descriptive Name"
  override def getType(element: PsiElement): String = element.asInstanceOf[UsageFindable].getType
  override def getHelpId(element: PsiElement): String = element.asInstanceOf[UsageFindable].getHelpId
  override def canFindUsagesFor(element: PsiElement): Boolean = element.isInstanceOf[UsageFindable]
  override def getWordsScanner: WordsScanner = new RobotWordsScanner
}

class RobotWordsScanner extends DefaultWordsScanner(new RobotLexer,
  TokenSet.create(RobotTokenTypes.Word, RobotTokenTypes.Space, RobotTokenTypes.Variable),
  RobotTokenTypes.CommentsTokens, TokenSet.EMPTY )

trait UsageFindable {
  val element: PsiElement
  def getNodeText(useFullName: Boolean): String = element.getText
  def getDescriptiveName: String = "AAA Descriptive Name"
  def getType: String = "AAA Type"
  def getHelpId: String = "AAA getHelpId"
}
