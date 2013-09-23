package amailp.lexer;

import amailp.psi.RobotTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.psi.TokenType;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class TestHeaders extends BaseLexerTest {

    @Test
    public void testHeader() {
        scanString("*** HeaderName ***");
        nextTokenIsType(RobotTypes.Header);
    }

    @Test
    public void testStrangeHeader() {
        scanString("\n\n*** Header $trange N4m& ***  ");
        nextTokenIsType(RobotTypes.LineTerminator);
        nextTokenIsType(RobotTypes.LineTerminator);
        nextTokenIsType(RobotTypes.Header);
        nextTokenIsType(TokenType.WHITE_SPACE);
    }
}
