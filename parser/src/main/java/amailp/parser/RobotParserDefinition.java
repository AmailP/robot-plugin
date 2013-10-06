package amailp.parser;

import amailp.elements.RobotASTTypes;
import amailp.elements.RobotTokenTypes;
import amailp.language.RobotLanguage;
import amailp.lexer.RobotLexer;
import amailp.psi.impl.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class RobotParserDefinition implements ParserDefinition {
//    public static final IFileElementType FILE = new IFileElementType(Language.<RobotLanguage>findInstance(RobotLanguage.class));
    public static final IFileElementType RobotFileElementType = new IFileElementType(RobotLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new RobotLexer();
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return RobotTokenTypes.WhitespacesTokens;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return RobotTokenTypes.CommentsTokens;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return RobotTokenTypes.StringLiteralElements;
    }

    @Override
    public PsiParser createParser(Project project) {
        return new RobotParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return RobotFileElementType;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        final IElementType type = node.getElementType();

        if (type == RobotASTTypes.Sentence) {
            return new RobotSentenceImpl(node);
        } else if (type == RobotASTTypes.Phrase) {
            return new RobotPhraseImpl(node);
        } else if (type == RobotASTTypes.Table) {
            return new RobotTableImpl(node);
        } else if (type == RobotASTTypes.TableList) {
            return new RobotTableListImpl(node);
        }

        //LOG.error("Alien element type [" + type + "]. Can't create Property PsiElement for that.");

        return new ASTWrapperPsiElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new RobotFileImpl(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
