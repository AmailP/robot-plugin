package parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class RobotParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();
        final PsiBuilder.Marker tableList = builder.mark();
        while (!builder.eof()) {
            TableParser.parseTable(builder);
        }
        tableList.done(RobotASTTypes.TableList);
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }
}
