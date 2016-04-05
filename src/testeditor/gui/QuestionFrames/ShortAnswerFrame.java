package testeditor.gui.QuestionFrames;

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
public class ShortAnswerFrame extends QuestionFrame {
    public ShortAnswerFrame(Question q) {
        super(q);

        answerPanel.setLayout(new GridBagLayout());

        List<Answer> answerList = q.getAnswerList();

        JLabel labelQuestion = new JLabel("Правильный ответ:");

        answerPanel.add(labelQuestion, new GBC(0, 0, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

        JTextArea answerText = new JTextArea(answerList.get(0).getAText());
        answerText.setWrapStyleWord(true);
        fields.add(answerText);
        answerPanel.add(answerText, new GBC(1, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL));

        add(answerPanel);
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        return aList;
    }
}
