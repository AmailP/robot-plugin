package amailp.lexer;

import amailp.elements.RobotTokenTypes;
import org.junit.Test;

public class TestHeaders extends BaseLexerTest {

    @Test
    public void testHeader() {
        scanString("*** HeaderName ***");
        nextTokenIsType(RobotTokenTypes.Header);
    }

    @Test
    public void testStrangeHeader() {
        scanString("\n\n*** Header $trange N4m& ***  ");
        nextTokenIsType(RobotTokenTypes.LineTerminator);
        nextTokenIsType(RobotTokenTypes.LineTerminator);
        nextTokenIsType(RobotTokenTypes.Header);
        nextTokenIsType(RobotTokenTypes.Whitespaces);
    }
}
