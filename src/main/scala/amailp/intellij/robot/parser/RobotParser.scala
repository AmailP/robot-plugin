package amailp.intellij.robot.parser

import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.elements.RobotTokenTypes._

object RobotParser extends PsiParser {
  def parse(root: IElementType, builder: PsiBuilder): ASTNode = {
    implicit val robotBuilder = new RobotPsiBuilder(builder)
    import robotBuilder._

    def parseTable() {
      val tableMarker = mark
      val tableType = parseHeaderRow() match {
        case SettingsHeader => parseTableItemsWithSubParser(SettingParser); Some(SettingsTable)
        case TestCasesHeader => parseTableItemsWith(parseTestCaseDefinition); Some(TestCasesTable)
        case KeywordsHeader => parseTableItemsWith(parseKeywordDefinition); Some(KeywordsTable)
        case VariablesHeader => parseTableItemsWith(parseBodyRow); Some(VariablesTable)
        case _ => None
      }
      tableType match {
        case Some(t) => tableMarker done t
        case None => tableMarker drop()
      }
    }

    def parseHeaderRow(): IElementType = {
      val headerMark = mark
      val headerType = currentType
      advanceLexer()
      if(isHeader(headerType)) headerMark done headerType
      else headerMark error "Table header expected"
      consumeLineTerminator()
      headerType
    }

    def parseTableItemsWithSubParser(subParser: SubParser) {
      while(hasMoreTokens && !isHeader(currentType))
        subParser.parse
    }

    def parseTableItemsWith(parseItem: () => Unit) {
      while(hasMoreTokens && !isHeader(currentType))
        parseItem()
    }

    def parseTestCaseDefinition() {
      parseDefinition(TestCaseName, TestCaseDefinition)
    }

    def parseKeywordDefinition() {
      parseDefinition(KeywordName, KeywordDefinition)
    }

    def parseDefinition(nameType: IElementType, definitionType: IElementType) {
      if(currentIsSpace) error(s"$nameType expected, not space")
      val definitionMark = mark
      parseCell(nameType)
      consumeLineTerminator()
      while(currentIsSeparator) {
        advanceLexer()
        currentType match {
          case TestCaseSetting => parseBodyRow()
          case Variable => parseBodyRow()
          case Ellipsis => parseCell(Ellipsis); parseBodyRow()
          case _ => parseAction()
        }
      }
      definitionMark done definitionType
    }

    def parseAction() {
      parseCell(Keyword)
      parseRemainingCells()
      consumeLineTerminator()
    }

    builder.setDebugMode(true)
    val rootMarker = mark
    while (hasMoreTokens) {
      parseTable()
    }
    rootMarker.done(root)
    builder.getTreeBuilt
  }
}