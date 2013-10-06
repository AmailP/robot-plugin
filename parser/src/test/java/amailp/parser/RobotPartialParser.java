package amailp.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class RobotPartialParser implements PsiParser {

    PartParser partParser;

    RobotPartialParser(PartParser partParser) {
        this.partParser = partParser;
    }

    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();
        partParser.parse(builder);
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

}
