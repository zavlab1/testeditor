package testeditor.gui.services;

import javax.swing.*;

/**
 * Created by dimitry on 12.04.16.
 */
public class HintLabel extends JLabel {
    public HintLabel() {
        super();
    }
    public HintLabel(String text) {
        super(text);
    }
    public void warning(String text) {
        setText("<html><p><font color='red'><b>" + text + "</font></b></p></html>");
    }
    public void info(String text) {
        setText(text);
    }
}