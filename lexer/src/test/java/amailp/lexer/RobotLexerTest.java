package amailp.lexer;

import amailp.psi.RobotTokenTypes;
import com.intellij.psi.TokenType;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.Scanner;

public class RobotLexerTest extends BaseLexerTest {

    @Test
    public void testVariable() {
        scanString("${aVariable}");
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
        nextTokenIsType(RobotTokenTypes.Whitespaces);
        nextTokenIs("A", RobotTokenTypes.Word);
        nextTokenIsType(RobotTokenTypes.Space);
        nextTokenIs("cell", RobotTokenTypes.Word);
    }

    @Test
    public void testVariableCell() {
        scanString("  This ${is} cell  ");
        nextTokenIsType(RobotTokenTypes.Whitespaces);
        nextTokenIs("This", RobotTokenTypes.Word);
        nextTokenIsType(RobotTokenTypes.Space);
        nextTokenIs("${is}", RobotTokenTypes.Variable);
        nextTokenIsType(RobotTokenTypes.Space);
        nextTokenIs("cell", RobotTokenTypes.Word);
        nextTokenIsType(RobotTokenTypes.Whitespaces);
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
