package amailp.intellij.robot.extensions

import com.intellij.pom.PomTarget
import com.intellij.psi.PsiElement
import com.intellij.util.Consumer
import amailp.intellij.robot.psi.KeywordName

class DeclarationSearcher extends com.intellij.pom.PomDeclarationSearcher {
  def findDeclarationsAt(element: PsiElement, offsetInElement: Int, consumer: Consumer[PomTarget]): Unit = {
    element match {
      case keywordName: KeywordName => consumer.consume(keywordName.getDefinition)
      case _ =>
    }
  }
}
