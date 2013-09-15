package amailp.lexer;

import amailp.psi.RobotTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.psi.TokenType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;

public class RobotLexerTest {

    private Lexer robotLexer;

    @Before
    public void initLexer() {
        robotLexer = new FlexAdapter(new RobotLexer(new StringReader("")));
    }


    @Test
    public void testHeaderDelimiter() {
        robotLexer.start("***");
        assertEquals(RobotTypes.HeaderDelimiter, robotLexer.getTokenType());
    }

    @Ignore
    @Test
    public void testHeader() {
        robotLexer.start("*** HeaderName ***");
        assertEquals(RobotTypes.HeaderDelimiter, robotLexer.getTokenType());
        robotLexer.advance();
        robotLexer.advance();
        assertEquals(RobotTypes.HeaderName, robotLexer.getTokenType());
        robotLexer.advance();
        robotLexer.advance();
        assertEquals(RobotTypes.HeaderDelimiter, robotLexer.getTokenType());
    }

    @Test
    public void firstTest() {
        robotLexer.start("\n\n*** ***");
        assertEquals(RobotTypes.LineTerminator, robotLexer.getTokenType());
        robotLexer.advance();
        assertEquals(RobotTypes.LineTerminator, robotLexer.getTokenType());
        robotLexer.advance();
        assertEquals(RobotTypes.HeaderDelimiter, robotLexer.getTokenType());
        robotLexer.advance();
        assertEquals(TokenType.WHITE_SPACE, robotLexer.getTokenType());
        robotLexer.advance();
        assertEquals(RobotTypes.HeaderDelimiter, robotLexer.getTokenType());
    }
}
