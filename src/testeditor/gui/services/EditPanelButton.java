package testeditor.gui.services;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dimitry on 05.04.16.
 */
public class EditPanelButton extends JButton {
    public EditPanelButton (String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.LEFT);
        setMargin(new Insets(0, 10, 0, 0));
    }
    public EditPanelButton (Action a) {
        super(a);
        setHorizontalAlignment(SwingConstants.LEFT);
        setMargin(new Insets(0, 10, 0, 0));
    }
}
