package testeditor.gui.question_view;

import testeditor.gui.services.GBC;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimitry on 03.04.16.
 */
public class TrueFalseFrame extends QuestionFrame {
    public TrueFalseFrame(Question q) {
        super(q);

        answerPanel.setLayout(new GridBagLayout());

        List<Answer> answerList = q.getAnswerList();

        JLabel labelQuestion = new JLabel("Верность ответа:");

        answerPanel.add(labelQuestion, new GBC(0, 0, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

        JRadioButton b = new JRadioButton();
        answerPanel.add(b, new GBC(1, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL));

        add(answerPanel);
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        return aList;
    }
}
