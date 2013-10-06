package amailp.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import amailp.elements.RobotTokenTypes;

%%

%class _RobotLexer
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

<YYINITIAL> {LineTerminator}            { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator; }

<YYINITIAL> {Header}                    { yybegin(YYINITIAL); return RobotTokenTypes.Header; }

<YYINITIAL> {Variable}                  { yybegin(YYINITIAL); return RobotTokenTypes.Variable; }

<YYINITIAL> {ListVariable}              { yybegin(YYINITIAL); return RobotTokenTypes.ListVariable; }

<YYINITIAL> {Word}                      { yybegin(YYINITIAL); return RobotTokenTypes.Word; }

<YYINITIAL> {TestCaseSetting}           { yybegin(YYINITIAL); return RobotTokenTypes.TestCaseSetting; }

<YYINITIAL> {Space}                     { yybegin(YYINITIAL); return RobotTokenTypes.Space; }

<YYINITIAL> {Whitespaces}               { yybegin(YYINITIAL); return RobotTokenTypes.Whitespaces; }

.                                       { return RobotTokenTypes.BadCharacter; }