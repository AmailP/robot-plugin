package amailp.psi.impl;

import amailp.psi.RobotTable;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class RobotTableImpl extends ASTWrapperPsiElement implements RobotTable {
    public RobotTableImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
