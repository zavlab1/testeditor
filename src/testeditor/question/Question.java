package testeditor.question;

import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * абстарктный класс "Вопрос"
 */
abstract public class Question implements Comparable<Question> {

    protected String qText;
    private String qType;
    private List<Answer> answers;
    protected String qName;
    public final String TYPE;

    /**
     * @param qText - заголовок вопроса
     * @param answers - списочный массив вариантов ответа к вопросу
     */
    Question(String type, String qName, String qText, List<Answer> answers) {
        this.answers = answers;
        answers.listIterator(); // to reset previously moved iterator
        this.qText = qText.trim();
        this.qName = qName;
        this.TYPE = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        else if (!(obj instanceof Question)) return false;
        return this.getQHead().equals(((Question)obj).getQHead());
    }

    @Override
    public int compareTo(Question q) {
        return this.getQHead().compareTo(q.getQHead());
    }

    public String getQHead() {
        return this.getQName() + this.getQText();
    }

    public String toString(){
        return qText;
    }

    abstract public String getLine(Saver saver);

    public String getQText() {
        return this.qText;
    }

    public String getQName() { return this.qName; }

    public void setQName(String qName) { this.qName = qName; }

    public void setQText(String qText) {
        this.qText = qText;
    }

    public List<Answer> getAnswerList() {
        return this.answers;
    }
}
