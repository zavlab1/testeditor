package testeditor.gui.question_view;

import testeditor.gui.services.GBC;
import testeditor.gui.services.QTextArea;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

/**
 * Created by dimitry on 03.04.16.
 */
public class ShortAnswerFrame extends QuestionFrame {
    List<Answer> answerList;
    QTextArea answerText;
    QTextArea commentText;

    public ShortAnswerFrame(Question q) {
        super(q);

        setSize(this.INITIAL_WIDTH,(int)(this.INITIAL_HEIGHT/1.5));
        setLocation((SCREEN_WIDTH - this.getWidth()) / 2,
                (SCREEN_HEIGHT - this.getHeight()) / 2);

        answerText = new QTextArea("");
        //JTextArea answerText = new QTextArea(answerList.get(0).getAText());
        fields.add(answerText);

        JLabel labelComment = new JLabel("Комментарий:");

        commentText = new QTextArea("");
        fields.add(commentText);

        GroupLayout answerLayout = new GroupLayout(answerPanel);
        answerLayout.setAutoCreateContainerGaps(true);
        answerLayout.setAutoCreateGaps(true);

        answerLayout.setHorizontalGroup(answerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(answerText)
                .addGroup(answerLayout.createSequentialGroup())
                    .addComponent(labelComment)
                    .addComponent(commentText)
        );

        answerLayout.setVerticalGroup(answerLayout.createSequentialGroup()
                        .addComponent(answerText)
                .addGroup(answerLayout.createParallelGroup(BASELINE)
                        .addComponent(labelComment)
                        .addComponent(commentText))
        );

        answerPanel.setLayout(answerLayout);
        answerPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder("Правильный ответ: ")));
        answerList = q.getAnswerList();

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
