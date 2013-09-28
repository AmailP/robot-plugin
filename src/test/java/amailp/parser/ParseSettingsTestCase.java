package amailp.parser;

import com.intellij.testFramework.ParsingTestCase;
import main.java.amailp.parser.RobotParserDefinition;

public class ParseSettingsTestCase extends ParsingTestCase {
    public ParseSettingsTestCase() {
        super("", "robot", new RobotParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "../../data/amailp.parser";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}