package amailp.lexer;

import amailp.psi.RobotTypes;
import com.intellij.psi.TokenType;
import org.junit.Test;

public class RobotLexerTest extends BaseLexerTest {

    @Test
    public void testVariable() {
        scanString("${aVariable}");
        nextTokenIsType(RobotTypes.Variable);
    }

    @Test
    public void testListVariable() {
        scanString("@{aListVariable}");
        nextTokenIsType(RobotTypes.ListVariable);
    }

    @Test
    public void testWord() {
        scanString("ThisIsAWord");
        nextTokenIsType(RobotTypes.Word);
    }

    @Test
    public void testWordWithSymbols() {
        scanString("!IsThisAWord??.");
        nextTokenIsType(RobotTypes.Word);
    }

    @Test
    public void testCell() {
        scanString("    A cell");
        nextTokenIsType(TokenType.WHITE_SPACE);
        nextTokenIs("A", RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIs("cell", RobotTypes.Word);
    }

    @Test
    public void testVariableCell() {
        scanString("  This ${is} cell  ");
        nextTokenIsType(TokenType.WHITE_SPACE);
        nextTokenIs("This", RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIs("${is}", RobotTypes.Variable);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIs("cell", RobotTypes.Word);
        nextTokenIsType(TokenType.WHITE_SPACE);
    }
}
