package amailp.intellij.robot

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.lang.impl.PsiBuilderAdapter
import com.intellij.lang.PsiBuilder.Marker
import amailp.intellij.robot.idea.RobotIElementType

package object parser {

  object SettingsTable extends RobotIElementType("SettingsTable")
  object Setting extends RobotIElementType("Setting")
  object SettingName extends RobotIElementType("SettingName")
  object ResourceName extends RobotIElementType("ResourceName")
  object ResourceValue extends RobotIElementType("ResourceValue")

  object TestCasesTable extends RobotIElementType("TestCasesTable")
  object TestCaseName extends RobotIElementType("TestCaseTitle")
  object TestCaseDefinition extends RobotIElementType("TestCaseDefinition")

  object KeywordsTable extends RobotIElementType("KeywordsTable")
  object KeywordName extends RobotIElementType("KeywordName")
  object KeywordDefinition extends RobotIElementType("KeywordDefinition")
  object Keyword extends RobotIElementType("Keyword")

  object VariablesTable extends RobotIElementType("VariablesTable")
  object TableRow extends RobotIElementType("TableRow")
  object NonEmptyCell extends RobotIElementType("NonEmptyCell")

  trait SubParser {
    def parse(implicit builder: RobotPsiBuilder)
  }

  implicit class RobotPsiBuilder(builder: PsiBuilder) extends PsiBuilderAdapter(builder) {
    def currentType = getTokenType
    def currentText = getTokenText

    def parseBodyRow() {
      val rowMarker = mark
      if(!currentIsSeparator) parseCell()
      parseRemainingCells()
      consumeLineTerminator()
      rowMarker done TableRow
    }

    def parseCell(cellType: IElementType = NonEmptyCell): IElementType = {
      parseCell((m, _) => {m done cellType; cellType})
    }

    def parseCell(terminateMarker: (Marker, String) => IElementType): IElementType = {
      val cellMarker = mark
      val contentBuilder = new StringBuilder
      while(!currentIsRowTerminator && currentType != Separator) {
        contentBuilder append currentText
        advanceLexer()
      }
      terminateMarker(cellMarker, contentBuilder.result())
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
  }
}
