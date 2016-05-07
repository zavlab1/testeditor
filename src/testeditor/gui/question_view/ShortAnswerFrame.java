package testeditor.gui.question_view;

import testeditor.gui.services.QTextArea;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dimitry on 03.04.16.
 */
public class ShortAnswerFrame extends QuestionFrame {
    List<Answer> answerList;
    QTextArea    answerText;
    QTextArea    commentText;

    public ShortAnswerFrame(Question q) {
        super(q);

        setSize((int)(this.initialHeight / 1.5),
                (int)(this.initialHeight / 1.5));

        JLabel labelComment = new JLabel("Комментарий:");

        answerList = q.getAnswerList();

        answerText  = new QTextArea(answerList.get(0).getAText());
        commentText = new QTextArea(answerList.get(0).getAComment());

        fields.add(answerText);
        fields.add(commentText);

        GroupLayout answerLayout = new GroupLayout(answerPanel);
        answerLayout.setAutoCreateContainerGaps(true);
        answerLayout.setAutoCreateGaps(true);

        answerLayout.setHorizontalGroup(
                answerLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(answerText  , 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelComment, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(commentText , 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        answerLayout.setVerticalGroup(
                answerLayout.createSequentialGroup()
                            .addComponent(answerText  , 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelComment, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(commentText , 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        answerPanel.setLayout(answerLayout);
        answerPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder (5, 5, 5, 5),
                                                                 new TitledBorder("Правильный ответ: ")));

        add(answerPanel);
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();

        Answer answer;

        answer = new Answer(answerText.getText(), Answer.MAX_DEGREE, commentText.getText());
        aList.add(answer);

        return aList;
    }
}
