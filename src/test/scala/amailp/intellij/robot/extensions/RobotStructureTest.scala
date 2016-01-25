package amailp.intellij.robot.extensions

import amailp.intellij.robot.psi.RobotPsiFile
import amailp.intellij.robot.structureView.RobotTreeBasedStructureViewBuilder
import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase

class RobotStructureTest extends RobotCodeInsightFixtureTestCase {

  def testVariablesInStructure(): Unit = {
    val files = myFixture.configureByFiles(
      "RobotStructureTest/one_variable.robot"
    ).map(_.asInstanceOf[RobotPsiFile])

    val oneVariable = files.head

    val structureViewModel =
      new RobotTreeBasedStructureViewBuilder(oneVariable).createStructureViewModel(myFixture.getEditor)

    val root = structureViewModel.getRoot
    root.getChildren should have size 1

    val variables = root.getChildren()(0)
    variables.getPresentation.getPresentableText should equal ("Variables")
    variables.getChildren should have size 1

    val firstVariable = variables.getChildren()(0)
    firstVariable.getPresentation.getPresentableText should equal ("${first}")
    firstVariable.getChildren should have size 0
  }
}
