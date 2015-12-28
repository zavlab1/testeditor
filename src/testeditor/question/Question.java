package testeditor.question;

import testeditor.saver.Saver;

import java.util.ArrayList;

/**
 * Created by main on 14.12.15.
 * абстарктный класс "Вопрос"
 */
abstract public class Question {
	/**
	 * @param saver - объект класса, сохраняющего в заданный формат
	 * @param answers - списочный массив вариантов ответа к вопросу
	 * @param head - заголовок вопроса
	 */

	private ArrayList<Answer> answers;
	private String head;
	protected Saver saver;

	Question(String head, ArrayList<Answer> answers) {
		this.answers = answers;
		this.head = head;
		this.saver = saver;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.head.equals(((Question)obj).head)) {
			return true;
		}
		return false;
	}

	abstract public void save(Saver saver);

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public ArrayList<Answer> getAnswerList() {
		return this.answers;
	}
}
