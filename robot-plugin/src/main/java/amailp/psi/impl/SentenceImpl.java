package amailp.psi.impl;

import amailp.psi.Sentence;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class SentenceImpl extends ASTWrapperPsiElement implements Sentence {
    public SentenceImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
