package amailp.intellij.robot

import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.lang.impl.PsiBuilderAdapter
import com.intellij.psi.tree.IElementType

import scala.language.implicitConversions

package object parser {

  trait SubParser {
    def parse(builder: RobotPsiBuilder)
  }

  implicit class RobotPsiBuilder(builder: PsiBuilder) extends PsiBuilderAdapter(builder) {
    def currentType = getTokenType
    def currentText = getTokenText

    def parserCommentLine(includingTerminator: Boolean = true): Unit = {
        while (currentType != SettingsHeader) {
          parseRowContent()
        }
    }

    def parseRowContent(includingTerminator: Boolean = true) {
      if (!currentIsSeparator) parseCellOfType(ast.NonEmptyCell)
      parseRemainingCells()
      if (includingTerminator)
        consumeLineTerminator()
    }

    def parseExpectedTypeCell(parseType: IElementType, expectedTypes: Set[IElementType]): IElementType =
      if (expectedTypes.contains(currentType))
        parseCellThen(collapseWithType(parseType))
      else
        parseCellThen(asError("Expected: " + expectedTypes.mkString(" | ")))

    def parseCellOfType(cellType: IElementType): IElementType =
      parseCellThen(doneWithType(cellType))

    def parseCellThen(action: ParametricActionOnMarker): IElementType = {
      val cellMarker = mark
      val contentBuilder = new StringBuilder
      while (!currentIsLineTerminator && currentType != Separator) {
        contentBuilder append currentText
        advanceLexer()
      }
      action(contentBuilder.result())(cellMarker)
    }

    type ActionOnMarker = Marker => IElementType
    def doneWithType(t: IElementType): ActionOnMarker = m => { m done t; t }
    def collapseWithType(t: IElementType): ActionOnMarker = m => { m collapse t; t }
    def asError(message: String): ActionOnMarker = { error(message); doneWithType(ast.NonEmptyCell) }

    type ParametricActionOnMarker = String => ActionOnMarker
    implicit def ignoreParameter(a: ActionOnMarker): ParametricActionOnMarker = s => a

    def parseRemainingCells() {
      while (currentIsSeparator) {
        consumeSeparator()
        parseCellOfType(ast.NonEmptyCell)
      }
    }

    def parseEllipsis(): IElementType = {
      val ellipsisMark = mark
      if (currentType != Ellipsis) error("Ellipsis expected")
      advanceLexer()
      ellipsisMark done Ellipsis
      Ellipsis
    }

    def hasMoreTokens = !eof

    def currentIsSpace = currentIsSeparator || currentType == Space

    def currentIsSeparator = currentType == Separator

    def currentIsLineTerminator = currentType == LineTerminator || eof

    def consumeSeparator() {
      if (!currentIsSeparator) error("Cell separator expected")
      advanceLexer()
    }

    def consumeLineTerminator() {
      if (!currentIsLineTerminator && !eof) error("Line terminator expected")
      advanceLexer()
    }

    def isHeader(token: IElementType) = HeaderTokens contains token
  }
}
