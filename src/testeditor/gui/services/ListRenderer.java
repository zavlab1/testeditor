package testeditor.gui.services;

import javax.swing.*;

import sun.plugin.javascript.navig.Anchor;
import testeditor.question.*;

import java.awt.*;

/**
 * Created by SERGEY on 23.03.2016.
 */
public class ListRenderer extends JPanel implements ListCellRenderer<Question> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Question> list, Question value, int index, boolean isSelected, boolean cellHasFocus) {
        setLayout(new GridBagLayout());
        setBackground(isSelected ? new Color(252,252,252): new Color(230,230,230));

        removeAll();

        JLabel labelQuestion = new JLabel("<html>" +
                "<b>"+value.getQName()+"</b>" +
                "<br>"+value.getQText()+
                "</html>");
        labelQuestion.setFont(new Font("Serif",Font.PLAIN,12));
        add(labelQuestion,new GBC(0,0,1,1,0,0,100,0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,20));

        JLabel labelType = new JLabel("<html>" +
                "<b>Тип вопроса:</b>" +
                "<br>" + value.TYPE + "</html>");
        labelType.setFont(new Font("Serif",Font.PLAIN,12));
        add(labelType,new GBC(2,0,1,1,0,0,0,0).setFill(GBC.HORIZONTAL).setInsets(10,20,10,10));

       add(new JSeparator(),new GBC(0,1,3,1,0,0,100,0).setFill(GBC.HORIZONTAL));

        return this;
    }
}
