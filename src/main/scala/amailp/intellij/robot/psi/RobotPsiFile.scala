package amailp.intellij.robot.psi

import javax.swing.Icon

import amailp.intellij.robot
import amailp.intellij.robot.lang.RobotLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.PsiTreeUtil

import scala.annotation.tailrec
import scala.collection.JavaConversions._
import scala.collection.immutable.Stream.Empty

class RobotPsiFile(viewProvider: FileViewProvider)
  extends PsiFileBase(viewProvider, RobotLanguage) {

  def getFileType: FileType = robot.file.FileType

  override def toString: String = "RobotFile: " + getVirtualFile.getName

  override def getIcon(flags: Int): Icon = super.getIcon(flags)

  private def getImportedRobotFiles: Stream[RobotPsiFile] = {
    PsiTreeUtil.findChildrenOfType(getNode.getPsi, classOf[ResourceValue])
      .toStream
      .flatMap(_.getReference.resolveReferenceValue())
  }

  def getImportedLibraries: Iterable[Library] = getLocallyImportedLibraries ++ getRecursivelyImportedLibraries ++
    Iterable[Library](BuiltInLibrary)

  private def getLocallyImportedLibraries: Iterable[Library] = {
    for {
      lib: Library <- PsiTreeUtil.findChildrenOfType(getNode.getPsi, classOf[LibraryValue])
    } yield lib
  }

  private def getRecursivelyImportedLibraries: Iterable[Library] = {
    for {
      file <- getRecursivelyImportedRobotFiles
      lib <- file.getLocallyImportedLibraries
    } yield lib
  }

  def getRecursivelyImportedRobotFiles: Stream[RobotPsiFile] = {
    @tailrec
    def visit(toVisit: Stream[RobotPsiFile],
              visited: Set[RobotPsiFile],
              cumulated: Set[RobotPsiFile],
              accumulator: Stream[RobotPsiFile]): Stream[RobotPsiFile] = {
      toVisit match {
        case head #:: tail if visited.contains(head) =>
          visit(toVisit.tail, visited, cumulated, accumulator)
        case head #:: tail if !visited.contains(head) =>
          val importedFromHead = head.getImportedRobotFiles
          visit(toVisit.tail #::: importedFromHead,
            visited + head,
            cumulated ++ importedFromHead,
            accumulator #::: importedFromHead.filterNot(cumulated.contains) )
        case Empty => accumulator
      }
    }
    visit(Stream(this), Set(), Set(), Stream())
  }
}
