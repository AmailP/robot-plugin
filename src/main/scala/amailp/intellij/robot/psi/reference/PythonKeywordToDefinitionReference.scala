package amailp.intellij.robot.psi.reference

import java.util
import java.util.regex.Pattern

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
import scala.util.matching.Regex


class PythonKeywordToDefinitionReference(element: PsiElement, textRange: TextRange) extends PsiReferenceBase[PsiElement](element, textRange)
  with PsiPolyVariantReference with ExtRobotPsiUtils {

  override def getVariants = Array.empty

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      keywordDefinition <- findMatchingInLibraries(element.getText)
    } yield new PsiElementResolveResult(keywordDefinition)
  }.toArray

  override def resolve: PsiElement = multiResolve(false).headOption.map(_.getElement).orNull

  def findMatchingInLibraries(reference: String) = {
    val referenceRegex = Pattern.quote(reference.replaceAll(" ", "_").toLowerCase).r
    val libraryQNames = currentRobotFile.getImportedLibraries.map(l => QualifiedName.fromDottedString(l.getText))

    type find[T] = (String, Project, Boolean) => util.Collection[T]

    def mmm(qName: QualifiedName, pySomething: NavigationItem) =
      qName.getComponentCount == 1 || (pySomething.getPresentation.getLocationString contains qName.removeLastComponent().toString)

    def findWith[T<:NavigationItem](index: find[T], find: (T, Regex) => Iterable[PsiElement]) = for {
        qName <- libraryQNames
        elem <- index(qName.getLastComponent, element.getProject, true)
        if mmm(qName, elem)
        psiElement <- find(elem, referenceRegex)
      } yield psiElement

    findWith(PyModuleNameIndex.find, findInPyFile) ++ findWith(PyClassNameIndex.find, findInPyClass)
  }

  def findInPyFile(pyFile: PyFile, reference: Regex) = for {
      keyword <- PsiTreeUtil.findChildrenOfType(pyFile, classOf[PyFunction])
      if pyFunctionMatches(keyword, reference) && Option(keyword.getContainingClass).isEmpty
    } yield keyword

  def findInPyClass(pyClass: PyClass, reference: Regex) = for {
      pyClass <- pyClass +: pyClass.getSuperClasses(null)
      keyword <- pyClass.getMethods
      if pyFunctionMatches(keyword, reference)
    } yield keyword

  def pyFunctionMatches(pyFunc: PyFunction, reference: Regex) : Boolean =
    pyFunc.getName.toLowerCase match {
      case reference() => true
      case _ => false
    }

  def utilsPsiElement: PsiElement = element
}
