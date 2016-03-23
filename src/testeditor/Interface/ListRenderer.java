package testeditor.Interface;

import javax.swing.*;
import testeditor.question.*;

import java.awt.*;

/**
 * Created by SERGEY on 23.03.2016.
 */
public class ListRenderer extends JPanel implements ListCellRenderer<Question> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Question> list, Question value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = new JLabel(value.getQHead());
        add(label);
        setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        return this;
    }
}
