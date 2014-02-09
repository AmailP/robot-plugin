package amailp

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi._
import com.intellij.psi.util.PsiTreeUtil
import scala.collection.JavaConversions._
import com.intellij.openapi.util.TextRange
import scala.Some
import com.intellij.psi.search.FilenameIndex
import com.intellij.openapi.vfs.VirtualFile

package object psi {
  case class Ellipsis(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class SettingName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class TestCaseName(node: ASTNode) extends ASTWrapperPsiElement(node)
  case class KeywordName (node: ASTNode) extends ASTWrapperPsiElement(node)

  case class Keyword(node: ASTNode) extends ASTWrapperPsiElement(node) {
    override lazy val getReference: PsiReference = new KeywordReference(this)
  }

  abstract class RobotReferenceBase[T <: PsiElement](element: T) extends PsiReferenceBase[T](element){
    def currentRobotFile = PsiTreeUtil.getParentOfType(element, classOf[RobotFile])
    def currentFile = currentRobotFile.getVirtualFile
    def currentDirectory = currentFile.getParent
    def psiManager = PsiManager.getInstance(element.getProject)
  }

  class KeywordReference(element: Keyword) extends RobotReferenceBase[Keyword](element){
    override def resolve() = {
      val textToBeFound = element.getText
      fileDefinedKeywordNames find ( _.getText == textToBeFound ) match {
        case Some(keyword) => keyword.getParent
        case None => null
      }
    }

    override def getVariants: Array[AnyRef] = fileDefinedKeywordNames.map(_.getText).toArray

    private def fileDefinedKeywordNames = {
      PsiTreeUtil findChildrenOfType (currentRobotFile, classOf[KeywordName])
    }
  }

  class KeywordManipulator extends AbstractElementManipulator[Keyword]{
    override def handleContentChange(element: Keyword, range: TextRange, newContent: String): Keyword = null
  }

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
}
