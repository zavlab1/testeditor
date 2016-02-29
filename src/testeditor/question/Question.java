package testeditor.question;

import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * абстарктный класс "Вопрос"
 */
abstract public class Question implements Comparable<Question> {

	private String head;
	private List<Answer> answers;

	/**
	 * @param head - заголовок вопроса
	 * @param answers - списочный массив вариантов ответа к вопросу
	 */
	Question(String head, List<Answer> answers) {
		this.answers = answers;
		answers.listIterator(); // to reset previously moved iterator
		this.head = head;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		else if (!(obj instanceof Question)) return false;
		return this.head.equals(((Question)obj).head);
	}

	@Override
	public int compareTo(Question q) {
		return (this.head.compareTo(q.getHead()));
	}

	public String toString(){
		return head;
	}

	abstract public String getLine(Saver saver);

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public List<Answer> getAnswerList() {
		return this.answers;
	}
}
