package amailp.intellij.robot.extensions

import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.lexer.RobotLexer
import amailp.intellij.robot.psi.RobotPsiFile
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.lang.ParserDefinition.SpaceRequirements
import amailp.intellij.robot.parser.{PsiElementBuilder, RobotParser}
import amailp.intellij.robot.lang.RobotLanguage

class ParserDefinition extends com.intellij.lang.ParserDefinition {
  def createLexer(project: Project): Lexer = new RobotLexer

  def getWhitespaceTokens: TokenSet = RobotTokenTypes.WhitespacesTokens

  def getCommentTokens: TokenSet = RobotTokenTypes.CommentsTokens

  def getStringLiteralElements: TokenSet = RobotTokenTypes.StringLiteralElements

  def createParser(project: Project): PsiParser = RobotParser

  def getFileNodeType: IFileElementType = ParserDefinition.RobotFileElementType

  def createElement(node: ASTNode): PsiElement = new PsiElementBuilder(node).build()

  def createFile(viewProvider: FileViewProvider): PsiFile = new RobotPsiFile(viewProvider)

  def spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): SpaceRequirements =
    SpaceRequirements.MAY
}

object ParserDefinition {
  final val RobotFileElementType: IFileElementType = new IFileElementType(RobotLanguage)
}