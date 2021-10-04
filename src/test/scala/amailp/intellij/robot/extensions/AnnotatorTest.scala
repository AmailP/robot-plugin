package amailp.intellij.robot.extensions

import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase

class AnnotatorTest extends RobotCodeInsightFixtureTestCase {

  def testVariablesInAnnotator(): Unit = {
    myFixture
      .configureByFiles(
          "complete.robot"
      )

    myFixture.checkHighlighting(false, true, false, true)
  }
}
