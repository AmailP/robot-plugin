package amailp.intellij.robot.extensions

import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.psi.PsiElement
import amailp.intellij.robot.findUsage.UsageFindable
import amailp.intellij.robot
import amailp.intellij.robot.psi.RobotPsiFile

class FindUsagesProvider extends com.intellij.lang.findUsages.FindUsagesProvider {
  override def getNodeText(element: PsiElement, useFullName: Boolean): String = element.asInstanceOf[UsageFindable].getNodeText(useFullName)
  override def getDescriptiveName(element: PsiElement): String = element match {
    case usageFindable: UsageFindable => usageFindable.getDescriptiveName
    case file: RobotPsiFile => file.getName
  }
  override def getType(element: PsiElement): String = element.asInstanceOf[UsageFindable].getType
  override def getHelpId(element: PsiElement): String = element.asInstanceOf[UsageFindable].getHelpId
  override def canFindUsagesFor(element: PsiElement): Boolean = element.isInstanceOf[UsageFindable]
  override def getWordsScanner: WordsScanner = new robot.findUsage.WordsScanner
}




