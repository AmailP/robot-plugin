package amailp.intellij.robot.parser

import amailp.intellij.robot.elements.RobotTokenTypes._
import com.intellij.lang.PsiBuilder.Marker
import com.intellij.psi.tree.IElementType
import amailp.intellij.robot.ast

object SettingParser extends SubParser {

  private val otherSettingNames = Set[String]("Variables", "Documentation", "Metadata", "Suite Setup", "Suite Teardown",
      "Suite Precondition", "Suite Postcondition", "Force Tags", "Default Tags", "Test Setup", "Test Teardown",
      "Test Precondition", "Test Postcondition", "Test Template", "Test Timeout")

  def parse(builder: RobotPsiBuilder) = {
    import builder._

    def parseSettingFirstCell(): IElementType = {
      if (currentType == Ellipsis)
        parseEllipsis()
      else
        parseCellThen {
          case "Resource" => doneWithType(ast.ResourceKey)
          case "Library" => doneWithType(ast.LibraryKey)
          case cnt if otherSettingNames contains cnt => doneWithType(ast.SettingName)
          case _ => asError("Settings name not known")
        }
    }

    def parseResouceValue() {
      consumeSeparator()
      parseCellOfType(ast.ResourceValue)
    }

    def parseLibraryValueAndParameters() {
      consumeSeparator()
      parseCellOfType(ast.LibraryValue)
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
