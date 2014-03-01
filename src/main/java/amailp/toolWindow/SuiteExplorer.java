package amailp.toolWindow;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;


public class SuiteExplorer extends SimpleToolWindowPanel {

    public static final Icon ToolWindowRobot = IconLoader.getIcon("/toolWindowRobot.png");

    public SuiteExplorer() {
        super(true);
    }
}
