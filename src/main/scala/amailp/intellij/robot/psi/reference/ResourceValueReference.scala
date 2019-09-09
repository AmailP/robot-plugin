package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import amailp.intellij.robot.psi.{ResourceValue, RobotPsiFile}
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.{PsiElement, PsiReferenceBase}

class ResourceValueReference(element: ResourceValue) extends PsiReferenceBase[ResourceValue](element) with ExtRobotPsiUtils {
  def resourceFilePath: String = getElement.getText.replace("${/}", "/")

  override def resolve() = resolveReferenceValue().orNull
  override def getVariants: Array[AnyRef] = Array()
  override def utilsPsiElement: PsiElement = getElement

  def resolveReferenceValue(): Option[RobotPsiFile] =
    resolveLocalFile orElse resolveAbsoluteFile orElse resolveFromSourceRoots

  private def resolveLocalFile: Option[RobotPsiFile] =
    maybeToRobotPsiFile(Option(currentDirectory.findFileByRelativePath(resourceFilePath)))

  private def resolveAbsoluteFile: Option[RobotPsiFile] =
    maybeToRobotPsiFile(Option(currentFile.getFileSystem.findFileByPath(resourceFilePath)))

  private def resolveFromSourceRoots: Option[RobotPsiFile] = {
    def sourceRoots = ModuleRootManager.getInstance(ModuleUtilCore.findModuleForPsiElement(getElement)).getSourceRoots(true).toList
    sourceRoots.map(s => maybeToRobotPsiFile(Option(s.findFileByRelativePath(resourceFilePath))))
      .foldLeft(None: Option[RobotPsiFile])((a, b) => a orElse b)
  }

  private def maybeToRobotPsiFile(file: Option[VirtualFile]): Option[RobotPsiFile] =
    file.flatMap(f =>
      Option(psiManager.findFile(f))
        .filter(_.isInstanceOf[RobotPsiFile])
        .map(_.asInstanceOf[RobotPsiFile])
    )
}
