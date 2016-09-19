package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase
import com.jetbrains.python.psi.PyFunction

class PythonKeywordToDefinitionReferenceTest extends RobotCodeInsightFixtureTestCase {

  def testSimpleReference(): Unit = {
    copyFilesToProjectSkipDir(
      "PythonKeywordToDefinitionReferenceTest/caret_inside_hello.robot",
      "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a [PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "hello"
  }

  def testReferenceFromMultipleWordKeyword(): Unit = {
    copyFilesToProjectSkipDir(
      "PythonKeywordToDefinitionReferenceTest/caret_inside_hello_world.robot",
      "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a [PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "hello_world"
  }

  def testCurlyBracesDoNotRaiseException(): Unit = {
    copyFilesToProjectSkipDir(
      "PythonKeywordToDefinitionReferenceTest/keyword_with_curly_braces.robot"
    )

    noException should be thrownBy {
      getResolvedPsisAtCaret
    }
  }
}
