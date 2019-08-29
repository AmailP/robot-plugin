package amailp.intellij.robot.psi.reference

import java.util

import amailp.intellij.robot.psi.reference.MethodFinderWithoutUnderscoresAndSpaces.pyElementMatches
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi._
import com.intellij.psi.util.{PsiTreeUtil, QualifiedName}
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import com.jetbrains.python.psi.{PyClass, PyFile, PyFunction}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

class PythonKeywordToDefinitionReference(element: PsiElement, textRange: TextRange)
    extends PsiReferenceBase[PsiElement](element, textRange)
    with PsiPolyVariantReference
    with ExtRobotPsiUtils {

  override def getVariants = Array.empty

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      keywordDefinition <- findMatchingInLibraries(element.getText)
    } yield new PsiElementResolveResult(keywordDefinition)
  }.toArray

  override def resolve: PsiElement = multiResolve(false).headOption.map(_.getElement).orNull

  def findMatchingInLibraries(reference: String) = {
    val libraryQNames = currentRobotFile.getImportedLibraries.map(l => QualifiedName.fromDottedString(l.getText))

    type find[T] = (String, Project, Boolean) => util.Collection[T]

    def mmm(qName: QualifiedName, pySomething: NavigationItem) =
      qName.getComponentCount == 1 || ((pySomething.getPresentation.getLocationString != null) &&
        (pySomething.getPresentation.getLocationString contains qName.removeLastComponent().toString))

    def findWith[T <: NavigationItem](index: find[T], find: (T, String) => Iterable[PsiElement]) =
      for {
        qName <- libraryQNames
        elem <- index(qName.getLastComponent, element.getProject, true)
        if mmm(qName, elem)
        psiElement <- find(elem, reference)
      } yield psiElement

    findWith(PyModuleNameIndex.find, findInPyFile) ++ findWith(PyClassNameIndex.find, findInPyClass)
  }

  def findInPyFile(pyFile: PyFile, reference: String) =
    for {
      keyword <- PsiTreeUtil.findChildrenOfType(pyFile, classOf[PyFunction])
      if Option(keyword.getContainingClass).isEmpty && pyElementMatches(keyword, reference)
    } yield keyword

  def findInPyClass(pyClass: PyClass, name: String): Iterable[PyFunction] = {
    val process = new MethodFinderWithoutUnderscoresAndSpaces(name)
    pyClass.visitMethods(process, true, null)
    process.getResult
  }

  def utilsPsiElement: PsiElement = element
}
