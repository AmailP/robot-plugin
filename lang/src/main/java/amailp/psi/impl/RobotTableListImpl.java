package amailp.psi.impl;

import amailp.psi.RobotTableList;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class RobotTableListImpl extends ASTWrapperPsiElement implements RobotTableList {
    public RobotTableListImpl(@NotNull final ASTNode node) {
        super(node);
    }
}
