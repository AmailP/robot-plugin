package amailp.parser

import amailp.elements.RobotTokenTypes._
import amailp.parser.RobotASTTypes._
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.psi.tree.IElementType

object SettingParser extends SubParser {

  val otherSettingNames = Set[String]("Library", "Variables", "Documentation", "Metadata", "Suite Setup",
    "Suite Teardown", "Suite Precondition", "Suite Postcondition", "Force Tags", "Default Tags", "Test Setup",
    "Test Teardown", "Test Precondition", "Test Postcondition", "Test Template", "Test Timeout")
  
  def parse(implicit builder: RobotPsiBuilder) = {
    import builder._

    def parseSettingFirstCell(): IElementType = {
      if(currentType == Ellipsis) {
        val ellipsisMarker = mark
        advanceLexer()
        ellipsisMarker done Ellipsis; Ellipsis
      }
      else parseCell((marker: Marker, content: String) => {
        content match {
          case "Resource" => marker done ResourceName ; ResourceName
          case cnt if otherSettingNames contains cnt => marker done SettingName ; SettingName
          case _ => marker error "Settings name not known" ; NonEmptyCell
        }
      })
    }

    def parseResouceValue() {
      consumeSeparator()
      parseCell(ResourceValue)
    }

    val settingMarker = mark
    parseSettingFirstCell() match {
      case ResourceName => parseResouceValue()
      case _ => parseRemainingCells()
    }
    settingMarker done Setting
    consumeLineTerminator()
  }
}
