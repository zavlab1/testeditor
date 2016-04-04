package testeditor.gui.QuestionFrames;

import testeditor.gui.ParentFrame;
import testeditor.gui.services.GBC;
import testeditor.gui.services.QLabel;
import testeditor.gui.services.QTextArea;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * Created by dimitry on 03.04.16.
 */
abstract public class QuestionFrame extends ParentFrame {
    protected JPanel answerPanel  = new JPanel();
    protected ArrayList<JTextComponent> fields = new ArrayList();
    protected JScrollPane aScrollPane;

    public QuestionFrame(Question q) {
        setSize((int)(INITIAL_WIDTH / 1.5), INITIAL_HEIGHT);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout(30, 30));

        JPanel north = new JPanel();
        north.setLayout(new GridBagLayout());

        QLabel labelName = new QLabel("<html><b>Название:</b></html>");
        north.add(labelName, new GBC(0, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(10, 10, 10, 10));

        QTextArea nameTextArea = new QTextArea(q.getQName());
        fields.add(nameTextArea);
        north.add(nameTextArea, new GBC(1, 0, 1, 1, 0, 0, 100, 0).setFill(GBC.HORIZONTAL).setInsets(10, 10, 10, 10));

        QLabel labelQuestion = new QLabel("<html><b>Вопрос:</b></html>");

        north.add(labelQuestion, new GBC(0, 1, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));

        QTextArea qTextArea = new QTextArea(q.getQText());
        qTextArea.setLineWrap(true);
        fields.add(qTextArea);
        north.add( qTextArea, new GBC(1, 1, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));

        QLabel typeLabel = new QLabel("<html><b>Тип вопроса: </b>" + q.TYPE + "</html>");
        north.add(typeLabel, new GBC(0,2,2,1,0,0,0,0).setFill(GBC.HORIZONTAL).setInsets(10,10,0,10));

        add(north,BorderLayout.NORTH);

        answerPanel.setLayout(new BorderLayout(10,10));
        aScrollPane = new JScrollPane(answerPanel);
        aScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       // aScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
        //    public void adjustmentValueChanged(AdjustmentEvent e) {
         //       e.getAdjustable().setValue(e.getAdjustable().getMaximum());}});
        add(aScrollPane);

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 10));

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");
        savePanel.add(saveButton);
        savePanel.add(cancelButton);

        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

        JLabel hintLabel = new JLabel("Подсказка");
        hintPanel.add(hintLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(savePanel, BorderLayout.NORTH);
        bottomPanel.add(hintPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
