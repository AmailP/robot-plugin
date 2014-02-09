package amailp.parser

import amailp.psi.Settings
import amailp.elements.RobotTokenTypes._
import amailp.parser.RobotASTTypes._

object SettingParser extends SubParser {

  def parse(builder: RobotPsiBuilder) = {
    import builder._

    def parseSettingFirstCell() {
      val firstCellMarker = mark
      if(currentType == Ellipsis) {
        advanceLexer()
        firstCellMarker done Ellipsis
      }
      else parseCell() match {
        case cnt if Settings.names contains cnt => firstCellMarker done SettingName
        case _ => firstCellMarker error "Settings name not known"
      }
    }

    val settingMarker = mark
    parseSettingFirstCell()
    parseRemainingCells()
    settingMarker done Setting
    consumeLineTerminator()
  }

}
