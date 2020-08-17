package amailp.intellij.robot.psi.utils

import com.intellij.psi.{PsiFileFactory, PsiManager, PsiElement}
import com.intellij.psi.util.PsiTreeUtil
import amailp.intellij.robot.psi._
import amailp.intellij.robot.file.FileType
import scala.collection.JavaConverters._

trait ExtRobotPsiUtils {
  def utilsPsiElement: PsiElement
  def currentRobotFile = PsiTreeUtil.getParentOfType(utilsPsiElement, classOf[RobotPsiFile])
  def originalRobotFile = currentRobotFile.getOriginalFile.asInstanceOf[RobotPsiFile]
  def currentFile = originalRobotFile.getVirtualFile
  def currentDirectory = currentFile.getParent
  def psiManager = PsiManager.getInstance(utilsPsiElement.getProject)

  def createKeyword(name: String): Keyword = {
    val dummyFile = shtg(s"""
          |*** Keywords ***
          |KeyDef
          |    $name
        """)
    PsiTreeUtil.findChildrenOfType(dummyFile.getNode.getPsi, classOf[Keyword]).asScala.head
  }

  def createKeywordDefinition(name: String): KeywordDefinition = {
    val dummyFile = shtg(s"""
          |*** Keywords ***
          |$name
        """)
    PsiTreeUtil.findChildrenOfType(dummyFile.getNode.getPsi, classOf[KeywordDefinition]).asScala.head
  }

  def createVariableDefinition(name: String): VariableDefinition = {
    val dummyFile = shtg(s"""
         |*** Variables ***
         |$name
        """)
    PsiTreeUtil.findChildrenOfType(dummyFile.getNode.getPsi, classOf[VariableDefinition]).asScala.head
  }

  def createResourceValue(path: String): ResourceValue = {
    val dummyFile = shtg(s"""
          |*** Settings ***
          |Resource    $path
        """)
    PsiTreeUtil.findChildrenOfType(dummyFile.getNode.getPsi, classOf[ResourceValue]).asScala.head
  }

  def shtg(fileContent: String): RobotPsiFile = {
    PsiFileFactory
      .getInstance(utilsPsiElement.getProject)
      .createFileFromText("dummy", FileType, fileContent.stripMargin)
      .asInstanceOf[RobotPsiFile]
  }
}
