package amailp.lexer;

import amailp.elements.RobotTokenTypes;
import org.junit.Test;

public class TestHeaders extends BaseLexerTest {

    @Test
    public void testHeader() {
        scanString("*** Setting ***");
        nextTokenIsType(RobotTokenTypes.SettingsHeader);
    }

    @Test
    public void testHeaderPlural() {
        scanString("*** Settings ***");
        nextTokenIsType(RobotTokenTypes.SettingsHeader);
    }
}
