package testeditor.saver;

import testeditor.question.*;

/**
 * Класс, ответственный за запись вопроса в файл в GIFT-формате
 * Абстрактный потому, что содержит пока что только статические методы;
 */
public class GiftSaver extends Saver {
	/**
	 * Записывает вопрос в текстовый файл в формате GIFT
	 * @param filepath - путь к выходному файлу
	 * @param question - объект вопроса
	 */
	public GiftSaver(Question question, String filepath) {
		super(question, filepath);
	}

	/**
 	 * @return возвращает строку с вопросом и вариантами ответа
	 * для вопросов на выброр в формате GIFT
	 */
	public String doLineForSelect(){

		String answerLine = question.getHead()+"\n{\n";

		for (Answer a : question.getAnswerList()) {
			answerLine += (a.isTrue() ? "\t=" : "\t~") + a.getValue() + "\n";
		}

		return answerLine.trim()+"\n}";
	}

	/**
	 * @return возвращает строку с вопросом и вариантами ответа
	 * для вопросов на порядок в  формате GIFT
	 */
	public String doLineForOrder(){

		String answerLine = question.getHead()+"\n{\n";

		for (Answer a : question.getAnswerList()) {
			answerLine += " ";
		}

		return answerLine.trim()+"\n}";
	}

}
