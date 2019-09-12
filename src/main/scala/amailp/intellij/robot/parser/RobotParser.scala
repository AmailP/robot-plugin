package amailp.intellij.robot.parser

import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.elements.RobotTokenTypes._
import amailp.intellij.robot.ast

object RobotParser extends PsiParser {
  def parse(root: IElementType, builder: PsiBuilder): ASTNode = {
    val robotBuilder = new RobotPsiBuilder(builder)
    import robotBuilder._

    def parseTable() {
      val tableMarker = mark
      val tableType = parseHeaderRow() match {
        case SettingsHeader => parseTableItemsWithSubParser(SettingParser); Some(ast.SettingsTable)
        case TestCasesHeader | TasksHeader => parseTableItemsWith(parseTestCaseDefinition); Some(ast.TestCasesTable)
        case KeywordsHeader => parseTableItemsWith(parseKeywordDefinition); Some(ast.KeywordsTable)
        case VariablesHeader => parseTableItemsWith(parseVariableDefinition); Some(ast.VariablesTable)
        case _ => None
      }
      tableType match {
        case Some(t) => tableMarker done t
        case None => tableMarker drop ()
      }
    }

    def parseHeaderRow(): IElementType = {
      val headerMark = mark
      val headerType = currentType
      advanceLexer()
      if (isHeader(headerType)) headerMark done headerType
      else headerMark error "Table header expected"
      consumeLineTerminator()
      headerType
    }

    def parseTableItemsWithSubParser(subParser: SubParser) {
      while (hasMoreTokens && !isHeader(currentType)) subParser.parse(robotBuilder)
    }

    def parseTableItemsWith(parseItem: () => Unit) {
      while (hasMoreTokens && !isHeader(currentType)) parseItem()
    }

    def parseTestCaseDefinition() {
      parseMultilineDefinition(ast.TestCaseName, ast.TestCaseDefinition)
    }

    def parseKeywordDefinition() {
      parseMultilineDefinition(ast.KeywordName, ast.KeywordDefinition)
    }

    def parseMultilineDefinition(nameType: IElementType, definitionType: IElementType) {
      if (currentIsSpace) error(s"$nameType expected, not space")
      val definitionMark = mark
      parseCellOfType(nameType)
      consumeLineTerminator()
      while (currentIsSeparator) {
        advanceLexer()
        val rowMarker = mark
        currentType match {
          case TestCaseSetting => parseRowContent()
          case ScalarVariable | ListVariable | DictionaryVariable => parseRowContent() // Maybe parseVariableDefinition?
          case Ellipsis => parseEllipsis(); parseRowContent()
          case _ => parseAction()
        }
        rowMarker done ast.TableRow
      }
      definitionMark done definitionType
    }

    def parseVariableDefinition() {
      val definitionMark = mark
      parseExpectedTypeCell(ast.VariableName, Set(ScalarVariable, ListVariable, DictionaryVariable))
      parseRemainingCells()
      while (continuesInNextLine()) {
        consumeLineTerminator()
        if (currentIsSpace) advanceLexer()
        parseEllipsis()
        parseRowContent(includingTerminator = false)
      }
      definitionMark done ast.VariableDefinition
      consumeLineTerminator()
    }

    def continuesInNextLine(): Boolean = {
      val first = lookAhead(1)
      val second = lookAhead(2)
      (first == Ellipsis
      || (first == Separator && second == Ellipsis)
      || (first == Space && second == Ellipsis))
    }

    def parseAction() {
      parseCellOfType(ast.Keyword)
      parseRowContent()
    }

    builder.setDebugMode(true)
    val rootMarker = mark
    val tablesMarker = mark
    while (hasMoreTokens) {
      parseTable()
    }
    tablesMarker done ast.Tables
    rootMarker done root
    builder.getTreeBuilt
  }
}
