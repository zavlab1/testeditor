package testeditor.gui.QuestionFrames;

import testeditor.gui.services.GBC;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dimitry on 03.04.16.
 */
public class ShortAnswerFrame extends QuestionFrame {
	public ShortAnswerFrame(Question q) {
		super(q);

		answers.setLayout(new GridBagLayout());

		java.util.List<Answer> answerList = q.getAnswerList();

		JLabel labelQuestion = new JLabel("Правильный ответ:");

		answers.add(labelQuestion, new GBC(0, 0, 1, 1, 10, 10, 0, 0).setFill(GBC.HORIZONTAL));

		JTextArea answerText = new JTextArea(answerList.get(0).getAText());
		answerText.setWrapStyleWord(true);
		fields.add(answerText);
		answers.add(answerText, new GBC(1,0,1,1,0,0,0,0).setFill(GBC.HORIZONTAL));

		add(answers);
	}
}
