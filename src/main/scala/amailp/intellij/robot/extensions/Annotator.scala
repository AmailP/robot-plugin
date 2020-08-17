package amailp.intellij.robot.extensions

import com.intellij.lang.annotation.{AnnotationHolder, HighlightSeverity}
import com.intellij.psi.PsiElement
import amailp.intellij.robot.psi._
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import DefaultLanguageHighlighterColors._
import com.intellij.openapi.editor.colors.TextAttributesKey

class Annotator extends com.intellij.lang.annotation.Annotator {
  def annotate(element: PsiElement, holder: AnnotationHolder) {

    def highlightAs(attr: TextAttributesKey) {
      holder.newSilentAnnotation(HighlightSeverity.INFORMATION).textAttributes(attr)
    }

    element match {
      case _: Ellipsis => highlightAs(LINE_COMMENT)
      case _: KeywordName => highlightAs(KEYWORD)
      case _: Keyword => highlightAs(NUMBER)
      case _: TestCaseName => highlightAs(LABEL)
      case _: SettingName => highlightAs(INSTANCE_FIELD)
      case _ =>
    }
  }
}
