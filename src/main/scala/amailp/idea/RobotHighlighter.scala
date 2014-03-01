package amailp.idea

import com.intellij.openapi.fileTypes.{SyntaxHighlighter, SyntaxHighlighterFactory, SyntaxHighlighterBase}
import com.intellij.lexer.Lexer
import amailp.lexer.RobotLexer
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors._
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import amailp.elements.RobotTokenTypes._
import com.intellij.openapi.editor.HighlighterColors._

class RobotHighlighter extends SyntaxHighlighterBase {
  def getHighlightingLexer: Lexer = new RobotLexer()

  def getTokenHighlights(tokenType: IElementType): Array[TextAttributesKey] = {

    def arrayOfAttributesKeys(fallbackKey: TextAttributesKey) = {
      Array(TextAttributesKey.createTextAttributesKey(tokenType.toString, fallbackKey))
    }

    tokenType match {
      case token if HeaderTokens.contains(token) => arrayOfAttributesKeys(METADATA)
      case Variable => arrayOfAttributesKeys(STRING)
      case TestCaseSetting => arrayOfAttributesKeys(INSTANCE_FIELD)
      case Comment => arrayOfAttributesKeys(LINE_COMMENT)
      case BadCharacter => arrayOfAttributesKeys(BAD_CHARACTER)
      case _ =>  Array()
    }
  }
}

class RobotHighlighterFactory extends SyntaxHighlighterFactory {
  def getSyntaxHighlighter(project: Project, virtualFile: VirtualFile): SyntaxHighlighter = new RobotHighlighter
}