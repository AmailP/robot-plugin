package idea

import com.intellij.lang.annotation.{AnnotationHolder, Annotator}
import com.intellij.psi.PsiElement
import psi.{KeywordTitle, TestCaseTitle}
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import DefaultLanguageHighlighterColors._
import com.intellij.openapi.editor.colors.TextAttributesKey

class RobotAnnotator extends Annotator {
  def annotate(element: PsiElement, holder: AnnotationHolder) {

    def highlightAs(attr: TextAttributesKey) {
      holder.createInfoAnnotation(element, null).setTextAttributes(attr)
    }

    element match {
      case KeywordTitle(_) => highlightAs(KEYWORD)
      case TestCaseTitle(_) => highlightAs(LABEL)
      case _ =>
    }
  }
}
