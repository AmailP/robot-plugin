package amailp.elements;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface RobotTokenTypes {


    final IElementType BadCharacter = TokenType.BAD_CHARACTER;

    final IElementType LineTerminator = new RobotIElementType("LineTerminator");

    final IElementType SettingsHeader = new RobotIElementType("SettingsHeader");
    final IElementType TestCasesHeader = new RobotIElementType("TestCasesHeader");
    final IElementType KeywordsHeader = new RobotIElementType("KeywordsHeader");
    final IElementType VariablesHeader = new RobotIElementType("VariablesHeader");

    final IElementType Variable = new RobotIElementType("Variable");
    final IElementType ListVariable = new RobotIElementType("ListVariable");
    final IElementType TestCaseSetting = new RobotIElementType("TestCaseSetting");
    final IElementType Word = new RobotIElementType("Word");
    final IElementType Space = new RobotIElementType("Space");
    final IElementType Separator = new RobotIElementType("Separator");
    final IElementType IrrelevantSpaces = new RobotIElementType("IrrelevantSpaces");
    final IElementType Comment = new RobotIElementType("Comment");

    final TokenSet WhitespacesTokens = TokenSet.create(IrrelevantSpaces);
    final TokenSet CommentsTokens = TokenSet.create(Comment);
    final TokenSet StringLiteralElements = TokenSet.EMPTY;
    final TokenSet HeaderTokens = TokenSet.create(SettingsHeader, TestCasesHeader, KeywordsHeader, VariablesHeader);
}