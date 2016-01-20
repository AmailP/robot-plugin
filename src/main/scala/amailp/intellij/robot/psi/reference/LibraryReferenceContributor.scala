package amailp.intellij.robot.psi.reference

import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi._
import com.intellij.util.ProcessingContext
import amailp.intellij.robot.ast.LibraryValue


class LibraryReferenceContributor extends PsiReferenceContributor {

  override def registerReferenceProviders(registrar: PsiReferenceRegistrar) {
    registrar.registerReferenceProvider(PlatformPatterns.psiElement(LibraryValue),
    new PsiReferenceProvider() {
      override def getReferencesByElement(element: PsiElement, context: ProcessingContext) : Array[PsiReference] = {
        List[PsiReference](new LibraryToDefinitionReference(element, new TextRange(0, element.getText.length)))
      }.toArray
    })
  }
}
