package amailp.psi;

import com.intellij.psi.tree.IElementType;

public interface RobotTypes {
    final IElementType LineTerminator = new RobotElementType("LineTerminator");
    final IElementType HeaderDelimiter = new RobotElementType("HeaderDelimiter");
}
