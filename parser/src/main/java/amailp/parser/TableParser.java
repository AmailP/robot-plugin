package amailp.parser;

import amailp.elements.RobotASTTypes;
import amailp.elements.RobotElementType;
import amailp.elements.RobotTokenTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

public class TableParser {
    public static void parseTable(PsiBuilder builder) {
        PsiBuilder.Marker tableMarker = builder.mark();
        parseHeader(builder);
        while(builder.getTokenType() != RobotTokenTypes.Header && !builder.eof()) {
            parseRow(builder);
        }
        tableMarker.done(RobotASTTypes.Table);
    }

    private static void parseRow(PsiBuilder builder) {
        PsiBuilder.Marker rowMarker = builder.mark();
        while(builder.getTokenType() != RobotTokenTypes.LineTerminator && !builder.eof()) {
            parseCell(builder);
        }
        builder.advanceLexer();
        rowMarker.done(RobotASTTypes.TableRow);
    }

    private static void parseCell(PsiBuilder builder) {
        PsiBuilder.Marker cellMarker = builder.mark();
        if(builder.getTokenType() == RobotTokenTypes.Whitespaces) {
            cellMarker.done(RobotASTTypes.EmptyCell);
            builder.advanceLexer();
            return;
        }
        while(!(isCellTerminator(builder.getTokenType()) || builder.eof())) {
            builder.advanceLexer();
        }
        cellMarker.done(RobotASTTypes.NonEmptyCell);
        if(builder.getTokenType() == RobotTokenTypes.Whitespaces) {
            builder.advanceLexer();
        }
    }

    private static void parseHeader(PsiBuilder builder) {
        PsiBuilder.Marker headerMark = builder.mark();
        if(builder.getTokenType() != RobotTokenTypes.Header){
            builder.error("Header token expected");
        }
        builder.advanceLexer();
        if(builder.getTokenType() == RobotTokenTypes.Whitespaces){
            builder.advanceLexer();
        }
        if(builder.getTokenType() != RobotTokenTypes.LineTerminator){
            builder.error("LineTerminator token expected");
        }
        builder.advanceLexer();
        headerMark.done(RobotASTTypes.HeaderRow);
    }

    private static boolean isCellTerminator(IElementType type) {
        return type == RobotTokenTypes.Whitespaces || type == RobotTokenTypes.LineTerminator;
    }
}
