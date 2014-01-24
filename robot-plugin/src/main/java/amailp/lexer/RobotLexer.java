package amailp.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class RobotLexer extends FlexAdapter {
    public RobotLexer() {
        super(new _RobotLexer((Reader)null));
    }
}
