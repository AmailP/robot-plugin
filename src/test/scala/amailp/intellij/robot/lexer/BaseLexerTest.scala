package amailp.intellij.robot.lexer

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import com.intellij.psi.tree.IElementType

class BaseLexerTest extends FunSuite with BeforeAndAfter {
  var robotLexer: RobotLexer = null

  before {
    robotLexer = new RobotLexer
  }

  after {
    def checkAllWasLexed() = assert(robotLexer.getTokenStart == robotLexer.getTokenEnd, "Something was left unlexed")
    checkAllWasLexed()
  }

  def scanString(toBeScanned: String) {
    robotLexer.start(toBeScanned)
  }

  def nextTokenIsType(elementType: IElementType) {
    assert(elementType == robotLexer.getTokenType)
    robotLexer.advance()
  }

  def nextTokenIs(text: String, elementType: IElementType) {
    assert(text == robotLexer.getTokenText)
    nextTokenIsType(elementType)
  }
}
