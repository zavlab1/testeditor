package testeditor.saver;

import testeditor.Test;
import testeditor.question.*;

/**
 * Класс, ответственный за запись вопроса в файл в GIFT-формате
 * Абстрактный потому, что содержит пока что только статические методы;
 */
public class GiftSaver extends Saver {
	/**
	 * Записывает вопрос в текстовый файл в формате GIFT
	 * @param filepath - путь к выходному файлу
	 * @param test - объект вопроса
	 */
	public GiftSaver(Test test, String filepath) {
		super(test, filepath);
	}

	/**
 	 * @return возвращает строку с вопросом и вариантами ответа
	 * для вопросов на выброр в формате GIFT
	 */
	public String doLineForSelect(Question q){
		String answerLine = q.getHead()+"\n{\n";

		for (Answer a : q.getAnswerList()) {
			answerLine += (a.isTrue() ? "\t=" : "\t~") + a.getValue() + "\n";
		}

		return answerLine.trim()+"\n}";
	}

	/**
	 * @return возвращает строку с вопросом и вариантами ответа
	 * для вопросов на порядок в  формате GIFT
	 */
	public String doLineForTrueFalse(Question q){
		String answerLine = q.getHead()+"\n{" + q.getAnswerList().get(0).getValue() + "}\n";
		return answerLine.trim()+"\n";
	}

	public String doLineForConformity(Question q){
		String answerLine = q.getHead()+"\n{" + q.getAnswerList().get(0) + "}\n";
		return answerLine.trim()+"\n";
	}

	public String doLineForOrder(Question q){
		String answerLine = q.getHead()+"\n{" + q.getAnswerList().get(0) + "}\n";
		return answerLine.trim();
	}
}
