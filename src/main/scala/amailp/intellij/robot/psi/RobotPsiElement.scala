package amailp.intellij.robot.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.lexer.RobotIElementType
import com.intellij.psi.PsiElement
import scala.collection.JavaConverters._
import amailp.intellij.robot.psi.utils.RobotPsiUtils

abstract class RobotPsiElement(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils {
  def findChildrenByType[T <: PsiElement](tokenTypes: List[RobotIElementType]): Iterable[T] = {
    findChildrenByType[T](TokenSet.create(tokenTypes: _*)).asScala
  }
}
