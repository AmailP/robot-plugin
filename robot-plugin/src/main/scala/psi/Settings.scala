package psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import amailp.elements.RobotElementType

case class Settings(node: ASTNode) extends ASTWrapperPsiElement(node){
}

case object Settings {
}
