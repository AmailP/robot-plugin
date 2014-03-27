package amailp.intellij.robot.parser

import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.ast

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
          case "Resource" => marker done ast.ResourceName ; ast.ResourceName
          case cnt if otherSettingNames contains cnt => marker done ast.SettingName ; ast.SettingName
          case _ => marker error "Settings name not known" ; ast.NonEmptyCell
        }
      })
    }

    def parseResouceValue() {
      consumeSeparator()
      parseCell(ast.ResourceValue)
    }

    val settingMarker = mark
    parseSettingFirstCell() match {
      case ast.ResourceName => parseResouceValue()
      case _ => parseRemainingCells()
    }
    settingMarker done ast.Setting
    consumeLineTerminator()
  }
}
