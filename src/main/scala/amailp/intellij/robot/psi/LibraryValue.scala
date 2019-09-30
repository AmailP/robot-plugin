package amailp.intellij.robot.psi

import amailp.intellij.robot.ast.{LibraryAlias, LibraryName}
import amailp.intellij.robot.psi.utils.RobotPsiUtils
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.util.QualifiedName

class LibraryValue(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils with Library {
  val libraryName: ASTNode = getNode.findChildByType(LibraryName)
  assume(libraryName != null, "It should not be null because of the way a LibraryValue is parsed")

  val libraryAlias: Option[ASTNode] = Option(getNode.findChildByType(LibraryAlias))

  def getType: String = "Library"
  def getRobotName: String = libraryAlias.getOrElse(libraryName).getText
  def getQualifiedName: QualifiedName = QualifiedName.fromDottedString(libraryName.getText)

  override def getName: String = libraryName.getText
  override def getReferences: Array[PsiReference] = ReferenceProvidersRegistry.getReferencesFromProviders(this)
}
