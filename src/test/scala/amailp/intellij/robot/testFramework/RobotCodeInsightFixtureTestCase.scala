package amailp.intellij.robot.testFramework

import java.io.File

import com.intellij.psi.{PsiPolyVariantReference, ResolveResult}
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.junit.Ignore
import org.scalatest.Matchers
import org.scalatest.junit.AssertionsForJUnit

@Ignore
class RobotCodeInsightFixtureTestCase extends LightPlatformCodeInsightFixtureTestCase with Matchers with AssertionsForJUnit  {
  override def getTestDataPath =
    new File(this.getClass.getClassLoader.getResource("complete.robot").toURI).getParent

  def copyFilesToProjectSkipDir(files: String*): Unit = files
    .map(f => myFixture.copyFileToProject(f, f.substring(f.indexOf('/'))))
    .headOption
    .foreach(myFixture.configureFromExistingVirtualFile)

  def getResolvedPsisAtCaret: Array[ResolveResult] =
    myFixture.getFile
      .findElementAt(myFixture.getCaretOffset)
      .getParent
      .getReferences
      .flatMap(_.asInstanceOf[PsiPolyVariantReference].multiResolve(false))

}
