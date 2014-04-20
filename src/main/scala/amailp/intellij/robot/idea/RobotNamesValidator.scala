package amailp.intellij.robot.idea

import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

class RobotNamesValidator extends NamesValidator {
  def isKeyword(name: String, project: Project): Boolean = false
  def isIdentifier(name: String, project: Project): Boolean = true
}
