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

TestCaseSetting = "[Documentation]"
    | "[Tags]"
    | "[Setup]"
    | "[Precondition]"
    | "[Teardown]"
    | "[Postcondition]"
    | "[Template]"
    | "[Timeout]"
    | "[Arguments]"
    | "[Return]"

Variable = "${" {Word} "}"
ListVariable = "@{" {Word} "}"

%state LINE

%%

<YYINITIAL> {
    {SpaceChars}* {LineTerminator}  { return RobotTokenTypes.BlankLine; }

    <LINE> {
        {SpaceChars}+ / {Comment}   { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces; }
        {SpaceChars}+ $             { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces; }
        {Comment}                   { yybegin(LINE); return RobotTokenTypes.Comment; }

        {SettingsHeader}            { yybegin(LINE); return RobotTokenTypes.SettingsHeader; }
        {TestCasesHeader}           { yybegin(LINE); return RobotTokenTypes.TestCasesHeader; }
        {KeywordsHeader}            { yybegin(LINE); return RobotTokenTypes.KeywordsHeader; }
        {VariablesHeader}           { yybegin(LINE); return RobotTokenTypes.VariablesHeader; }

        {Variable}                  { yybegin(LINE); return RobotTokenTypes.Variable; }
        {ListVariable}              { yybegin(LINE); return RobotTokenTypes.ListVariable; }
        {TestCaseSetting}           { yybegin(LINE); return RobotTokenTypes.TestCaseSetting; }
        {Word}                      { yybegin(LINE); return RobotTokenTypes.Word; }
        {Space}                     { yybegin(LINE); return RobotTokenTypes.Space; }
        {Separator}                 { yybegin(LINE); return RobotTokenTypes.Separator; }
    }
}

<LINE>  {LineTerminator}            { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator; }

//.                               { return RobotTokenTypes.BadCharacter; }