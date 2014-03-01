package amailp.psi

import com.intellij.psi.{PsiManager, FileViewProvider}
import com.intellij.extapi.psi.PsiFileBase
import amailp.idea.RobotLanguage
import com.intellij.openapi.fileTypes.FileType
import amailp.file.RobotFileType
import java.lang.String
import javax.swing.Icon
import scala.annotation.tailrec
import com.intellij.psi.util.PsiTreeUtil
import scala.collection.JavaConversions._

class RobotFile(viewProvider: FileViewProvider)
  extends PsiFileBase(viewProvider, RobotLanguage) {

  def getFileType: FileType = RobotFileType.INSTANCE

  override def toString: String = "RobotFile: " + getVirtualFile.getName

  override def getIcon(flags: Int): Icon = super.getIcon(flags)

  def getDefinedKeywordNames = PsiTreeUtil.findChildrenOfType(getNode.getPsi,classOf[KeywordName]).toSet

  def getImportedRobotFiles: Set[RobotFile] = {
    val currentDir = getOriginalFile.getVirtualFile.getParent
    for (
      resourceValue: ResourceValue <- PsiTreeUtil.findChildrenOfType(getNode.getPsi,classOf[ResourceValue]).toSet;
      linkedFile = currentDir.findFileByRelativePath(resourceValue.getText) if !(linkedFile == null);
      robotFile = PsiManager.getInstance(getProject).findFile(linkedFile) if robotFile.isInstanceOf[RobotFile]
    ) yield robotFile.asInstanceOf[RobotFile]
  }

  def getRecursivelyImportedRobotFiles = {
    @tailrec
    def visit(toVisit: Set[RobotFile], visited: Set[RobotFile], accumulator: Set[RobotFile]): Set[RobotFile] = {
      if(toVisit.isEmpty){
        accumulator
      } else {
        val head = toVisit.head
        val importedFromHead = head.getImportedRobotFiles
        visit(toVisit - head | importedFromHead, visited + head, accumulator | importedFromHead )
      }
    }
    visit(Set(this), Set(), Set())
  }
}
