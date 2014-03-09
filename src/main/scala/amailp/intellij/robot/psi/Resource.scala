package amailp.intellij.robot.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.psi.{AbstractElementManipulator, PsiReference}
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.util.TextRange

case class ResourceValue(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override lazy val getReference: PsiReference = new ResourceValueReference(this)
}

class ResourceValueReference(element: ResourceValue) extends RobotReferenceBase[ResourceValue](element){
  override def resolve() = {
    Option[VirtualFile](currentDirectory.findFileByRelativePath(element.getText)) match {
      case Some(targetFile) => psiManager.findFile(targetFile)
      case None => null
    }
  }

  override def getVariants: Array[AnyRef] = Array()
}

class ResourceValueManipulator extends AbstractElementManipulator[Keyword]{
  override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
}