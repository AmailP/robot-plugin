package amailp.psi.impl;

import amailp.psi.Phrase;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class PhraseImpl extends ASTWrapperPsiElement implements Phrase {
    public PhraseImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
