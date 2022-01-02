package amailp.intellij.robot.lang

import com.intellij.lang.Language

class RobotLanguage private extends Language("Robotframework")

object RobotLanguage {
  val Instance = new RobotLanguage
}
