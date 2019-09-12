package amailp.intellij.robot.psi

import amailp.intellij.robot.psi.utils.RobotPsiUtils
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

class LibraryValue(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils with Library {

  def getType: String = "Library"

  override def getText: String = getNode.getText

  override def getReferences: Array[PsiReference] = ReferenceProvidersRegistry.getReferencesFromProviders(this)
}
