package amailp.intellij.robot.extensions

class Commenter extends com.intellij.lang.Commenter {
  def getLineCommentPrefix: String = "#"

  def getCommentedBlockCommentSuffix: String = null
  def getCommentedBlockCommentPrefix: String = null
  def getBlockCommentSuffix: String = null
  def getBlockCommentPrefix: String = null
}
