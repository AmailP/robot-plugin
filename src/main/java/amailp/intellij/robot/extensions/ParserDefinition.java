package amailp.intellij.robot.extensions;

import amailp.intellij.robot.elements.RobotTokenTypes;
import amailp.intellij.robot.lang.RobotLanguage;
import amailp.intellij.robot.lexer.RobotLexer;
import amailp.intellij.robot.parser.PsiElementBuilder;
import amailp.intellij.robot.parser.RobotParser$;
import amailp.intellij.robot.psi.RobotPsiFile;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class ParserDefinition implements com.intellij.lang.ParserDefinition {

    @Override
    @NotNull
    public Lexer createLexer(Project project) {
        return new RobotLexer();
    }

    @Override
    @NotNull
    public TokenSet getWhitespaceTokens() {
        return RobotTokenTypes.WhitespacesTokens;
    }


    @Override
    @NotNull
    public TokenSet getCommentTokens() {
        return RobotTokenTypes.CommentsTokens;
    }

    @Override
    @NotNull
    public TokenSet getStringLiteralElements() {
        return RobotTokenTypes.StringLiteralElements;
    }

    @Override
    @NotNull
    public PsiParser createParser(Project project) {
        return RobotParser$.MODULE$;
    }

    @Override
    @NotNull
    public IFileElementType getFileNodeType() {
        return ParserDefinition.RobotFileElementType;
    }

    @Override
    @NotNull
    public PsiElement createElement(ASTNode node) {
        return new PsiElementBuilder(node).build();
    }

    @Override
    @NotNull
    public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new RobotPsiFile(viewProvider);
    }

    final static IFileElementType RobotFileElementType = new IFileElementType(RobotLanguage.Instance());
}
