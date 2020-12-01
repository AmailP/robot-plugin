package amailp.intellij.robot.psi.tables

import amailp.intellij.robot.psi.RobotPsiFile
import amailp.intellij.robot.structureView.RobotTreeBasedStructureViewBuilder
import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase

class RobotCommentsTableTest extends RobotCodeInsightFixtureTestCase {

  def testCommentsSetting(): Unit = {
    val files = myFixture
      .configureByFiles(
          "CommentsTableTest/CommentsTableSettings.robot"
      )
      .map(_.asInstanceOf[RobotPsiFile])

    val oneComments = files.head

    val structureViewModel =
      new RobotTreeBasedStructureViewBuilder(oneComments).createStructureViewModel(myFixture.getEditor)

    val root = structureViewModel.getRoot
    root.getChildren should have size 1


    val comments = root.getChildren()(0)
    comments.getPresentation.getPresentableText should equal("Comments")
    comments.getChildren should have size 1
  }
}
