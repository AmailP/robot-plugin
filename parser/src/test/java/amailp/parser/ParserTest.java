package amailp.parser;

import amailp.language.RobotLanguage;
import com.intellij.lang.*;
import com.intellij.lang.impl.PsiBuilderFactoryImpl;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.testFramework.ParsingTestCase;
import com.intellij.testFramework.PlatformTestCase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//public class ParserTest extends ParsingTestCase {
//
//    public ParserTest() {
//        super("", "robot", new RobotParserDefinition());
//        PlatformTestCase.initPlatformLangPrefix();
//    }
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//    }
//
//    public static void main(String[] args) throws Exception {
//        new ParserTest().setUp();
//        ParserDefinition pd = new RobotParserDefinition();
//        String robotTestCase = StreamUtil.readText(pd.getClass().getResourceAsStream("complete.robot"), "utf-8");
//        PsiBuilder builder = new PsiBuilderFactoryImpl().createBuilder(pd, pd.createLexer(null), robotTestCase);
//        builder.setDebugMode(true);
//        final PsiParser parser = new RobotParser();
//        IFileElementType fileElem = new IFileElementType(RobotLanguage.INSTANCE);
//        ASTNode root = parser.parse(fileElem, builder);
//        System.out.println(DebugUtil.treeToString(root.getFirstChildNode(), true));
//    }
//}
