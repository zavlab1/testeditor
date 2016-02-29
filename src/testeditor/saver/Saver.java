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

	protected Test test;
	protected String filepath;

	Saver(Test test, String filepath){
		this.test = test;
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
				System.out.println("create");
				file.createNewFile();
			} else {
				System.out.println("exists");
			}

			PrintWriter out = new PrintWriter(file.getAbsoluteFile());
			out.println(answersText);
			out.close();
		} catch(IOException e){
			System.err.println("Не могу записать данные в файл " + filepath);
		}
	}

	public void insertToTest(Question q) {
		//System.out.println(q);
		test.remove(q); //удаляем существующий вопрос с таким же заголовком
		test.add(q);
	}

	public void save(Question question) {
		System.out.println("123");
		insertToTest(question);

		toFile(getText());
	}

	private String getText(){
		String text = "";
		for(Question q : test){
			text += q.getLine(this) + "\n\n";
		}
		return text;
	}

	abstract public String doLineForSelect(Question q);
	abstract public String doLineForOrder(Question q);
}
