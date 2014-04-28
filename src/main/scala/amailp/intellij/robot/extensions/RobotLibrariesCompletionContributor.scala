package amailp.intellij.robot.extensions

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.LookupElementBuilder
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi.{KeywordDefinition, TestCaseDefinition}
import com.jetbrains.python.psi.stubs.PyClassNameIndex
import com.jetbrains.python.psi.{PyFunction, PyClass}
import java.util
import scala.collection.JavaConversions._

class RobotLibrariesCompletionContributor extends CompletionContributor {

  extend(CompletionType.BASIC,
  PlatformPatterns.psiElement(RobotTokenTypes.Word),
  new CompletionProvider[CompletionParameters]() {
    override def addCompletions(
                                 completionParameters: CompletionParameters,
                                 processingContext: ProcessingContext,
                                 completionResultSet:  CompletionResultSet) = {
      completionParameters.getPosition.getParent.getParent match {
        case _: TestCaseDefinition | _: KeywordDefinition =>
          val coll: util.Collection[PyClass] = PyClassNameIndex.find("Collections", completionParameters.getPosition.getProject, true)
          val scoll: Iterable[PyClass] = coll
          for {
            clazz: PyClass <- scoll
            func: PyFunction <- clazz.getMethods
          } println(func.getName)
          completionResultSet.addElement(LookupElementBuilder.create("something"))
        case _ =>
          completionResultSet.addElement(LookupElementBuilder.create("somethung"))
      }
    }
  })
}
