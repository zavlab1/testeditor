package testeditor.gui.QuestionFrames;

import testeditor.gui.ParentFrame;
import testeditor.gui.services.GBC;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;

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
        north.setLayout(new GridBagLayout());

        JLabel labelName = new JLabel("Название:");
        north.add(labelName, new GBC(0, 0, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL).setInsets(10, 0, 0, 0));

        JTextArea nameTextArea = new JTextArea(q.getQName());
        fields.add(nameTextArea);
        north.add(nameTextArea, new GBC(1, 0, 1, 1, 0, 0, 100, 0).setFill(GBC.HORIZONTAL).setInsets(10, 0, 0, 0));

        JLabel labelQuestion = new JLabel("Вопрос:");

        north.add(labelQuestion, new GBC(0, 1, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

        JTextArea qTextArea = new JTextArea(q.getQText());
        fields.add(qTextArea);
        north.add( qTextArea, new GBC(1, 1, 1, 1, 0, 0, 100, 0).setFill(GBC.HORIZONTAL));

        add(north,BorderLayout.NORTH);

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 30));

        JButton saveButton = new JButton("Сохранить");
        savePanel.add(saveButton);

        add(savePanel,BorderLayout.SOUTH);
    }
}
