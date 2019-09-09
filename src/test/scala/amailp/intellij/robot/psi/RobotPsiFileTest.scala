package amailp.intellij.robot.psi

import amailp.intellij.robot.testFramework.RobotCodeInsightFixtureTestCase

class RobotPsiFileTest extends RobotCodeInsightFixtureTestCase {

  def testIsRobotPsiFile(): Unit = {
    myFixture.configureByFile("RobotPsiFileTest/include_some_keywords.robot")
    myFixture.getFile shouldBe a [RobotPsiFile]
  }

  def testImportedRobotFiles(): Unit = {
    val files = myFixture.configureByFiles(
      "RobotPsiFileTest/include_some_keywords.robot",
      "RobotPsiFileTest/some_keywords.robot"
    ).map(_.asInstanceOf[RobotPsiFile])

    val includeSomeKeywords = files.head
    val someKeywords = files(1)

    val imported = includeSomeKeywords.getRecursivelyImportedRobotFiles
    imported should have size 1
    imported should contain (someKeywords)
  }

  def testImportedRobotFilesWithPathSeparator(): Unit = {
    val files = myFixture.configureByFiles(
      "RobotPsiFileTest/include_some_keywords_with_path_separator.robot",
      "RobotPsiFileTest/some_keywords.robot"
    ).map(_.asInstanceOf[RobotPsiFile])

    val includeSomeKeywords = files.head
    val someKeywords = files(1)

    val imported = includeSomeKeywords.getRecursivelyImportedRobotFiles
    imported should have size 1
    imported should contain (someKeywords)
  }

  def testRecursivelyImportedRobotFiles(): Unit = {
    val files = myFixture.configureByFiles(
      "RobotPsiFileTest/include_some_other_keywords.robot",
      "RobotPsiFileTest/some_other_keywords.robot",
      "RobotPsiFileTest/some_keywords.robot"
    ).map(_.asInstanceOf[RobotPsiFile])

    val includeSomeOtherKeywords = files.head
    val someOtherKeywords = files(1)
    val someKeywords = files(2)

    val imported = includeSomeOtherKeywords.getRecursivelyImportedRobotFiles
    imported should have size 2
    imported should contain (someOtherKeywords)
    imported should contain (someKeywords)
  }

}

