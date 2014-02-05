package amailp.lexer;

import amailp.elements.RobotTokenTypes;
import amailp.idea.RobotParserDefinition;
import amailp.idea.RobotParserDefinition$;
import org.junit.Test;

import java.util.Scanner;

public class RobotLexerTest extends BaseLexerTest {

    @Test
    public void testVariable() {
        scanString("${a_Variable}");
        nextTokenIsType(RobotTokenTypes.Variable);
    }

    @Test
    public void testListVariable() {
        scanString("@{aListVariable}");
        nextTokenIsType(RobotTokenTypes.ListVariable);
    }

    @Test
    public void testWord() {
        scanString("ThisIsAWord");
        nextTokenIsType(RobotTokenTypes.Word);
    }

    @Test
    public void testWordWithSymbols() {
        scanString("!IsThisAWord??.");
        nextTokenIsType(RobotTokenTypes.Word);
    }

    @Test
    public void testCell() {
        scanString("    A cell");
        nextTokenIsType(RobotTokenTypes.Separator);
        nextTokenIs("A", RobotTokenTypes.Word);
        nextTokenIsType(RobotTokenTypes.Space);
        nextTokenIs("cell", RobotTokenTypes.Word);
    }

    @Test
    public void testVariableCell() {
        scanString("  This ${is} cell  \n");
        nextTokenIsType(RobotTokenTypes.Separator);
        nextTokenIs("This", RobotTokenTypes.Word);
        nextTokenIsType(RobotTokenTypes.Space);
        nextTokenIs("${is}", RobotTokenTypes.Variable);
        nextTokenIsType(RobotTokenTypes.Space);
        nextTokenIs("cell", RobotTokenTypes.Word);
        nextTokenIsType(RobotTokenTypes.IrrelevantSpaces);
        nextTokenIsType(RobotTokenTypes.LineTerminator);
    }

    @Test
    public void testComment() {
        scanString("  #This is comment  ");
        nextTokenIsType(RobotTokenTypes.IrrelevantSpaces);
        nextTokenIs("#This is comment  ", RobotTokenTypes.Comment);
    }

    @Test
    public void testAll() {
        String s = new Scanner(this.getClass().getClassLoader().getResourceAsStream("complete.robot")).useDelimiter("\\A").next();
        scanString(s);
        while(robotLexer.getTokenType() != null) {
            System.out.println(robotLexer.getTokenType() + "\t\t"+ robotLexer.getTokenText());
            robotLexer.advance();
        }
    }
}
