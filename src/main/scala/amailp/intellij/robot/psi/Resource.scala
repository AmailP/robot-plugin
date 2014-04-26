package amailp.intellij.robot.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.psi._
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.util.TextRange
import scala.Some
import amailp.intellij.robot.file.FileType
import com.intellij.psi.util.PsiTreeUtil
import scala.collection.JavaConversions._

case class ResourceValue(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override def getReference: PsiReference = new ResourceValueReference(this)
}

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

class ResourceValueManipulator extends AbstractElementManipulator[ResourceValue]{
  override def handleContentChange(element: ResourceValue, range: TextRange, newContent: String): ResourceValue = {
    //TODO factorize with KeyDef one
    val fileContent =
      s"""
          |*** Settings ***
          |Resource    $newContent
        """.stripMargin
    val dummyFile = PsiFileFactory.getInstance(element.getProject).createFileFromText("dummy", FileType, fileContent).asInstanceOf[RobotPsiFile]
    val dummyKeyword= PsiTreeUtil.findChildrenOfType(dummyFile.getNode.getPsi, classOf[ResourceValue]).head
    element.getNode.getTreeParent.replaceChild(element.getNode, dummyKeyword.getNode)
    element
  }
}