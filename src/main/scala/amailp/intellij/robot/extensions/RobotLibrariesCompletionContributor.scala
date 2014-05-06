package amailp.intellij.robot.extensions

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi.{KeywordDefinition, TestCaseDefinition}
import com.jetbrains.python.psi.stubs.PyClassNameIndex
import com.jetbrains.python.psi.PyClass
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
      //TODO rethink filtering, this maybe does not work well enough
      currentPsiElem.getParent.getParent.getParent match {
        case _: TestCaseDefinition | _: KeywordDefinition =>
          for {
            libName <- psiUtils.currentRobotFile.getRecursivelyImportedRobotLibraries.map(_.getText) ++ Iterable("BuiltIn")
            pyBaseClass <- Option(PyClassNameIndex.findClass(s"robot.libraries.$libName.$libName", completionParameters.getPosition.getProject))
          } {
            println(s"QName: ${pyBaseClass.getQualifiedName}")
            val ancestors: Iterable[PyClass] = pyBaseClass.getAncestorClasses
            val pyClasses =  pyBaseClass +: ancestors.toSeq
            for {
              pyClass <- pyClasses
              method <- pyClass.getMethods
              methodName = method.getName if !methodName.startsWith("_")
            } {
              println(s"a: ${pyClass.getName}\nmeth: $methodName")
              completionResultSet.addElement(LookupElementBuilder.create(methodName.replace('_',' '))
                .withCaseSensitivity(false)
                .withTypeText(pyBaseClass.getName, true)
                .withIcon(Icons.robot)
                .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE))
            }
          }
        case _ =>
      }
    }
  })
}
