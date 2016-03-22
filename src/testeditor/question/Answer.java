package testeditor.question;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вариант ответа
 */
public class Answer {

    public static final int MAX_DEGREE = 100;
    public static final int MIN_DEGREE = 0;

    private int degree;
    private String aText;
    private String aComment;

    /**
     * @param degree - указывает на степень правильности варианта ответа (от 0 до 1 с точностью до сотых)
     * @param text - значение варианта ответа c комментарием (если есть)
     */
    public Answer(String text, int degree) {
        String[] value = text.split("#");
        this.aText = value[0];
        this.aComment = value.length == 2 ? value[1] : "";
        this.degree = degree;
    }
    public Answer(String text) {
        this(text, Answer.MAX_DEGREE);
    }

    /**
     * @return возвращает строку с КОНКРЕТНЫМ вариантом ответа
     */
    public String getAText() {
        return this.aText;
    }
    public String getAComment() {
        return this.aComment;
    }
    /**
     * @return возвращает правильность варианта в процентах
     */
    public int getDegree() {
        return degree;
    }
    public void setValue (String text, int degree) {
        String[] value = text.split("#");
        this.aText = value[0];
        this.aComment = value.length == 2 ? value[1] : "";
        this.degree = degree;
    }

    public void setAText(String aText) {
        this.aText = aText;
    }
}
