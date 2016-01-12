package amailp.intellij.robot.parser

import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.ast

object SettingParser extends SubParser {

  private val otherSettingNames = Set[String]("Variables", "Documentation", "Metadata", "Suite Setup",
    "Suite Teardown", "Suite Precondition", "Suite Postcondition", "Force Tags", "Default Tags", "Test Setup",
    "Test Teardown", "Test Precondition", "Test Postcondition", "Test Template", "Test Timeout")
  
  def parse(builder: RobotPsiBuilder) = {
    import builder._

    def parseSettingFirstCell(): IElementType = {
      if(currentType == Ellipsis)
        parseEllipsis()
      else parseCell((marker: Marker, content: String) => {
        content match {
          case "Resource" => marker done ast.ResourceKey ; ast.ResourceKey
          case "Library" => marker done ast.LibraryKey ; ast.LibraryKey
          case cnt if otherSettingNames contains cnt => marker done ast.SettingName ; ast.SettingName
          case _ => marker error "Settings name not known" ; ast.NonEmptyCell
        }
      })
    }

    def parseResouceValue() {
      consumeSeparator()
      parseCell(ast.ResourceValue)
    }

    def parseLibraryValueAndParameters() {
      consumeSeparator()
      parseCell(ast.LibraryValue)
      parseRemainingCells()
    }

    val settingMarker = mark
    parseSettingFirstCell() match {
      case ast.ResourceKey => parseResouceValue()
      case ast.LibraryKey => parseLibraryValueAndParameters()
      case _ => parseRemainingCells()
    }
    settingMarker done ast.Setting
    consumeLineTerminator()
  }
}
