package amailp.lexer;

import amailp.psi.RobotTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import org.junit.After;
import org.junit.Before;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class BaseLexerTest {

    protected Lexer robotLexer;

    @Before
    public void initLexer() {
        robotLexer = new FlexAdapter(new RobotLexer(new StringReader("")));
    }

    @After
    public void checkAllWasLexed() {
        System.out.println(robotLexer.getBufferEnd() + " " +  robotLexer.getTokenEnd());
        assertEquals(robotLexer.getBufferEnd(), robotLexer.getTokenEnd());
    }

    protected void nextTokenIsType(IElementType type) {
        assertEquals(robotLexer.getTokenText(), type, robotLexer.getTokenType());
        if(robotLexer.getBufferEnd() != robotLexer.getTokenEnd())
            robotLexer.advance();
    }
}
