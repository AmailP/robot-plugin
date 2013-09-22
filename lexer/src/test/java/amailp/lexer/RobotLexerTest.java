package amailp.lexer;

import amailp.psi.RobotTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.psi.TokenType;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;

public class RobotLexerTest extends BaseLexerTest {

    @Test
    public void testVariable() {
        robotLexer.start("${aVariable}");
        nextTokenIsType(RobotTypes.Variable);
    }

    @Test
    public void testWord() {
        robotLexer.start("ParvenueIsMyWord");
        nextTokenIsType(RobotTypes.Word);
    }

    @Test
    public void testCell() {
        robotLexer.start("    This is a text cell");
        nextTokenIsType(TokenType.WHITE_SPACE);
        nextTokenIsType(RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIsType(RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIsType(RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIsType(RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIsType(RobotTypes.Word);
    }

    @Test
    public void testVariableCell() {
        robotLexer.start("  This ${is} cell  ");
        nextTokenIsType(TokenType.WHITE_SPACE);
        nextTokenIsType(RobotTypes.Word);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIsType(RobotTypes.Variable);
        nextTokenIsType(RobotTypes.Space);
        nextTokenIsType(RobotTypes.Word);
        nextTokenIsType(TokenType.WHITE_SPACE);
    }

    @Ignore
    @Test
    public void testEscaped() {
        robotLexer.start("\\?");
        nextTokenIsType(RobotTypes.Escaped);
    }


    @Ignore
    @Test
    public void testNotVariable() {
        robotLexer.start("\\${notVariable}");
        nextTokenIsType(RobotTypes.Escaped);
        nextTokenIsType(RobotTypes.Word);
    }
}
