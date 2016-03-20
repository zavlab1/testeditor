package testeditor.question;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вариант ответа
 */
public class Answer {

    private int degree;
    private String value;

    /**
     * @param degree - указывает на степень правильности варианта ответа (от 0 до 1 с точностью до сотых)
     * @param value - значение варианта ответа
     */
    public Answer(String value, int degree) {
        this.value = value;
        this.degree = degree;
    }

    /**
     * @return возвращает строку с КОНКРЕТНЫМ вариантом ответа
     */
    public String getValue() {
        return this.value;
    }

    public void setValue(String value, int degree) {
        this.value = value;
        this.degree = degree;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return возвращает правильность варианта
     */
    public int getDegree() {
        return degree;
    }
}
