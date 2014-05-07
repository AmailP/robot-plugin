package amailp.intellij.robot.extensions

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi.{KeywordDefinition, TestCaseDefinition}
import com.jetbrains.python.psi.stubs.PyClassNameIndex
import scala.collection.JavaConversions._
import amailp.intellij.robot.file.Icons
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import com.intellij.psi.PsiElement
import com.jetbrains.python.psi.PyParameterList

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
            libName <- robotLibrariesInScope
            pyBaseClass <- findRobotPyClass(libName)
            pyClass <- pyBaseClass +: pyBaseClass.getAncestorClasses.toSeq
            method <- pyClass.getMethods
            methodName = method.getName if !methodName.startsWith("_")
          } completionResultSet.addElement(LookupElementBuilder.create(methodName.replace('_',' '))
                .withCaseSensitivity(false)
                .withIcon(Icons.robot)
                .withTypeText(pyBaseClass.getName, true)
                .withTailText(formatMethodParameters(method.getParameterList))
                .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE))
        case _ =>
      }
      def robotLibrariesInScope =
        psiUtils.currentRobotFile.getRecursivelyImportedRobotLibraries.map(_.getText) ++ Iterable("BuiltIn")
      def findRobotPyClass(name: String) =
        Option(PyClassNameIndex.findClass(s"robot.libraries.$name.$name", currentPsiElem.getProject))
      def formatMethodParameters(parameterList: PyParameterList) = {
        for (parameter <- parameterList.getParameters)
          yield parameter.getName
      }.drop(1).mkString(" (", ", ", ")")
    }
  })

}
