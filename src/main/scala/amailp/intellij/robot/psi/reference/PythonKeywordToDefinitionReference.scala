package amailp.intellij.robot.psi.reference

import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.openapi.util.TextRange
import com.intellij.psi.util.{QualifiedName, PsiTreeUtil}
import com.intellij.psi._
import com.jetbrains.python.psi.{PyClass, PyFunction, PyFile}
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import collection.JavaConversions._
import amailp.intellij.robot.file.Icons
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils


class PythonKeywordToDefinitionReference(element: PsiElement, textRange: TextRange) extends PsiReferenceBase[PsiElement](element, textRange)
  with PsiPolyVariantReference with ExtRobotPsiUtils {

  override def getVariants = {
    for {
      keyword <- findMatchingInLibraries().map(_.getName)
    } yield LookupElementBuilder.create(keyword)
      .withCaseSensitivity(false)
      .withTypeText("Keyword", true)
      .withIcon(Icons.keyword)
      .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      .asInstanceOf[AnyRef]
  }.toArray

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {

    for {
      keywordDefinition <- findMatchingInLibraries(element.getText)
    } yield new PsiElementResolveResult(keywordDefinition)
  }.toArray

  override def resolve: PsiElement = {
    val resolveResults = multiResolve(false)
    if (resolveResults.length > 0) resolveResults(0).getElement else null
  }

  def findMatchingInLibraries(reference: String = null) = {
    (
    for {
      library <- currentRobotFile.getImportedLibraries
      qName = QualifiedName.fromDottedString(library.getText)
      pyFile <- PyModuleNameIndex.find(qName.getLastComponent, element.getProject, true)
      if qName.getComponentCount == 1 || (pyFile.getPresentation.getLocationString contains qName.removeLastComponent().toString)
      keyword <- findInPyFile(pyFile, reference)
    } yield keyword
    ) ++ (
      for {
        library <- currentRobotFile.getImportedLibraries
        qName = QualifiedName.fromDottedString(library.getText)
        pyClass <- PyClassNameIndex.find(qName.getLastComponent, element.getProject, true)
        if qName.getComponentCount == 1 || (pyClass.getPresentation.getLocationString contains qName.removeLastComponent().toString)
        keyword <- findInPyClass(pyClass, reference)
      } yield keyword
      )
  }

  def findInPyFile(pyFile: PyFile, reference: String) = {
    for {
      keyword <- PsiTreeUtil.findChildrenOfType(pyFile, classOf[PyFunction])
      if pyFunctionMatches(keyword, reference) && Option(keyword.getContainingClass).isEmpty
    } yield keyword
  }

  def findInPyClass(pyClass: PyClass, reference: String) = {
    for {
      keyword <- pyClass.getMethods
      if pyFunctionMatches(keyword, reference)
    } yield keyword
  }

  def pyFunctionMatches(pyFunc: PyFunction, ref: String) : Boolean = pyFunc.getName.toLowerCase matches ref.replaceAll(" ", "_").toLowerCase

  def utilsPsiElement: PsiElement = element
}
