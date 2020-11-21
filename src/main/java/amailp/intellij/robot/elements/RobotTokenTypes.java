package amailp.intellij.robot.elements;

import amailp.intellij.robot.lexer.RobotIElementType;
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
    final IElementType TasksHeader = new RobotIElementType("TasksHeader");
    final IElementType CommentsHeader = new RobotIElementType("CommentsHeader");

    final IElementType Ellipsis = new RobotIElementType("Ellipsis");
    final IElementType ScalarVariable = new RobotIElementType("ScalarVariable");
    final IElementType ListVariable = new RobotIElementType("ListVariable");
    final IElementType DictionaryVariable = new RobotIElementType("DictionaryVariable");
    final IElementType EnvironmentVariable = new RobotIElementType("EnvironmentVariable");
    final IElementType TestCaseSetting = new RobotIElementType("TestCaseSetting");
    final IElementType Word = new RobotIElementType("Word");
    final IElementType Space = new RobotIElementType("Space");
    final IElementType Separator = new RobotIElementType("Separator");
    final IElementType IrrelevantSpaces = new RobotIElementType("IrrelevantSpaces");
    final IElementType BlankLine = new RobotIElementType("BlankLine");
    final IElementType Comment = new RobotIElementType("Comment");
    final IElementType WithName = new RobotIElementType("LibraryAliasSeparator");

    final TokenSet WhitespacesTokens = TokenSet.create(IrrelevantSpaces, BlankLine);
    final TokenSet CommentsTokens = TokenSet.create(Comment);
    final TokenSet StringLiteralElements = TokenSet.EMPTY;
    final TokenSet HeaderTokens = TokenSet.create(SettingsHeader, TestCasesHeader, KeywordsHeader, VariablesHeader, TasksHeader);
}