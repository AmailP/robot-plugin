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

class KeywordReference(reference: String) {
  private val qReference: QualifiedName = QualifiedName.fromDottedString(reference)
  def keywordName: String = qReference.getLastComponent
  def libraryName: Option[String] =
    if (qReference.getComponentCount == 1) None else Some(qReference.removeLastComponent().toString)
}

class PythonKeywordToDefinitionReference(element: PsiElement, textRange: TextRange)
    extends PsiReferenceBase[PsiElement](element, textRange)
    with PsiPolyVariantReference
    with ExtRobotPsiUtils {

  override def getVariants = Array.empty

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    for {
      keywordDefinition <- findMatchingInLibraries(new KeywordReference(element.getText))
    } yield new PsiElementResolveResult(keywordDefinition)
  }.toArray

  override def resolve: PsiElement = multiResolve(false).headOption.map(_.getElement).orNull

  private def findMatchingInLibraries(reference: KeywordReference) = {

    val libraries = currentRobotFile.getImportedLibraries.filter(
        l => reference.libraryName.forall(_.equalsIgnoreCase(l.getRobotName))
    )

    type find[T] = (String, Project, Boolean) => util.Collection[T]

    def mmm(qName: QualifiedName, pySomething: NavigationItem) =
      qName.getComponentCount == 1 || ((pySomething.getPresentation.getLocationString != null) &&
        (pySomething.getPresentation.getLocationString contains qName.removeLastComponent().toString))

    def findWith[T <: NavigationItem](index: find[T], find: (T, String) => Iterable[PsiElement]) =
      for {
        library <- libraries
        elem <- index(library.getQualifiedName.getLastComponent, element.getProject, true)
        if mmm(library.getQualifiedName, elem)
        psiElement <- find(elem, reference.keywordName)
      } yield psiElement

    findWith(PyModuleNameIndex.find, findInPyFile) ++ findWith(PyClassNameIndex.find, findInPyClass)
  }

  private def findInPyFile(pyFile: PyFile, reference: String) =
    for {
      keyword <- PsiTreeUtil.findChildrenOfType(pyFile, classOf[PyFunction])
      if Option(keyword.getContainingClass).isEmpty && pyElementMatches(keyword, reference)
    } yield keyword

  private def findInPyClass(pyClass: PyClass, name: String): Iterable[PyFunction] = {
    val process = new MethodFinderWithoutUnderscoresAndSpaces(name)
    pyClass.visitMethods(process, true, null)
    process.getResult
  }

  def utilsPsiElement: PsiElement = element
}
