package amailp.intellij.robot.psi.reference

import amailp.intellij.robot.ast.Keyword
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi._
import com.intellij.util.ProcessingContext

class PythonKeywordReferenceContributor extends PsiReferenceContributor {

  override def registerReferenceProviders(registrar: PsiReferenceRegistrar) {
    registrar.registerReferenceProvider(PlatformPatterns.psiElement(Keyword),
      new PsiReferenceProvider() {
        override def getReferencesByElement(element: PsiElement, context: ProcessingContext) : Array[PsiReference] = {
          List[PsiReference](new PythonKeywordToDefinitionReference(element, new TextRange(0, element.getText.length)))
        }.toArray
      })
  }
}
