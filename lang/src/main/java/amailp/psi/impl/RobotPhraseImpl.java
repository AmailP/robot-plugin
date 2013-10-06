package amailp.psi.impl;

import amailp.psi.RobotPhrase;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class RobotPhraseImpl extends ASTWrapperPsiElement implements RobotPhrase {
    public RobotPhraseImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
