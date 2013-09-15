package amailp.lexer;

import amailp.psi.RobotTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.psi.TokenType;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;

public class RobotLexerTest {
    @Test
    public void testHeaderDelimiter() {

    }

    @Test
    public void firstTest() {

        Lexer robotLexer = new FlexAdapter(new RobotLexer(new StringReader("")));
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
