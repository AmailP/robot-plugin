package amailp.intellij.robot.file;

import amailp.intellij.robot.lang.RobotLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class FileType extends LanguageFileType {

    private static final String NAME = "Robot file";
    private static final String DESCRIPTION = "Robotframework language file";
    private static final String DEFAULT_EXTENSION = "robot";
    public static final LanguageFileType INSTANCE = new FileType();

    private FileType() {
        super(RobotLanguage.Instance());
    }

    @Override
    @NotNull
    public String getName() {
        return NAME;
    }

    @Override
    @NotNull
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    @NotNull
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Override
    @Nullable
    public Icon getIcon() {
        return Icons.file();
    }
}
