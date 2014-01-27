package amailp.parser

import amailp.elements.RobotIElementType

object RobotASTTypes {
  final val SettingsTable: RobotIElementType = new RobotIElementType("SettingsTable")
  final val TestCasesTable: RobotIElementType = new RobotIElementType("TestCasesTable")
  final val KeywordsTable: RobotIElementType = new RobotIElementType("KeywordsTable")
  final val TestCaseTitle: RobotIElementType = new RobotIElementType("TestCaseTitle")
  final val KeywordTitle: RobotIElementType = new RobotIElementType("KeywordTitle")
  final val TestCase: RobotIElementType = new RobotIElementType("TestCase")
  final val Keyword: RobotIElementType = new RobotIElementType("Keyword")
  final val VariablesTable: RobotIElementType = new RobotIElementType("VariablesTable")
  final val TableRow: RobotIElementType = new RobotIElementType("TableRow")
  final val NonEmptyCell: RobotIElementType = new RobotIElementType("NonEmptyCell")
}