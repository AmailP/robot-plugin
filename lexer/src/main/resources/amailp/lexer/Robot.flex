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
HeaderDelimiter = "***"
Whitespace=[\ \t\f]

%%

<YYINITIAL> {LineTerminator}                           { yybegin(YYINITIAL); return RobotTypes.LineTerminator; }

<YYINITIAL> {HeaderDelimiter}                           { yybegin(YYINITIAL); return RobotTypes.HeaderDelimiter; }

<YYINITIAL> {Whitespace}                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

.                                                           { return TokenType.BAD_CHARACTER; }