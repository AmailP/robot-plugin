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
        case SettingsHeader => parseSettings(); Some(SettingsTable)
        case TestCasesHeader => parseTestCases(); Some(TestCasesTable)
        case KeywordsHeader => parseKeywords(); Some(KeywordsTable)
        case VariablesHeader => parseVariables(); Some(VariablesTable)
        case _ => None
      }
      tableType match {
        case Some(typ) => tableMarker done typ
        case None => tableMarker drop()
      }
    }

    def parseSettings() {
      while(hasMoreTokens && !isHeader(currentType))
        parseSetting()
    }

    def parseSetting() {
      val settingMarker = mark
      parseSettingFirstCell()
      while(currentIsSeparator)
      {
        consumeSeparator()
        parseCell()
      }
      settingMarker done Setting
      consumeLineTerminator()
    }

    def parseSettingFirstCell() {
      val firstCellMarker = mark
      val content = parseCell()
      content match {
        case amailp.psi.Ellipsis.string => firstCellMarker done Ellipsis
        case _ @ cnt if Settings.names contains cnt => firstCellMarker done SettingName
        case _ => firstCellMarker error "Settings name not known"
      }
    }

    def parseTestCases() {
      while(hasMoreTokens && !isHeader(currentType))
        parseTestCase()
    }

    def parseTestCase() {
      if(currentIsSpace) error("Title expected, not space")
      val keywordMark = mark
      parseTitle(TestCaseTitle)
      //        parseKeywordSettings()
      while(currentIsSeparator)
        parseBodyRow()
      keywordMark done TestCase
    }

    def parseKeywords() {
      while(hasMoreTokens && !isHeader(currentType)) {
        parseKeyword()
      }
    }

    def parseKeyword() {
      if(currentIsSpace) error("Title expected, not space")
      val keywordMark = mark
      parseTitle(KeywordTitle)
      //        parseKeywordSettings()
      while(currentIsSeparator)
        parseBodyRow()
      keywordMark done Keyword
    }

    def parseTitle(titleType: IElementType) {
      val titleMark = mark
      parsePhrase()
      titleMark done titleType
      consumeLineTerminator()
    }

    def parseVariables() {
      while(hasMoreTokens && !isHeader(currentType))
        parseBodyRow()
    }

    def parseHeaderRow(): IElementType = {
      val headerMark = mark
      val headerType = currentType
      if(!isHeader(headerType)) error("Header token expected")
      advanceLexer()
      headerMark done headerType
      consumeLineTerminator()
      headerType
    }
    
    def parseBodyRow() {
      val rowMarker = mark
      if(!currentIsSeparator)
        parseCell()
      while(currentIsSeparator)
      {
        consumeSeparator()
        parseCell()
      }
      consumeLineTerminator()
      rowMarker done TableRow
    }

    def parseCell(): String = {
      val cellMarker = mark
      val result = parsePhrase()
      cellMarker done NonEmptyCell
      result
    }

    def parsePhrase(): String = {
      val content = new StringBuilder
      while(!currentIsRowTerminator && currentType != Separator) {
        content append currentText
        advanceLexer()
      }
      content.result()
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