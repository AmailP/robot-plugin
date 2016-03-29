package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.ast.Keyword
import amailp.intellij.robot.psi.{Keyword => psiKeyword}
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi._
import com.intellij.util.ProcessingContext

class KeywordReferenceContributor extends PsiReferenceContributor {

  override def registerReferenceProviders(registrar: PsiReferenceRegistrar) {
    registrar.registerReferenceProvider(PlatformPatterns.psiElement(Keyword),
      new PsiReferenceProvider() {
        override def getReferencesByElement(element: PsiElement, context: ProcessingContext) : Array[PsiReference] = {
          List[PsiReference](new KeywordToDefinitionReference(new psiKeyword(element.getNode)))
        }.toArray
      })
  }
}
