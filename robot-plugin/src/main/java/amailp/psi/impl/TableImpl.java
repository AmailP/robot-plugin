package amailp.psi.impl;

import amailp.psi.Table;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class TableImpl extends ASTWrapperPsiElement implements Table {
    public TableImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
