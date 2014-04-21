package amailp.intellij.robot.extensions

import com.intellij.pom.PomTarget
import com.intellij.psi.{DelegatePsiTarget, PsiElement}
import com.intellij.util.Consumer
import amailp.intellij.robot.psi.KeywordName

class DeclarationSearcher extends com.intellij.pom.PomDeclarationSearcher {
  def findDeclarationsAt(element: PsiElement, offsetInElement: Int, consumer: Consumer[PomTarget]): Unit = {
    element match {
    //TODO instead of instantiating DelegatePsiTarget, KeywordDefinition could possibly implement PsiTarget
      case keywordName : KeywordName => consumer.consume(new DelegatePsiTarget(keywordName.getParent))
      case _ =>
    }
  }
}

