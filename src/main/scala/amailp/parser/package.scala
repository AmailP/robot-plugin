package amailp

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import amailp.parser.RobotASTTypes._
import amailp.elements.RobotTokenTypes._
import com.intellij.lang.impl.PsiBuilderAdapter
import com.intellij.lang.PsiBuilder.Marker

package object parser {
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
