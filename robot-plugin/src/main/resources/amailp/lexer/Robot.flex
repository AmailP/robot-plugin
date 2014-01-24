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
VariablesHeader  = "*** Variables ***" | "*** Variable ***"


WordChar = [^\ \t\f\r\n]
Word = {WordChar}+

TestCaseSetting = "[" {Word} "]"

Variable = "${" {Word} "}"
ListVariable = "@{" {Word} "}"
AnyChar = [^]

%state LINE

%%

<YYINITIAL> {AnyChar}                   { yypushback(yylength()); yybegin(LINE); }
<LINE>      {LineTerminator}            { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator; }

<LINE>      {SettingsHeader}            { return RobotTokenTypes.SettingsHeader; }
<LINE>      {TestCasesHeader}           { return RobotTokenTypes.TestCasesHeader; }
<LINE>      {KeywordsHeader}            { return RobotTokenTypes.KeywordsHeader; }
<LINE>      {VariablesHeader}           { return RobotTokenTypes.VariablesHeader; }

<LINE>      {Variable}                  { return RobotTokenTypes.Variable; }
<LINE>      {ListVariable}              { return RobotTokenTypes.ListVariable; }
<LINE>      {TestCaseSetting}           { return RobotTokenTypes.TestCaseSetting; }
<LINE>      {Word}                      { return RobotTokenTypes.Word; }
<LINE>      {Space}                     { return RobotTokenTypes.Space; }
<LINE>      {Whitespaces}               { return RobotTokenTypes.Whitespaces; }
.                                       { return RobotTokenTypes.BadCharacter; }