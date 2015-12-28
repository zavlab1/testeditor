package testeditor.question;

import testeditor.Test;
import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class Select extends Question {

	public Select(String head, ArrayList<Answer> answers) {
		super(head, answers);
	}

	public void save(Saver saver) {
		Test t = saver.insertToTest();
		String answerLine = saver.doLineForSelect();
		saver.toFile(answerLine);
	}
}
