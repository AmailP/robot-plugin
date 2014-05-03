package amailp.intellij.robot.lexer

import amailp.intellij.robot.elements.RobotTokenTypes

/**
 * Created by virva on 04/05/2014.
 */
class TestHeaders extends BaseLexerTest {
  test("Header") {
    scanString("*** Setting ***")
    nextTokenIsType(RobotTokenTypes.SettingsHeader)
  }

  test("HeaderPlural") {
    scanString("*** Settings ***")
    nextTokenIsType(RobotTokenTypes.SettingsHeader)
  }

test("LineBeforeHeader") {
    scanString("\n*** Test Cases ***")
    nextTokenIsType(RobotTokenTypes.BlankLine)
    nextTokenIsType(RobotTokenTypes.TestCasesHeader)
  }

test("LineBetweenHeaders") {
    scanString("*** Settings ***\n*** Setting ***")
    nextTokenIsType(RobotTokenTypes.SettingsHeader)
    nextTokenIsType(RobotTokenTypes.LineTerminator)
    nextTokenIsType(RobotTokenTypes.SettingsHeader)
  }
}
