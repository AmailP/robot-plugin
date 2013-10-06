package amailp.psi.impl;

import amailp.file.RobotFileType;
import amailp.language.RobotLanguage;
import amailp.psi.RobotFile;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RobotFileImpl extends PsiFileBase implements RobotFile {
    public RobotFileImpl(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RobotLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return RobotFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Simple File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
