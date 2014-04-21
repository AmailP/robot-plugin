package amailp.intellij.robot.extensions

import com.intellij.psi.PsiElement
import com.intellij.usages.impl.rules.UsageType
import amailp.intellij.robot.psi.Keyword
import amailp.intellij.robot.findUsage.UsageTypes

class UsageTypeProvider extends com.intellij.usages.impl.rules.UsageTypeProvider {
  def getUsageType(element: PsiElement): UsageType = element match{
    case _: Keyword => UsageTypes.KeywordUsage
    case _ => UsageType.UNCLASSIFIED
  }
}
