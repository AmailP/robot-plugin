package amailp.intellij.robot.highlighting

import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.lexer.Lexer
import amailp.intellij.robot.lexer.RobotLexer
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.colors.TextAttributesKey
import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors._
import com.intellij.openapi.editor.HighlighterColors._

class SyntaxHighlighter extends SyntaxHighlighterBase {
  def getHighlightingLexer: Lexer = new RobotLexer()

  def getTokenHighlights(tokenType: IElementType): Array[TextAttributesKey] = {

    def arrayOfAttributesKeys(fallbackKey: TextAttributesKey) = {
      Array(TextAttributesKey.createTextAttributesKey(tokenType.toString, fallbackKey))
    }

    tokenType match {
      case token if HeaderTokens.contains(token) => arrayOfAttributesKeys(METADATA)
      case ScalarVariable | ListVariable | DictionaryVariable => arrayOfAttributesKeys(STRING)
      case TestCaseSetting => arrayOfAttributesKeys(INSTANCE_FIELD)
      case Comment => arrayOfAttributesKeys(LINE_COMMENT)
      case BadCharacter => arrayOfAttributesKeys(BAD_CHARACTER)
      case _ =>  Array()
    }
  }
}
