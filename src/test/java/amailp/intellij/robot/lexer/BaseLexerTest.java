package amailp.intellij.robot.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import org.junit.After;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BaseLexerTest {

    protected Lexer robotLexer;

    @Before
    public void initLexer() {
        robotLexer = new RobotLexer();
    }

    @After
    public void checkAllWasLexed() {
        assertEquals("Something was left unlexed", robotLexer.getTokenStart(), robotLexer.getTokenEnd());
    }

    protected void scanString(String toBeScanned) {
        robotLexer.start(toBeScanned);
    }

    protected void nextTokenIsType(IElementType type) {
        assertThat(type, equalTo(robotLexer.getTokenType()));
        robotLexer.advance();
    }

    protected void nextTokenIs(String text, IElementType type) {
        assertThat(text, equalTo(robotLexer.getTokenText()));
        nextTokenIsType(type);
    }
}
