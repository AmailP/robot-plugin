package amailp.intellij.robot.psi

import amailp.intellij.robot.ast
import amailp.intellij.robot.file.Icons
import amailp.intellij.robot.findUsage.UsageFindable
import amailp.intellij.robot.structureView.InStructureView
import com.intellij.lang.ASTNode
import com.intellij.psi.{PsiNamedElement, PsiElement}

class VariableDefinition(node: ASTNode)
    extends RobotPsiElement(node)
    with InStructureView
    with PsiNamedElement
    with UsageFindable {
  private def variableName = Option(getNode.findChildByType(ast.VariableName))
  override def getName: String = variableName.map(_.getText).orNull
  override def setName(name: String): PsiElement = {
    val dummyKeyword = createVariableDefinition(name)

    variableName.foreach(vN => dummyKeyword.variableName.foreach(dVN => this.getNode.replaceChild(vN, dVN)))
    this
  }

  override def structureViewText = getName
  override def structureViewChildrenTokenTypes = Nil
  override def structureViewIcon = Icons.variable

  override def getType: String = "variable"
  override def getDescriptiveName: String = getName
}
