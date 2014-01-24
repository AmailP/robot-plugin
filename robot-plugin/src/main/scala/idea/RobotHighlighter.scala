package idea

import com.intellij.openapi.fileTypes.{SyntaxHighlighter, SyntaxHighlighterFactory, SyntaxHighlighterBase}
import com.intellij.lexer.Lexer
import amailp.lexer.RobotLexer
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import amailp.elements.RobotTokenTypes

class RobotHighlighter extends SyntaxHighlighterBase {
  def getHighlightingLexer: Lexer = new RobotLexer()

  def getTokenHighlights(tokenType: IElementType): Array[TextAttributesKey] = {
    if (RobotTokenTypes.HeaderTokens.contains(tokenType))
      Array(TextAttributesKey.createTextAttributesKey("Header",
        DefaultLanguageHighlighterColors.NUMBER))
    else if(tokenType == RobotTokenTypes.Variable)
      Array(TextAttributesKey.createTextAttributesKey("Variable",
        DefaultLanguageHighlighterColors.STRING))
    else if(tokenType == RobotTokenTypes.TestCaseSetting)
      Array(TextAttributesKey.createTextAttributesKey("TestCaseSetting",
        DefaultLanguageHighlighterColors.INSTANCE_FIELD))
    else if(tokenType == RobotTokenTypes.BadCharacter)
      Array(TextAttributesKey.createTextAttributesKey("BadCharacter",
        com.intellij.openapi.editor.HighlighterColors.BAD_CHARACTER))
    else
      Array()
  }
}

class RobotHighlighterFactory extends SyntaxHighlighterFactory {
  def getSyntaxHighlighter(project: Project, virtualFile: VirtualFile): SyntaxHighlighter = new RobotHighlighter
}