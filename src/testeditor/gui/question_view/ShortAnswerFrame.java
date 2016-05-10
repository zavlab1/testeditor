package testeditor.gui.question_view;

import testeditor.gui.services.QTextArea;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;
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

        answerList = q.getAnswerList();

        answerText  = new QTextArea(answerList.get(0).getAText());
        commentText = new QTextArea(answerList.get(0).getAComment());

        fields.add(answerText);
        fields.add(commentText);


        JPanel rightAnswerPanel = new JPanel(new BorderLayout());

        rightAnswerPanel.setBorder(BorderFactory.createCompoundBorder (
                                            BorderFactory.createTitledBorder("Правильный ответ:"),
                                            BorderFactory.createEmptyBorder(10 , 10 , 10 , 10)
                                    ));

        rightAnswerPanel.add(answerText);


        JPanel commentPanel = new JPanel(new BorderLayout());

        commentPanel.setBorder(BorderFactory.createCompoundBorder (
                                            BorderFactory.createTitledBorder("Комментарий:"),
                                            BorderFactory.createEmptyBorder(10 , 10 , 10 , 10)
                                ));

        commentPanel.add(commentText);

        GroupLayout answerLayout = new GroupLayout(answerPanel);
        answerLayout.setAutoCreateContainerGaps(true);
        answerLayout.setAutoCreateGaps(true);

        answerLayout.setHorizontalGroup (
                answerLayout.createParallelGroup()
                            .addComponent ( rightAnswerPanel,
                                            0,
                                            GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE)
                            .addComponent ( commentPanel,
                                            0,
                                            GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE)
                                         );

        answerLayout.setVerticalGroup (
                answerLayout.createSequentialGroup()
                            .addComponent ( rightAnswerPanel  ,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.PREFERRED_SIZE )
                            .addComponent ( commentPanel ,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.PREFERRED_SIZE )
                                        );

        answerPanel.setLayout(answerLayout);

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
