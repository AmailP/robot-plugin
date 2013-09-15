package amailp.psi;

import amailp.language.RobotLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class RobotElementType extends IElementType {
    RobotElementType(@NotNull @NonNls String debugName) {
        super(debugName, RobotLanguage.INSTANCE);
    }
}
