package amailp.intellij.robot.psi

import amailp.intellij.robot.psi.reference.LibraryToDefinitionReference
import amailp.intellij.robot.psi.utils.RobotPsiUtils
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode


class Library(node: ASTNode) extends ASTWrapperPsiElement(node) with RobotPsiUtils {

  val stdLibs = List("BuiltIn", "Collections", "DateTime", "Dialogs", "Easter", "OperatingSystem", "Process", "Remote",
                     "Reserved", "Screenshot", "String", "Telnet", "XML")
  val deprecatedStdLibs = List("DeprecatedBuiltIn", "DeprecatedOperatingSystem")

  def getType: String = "Library"
  def getDescriptiveName: String = getNode.getText

  override def getReference = new LibraryToDefinitionReference(this)
}
