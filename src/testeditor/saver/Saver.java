package testeditor.saver;

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

	/**
	 * Запись теста в файл
	 * @param answerLine - строка для записи
	 */
	public void toFile(String answerLine) {

		File file = new File(filepath);

		try {
			//если файл не существует то создаем его
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(file.getAbsoluteFile());
			out.println(answerLine);
			out.close();
		} catch(IOException e){
			System.err.println("Не могу записать данные в файл " + filepath);
		}
	}

	abstract public String doLineForSelect();
	abstract public String doLineForOrder();
}
