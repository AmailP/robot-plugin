package amailp.intellij.robot.parser;

import amailp.intellij.robot.lang.RobotLanguage;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.impl.PsiBuilderFactoryImpl;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.testFramework.ParsingTestCase;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ParserTest extends ParsingTestCase {

    public ParserTest() {
        super("", "robot", new amailp.intellij.robot.extensions.ParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    // Smoke test
    public void testCompleteParsing() throws IOException {
        ParserDefinition pd = new amailp.intellij.robot.extensions.ParserDefinition();
        String robotTestCase =
            StreamUtil.readText(
                new InputStreamReader(pd.getClass().getClassLoader().getResourceAsStream("complete.robot"), StandardCharsets.UTF_8));
        PsiBuilder builder = new PsiBuilderFactoryImpl().createBuilder(pd, pd.createLexer(null), robotTestCase);
        builder.setDebugMode(true);
        final PsiParser parser = RobotParser$.MODULE$;
        IFileElementType fileElem = new IFileElementType(RobotLanguage.Instance());
        ASTNode root = parser.parse(fileElem, builder);
//        System.out.println(DebugUtil.treeToString(root, true));
    }
}
