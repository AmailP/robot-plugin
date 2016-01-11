package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import amailp.intellij.robot.psi.LibraryValue
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi.{PsiElement, PsiElementResolveResult, ResolveResult, PsiPolyVariantReferenceBase}
import com.intellij.psi.util.QualifiedName
import com.jetbrains.python.psi.PyFile
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import scala.collection.JavaConversions._
import scala.collection.mutable


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
    val qName = QualifiedName.fromDottedString(library.getText)
    var modules = mutable.MutableList[PyFile]()
    if (qName.getComponentCount > 1) {
      for {
        psiFile <- PyModuleNameIndex.find(qName.getLastComponent, utilsPsiElement.getProject, true)
        if psiFile.getPresentation.getLocationString contains qName.getComponents.dropRight(1).mkString(".")
      } modules += psiFile
    } else {
      modules ++= PyModuleNameIndex.find(library.getText, utilsPsiElement.getProject, true)
    }
    for {
      psiFile <- modules ++ PyClassNameIndex.find(library.getText, utilsPsiElement.getProject, true) ++
        Option(PyClassNameIndex.findClass(library.getText, utilsPsiElement.getProject))
    } yield new PsiElementResolveResult(psiFile)
  }.toArray

  def utilsPsiElement: PsiElement = getElement
}
