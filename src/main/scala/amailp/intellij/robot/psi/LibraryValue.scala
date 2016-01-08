package amailp.intellij.robot.psi

import amailp.intellij.robot.psi.reference.LibraryToDefinitionReference
import amailp.intellij.robot.psi.utils.RobotPsiUtils
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode


class LibraryValue(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils with Library {

  def getType: String = "Library"

  override def getText: String = getNode.getText

  override def getReference = new LibraryToDefinitionReference(this)
}
