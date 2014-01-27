package amailp.elements;

import amailp.idea.RobotLanguage$;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class RobotIElementType extends IElementType {
    public RobotIElementType(@NotNull @NonNls String debugName) {
        super(debugName, RobotLanguage$.MODULE$);
    }
}
