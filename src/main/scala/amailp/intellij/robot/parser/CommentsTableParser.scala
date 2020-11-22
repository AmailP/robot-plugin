package amailp.intellij.robot.parser

object CommentsTableParser extends SubParser {
  override def parse(builder: RobotPsiBuilder): Unit = {
    builder.parserCommentLine()
  }
}
