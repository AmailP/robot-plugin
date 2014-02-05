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
SpaceChars = [\ \t\f]
Separator = [\t\f] | {SpaceChars} {SpaceChars}+
Comment = "#" .*

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

<YYINITIAL> {AnyChar}           { yypushback(yylength()); yybegin(LINE); }
<LINE> {
    {SpaceChars}+ / {Comment}   { return RobotTokenTypes.IrrelevantSpaces; }
    {SpaceChars}+ $             { return RobotTokenTypes.IrrelevantSpaces; }
    {Comment}                   { return RobotTokenTypes.Comment; }

    {SettingsHeader}            { return RobotTokenTypes.SettingsHeader; }
    {TestCasesHeader}           { return RobotTokenTypes.TestCasesHeader; }
    {KeywordsHeader}            { return RobotTokenTypes.KeywordsHeader; }
    {VariablesHeader}           { return RobotTokenTypes.VariablesHeader; }

    {Variable}                  { return RobotTokenTypes.Variable; }
    {ListVariable}              { return RobotTokenTypes.ListVariable; }
    {TestCaseSetting}           { return RobotTokenTypes.TestCaseSetting; }
    {Word}                      { return RobotTokenTypes.Word; }
    {Space}                     { return RobotTokenTypes.Space; }
    {Separator}                 { return RobotTokenTypes.Separator; }
    <<EOF>>                     { return RobotTokenTypes.LineTerminator; }
    {LineTerminator}            { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator; }
}

//.                               { return RobotTokenTypes.BadCharacter; }