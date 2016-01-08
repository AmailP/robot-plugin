package amailp.intellij.robot.psi

import amailp.intellij.robot.ast
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi._
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.python.psi.{PyFunction, PyFile}
import com.jetbrains.python.psi.stubs.PyModuleNameIndex
import scala.collection.JavaConversions._
import amailp.intellij.robot.findUsage.UsageFindable
import amailp.intellij.robot.structureView.InStructureView
import amailp.intellij.robot.file.Icons


class KeywordDefinition (node: ASTNode) extends RobotPsiElement(node) with PsiNamedElement with UsageFindable with InStructureView with PsiTarget {
  private def keywordName = getNode.findChildByType(ast.KeywordName).getPsi(classOf[KeywordName])
  override def getNodeText(useFullName: Boolean) = getName
  override def getName: String = keywordName.getText
  override def setName(name: String): PsiElement = {
    val dummyKeyword = createKeywordDefinition(name)
    this.getNode.replaceChild(keywordName.getNode, dummyKeyword.keywordName.getNode)
    this
  }
  override val element: PsiElement = this

  def getType: String = "Keyword definition"
  def getDescriptiveName: String = getName

  def structureViewText = getName
  def structureViewIcon = Icons.keyword
  def structureViewChildrenTokenTypes = Nil
}

object KeywordDefinition {
  def findMatchingInFiles(files: Stream[RobotPsiFile], reference: String) = {
    for {
      keywordDefinition <- findInFiles(files)
      if keywordDefinition.keywordName matches reference
    } yield keywordDefinition
  }

  def findInFiles(files: Stream[RobotPsiFile]) = {
    for {
      file <- files
      keywordDefinition <- findInFile(file)
    } yield keywordDefinition
  }

  def findInFile(file: RobotPsiFile) = PsiTreeUtil.findChildrenOfType(file.getNode.getPsi, classOf[KeywordDefinition]).toSet

  def findMatchingInLibraries(files: Iterable[Library], project: Project, reference: String) = {
    for {
      library <- files
      pyFile <- PyModuleNameIndex.find(library.getText, project, true)
      keyword <- findInPythonFile(pyFile)
      if keyword.getName.toLowerCase matches reference.replaceAll(" ", "_").toLowerCase
    } yield keyword
  }

  def findInPythonFile(file: PyFile) = PsiTreeUtil.findChildrenOfType(file.getNode.getPsi, classOf[PyFunction]).toSet
}
