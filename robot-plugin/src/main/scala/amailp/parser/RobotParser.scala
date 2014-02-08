package amailp.parser

import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import amailp.elements.RobotTokenTypes._
import RobotASTTypes._
import amailp.psi.Settings

object RobotParser extends PsiParser {
  def parse(root: IElementType, builder: PsiBuilder): ASTNode = {
    import builder._
    def currentType = getTokenType
    def currentText = getTokenText

    def parseTable() {
      val tableMarker = mark
      val tableType = parseHeaderRow() match {
        case SettingsHeader => parseTableItemsWith(parseSetting); Some(SettingsTable)
        case TestCasesHeader => parseTableItemsWith(parseTestCaseDefinition); Some(TestCasesTable)
        case KeywordsHeader => parseTableItemsWith(parseKeywordDefinition); Some(KeywordsTable)
        case VariablesHeader => parseTableItemsWith(parseBodyRow); Some(VariablesTable)
        case _ => None
      }
      tableType match {
        case Some(typ) => tableMarker done typ
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

    def parseTableItemsWith(parseItem: () => Unit) {
      while(hasMoreTokens && !isHeader(currentType))
        parseItem()
    }

    def parseSetting() {
      val settingMarker = mark
      parseSettingFirstCell()
      parseRemainingCells()
      settingMarker done Setting
      consumeLineTerminator()
    }

    def parseSettingFirstCell() {
      val firstCellMarker = mark
      if(currentType == Ellipsis) {
        advanceLexer()
        firstCellMarker done Ellipsis
      }
      else parseCell() match {
        case cnt if Settings.names contains cnt => firstCellMarker done SettingName
        case _ => firstCellMarker error "Settings name not known"
      }
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
    
    def parseBodyRow() {
      val rowMarker = mark
      if(!currentIsSeparator) parseCell()
      parseRemainingCells()
      consumeLineTerminator()
      rowMarker done TableRow
    }

    def parseCell(cellType: IElementType = NonEmptyCell): String = {
      val cellMarker = mark
      val content = new StringBuilder
      while(!currentIsRowTerminator && currentType != Separator) {
        content append currentText
        advanceLexer()
      }
      cellMarker done cellType
      content.result()
    }

    def parseRemainingCells() {
      while(currentIsSeparator)
      {
        consumeSeparator()
        parseCell()
      }
    }

    def hasMoreTokens = !eof

    def currentIsSpace = currentIsSeparator || currentType == Space

    def currentIsSeparator = currentType == Separator

    def currentIsRowTerminator = currentType == LineTerminator || eof

    def consumeSeparator() {
      if(!currentIsSeparator) error("Cell separator expected")
      advanceLexer()
    }

    def consumeLineTerminator() {
      if (!currentIsRowTerminator) error("Line terminator expected")
      advanceLexer()
    }
    
    def isHeader(token: IElementType) = HeaderTokens contains token

    builder.setDebugMode(true)
    val rootMarker = mark
    while (hasMoreTokens) {
      parseTable()
    }
    rootMarker.done(root)
    builder.getTreeBuilt
  }
}