package amailp.psi.impl;

import amailp.psi.RobotPhrase;
import amailp.psi.RobotSentence;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class RobotSentenceImpl extends ASTWrapperPsiElement implements RobotSentence {
    public RobotSentenceImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
