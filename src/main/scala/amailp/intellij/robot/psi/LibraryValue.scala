package amailp.intellij.robot.psi

import amailp.intellij.robot.psi.utils.RobotPsiUtils
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.{PsiReference, PsiElement, PsiNameIdentifierOwner}


class LibraryValue(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils with Library with PsiNameIdentifierOwner {

  override def getName: String = getText

  override def setName(name: String): PsiElement = {
//    No refactoring support yet, thus skipping.
    this
  }

  def getType: String = "Library"

  override def getText: String = getNode.getText

  override def getNameIdentifier: PsiElement = this

  override def getReferences: Array[PsiReference] = ReferenceProvidersRegistry.getReferencesFromProviders(this)
}
