package amailp.intellij.robot.extensions

import com.intellij.psi.PsiFile
import com.intellij.openapi.project.Project
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.refactoring.rename.PsiElementRenameHandler
import FindUsagesHandlerFactory._

class RenameHandler extends PsiElementRenameHandler {
  override def invoke(project: Project, editor: Editor, file: PsiFile, dataContext: DataContext): Unit = {
    getKeywordDefinition(PsiElementRenameHandler.getElement(dataContext)) match {
      case Some(keywordDefinition) => super.invoke(project, Array(keywordDefinition), dataContext)
      case None => super.invoke(project, editor, file, dataContext)
    }
  }
}

