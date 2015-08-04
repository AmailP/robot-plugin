package amailp.intellij.robot

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.lang.impl.PsiBuilderAdapter
import com.intellij.lang.PsiBuilder.Marker

package object parser {

  trait SubParser {
    def parse(implicit builder: RobotPsiBuilder)
  }

  implicit class RobotPsiBuilder(builder: PsiBuilder) extends PsiBuilderAdapter(builder) {
    def currentType = getTokenType
    def currentText = getTokenText

    def parseRowContent() {
      if(!currentIsSeparator) parseCell()
      parseRemainingCells()
      consumeLineTerminator()
    }

    def parseExpectedTypeCell(cellType: IElementType, expectedTypes: Set[IElementType]): IElementType = {
      if(!expectedTypes.contains(currentType)) error("Expected: " + expectedTypes.mkString(" | "))
      parseCell(cellType)
    }

    def parseCell(cellType: IElementType = ast.NonEmptyCell): IElementType = {
      parseCell((m, _) => {m done cellType; cellType})
    }

    def parseCell(terminateMarker: (Marker, String) => IElementType): IElementType = {
      val cellMarker = mark
      val contentBuilder = new StringBuilder
      while(!currentIsLineTerminator && currentType != Separator) {
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

    def currentIsLineTerminator = currentType == LineTerminator || eof

    def consumeSeparator() {
      if(!currentIsSeparator) error("Cell separator expected")
      advanceLexer()
    }

    def consumeLineTerminator() {
      if (!currentIsLineTerminator && !eof) error("Line terminator expected")
      advanceLexer()
    }

    def isHeader(token: IElementType) = HeaderTokens contains token
  }
}
