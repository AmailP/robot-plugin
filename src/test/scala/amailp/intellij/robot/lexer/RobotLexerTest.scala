package amailp.intellij.robot.lexer

import java.util.Scanner;

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import com.intellij.psi.tree.IElementType

import amailp.intellij.robot.elements.RobotTokenTypes

class _BaseLexerTest extends FunSuite with BeforeAndAfter {
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

class RobotLexerTest extends _BaseLexerTest {
  test("Variable") {
    scanString("${a_Variable}")
    nextTokenIsType(RobotTokenTypes.Variable)
  }

  test("ListVariable") {
    scanString("@{aListVariable}")
    nextTokenIsType(RobotTokenTypes.ListVariable)
  }

  test("Word") {
    scanString("ThisIsAWord")
    nextTokenIsType(RobotTokenTypes.Word)
  }

  test("WordWithSymbols") {
    scanString("!IsThisAWord??.")
    nextTokenIsType(RobotTokenTypes.Word)
  }

  test("testCell") {
    scanString("    A cell")
    nextTokenIsType(RobotTokenTypes.Separator)
    nextTokenIs("A", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("cell", RobotTokenTypes.Word)
  }

  test("VariableCell") {
    scanString("  This ${is} cell  \n")
    nextTokenIsType(RobotTokenTypes.Separator)
    nextTokenIs("This", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("${is}", RobotTokenTypes.Variable)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("cell", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.IrrelevantSpaces)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
  }

  test("Comment") {
    scanString("  #This is comment  ")
    nextTokenIsType(RobotTokenTypes.IrrelevantSpaces)
    nextTokenIs("#This is comment  ", RobotTokenTypes.Comment)
  }

  test("BlankLine") {
    scanString("   \n")
    nextTokenIsType(RobotTokenTypes.BlankLine)
  }

  test("Ellipsis") {
    scanString(" ...\n")
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIsType(RobotTokenTypes.Ellipsis)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
  }

  test("All") {
    val s: String = new Scanner(this.getClass.getClassLoader.getResourceAsStream("complete.robot")).useDelimiter("\\A").next
    scanString(s)
    while (robotLexer.getTokenType != null) {
      System.out.println(robotLexer.getTokenType + "\t\t" + robotLexer.getTokenText)
      robotLexer.advance
    }
  }
}
