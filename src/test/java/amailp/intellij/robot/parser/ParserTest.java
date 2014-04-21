package amailp.intellij.robot.parser;

import amailp.intellij.robot.lang.RobotLanguage$;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.impl.PsiBuilderFactoryImpl;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.testFramework.ParsingTestCase;
import com.intellij.testFramework.PlatformTestCase;

public class ParserTest extends ParsingTestCase {

    public ParserTest() {
        super("", "robot", new amailp.intellij.robot.extensions.ParserDefinition());
        PlatformTestCase.initPlatformLangPrefix();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public static void main(String[] args) throws Exception {
        new ParserTest().setUp();
        ParserDefinition pd = new amailp.intellij.robot.extensions.ParserDefinition();
        String robotTestCase = StreamUtil.readText(pd.getClass().getResourceAsStream("complete.robot"), "utf-8");
        PsiBuilder builder = new PsiBuilderFactoryImpl().createBuilder(pd, pd.createLexer(null), robotTestCase);
        builder.setDebugMode(true);
        final PsiParser parser = RobotParser$.MODULE$;
        IFileElementType fileElem = new IFileElementType(RobotLanguage$.MODULE$);
        ASTNode root = parser.parse(fileElem, builder);
        System.out.println(DebugUtil.treeToString(root, true));
    }
}
