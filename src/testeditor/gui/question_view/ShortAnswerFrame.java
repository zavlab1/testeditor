package testeditor.gui.question_view;

import testeditor.gui.services.GBC;
import testeditor.gui.services.QTextArea;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimitry on 03.04.16.
 */
public class ShortAnswerFrame extends QuestionFrame {
    List<Answer> answerList;
    QTextArea answerText;
    QTextArea commentText;

    public ShortAnswerFrame(Question q) {
        super(q);

        answerPanel.setLayout(new GridBagLayout());
        answerPanel.setBorder(new TitledBorder("Правильный ответ: "));

        answerList = q.getAnswerList();

        JLabel labelQuestion = new JLabel("Правильный ответ:");

        answerPanel.add(labelQuestion, new GBC(0, 0, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

        answerText = new QTextArea(answerList.get(0).getAText());
        JTextArea answerText = new QTextArea(answerList.get(0).getAText());
        answerText.setLineWrap(true);
        answerText.setWrapStyleWord(true);
        fields.add(answerText);
        answerPanel.add(answerText,new GBC(0,0,1,1,0,0,100,0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));

        JLabel labelComment = new JLabel("Комментарий:");

        answerPanel.add(labelComment, new GBC(0, 2, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

        commentText = new QTextArea(answerList.get(0).getAComment());
        commentText.setWrapStyleWord(true);
        fields.add(commentText);
        answerPanel.add(commentText, new GBC(1, 2, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL));

        add(answerPanel);
        setSize(this.INITIAL_WIDTH,(int)(this.INITIAL_HEIGHT/1.5));
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();

        Answer answer;

        answer = new Answer(answerText.getText(), Answer.MAX_DEGREE, commentText.getText());
        aList.add(answer);

        return aList;
    }
}
