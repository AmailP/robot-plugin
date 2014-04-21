package amailp.intellij.robot.extensions

import com.intellij.openapi.project.Project

class NamesValidator extends com.intellij.lang.refactoring.NamesValidator {
  def isKeyword(name: String, project: Project): Boolean = false
  def isIdentifier(name: String, project: Project): Boolean = true
}
