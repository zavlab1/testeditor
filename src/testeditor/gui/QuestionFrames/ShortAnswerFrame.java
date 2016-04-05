package testeditor.gui.QuestionFrames;

import testeditor.gui.services.GBC;
import testeditor.gui.services.QLabel;
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
    private List<Answer> answerList;

    public ShortAnswerFrame(Question q) {
        super(q);

        answerPanel.setLayout(new GridBagLayout());
        answerPanel.setBorder(new TitledBorder("Правильный ответ: "));

        answerList = q.getAnswerList();

        JTextArea answerText = new QTextArea(answerList.get(0).getAText());
        answerText.setLineWrap(true);
        answerText.setWrapStyleWord(true);
        fields.add(answerText);
        answerPanel.add(answerText,new GBC(0,0,1,1,0,0,100,0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));

        add(answerPanel);
        setSize(this.INITIAL_WIDTH,(int)(this.INITIAL_HEIGHT/1.5));
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        return aList;
    }
}
