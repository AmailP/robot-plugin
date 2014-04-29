package amailp.intellij.robot.extensions

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi.{KeywordDefinition, TestCaseDefinition}
import com.jetbrains.python.psi.stubs.PyClassNameIndex
import com.jetbrains.python.psi.{Property, PyFunction, PyClass}
import scala.collection.JavaConversions._
import amailp.intellij.robot.file.Icons
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import com.intellij.psi.PsiElement

class RobotLibrariesCompletionContributor extends CompletionContributor {

  extend(CompletionType.BASIC,
  PlatformPatterns.psiElement(RobotTokenTypes.Word),
  new CompletionProvider[CompletionParameters]() {
    override def addCompletions(
                                 completionParameters: CompletionParameters,
                                 processingContext: ProcessingContext,
                                 completionResultSet:  CompletionResultSet) = {
      val currentPsiElem = completionParameters.getPosition
      val psiUtils: ExtRobotPsiUtils = new ExtRobotPsiUtils {
        def utilsPsiElement: PsiElement = currentPsiElem
      }
      println(s"Robot libs: ${for {
        lib <- psiUtils.currentRobotFile.getRecursivelyImportedRobotLibraries.toList
      } yield lib.getText}")
      //TODO redo filtering, this does not work well enough
      println(s"Parent: ${currentPsiElem.getParent.getParent.getText}")
      println(s"Parent type: ${currentPsiElem.getParent.getParent.getClass.getSimpleName}")
      currentPsiElem.getParent.getParent match {
        case _: TestCaseDefinition | _: KeywordDefinition =>
          for {
            lib <- psiUtils.currentRobotFile.getRecursivelyImportedRobotLibraries
            pyClass <- Option(PyClassNameIndex.findClass(s"robot.libraries.${lib.getText}.${lib.getText}", completionParameters.getPosition.getProject))
          } {
            println(s"QName: ${pyClass.getQualifiedName}")
            val ancestors: Iterable[PyClass] = pyClass.getAncestorClasses
            val pyClasses =  pyClass +: ancestors.toSeq
            for {
              pyClass <- pyClasses
              method <- pyClass.getMethods
              methodName = method.getName if !methodName.startsWith("_")
            } {
              println(s"a: ${pyClass.getName}\nmeth: ${methodName}")
              completionResultSet.addElement(LookupElementBuilder.create(methodName.replace('_',' '))
                .withCaseSensitivity(false)
                .withTypeText("RobotKeyword", true)
                .withIcon(Icons.robot)
                .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE))
            }
          }
        case _ =>
      }
    }
  })
}
