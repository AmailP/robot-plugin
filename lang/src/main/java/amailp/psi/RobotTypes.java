package amailp.psi;

import com.intellij.psi.tree.IElementType;

public interface RobotTypes {
    final IElementType LineTerminator = new RobotElementType("LineTerminator");
    final IElementType Header = new RobotElementType("Header");
    final IElementType Variable = new RobotElementType("Variable");
    final IElementType ListVariable = new RobotElementType("ListVariable");
    final IElementType TestCaseSetting = new RobotElementType("TestCaseSetting");
    final IElementType Word = new RobotElementType("Word");
    final IElementType Space = new RobotElementType("Space");
}
