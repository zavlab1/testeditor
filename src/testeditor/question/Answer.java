package testeditor.question;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вариант ответа
 */
public class Answer {
	/**
	 * @param right - указывает на правильность варианта ответа
	 * @param value - значение варианта ответа
	 */
	private boolean right;
	private String value;

	public Answer(String value, boolean right) {
		this.value = value;
		this.right = right;
	}

	/**
	 * @return возвращает строку с КОНКРЕТНЫМ вариантом ответа
	 */
	public String getValue() {
		return this.value;
	}

	public void setValue(String value, boolean right) {
		this.value = value;
		this.right = right;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return возвращает правильность варианта
	 */
	public boolean isTrue() {
		return right;
	}
}
