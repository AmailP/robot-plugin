package amailp.intellij.robot.findUsage

import com.intellij.psi.PsiElement

trait UsageFindable {
  self: PsiElement =>
  def getNodeText(useFullName: Boolean): String = self.getText
  def getDescriptiveName: String
  def getType: String
  def getHelpId: String = "AAA getHelpId"
}
