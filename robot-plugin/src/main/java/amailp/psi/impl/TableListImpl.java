package amailp.psi.impl;

import amailp.psi.TableList;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class TableListImpl extends ASTWrapperPsiElement implements TableList {
    public TableListImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
