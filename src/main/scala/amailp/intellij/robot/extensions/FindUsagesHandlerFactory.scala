package amailp.intellij.robot.extensions

import com.intellij.psi.{DelegatePsiTarget, PsiElement}
import com.intellij.pom.PomTargetPsiElement
import amailp.intellij.robot.psi.KeywordDefinition
import com.intellij.find.findUsages.FindUsagesHandler

class FindUsagesHandlerFactory extends com.intellij.find.findUsages.FindUsagesHandlerFactory {
  def canFindUsages(element: PsiElement): Boolean = getKeywordDefinition(element).isDefined

  def createFindUsagesHandler(element: PsiElement, forHighlightUsages: Boolean): FindUsagesHandler =
    getKeywordDefinition(element) match {
      case Some(keywordDefinition) => new FindUsagesHandler(keywordDefinition){}
      case None => null
    }

  private def getKeywordDefinition(element: PsiElement): Option[KeywordDefinition] = element match {
    case e: PomTargetPsiElement => e.getTarget match {
      case t: DelegatePsiTarget => t.getNavigationElement match {
        case p: KeywordDefinition => Some(p)
        case _ => None
      }
      case _ => None
    }
    case _ => None
  }
}
