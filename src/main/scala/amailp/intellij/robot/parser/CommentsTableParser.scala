package amailp.intellij.robot.parser

class CommentsTableParser extends SubParser {
  override def parse(builder: RobotPsiBuilder): Unit = {
    builder.parserCommentLine()
  }
}
