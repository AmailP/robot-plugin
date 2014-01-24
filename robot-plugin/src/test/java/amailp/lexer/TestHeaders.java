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

    @Test
    public void testLineBeforeHeader() {
        scanString("\n*** Test Cases ***");
        nextTokenIsType(RobotTokenTypes.LineTerminator);
        nextTokenIsType(RobotTokenTypes.TestCasesHeader);
    }

    @Test
    public void testLineBetweenHeaders() {
        scanString("*** Settings ***\n*** Setting ***");
        nextTokenIsType(RobotTokenTypes.SettingsHeader);
        nextTokenIsType(RobotTokenTypes.LineTerminator);
        nextTokenIsType(RobotTokenTypes.SettingsHeader);
    }
}
