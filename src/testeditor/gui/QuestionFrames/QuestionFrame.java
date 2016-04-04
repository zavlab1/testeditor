package testeditor.gui.QuestionFrames;

import testeditor.gui.ParentFrame;
import testeditor.gui.services.GBC;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import static javax.swing.GroupLayout.Alignment.*;

/**
 * Created by dimitry on 03.04.16.
 */
abstract public class QuestionFrame extends ParentFrame {
    protected JPanel answers  = new JPanel();
    protected ArrayList<JTextComponent> fields = new ArrayList();

    public QuestionFrame(Question q) {
        setSize((int)(INITIAL_WIDTH / 1.5), INITIAL_HEIGHT);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout(30, 30));

        JPanel north = new JPanel();
        GroupLayout northLayout = new GroupLayout(north);
        north.setLayout(northLayout);
        northLayout.setAutoCreateContainerGaps(true);
        northLayout.setAutoCreateGaps(true);

        JLabel labelName = new JLabel("Название:");
        JLabel labelQuestion = new JLabel("Вопрос:");
        JTextArea nameTextArea = new JTextArea(q.getQName());
        JTextArea qTextArea = new JTextArea(q.getQText());
        fields.add(nameTextArea);
        fields.add(qTextArea);

        northLayout.setHorizontalGroup(northLayout.createSequentialGroup()
                .addGroup(northLayout.createParallelGroup(LEADING)
                        .addComponent(labelName)
                        .addComponent(labelQuestion))
                .addGroup(northLayout.createParallelGroup(LEADING)
                        .addComponent(nameTextArea)
                        .addComponent(qTextArea))
        );

        northLayout.linkSize(SwingConstants.HORIZONTAL, labelName, labelQuestion);

        northLayout.setVerticalGroup(northLayout.createSequentialGroup()
                .addGroup(northLayout.createParallelGroup(BASELINE)
                        .addComponent(labelName)
                        .addComponent(nameTextArea))
                .addGroup(northLayout.createParallelGroup(LEADING)
                        .addComponent(labelQuestion)
                        .addComponent(qTextArea))
        );

        add(north,BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2,0,0,12));

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        JButton saveButton = new JButton("Сохранить");
        savePanel.add(saveButton);
        southPanel.add(savePanel);

        JLabel warnings = new JLabel("Системные сообщения");
        southPanel.add(warnings);

        add(southPanel,BorderLayout.SOUTH);
    }
}
