package testeditor.question;

import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class TrueFalse extends Question {

	public TrueFalse(String head, List<Answer> answers) {
		super(head, answers);
	}

	public String getLine(Saver saver){
		return saver.doLineForTrueFalse(this);
	}
}
