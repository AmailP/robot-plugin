package amailp.intellij.robot.findUsage

import com.intellij.psi.PsiElement

trait UsageFindable {
  val element: PsiElement
  def getNodeText(useFullName: Boolean): String = element.getText
  def getDescriptiveName: String = ""
  def getType: String = "Keyword"
  def getHelpId: String = "AAA getHelpId"
}
