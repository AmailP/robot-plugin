package amailp.intellij.robot.testFramework

import java.io.File

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.scalatest.Matchers
import org.scalatest.junit.AssertionsForJUnit

class RobotCodeInsightFixtureTestCase extends LightPlatformCodeInsightFixtureTestCase with Matchers with AssertionsForJUnit  {
  override def getTestDataPath =
    new File(this.getClass.getClassLoader.getResource("complete.robot").toURI).getParent
}
