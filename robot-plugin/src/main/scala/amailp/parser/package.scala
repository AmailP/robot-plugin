package amailp

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import amailp.parser.RobotASTTypes._
import amailp.elements.RobotTokenTypes._
import com.intellij.lang.impl.PsiBuilderAdapter

package object parser {
  trait SubParser {
    def parse(implicit builder: RobotPsiBuilder)
  }

  class RobotPsiBuilder(builder: PsiBuilder) extends PsiBuilderAdapter(builder) {
    def currentType = getTokenType
    def currentText = getTokenText

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
  }
}
