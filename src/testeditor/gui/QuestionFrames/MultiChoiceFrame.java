package testeditor.gui.QuestionFrames;

import testeditor.gui.services.GBC;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.util.List;

/**
 * Created by dimitry on 03.04.16.
 */
public class MultiChoiceFrame extends QuestionFrame {
	public MultiChoiceFrame(Question q) {
		super(q);
		answers.setLayout(new GridBagLayout());

		List<Answer> aList = q.getAnswerList();

		for(int i=0; i<aList.size(); i++) {

			JRadioButton b = new JRadioButton();
			answers.add(b, new GBC(0,i,1,1,0,0,0,0).setFill(GBC.HORIZONTAL));

			JTextArea answerText = new JTextArea(aList.get(i).getAText());
			answerText.setWrapStyleWord(true);
			fields.add(answerText);
			answers.add(answerText, new GBC(1,i,1,1,0,0,0,0).setFill(GBC.HORIZONTAL));

			JButton delButton = new JButton("<html><font color='red'><b>X</b></font></html>");
			answers.add(delButton,new GBC(2,i,1,1,0,0,0,0).setFill(GBC.HORIZONTAL));
		}

		add(answers);
	}
}
