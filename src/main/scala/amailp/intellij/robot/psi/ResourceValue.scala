package amailp.intellij.robot.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.psi._
import amailp.intellij.robot.psi.reference.ResourceValueReference

class ResourceValue(node: ASTNode) extends ASTWrapperPsiElement(node) {
  override def getReference: ResourceValueReference = new ResourceValueReference(this)
}
