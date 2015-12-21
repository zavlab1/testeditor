package testeditor.question;

import java.util.ArrayList;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class Select extends Question {
	private final String type = "select";

	public Select(String head, ArrayList<Answer> answers) {
		super(head, answers);
	}

	public String getType(){
		return type;
	}
	/**
	 * @return возвращает строку с вопросом и вариантами ответа в формате GIFT

	public String getQuestionLine() {
		String answerLine =this.getHead()+"\n{\n";
		for (Answer a : this.getAnswerList()) {
			 answerLine += (a.isTrue() ? "\t=" : "\t~") + a.getValue() + "\n";
		}
		return answerLine.trim()+"\n}";
	}
	*/
	public String xyz() {
		String answerLine =this.getHead()+"\n{\n";
		for (Answer a : this.getAnswerList()) {
			answerLine += (a.isTrue() ? "\t=" : "\t~") + a.getValue() + "\n";
		}
		return answerLine.trim()+"\n}";
	}
}
