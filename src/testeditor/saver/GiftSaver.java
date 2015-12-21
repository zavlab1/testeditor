package testeditor.saver;

import testeditor.question.*;


import java.io.*;

/**
 * Класс, ответственный за запись вопроса в файл в GIFT-формате
 * Абстрактный потому, что содержит пока что только статические методы;
 */
public abstract class GiftSaver implements Saver {
	/**
	 * Записывает вопрос в текстовый файл в формате GIFT
	 * @param filename - путь к выходному файлу
	 * @param question - объект вопроса
	 */
	public static void toFile (String filename, Question question) throws IOException
	{

		File file = new File(filename);

		//если файл не существует то создаем его
		if(!file.exists())
			file.createNewFile();

		PrintWriter out = new PrintWriter(file.getAbsoluteFile());
		out.println(getQuestionLine(question));
		out.close();
	}

	/**
 	 * @return возвращает строку с вопросом и вариантами ответа
	 * в зависимости от его типа в формате GIFT
	 */
	protected static String getQuestionLine (Question question){
		String answerLine = question.getHead()+"\n{\n";

		switch (question.getType()){
			case "select":
				for (Answer a : question.getAnswerList()) {
				answerLine += (a.isTrue() ? "\t=" : "\t~") + a.getValue() + "\n";
				}
				return answerLine.trim()+"\n}";
			default: return null;

		}
	}

}
