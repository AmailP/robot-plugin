package amailp.intellij.robot.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import amailp.intellij.robot.elements.RobotTokenTypes;

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
Ellipsis = \.\.\.
WithName = "WITH NAME"

SettingsHeader = "*** Settings ***" | "*** Setting ***"
TestCasesHeader  = "*** Test Cases ***" | "*** Test Case ***"
KeywordsHeader  = "*** Keywords ***" | "*** Keyword ***"
VariablesHeader  = "*** Variables ***" | "*** Variable ***"
TasksHeader  = "*** Tasks ***" | "*** Task ***"
CommentsHeader = "*** Comments ***"


WordChar = [^$@&%\ \t\f\r\n]
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

ScalarVariable = "${" ~"}"
ListVariable = "@{" ~"}"
DictionaryVariable = "&{" ~"}"
EnvironmentVariable = "%{" ~"}"

%state LINE

%%

<YYINITIAL> {
    {SpaceChars}* {LineTerminator}  { return RobotTokenTypes.BlankLine; }
    {SpaceChars}* {Comment} {LineTerminator}  { return RobotTokenTypes.Comment; }

    <LINE> {
        {SpaceChars}+ / {Comment}       { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces; }
        {SpaceChars}+ $                 { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces; }
        {Comment}                       { yybegin(LINE); return RobotTokenTypes.Comment; }

        {SettingsHeader}                { yybegin(LINE); return RobotTokenTypes.SettingsHeader; }
        {TestCasesHeader}               { yybegin(LINE); return RobotTokenTypes.TestCasesHeader; }
        {KeywordsHeader}                { yybegin(LINE); return RobotTokenTypes.KeywordsHeader; }
        {VariablesHeader}               { yybegin(LINE); return RobotTokenTypes.VariablesHeader; }
        {TasksHeader}                   { yybegin(LINE); return RobotTokenTypes.TasksHeader; }
        {CommentsHeader}                { yybegin(LINE); return RobotTokenTypes.CommentsHeader;}

        {Ellipsis}                      { yybegin(LINE); return RobotTokenTypes.Ellipsis; }
        {ScalarVariable}                { yybegin(LINE); return RobotTokenTypes.ScalarVariable; }
        {ListVariable}                  { yybegin(LINE); return RobotTokenTypes.ListVariable; }
        {DictionaryVariable}            { yybegin(LINE); return RobotTokenTypes.DictionaryVariable; }
        {EnvironmentVariable}           { yybegin(LINE); return RobotTokenTypes.EnvironmentVariable; }
        {TestCaseSetting}               { yybegin(LINE); return RobotTokenTypes.TestCaseSetting; }
        {Word} | "$" | "@" | "&" | "%"  { yybegin(LINE); return RobotTokenTypes.Word; }
        {Space}                         { yybegin(LINE); return RobotTokenTypes.Space; }
        {Separator}                     { yybegin(LINE); return RobotTokenTypes.Separator; }
        {WithName}                      { yybegin(LINE); return RobotTokenTypes.WithName; }
    }
}

<LINE>  {LineTerminator}            { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator; }

//.                               { return RobotTokenTypes.BadCharacter; }