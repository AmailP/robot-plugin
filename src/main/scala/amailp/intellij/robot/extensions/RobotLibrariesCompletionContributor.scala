package amailp.intellij.robot.extensions

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.{AutoCompletionPolicy, LookupElementBuilder}
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi.{KeywordDefinition, TestCaseDefinition}
import com.jetbrains.python.psi.stubs.PyClassNameIndex
import com.jetbrains.python.psi.{Property, PyFunction, PyClass}
import java.util
import scala.collection.JavaConversions._
import amailp.intellij.robot.file.Icons

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
          val c = PyClassNameIndex.findClass("robot.libraries.Collections.Collections", completionParameters.getPosition.getProject)
          //val scoll: Iterable[PyClass] = coll
          for {
            clazz: PyClass <- List(c)
          } {
            println(s"QName: ${clazz.getQualifiedName}")
            val m: scala.collection.Map[String, Property] = clazz.getProperties
            for ((k, v) <- m) {
              println(s"prop: ${k}")
            }
            val anc: Iterable[PyClass] = clazz.getAncestorClasses
            for {
              a <- anc
              m <- a.getMethods
              n = m.getName if !n.startsWith("_")
            } {
              println(s"a: ${a.getName}\nmeth: ${n}")
              completionResultSet.addElement(LookupElementBuilder.create(n.replace('_',' '))
                .withCaseSensitivity(false)
                .withTypeText("RobotKeyword", true)
                .withIcon(Icons.robot)
                .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE))
            }
          }
          completionResultSet.addElement(LookupElementBuilder.create("something"))
        case _ =>
          completionResultSet.addElement(LookupElementBuilder.create("somethung"))
      }
    }
  })
}
