package amailp.intellij.robot.extensions

import com.intellij.psi.{ElementDescriptionLocation, PsiElement}
import com.intellij.usageView.UsageViewLongNameLocation
import amailp.intellij.robot.findUsage.UsageFindable

class ElementDescriptionProvider extends com.intellij.psi.ElementDescriptionProvider {
  def getElementDescription(element: PsiElement, location: ElementDescriptionLocation): String = {
    (element, location) match {
      case (e: UsageFindable, _: UsageViewLongNameLocation) => s"'${e.getDescriptiveName}'"
      case _ => null
    }
  }
}
