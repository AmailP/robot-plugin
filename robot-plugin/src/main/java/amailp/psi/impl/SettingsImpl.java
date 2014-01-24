package amailp.psi.impl;

import amailp.psi.Settings;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;


public class SettingsImpl extends ASTWrapperPsiElement implements Settings{

    public SettingsImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
