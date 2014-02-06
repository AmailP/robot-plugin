package amailp.idea

import com.intellij.lang.annotation.{AnnotationHolder, Annotator}
import com.intellij.psi.PsiElement
import amailp.psi._
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import DefaultLanguageHighlighterColors._
import com.intellij.openapi.editor.colors.TextAttributesKey

class RobotAnnotator extends Annotator {
  def annotate(element: PsiElement, holder: AnnotationHolder) {

    def highlightAs(attr: TextAttributesKey) {
      holder.createInfoAnnotation(element, null).setTextAttributes(attr)
    }

    element match {
      case Ellipsis(_) => highlightAs(LINE_COMMENT)
      case KeywordName(_) => highlightAs(KEYWORD)
      case TestCaseName(_) => highlightAs(LABEL)
      case SettingName(_) => highlightAs(INSTANCE_FIELD)
      case _ =>
    }
  }
}
