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

SettingsHeader = "*** Settings ***" | "*** Setting ***"
TestCasesHeader  = "*** Test Cases ***" | "*** Test Case ***"
KeywordsHeader  = "*** Keywords ***" | "*** Keyword ***"


WordChar = [a-zA-Z_,\.\!\?]
Word = {WordChar}+

TestCaseSetting = "[" {Word} "]"

Variable = "${" {Word} "}"
ListVariable = "@{" {Word} "}"

%%

<YYINITIAL> {LineTerminator}            { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator; }

<YYINITIAL> {SettingsHeader}            { yybegin(YYINITIAL); return RobotTokenTypes.SettingsHeader; }
<YYINITIAL> {TestCasesHeader}           { yybegin(YYINITIAL); return RobotTokenTypes.TestCasesHeader; }
<YYINITIAL> {KeywordsHeader}             { yybegin(YYINITIAL); return RobotTokenTypes.KeywordsHeader; }

<YYINITIAL> {Variable}                  { yybegin(YYINITIAL); return RobotTokenTypes.Variable; }
<YYINITIAL> {ListVariable}              { yybegin(YYINITIAL); return RobotTokenTypes.ListVariable; }
<YYINITIAL> {Word}                      { yybegin(YYINITIAL); return RobotTokenTypes.Word; }
<YYINITIAL> {TestCaseSetting}           { yybegin(YYINITIAL); return RobotTokenTypes.TestCaseSetting; }
<YYINITIAL> {Space}                     { yybegin(YYINITIAL); return RobotTokenTypes.Space; }
<YYINITIAL> {Whitespaces}               { yybegin(YYINITIAL); return RobotTokenTypes.Whitespaces; }
.                                       { return RobotTokenTypes.BadCharacter; }