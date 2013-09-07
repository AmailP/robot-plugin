package main.java.amailp.psi;

import main.java.amailp.lang.RobotLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class RobotElementType extends IElementType {

    public RobotElementType(@NotNull @NonNls String debugName) {
        super(debugName, RobotLanguage.INSTANCE);
    }
}
