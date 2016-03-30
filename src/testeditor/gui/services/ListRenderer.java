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
        setLayout(new BorderLayout(20,20));
        setBackground(isSelected ? new Color(252,252,252): new Color(230,230,230));

        removeAll();

        JLabel labelQuestion = new JLabel("<html>" +
                "<br><b>"+value.getQName()+"</b>" +
                "<br>"+value.getQText()+
                "</html>");
        labelQuestion.setFont(new Font("Serif",Font.PLAIN,12));
        add(labelQuestion,"West");

        JLabel labelType = new JLabel("<html>" +
                "<br><b>Тип вопроса:</b>" +
                "<br>"+value.getType()+"</html>");
        labelType.setFont(new Font("Serif",Font.PLAIN,12));
        add(labelType,"East");

        add(new JSeparator(),"South");

        return this;
    }
}
