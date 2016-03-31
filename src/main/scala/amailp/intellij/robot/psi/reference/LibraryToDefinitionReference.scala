package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import com.intellij.psi._
import com.intellij.psi.util.QualifiedName
import com.jetbrains.python.psi.impl.PyElementPresentation
import com.jetbrains.python.psi.resolve.QualifiedNameFinder
import com.jetbrains.python.psi.stubs.{PyClassNameIndex, PyModuleNameIndex}
import com.jetbrains.python.psi.{PyElement, PyFile}

import scala.collection.JavaConversions._
import scala.collection.breakOut


class LibraryToDefinitionReference(element: PsiElement) extends PsiReferenceBase[PsiElement](element)
  with PsiPolyVariantReference with ExtRobotPsiUtils {

  override def getVariants = (for {
      lib <- currentRobotFile.getImportedLibraries
    } yield LookupElementBuilder.create(lib.getText)
      .withCaseSensitivity(false)
      .withTypeText("Library", true)
      .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      .asInstanceOf[AnyRef])(breakOut)

  override def multiResolve(incompleteCode: Boolean): Array[ResolveResult] = {
    val qNameLast = QualifiedName.fromDottedString(element.getText).getLastComponent
    def getClassIfHasSameName(file: PyFile): PyElement =
      Option(file.findTopLevelClass(qNameLast)).getOrElse(file)

    def findModuleOrClass(name: String) =
      PyModuleNameIndex.find(name, element.getProject, true)
        .filter(PyElementPresentation.getPackageForFile(_) == element.getText)
        .map(getClassIfHasSameName) ++
        PyClassNameIndex.find(name, element.getProject, true)
          .filter(QualifiedNameFinder.getQualifiedName(_) == element.getText)

    findModuleOrClass(qNameLast).map(new PsiElementResolveResult(_))(breakOut)
  }

  override def resolve: PsiElement = multiResolve(false).headOption.map(_.getElement).orNull

  def utilsPsiElement: PsiElement = element
}
