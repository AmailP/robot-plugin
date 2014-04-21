package amailp.intellij.robot.extensions

import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import amailp.intellij.robot

class SyntaxHighlighterFactory extends com.intellij.openapi.fileTypes.SyntaxHighlighterFactory {
  def getSyntaxHighlighter(project: Project, virtualFile: VirtualFile): SyntaxHighlighter =
    new robot.highlighting.SyntaxHighlighter
}