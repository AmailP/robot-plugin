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
    final IElementType Whitespaces = new RobotIElementType("Whitespaces");

    final TokenSet WhitespacesTokens = TokenSet.EMPTY;
    final TokenSet CommentsTokens = TokenSet.EMPTY;
    final TokenSet StringLiteralElements = TokenSet.EMPTY;
    final TokenSet HeaderTokens = TokenSet.create(SettingsHeader, TestCasesHeader, KeywordsHeader);
}