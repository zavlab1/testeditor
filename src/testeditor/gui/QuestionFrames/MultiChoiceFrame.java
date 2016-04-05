package testeditor.gui.QuestionFrames;

import testeditor.gui.services.GBC;
import testeditor.gui.services.QLabel;
import testeditor.gui.services.QTextArea;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimitry on 03.04.16.
 */
public class MultiChoiceFrame extends QuestionFrame {
    List<Answer> aList;
    JPanel answers = new JPanel();
    int aCount;

    public MultiChoiceFrame(Question q) {
        super(q);
        answers.setLayout(new GridBagLayout());

        aList = q.getAnswerList();
        aCount = aList.size();

        for(int i=0; i<aCount; i++) {
            addAnswer(i, aList.get(i).getAText());
        }

        JButton addButton = new JButton("<html><font color='green' size=+1>&nbsp;<b>&#10010;&nbsp;</b></font>Добавить&nbsp;</html>");

        answerPanel.add(answers, BorderLayout.NORTH);

        JPanel addButtonPanel = new JPanel(new FlowLayout());
        addButtonPanel.add(addButton);
        addButton.addActionListener((ActionEvent e)->{
            addAnswer(aCount+1, "New");
            answers.updateUI();
            aCount++;
            aScrollPane.setViewportView(answerPanel);
        });

        answerPanel.add(addButtonPanel, BorderLayout.CENTER);
    }

    public void addAnswer (int pos, String s){
        JRadioButton b = new JRadioButton();
        answers.add(b, new GBC(0, pos+1, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(10, 5, 10, 10));

        QTextArea answerText = new QTextArea(s);
        answerText.setLineWrap(true);
        fields.add(answerText);
        answers.add(answerText, new GBC(1, pos+1, 1, 1, 0, 0, 100, 0).setFill(GBC.HORIZONTAL).setInsets(5, 5, 5, 5));

        JButton delButton = new JButton("<html><font color='red'><b>&nbsp;&#10006;&nbsp;</b></font></html>");
        answers.add(delButton, new GBC(2, pos+1, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(5, 10, 5, 5));
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        return aList;
    }
}