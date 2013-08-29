package amailp.parser;

import com.intellij.testFramework.ParsingTestCase;

public class ParseSettingsTestCase extends ParsingTestCase {
    public ParseSettingsTestCase() {
        super("", "robot", new RobotParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "../../data/parser";
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