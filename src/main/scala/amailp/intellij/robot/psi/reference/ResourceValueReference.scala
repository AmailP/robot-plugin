package amailp.intellij.robot.psi.reference

import com.intellij.psi.{PsiElement, PsiReferenceBase}
import amailp.intellij.robot.psi.ResourceValue
import com.intellij.openapi.vfs.VirtualFile
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils

class ResourceValueReference(element: ResourceValue) extends PsiReferenceBase[ResourceValue](element) with ExtRobotPsiUtils {
  override def resolve() = {
    Option[VirtualFile](currentDirectory.findFileByRelativePath(getElement.getText)) match {
      case Some(targetFile) => psiManager.findFile(targetFile)
      case None => null
    }
  }

  override def getVariants: Array[AnyRef] = Array()
  override def utilsPsiElement: PsiElement = getElement
}
