package amailp.intellij.robot.psi.utils

import com.intellij.psi.PsiElement

trait RobotPsiUtils extends PsiElement with ExtRobotPsiUtils {
  def utilsPsiElement = this
}
