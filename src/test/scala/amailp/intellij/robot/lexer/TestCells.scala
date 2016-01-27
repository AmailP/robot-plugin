package amailp.intellij.robot.lexer

import java.util.Scanner

import amailp.intellij.robot.elements.RobotTokenTypes
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class TestCells extends BaseLexerTest {
  test("ScalarVariable") {
    scanString("${a_Variable}")
    nextTokenIsType(RobotTokenTypes.ScalarVariable)
  }

  test("ListVariable") {
    scanString("@{aListVariable}")
    nextTokenIsType(RobotTokenTypes.ListVariable)
  }

  test("EnvironmentVariable") {
    scanString("%{anEnvVariable}")
    nextTokenIsType(RobotTokenTypes.EnvironmentVariable)
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
    nextTokenIs("${is}", RobotTokenTypes.ScalarVariable)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("cell", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.IrrelevantSpaces)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
  }

  test("VariableQuotedCell") {
    scanString("  This '${is}' cell  \n")
    nextTokenIsType(RobotTokenTypes.Separator)
    nextTokenIs("This", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("'", RobotTokenTypes.Word)
    nextTokenIs("${is}", RobotTokenTypes.ScalarVariable)
    nextTokenIs("'", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("cell", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.IrrelevantSpaces)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
  }

  test("NonVariableCell") {
    scanString("  This $ {is} notCell  \n")
    nextTokenIsType(RobotTokenTypes.Separator)
    nextTokenIs("This", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("$", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("{is}", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("notCell", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.IrrelevantSpaces)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
  }

  test("NotEndedVariableCell") {
    scanString("This ${is notCell\n")
    nextTokenIs("This", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("$", RobotTokenTypes.Word)
    nextTokenIs("{is", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.Space)
    nextTokenIs("notCell", RobotTokenTypes.Word)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
  }

  test("ListVariableCell") {
    scanString("@{hello}")
    nextTokenIs("@{hello}", RobotTokenTypes.ListVariable)
  }

  test("DictionaryVariableCell") {
    scanString("&{hello}")
    nextTokenIs("&{hello}", RobotTokenTypes.DictionaryVariable)
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
      robotLexer.advance()
    }
  }
}
