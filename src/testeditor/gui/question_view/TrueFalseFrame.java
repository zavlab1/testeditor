package testeditor.gui.question_view;

import javax.swing.JCheckBox;
import testeditor.gui.services.GBC;
import testeditor.gui.services.QTextArea;
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
    QTextArea commentText;
    JCheckBox cb;

    public TrueFalseFrame(Question q) {
        super(q);

        answerPanel.setLayout(new GridBagLayout());

        List<Answer> answerList = q.getAnswerList();

        JLabel labelQuestion = new JLabel("Верность ответа:");

        //answerPanel.add(labelQuestion, new GBC(0, 0, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));
        answerPanel.add(labelQuestion, new GBC(0, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL));

        cb = new JCheckBox();
        answerPanel.add(cb, new GBC(0, 1, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL));

        JLabel labelComment = new JLabel("Комментарий:");

        answerPanel.add(labelComment, new GBC(0, 2, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

        commentText = new QTextArea(answerList.get(0).getAComment());
        commentText.setWrapStyleWord(true);
        fields.add(commentText);
        answerPanel.add(commentText, new GBC(1, 2, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL));

        add(answerPanel);
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();

        Answer answer;

        answer = new Answer("", cb.isSelected() ? Answer.MAX_DEGREE : Answer.MIN_DEGREE, commentText.getText());
        aList.add(answer);

        return aList;
    }
}
