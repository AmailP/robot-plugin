package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase
import com.jetbrains.python.psi.{PyClass, PyFile, PyFunction}

class PythonKeywordToDefinitionReferenceTest extends RobotCodeInsightFixtureTestCase {

  def testSimpleReference(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_hello.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "hello"
  }

  def testReferenceFromMultipleWordKeyword(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_hello_world.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "hello_world"
  }

  def testReferenceIgnoringSpaces(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_no_underscores.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "nounderscores"
  }

  def testReferenceToBeIgnored(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_to_be_ignored.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 0
  }

  def testReferenceToClassMethod(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_keyword_from_class_method.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "action_one"
  }

  def testReferenceToDerivedClassMethod(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_derived_class_keyword.robot",
        "PythonKeywordToDefinitionReferenceTest/DerivedClass.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret

    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "keyword_a"
  }

  def testReferenceToMultipleDefinedMethods(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/caret_inside_multiple_defined.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )

    val resolvedPsis = getResolvedPsisAtCaret
    resolvedPsis should have size 2

    val moduleMethod = resolvedPsis.apply(0)
    val classMethod = resolvedPsis.apply(1)
    moduleMethod.getElement shouldBe a[PyFunction]
    moduleMethod.getElement.asInstanceOf[PyFunction].getName shouldBe "multiple_defined"
    moduleMethod.getElement.getParent.asInstanceOf[PyFile].getName shouldBe "module_a.py"
    classMethod.getElement shouldBe a[PyFunction]
    classMethod.getElement.asInstanceOf[PyFunction].getName shouldBe "multiple_defined"
    classMethod.getElement.getParent.getParent.asInstanceOf[PyClass].getName shouldBe "SampleClass"
  }

  def testCurlyBracesDoNotRaiseException(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/keyword_with_curly_braces.robot"
    )

    noException should be thrownBy {
      getResolvedPsisAtCaret
    }
  }

  def testSynonymReferenceToKeyword(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/librarySynonyms/caret_inside_class_keyword.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )
    val resolvedPsis = getResolvedPsisAtCaret
    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "action_one"
  }

  def testSynonymReferenceToKeywordWithLibraryPrefix(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/librarySynonyms/caret_inside_class_keyword_with_library_prefix.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )
    val resolvedPsis = getResolvedPsisAtCaret
    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "action_one"
  }

  def testSynonymReferenceToKeywordWithLowercasePrefix(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/librarySynonyms/caret_inside_class_keyword_with_lowercase_prefix.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )
    val resolvedPsis = getResolvedPsisAtCaret
    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "action_one"
  }

  def testSynonymReferenceToKeywordWithDottedPrefix(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/librarySynonyms/caret_inside_class_keyword_with_dotted_prefix.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )
    val resolvedPsis = getResolvedPsisAtCaret
    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "action_one"
  }

  def testSynonymReferenceToMultipleDefinedMethods(): Unit = {
    copyFilesToProjectSkipDir(
        "PythonKeywordToDefinitionReferenceTest/librarySynonyms/caret_inside_class_keyword_multiple_defined.robot",
        "PythonKeywordToDefinitionReferenceTest/module_a.py"
    )
    val resolvedPsis = getResolvedPsisAtCaret
    resolvedPsis should have size 1
    resolvedPsis.head.getElement shouldBe a[PyFunction]
    resolvedPsis.head.getElement.asInstanceOf[PyFunction].getName shouldBe "multiple_defined"
    resolvedPsis.head.getElement.getParent.getParent.asInstanceOf[PyClass].getName shouldBe "SampleClass"
  }

}
