package amailp.intellij.robot.testFramework

import java.io.File

import amailp.intellij.robot.psi.LibraryValue
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.{PsiPolyVariantReference, ResolveResult}
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Ignore
import org.scalatest.Matchers
import org.scalatest.junit.AssertionsForJUnit

@Ignore
class RobotCodeInsightFixtureTestCase
    extends BasePlatformTestCase
    with Matchers
    with AssertionsForJUnit {
  override def getTestDataPath =
    new File(this.getClass.getClassLoader.getResource("complete.robot").toURI).getParent

  def copyFilesToProjectSkipDir(files: String*): Unit =
    files
      .map(f => myFixture.copyFileToProject(f, f.substring(f.indexOf('/'))))
      .headOption
      .foreach(myFixture.configureFromExistingVirtualFile)

  def getResolvedPsisAtCaret: Array[ResolveResult] =
    myFixture.getFile
      .findElementAt(myFixture.getCaretOffset)
      .getParent
      .getReferences
      .flatMap(_.asInstanceOf[PsiPolyVariantReference].multiResolve(false))

  def getResolvedLibraryPsisAtCaret: Array[ResolveResult] = {
    val caretElement = myFixture.getFile.findElementAt(myFixture.getCaretOffset)
    PsiTreeUtil
      .getParentOfType(caretElement, classOf[LibraryValue])
      .getReferences
      .flatMap(_.asInstanceOf[PsiPolyVariantReference].multiResolve(false))
  }

}
