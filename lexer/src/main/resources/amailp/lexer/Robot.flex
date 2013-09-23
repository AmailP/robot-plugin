package amailp.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import amailp.psi.RobotTypes;
import com.intellij.psi.TokenType;

%%

%class RobotLexer
%implements FlexLexer
%public
%unicode
%function advance
%type IElementType
%eof{
return;
%eof}

LineTerminator = \n | \r | \r\n
Space = "\ "
WhitespaceChar = [\ \t\f]
Whitespaces = {WhitespaceChar} {WhitespaceChar}+

HeaderDelimiter = "***"
Header = {HeaderDelimiter} ~{HeaderDelimiter}

WordChar = [a-zA-Z,\.\!\?]
Word = {WordChar}+

TestCaseSetting = "[" {Word} "]"

Variable = "${" {Word} "}"
ListVariable = "@{" {Word} "}"

%%

<YYINITIAL> {LineTerminator}            { yybegin(YYINITIAL); return RobotTypes.LineTerminator; }

<YYINITIAL> {Header}                    { yybegin(YYINITIAL); return RobotTypes.Header; }

<YYINITIAL> {Variable}                  { yybegin(YYINITIAL); return RobotTypes.Variable; }

<YYINITIAL> {ListVariable}              { yybegin(YYINITIAL); return RobotTypes.ListVariable; }

<YYINITIAL> {Word}                      { yybegin(YYINITIAL); return RobotTypes.Word; }

<YYINITIAL> {TestCaseSetting}           { yybegin(YYINITIAL); return RobotTypes.TestCaseSetting; }

<YYINITIAL> {Space}                     { yybegin(YYINITIAL); return RobotTypes.Space; }

<YYINITIAL> {Whitespaces}               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

.                                       { return TokenType.BAD_CHARACTER; }