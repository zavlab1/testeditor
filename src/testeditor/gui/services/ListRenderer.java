package testeditor.gui.services;

import javax.swing.*;

import testeditor.gui.test_view.TestView;
import testeditor.question.*;

import java.awt.*;

/**
 * Created by SERGEY on 23.03.2016.
 */
public class ListRenderer extends JPanel implements ListCellRenderer<Question> {
    private String findText;

    @Override
    public Component getListCellRendererComponent(JList<? extends Question> list, Question value, int index, boolean isSelected, boolean cellHasFocus) {
        setLayout(new GridBagLayout());

        setBackground(isSelected ? new Color(252, 252, 252): new Color(230, 230, 230));

        removeAll();

        JLabel labelNumber = new JLabel("Вопрос №" + Integer.toString(index + 1));

        labelNumber.setFont(new Font("Sans-Serif",Font.BOLD,12));
        add(labelNumber, new GBC(0, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL)
                                                        .setInsets(10, 10, 10, 10));

        add(new JSeparator(JSeparator.VERTICAL), new GBC(1,0,1,1,0,0,0,0).setFill(GBC.VERTICAL));

        JLabel labelQuestion = new JLabel("<html><p>" +
                                              "<b>" + value.getQName() + "</b>" +
                                              "<br>" + value.getQText() +
                                          "</p><br></html>");

        labelQuestion.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        add(labelQuestion, new GBC(2, 0, 1, 1, 0, 0, 100, 0).setFill(GBC.BOTH)
                                                             .setInsets(10, 5, 10, 10));

        JLabel labelType = new JLabel("<html>" +
                                          "<p align='right'>" +
                                                "<b>Тип вопроса:</b>" +
                                                "<br>" + value.TYPE +
                                          "</p>" +
                                      "<br></html>" );

        labelType.setFont(new Font("Sans-Serif", Font.PLAIN, 12));

        add(labelType, new GBC(3, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.BOTH)
                                                      .setInsets(10, 20, 10, 5));

        JSeparator lineSeparator = new JSeparator();
        lineSeparator.setBorder(BorderFactory.createEmptyBorder());
        add(lineSeparator, new GBC(0, 1, 5, 1, 0, 0, 100, 0).setFill(GBC.HORIZONTAL));

        return this;

    }

    public void setFindText(String findText) {
        this.findText = findText;
    }
}
