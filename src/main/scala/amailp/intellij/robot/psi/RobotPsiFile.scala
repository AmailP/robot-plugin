package amailp.intellij.robot.psi

import com.intellij.psi.{PsiManager, FileViewProvider}
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import java.lang.String
import javax.swing.Icon
import scala.annotation.tailrec
import com.intellij.psi.util.PsiTreeUtil
import scala.collection.JavaConversions._
import scala.collection.immutable.Stream.Empty
import amailp.intellij.robot
import amailp.intellij.robot.lang.RobotLanguage

class RobotPsiFile(viewProvider: FileViewProvider)
  extends PsiFileBase(viewProvider, RobotLanguage) {

  def getFileType: FileType = robot.file.FileType

  override def toString: String = "RobotFile: " + getVirtualFile.getName

  override def getIcon(flags: Int): Icon = super.getIcon(flags)

  def getImportedRobotFiles: Stream[RobotPsiFile] = {
    val currentDir = getOriginalFile.getVirtualFile.getParent
    for (
      resourceValue: ResourceValue <- PsiTreeUtil.findChildrenOfType(getNode.getPsi,classOf[ResourceValue]).toStream;
      linkedFile = currentDir.findFileByRelativePath(resourceValue.getText) if !(linkedFile == null);
      robotFile = PsiManager.getInstance(getProject).findFile(linkedFile) if robotFile.isInstanceOf[RobotPsiFile]
    ) yield robotFile.asInstanceOf[RobotPsiFile]
  }

  def getRecursivelyImportedRobotFiles: Stream[RobotPsiFile] = {
    @tailrec
    def visit(toVisit: Stream[RobotPsiFile], visited: Set[RobotPsiFile], accumulator: Stream[RobotPsiFile]): Stream[RobotPsiFile] = {
      toVisit match {
        case head #:: tail if visited.contains(head) =>
          visit(toVisit.tail, visited, accumulator)
        case head #:: tail if !visited.contains(head) =>
          val importedFromHead = head.getImportedRobotFiles
          visit(toVisit.tail #::: importedFromHead, visited + head, accumulator #::: importedFromHead )
        case Empty => accumulator
      }
    }
    visit(Stream(this), Set(), Stream())
  }
}
