package amailp.elements;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface RobotTokenTypes {


    final IElementType BadCharacter = TokenType.BAD_CHARACTER;

    final IElementType LineTerminator = new RobotElementType("LineTerminator");
    final IElementType Header = new RobotElementType("Header");
    final IElementType Variable = new RobotElementType("Variable");
    final IElementType ListVariable = new RobotElementType("ListVariable");
    final IElementType TestCaseSetting = new RobotElementType("TestCaseSetting");
    final IElementType Word = new RobotElementType("Word");
    final IElementType Space = new RobotElementType("Space");
    final IElementType Whitespaces = new RobotElementType("Whitespaces");

    final TokenSet WhitespacesTokens = TokenSet.EMPTY;
    final TokenSet CommentsTokens = TokenSet.EMPTY;
    final TokenSet StringLiteralElements = TokenSet.EMPTY;
}