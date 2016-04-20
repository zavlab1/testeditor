package testeditor.gui.services;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
        setText("<html><p><font color='green'><b>" + text + "</font></b></p></html>");
    }
    public void error (String text) {
        setText("<html><p><font color='red'><b>" + text + "</font></b></p></html>");
    }
    public void error (Consumer<JComponent> func, JComponent comp, String text) {
        setText("<html><p><font color='red'><b>" + text + "</font></b></p></html>");
        func.accept(comp);
    }
    public void info(String text) {
        setText("<html><p>" + text + "</p></html>");
    }
}