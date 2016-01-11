package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import amailp.intellij.robot.psi.LibraryValue
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi.{PsiElement, PsiElementResolveResult, ResolveResult, PsiPolyVariantReferenceBase}
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import scala.collection.JavaConversions._


class LibraryToDefinitionReference(library: LibraryValue) extends PsiPolyVariantReferenceBase[LibraryValue](library)
  with ExtRobotPsiUtils {

  override def getVariants = {
    for {
      lib <- currentRobotFile.getImportedLibraries
    } yield LookupElementBuilder.create(lib.getText)
      .withCaseSensitivity(false)
      .withTypeText("Library", true)
      .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      .asInstanceOf[AnyRef]
  }.toArray

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      psiFile <- PyModuleNameIndex.find(library.getText, utilsPsiElement.getProject, true) ++
        PyClassNameIndex.find(library.getText, utilsPsiElement.getProject, true) ++
        Option(PyClassNameIndex.findClass(library.getText, utilsPsiElement.getProject))
    } yield new PsiElementResolveResult(psiFile)
  }.toArray

  def utilsPsiElement: PsiElement = getElement
}
