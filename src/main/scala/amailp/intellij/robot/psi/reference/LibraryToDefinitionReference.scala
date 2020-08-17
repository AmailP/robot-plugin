package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.psi.LibraryValue
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi._
import com.intellij.psi.util.QualifiedName
import com.jetbrains.python.psi.impl.PyElementPresentation
import com.jetbrains.python.psi.resolve.QualifiedNameFinder
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import com.jetbrains.python.psi.{PyElement, PyFile}

import scala.collection.JavaConverters._
import scala.collection.breakOut

class LibraryToDefinitionReference(element: LibraryValue)
    extends PsiReferenceBase[PsiElement](element)
    with PsiPolyVariantReference
    with ExtRobotPsiUtils {

  override def getVariants =
    (for {
      lib <- currentRobotFile.getImportedLibraries
    } yield LookupElementBuilder
      .create(lib.getName)
      .withCaseSensitivity(false)
      .withTypeText("Library", true)
      .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      .asInstanceOf[AnyRef])(breakOut)

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    val qNameLast = QualifiedName.fromDottedString(element.getName).getLastComponent
    def getClassIfHasSameName(file: PyFile): PyElement =
      Option(file.findTopLevelClass(qNameLast)).getOrElse(file)

    def findModuleOrClass(name: String) =
      PyModuleNameIndex
        .find(name, element.getProject, true)
        .asScala
        .filter(PyElementPresentation.getPackageForFile(_) == element.getName)
        .map(getClassIfHasSameName) ++
        PyClassNameIndex
          .find(name, element.getProject, true)
          .asScala
          .filter(QualifiedNameFinder.getQualifiedName(_) == element.getName)

    findModuleOrClass(qNameLast).map(new PsiElementResolveResult(_))(breakOut)
  }

  override def resolve: PsiElement = multiResolve(false).headOption.map(_.getElement).orNull

  def utilsPsiElement: PsiElement = element
}
