package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase
import com.intellij.psi.{PsiPolyVariantReference, ResolveResult}
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.jetbrains.python.psi.PyClass

class LibraryReferencesTest extends RobotCodeInsightFixtureTestCase {

  def copyFilesToProjectSkipDir(files: String*): Unit = files
    .map(f => myFixture.copyFileToProject(f, f.substring(f.indexOf('/'))))
    .headOption
    .foreach(myFixture.configureFromExistingVirtualFile)

  def getResolvedPsisAtCaret(fixture: CodeInsightTestFixture): Array[ResolveResult] =
    fixture.getFile
      .findElementAt(fixture.getCaretOffset)
      .getParent
      .getReferences
      .head
      .asInstanceOf[PsiPolyVariantReference]
      .multiResolve(false)

  def containigFileNameOf(resolveResult: ResolveResult): String =
    resolveResult.getElement.getContainingFile.getName

  def testDanglingReference(): Unit = {
    copyFilesToProjectSkipDir("LibraryReferencesTest/using_module_a.robot")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesWithoutPackage(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_module_a.robot",
      "LibraryReferencesTest/module_a.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    containigFileNameOf(resolvedPsis.head) should equal ("module_a.py")
  }

  def testReferencesShouldObserveCaseSensitivity(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_module_a_wrong_case.robot",
      "LibraryReferencesTest/module_a.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesAllowPackages(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_module_1_module_b.robot",
      "LibraryReferencesTest/module_1/module_b.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    containigFileNameOf(resolvedPsis.head) should equal ("module_b.py")
  }

  def testReferencesShouldHavePackageIfPresent(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_module_b_no_package.robot",
      "LibraryReferencesTest/module_1/module_b.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesAllowNestedPackages(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_module_1_module_2_module_c.robot",
      "LibraryReferencesTest/module_1/module_2/module_c.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    containigFileNameOf(resolvedPsis.head) should equal ("module_c.py")
  }

  def testReferencesShouldNotMatchPartialPackages(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_module_2_module_c.robot",
      "LibraryReferencesTest/module_1/module_2/module_c.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesAllowClass(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_ClassA_ClassA.robot",
      "LibraryReferencesTest/ClassA.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    resolvedPsis.head.getElement.asInstanceOf[PyClass].getName should equal ("ClassA")
  }

  def testReferencesAllowClassWithNameOtherThanModule(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_ClassA_ClassB.robot",
      "LibraryReferencesTest/ClassA.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    resolvedPsis.head.getElement.asInstanceOf[PyClass].getName should equal ("ClassB")
  }

  def testReferencesAllowClassWithoutModule(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_ClassA.robot",
      "LibraryReferencesTest/ClassA.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    resolvedPsis.head.getElement.asInstanceOf[PyClass].getName should equal ("ClassA")
  }
}
