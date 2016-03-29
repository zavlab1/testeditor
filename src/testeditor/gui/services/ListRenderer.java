package testeditor.gui.services;

import javax.swing.*;
import testeditor.question.*;

import java.awt.*;

/**
 * Created by SERGEY on 23.03.2016.
 */
public class ListRenderer extends JPanel implements ListCellRenderer<Question> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Question> list, Question value, int index, boolean isSelected, boolean cellHasFocus) {
        setLayout(new BorderLayout(30,30));

        JLabel label = new JLabel("    "+value.getQName());

        removeAll();
        add(label,"West");
        add(new JSeparator(),"South");

        return this;
    }
}
