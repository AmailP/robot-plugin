package amailp.parser;

import amailp.psi.RobotASTTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class RobotParser implements PsiParser {

    RobotParser()

    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();
            PhraseParser.parsePhrase(builder);
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }
}
