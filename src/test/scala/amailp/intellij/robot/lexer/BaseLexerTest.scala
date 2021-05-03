package amailp.intellij.robot.lexer

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import com.intellij.psi.tree.IElementType
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BaseLexerTest extends AnyFunSuite with BeforeAndAfter {
  var robotLexer: RobotLexer = null

  before {
    robotLexer = new RobotLexer
  }

  after {
    def checkAllWasLexed() = assert(robotLexer.getTokenStart == robotLexer.getTokenEnd, "Something was left unlexed")
    checkAllWasLexed()
  }

  def scanString(toBeScanned: String): Unit = {
    robotLexer.start(toBeScanned)
  }

  def nextTokenIsType(elementType: IElementType): Unit = {
    assert(elementType == robotLexer.getTokenType)
    robotLexer.advance()
  }

  def nextTokenIs(text: String, elementType: IElementType): Unit = {
    assert(text == robotLexer.getTokenText)
    nextTokenIsType(elementType)
  }
}
