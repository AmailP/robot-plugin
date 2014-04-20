package amailp.intellij.robot.idea

import com.intellij.pom.{PomTarget, PomDeclarationSearcher}
import com.intellij.psi.{DelegatePsiTarget, PsiElement}
import com.intellij.util.Consumer
import amailp.intellij.robot.psi.KeywordName

class RobotDeclarationSearcher extends PomDeclarationSearcher {
  def findDeclarationsAt(element: PsiElement, offsetInElement: Int, consumer: Consumer[PomTarget]): Unit = {
    element match {
      case keywordName : KeywordName => consumer.consume(new DelegatePsiTarget(keywordName.getParent))
      case _ =>
    }
  }
}

