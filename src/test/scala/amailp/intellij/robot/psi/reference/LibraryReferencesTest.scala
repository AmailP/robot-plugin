package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase
import com.intellij.psi.{PsiPolyVariantReference, ResolveResult}
import com.intellij.testFramework.fixtures.CodeInsightTestFixture

class LibraryReferencesTest extends RobotCodeInsightFixtureTestCase {

  def copyFilesToProjectSkipDir(files: String*): Unit = files
    .map(f => myFixture.copyFileToProject(f, f.substring(f.indexOf('/'))))
    .headOption
    .foreach(myFixture.configureFromExistingVirtualFile(_))

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
    copyFilesToProjectSkipDir("LibraryReferencesTest/using_library_a.robot")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesWithoutPackage(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_library_a.robot",
      "LibraryReferencesTest/library_a.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    containigFileNameOf(resolvedPsis.head) should equal ("library_a.py")
  }

  def testReferencesShouldObserveCaseSensitivity(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_library_a_wrong_case.robot",
      "LibraryReferencesTest/library_a.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesAllowPackages(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_pkg_1_library_b.robot",
      "LibraryReferencesTest/pkg_1/library_b.py" )

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 1
    containigFileNameOf(resolvedPsis.head) should equal ("library_b.py")
  }

  def testReferencesShouldHavePackageIfPresent(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_library_b_no_package.robot",
      "LibraryReferencesTest/pkg_1/library_b.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }

  def testReferencesShouldNotMatchPartialPackages(): Unit = {
    copyFilesToProjectSkipDir(
      "LibraryReferencesTest/using_library_c_partial_package.robot",
      "LibraryReferencesTest/pkg_1/pkg_2/library_c.py")

    val resolvedPsis = getResolvedPsisAtCaret(myFixture)

    resolvedPsis should have size 0
  }
}
