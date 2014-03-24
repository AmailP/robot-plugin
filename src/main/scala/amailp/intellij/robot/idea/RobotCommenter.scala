package amailp.intellij.robot.idea

import com.intellij.lang.Commenter

class RobotCommenter extends Commenter {
  def getLineCommentPrefix: String = "#"

  def getCommentedBlockCommentSuffix: String = null
  def getCommentedBlockCommentPrefix: String = null
  def getBlockCommentSuffix: String = null
  def getBlockCommentPrefix: String = null
}
