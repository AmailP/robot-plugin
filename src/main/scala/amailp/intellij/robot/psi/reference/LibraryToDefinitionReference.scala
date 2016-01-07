package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import amailp.intellij.robot.psi.Library
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi.{PsiElement, PsiElementResolveResult, ResolveResult, PsiPolyVariantReferenceBase}
import com.jetbrains.python.psi.stubs.PyModuleNameIndex
import scala.collection.JavaConversions._


class LibraryToDefinitionReference(library: Library) extends PsiPolyVariantReferenceBase[Library](library)
  with ExtRobotPsiUtils {

  override def getVariants = {
    for {
      lib <- currentRobotFile.getImportedLibraries
    } yield LookupElementBuilder.create(lib.getDescriptiveName)
      .withCaseSensitivity(false)
      .withTypeText("Library", true)
      .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      .asInstanceOf[AnyRef]
  }.toArray

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      psiFile <- PyModuleNameIndex.find(library.getDescriptiveName, utilsPsiElement.getProject, true)
    } yield new PsiElementResolveResult(psiFile)
  }.toArray

  def utilsPsiElement: PsiElement = getElement
}
