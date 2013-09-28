package amailp.parser;

import amailp.psi.RobotTokenTypes;
import com.intellij.lang.PsiBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: angelini
 * Date: 24/09/2013
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class TableParser {
    public static void parseTable(PsiBuilder builder) {
        builder.mark();
        parseHeader(builder);
    }

    private static void parseHeader(PsiBuilder builder) {
        PsiBuilder.Marker headerMark = builder.mark();
        if(builder.getTokenType() == RobotTokenTypes.Header){
            while( builder.getTokenType() != RobotTokenTypes.Header ) {

            }
        }
    }
}
