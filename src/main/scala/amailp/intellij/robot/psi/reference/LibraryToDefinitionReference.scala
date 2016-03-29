package amailp.intellij.robot.psi.reference

import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.openapi.util.TextRange
import com.intellij.psi._
import com.intellij.psi.util.QualifiedName
import com.jetbrains.python.psi.{PyClass, PyFile}
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import scala.collection.JavaConversions._
import scala.collection.mutable
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils


class LibraryToDefinitionReference(element: PsiElement, textRange: TextRange) extends PsiReferenceBase[PsiElement](element, textRange)
  with PsiPolyVariantReference with ExtRobotPsiUtils {

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
    val qName = QualifiedName.fromDottedString(element.getText)
    var fileLibraries = mutable.MutableList[PyFile]()
    var classLibraries = mutable.MutableList[PyClass]()
    if (qName.getComponentCount > 1) {
      for {
        psiFile <- PyModuleNameIndex.find(qName.getLastComponent, element.getProject, true)
        if psiFile.getPresentation.getLocationString contains qName.removeLastComponent().toString
      } fileLibraries += psiFile
      for {
        psiFile <- PyClassNameIndex.find(qName.getLastComponent, element.getProject, true)
        if psiFile.getPresentation.getLocationString contains qName.removeLastComponent().toString
      } classLibraries += psiFile
    } else {
      fileLibraries ++= PyModuleNameIndex.find(element.getText, element.getProject, true)
      classLibraries ++= PyClassNameIndex.find(element.getText, element.getProject, true)
    }
    for {
      psiFile <- fileLibraries ++ classLibraries
    } yield new PsiElementResolveResult(psiFile)
  }.toArray

  override def resolve: PsiElement = {
        val resolveResults = multiResolve(false)
        if (resolveResults.length > 0) resolveResults(0).getElement else null
    }

  def utilsPsiElement: PsiElement = element
}
