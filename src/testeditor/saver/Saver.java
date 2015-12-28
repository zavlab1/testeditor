package testeditor.saver;

import testeditor.Test;
import testeditor.question.Question;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by main on 15.12.15.
 * интерфейс, содержащий методы сохранения теста в файл
 */
abstract public class Saver {

	protected Question question;
	protected String filepath;

	Saver(Question question, String filepath){
		this.question = question;
		this.filepath = filepath;
	}
	/**
	 * Запись теста в файл
	 * @param answersText - строка для записи
	 */
	public void toFile(String answersText) {

		File file = new File(filepath);

		try {
			//если файл не существует то создаем его
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(file.getAbsoluteFile());
			out.println(answersText);
			out.close();
		} catch(IOException e){
			System.err.println("Не могу записать данные в файл " + filepath);
		}
	}

	public Test insertToTest() {
		Test t = Test.getInstance();
		t.getQuestions().add(question);
		return t;
	}

	protected void save() {
		question.save(this);
	}

	abstract public String doLineForSelect();
	abstract public String doLineForOrder();
}
