package amailp.intellij.robot

import amailp.intellij.robot.lexer.RobotIElementType
import com.intellij.psi.tree.TokenSet

package object ast {
  object Tables extends RobotIElementType("Tables")

  object SettingsTable extends RobotIElementType("SettingsTable")
  object Setting extends RobotIElementType("Setting")
  object SettingName extends RobotIElementType("SettingName")
  object ResourceKey extends RobotIElementType("ResourceKey")
  object ResourceValue extends RobotIElementType("ResourceValue")
  object LibraryKey extends RobotIElementType("LibraryKey")
  object LibraryValue extends RobotIElementType("LibraryValue")

  object TestCasesTable extends RobotIElementType("TestCasesTable")
  object TestCaseName extends RobotIElementType("TestCaseTitle")
  object TestCaseDefinition extends RobotIElementType("TestCaseDefinition")

  object KeywordsTable extends RobotIElementType("KeywordsTable")
  object KeywordName extends RobotIElementType("KeywordName")
  object KeywordDefinition extends RobotIElementType("KeywordDefinition")
  object Keyword extends RobotIElementType("Keyword")

  object VariablesTable extends RobotIElementType("VariablesTable")
  object VariableDefinition extends RobotIElementType("VariableDefinition")
  object VariableName extends RobotIElementType("VariableName")
  object TableRow extends RobotIElementType("TableRow")
  object NonEmptyCell extends RobotIElementType("NonEmptyCell")

  val tableElementTypes = TokenSet.create(SettingsTable, TestCasesTable, KeywordsTable, VariablesTable)

}
