package testeditor.question;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вариант ответа
 */
public class Answer {

<<<<<<< HEAD
    public static final int MAX_DEGREE = 100;
    public static final int MIN_DEGREE = 0;

    private int degree;
    private String aText;
    private String aComment;
=======
    private int degree;
    private String value;
>>>>>>> 836a764... Do degree for answer int type & small fixes

    /**
     * @param text - значение варианта ответа
     * @param degree - указывает на степень правильности варианта ответа (от 0 до 1 с точностью до сотых)
     * @param comment - Комментарий (если есть)
     */
<<<<<<< HEAD
    public Answer(String text, int degree, String comment) {
        aText = text;
        aComment = comment;
=======
    public Answer(String value, int degree) {
        this.value = value;
>>>>>>> 836a764... Do degree for answer int type & small fixes
        this.degree = degree;
    }
    public Answer(String text, int degree) {
        this(text, degree, "");
    }
    public Answer(String text) {
        this(text, Answer.MAX_DEGREE, "");
    }

    /**
     * @return возвращает строку с вариантом ответа
     */
<<<<<<< HEAD
    public String getAText() {
        return this.aText;
=======
    public String getValue() {
        return this.value;
    }

    public void setValue(String value, int degree) {
        this.value = value;
        this.degree = degree;
>>>>>>> 836a764... Do degree for answer int type & small fixes
    }

    /**
     * @return возвращает строку с комментарием для вариантом ответа
     */
    public String getAComment() {
        return this.aComment;
    }

    /**
     * @return возвращает правильность варианта в процентах
     */
    public int getDegree() {
        return degree;
    }
    public void setValue (String text, int degree, String comment) {
        aText = text;
        aComment = comment;
        this.degree = degree;
    }

    public void setAText(String aText) {
        this.aText = aText;
    }
    public void setDegree(int degree) {
        this.degree = degree;
    }
    public void setAComment(String aComment) {
        this.aComment = aComment;
    }
}
