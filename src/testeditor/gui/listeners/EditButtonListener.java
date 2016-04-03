package testeditor.gui.listeners;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by dimitry on 03.04.16.
 */
public class EditButtonListener extends MouseAdapter {
    JList list;
    public EditButtonListener(JList list) {
        this.list = list;
    }
    public void mouseReleased(MouseEvent e) {
        int index = list.getSelectedIndex();
        JButton button = (JButton) e.getSource();
        button.setText(button.getText() + " mouseReleased()");

        Question q = (Question)Test.getTest().toArray()[index];
        q.getFrame().setVisible(true);
    }
}


