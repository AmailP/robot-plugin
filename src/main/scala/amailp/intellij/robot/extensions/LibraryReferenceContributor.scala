package amailp.intellij.robot.extensions

import amailp.intellij.robot.ast.LibraryValue
import amailp.intellij.robot.psi.reference.LibraryToDefinitionReference
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi._
import com.intellij.util.ProcessingContext


class LibraryReferenceContributor extends PsiReferenceContributor {

  override def registerReferenceProviders(registrar: PsiReferenceRegistrar) {
    registrar.registerReferenceProvider(PlatformPatterns.psiElement(LibraryValue),
    new PsiReferenceProvider() {
      override def getReferencesByElement(element: PsiElement, context: ProcessingContext) : Array[PsiReference] =
        Array[PsiReference](new LibraryToDefinitionReference(element))
    })
  }
}
