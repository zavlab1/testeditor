package testeditor.question;

import testeditor.Test;
import testeditor.gui.question_view.QuestionFrame;
import testeditor.saver.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * абстарктный класс "Вопрос"
 */
abstract public class Question implements Comparable<Question> {

    protected String qText;
    private List<Answer> answers;
    protected String qName;
    public final String TYPE;
    private float defaultGrade;
    private float penalty;
    protected QuestionFrame frame;

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
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                Component component = (Component) event.getSource();
                JFrame f = (JFrame) SwingUtilities.getRoot(component);
                f.repaint();
            }
        });
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

    public String getQText() {
        return this.qText;
    }

    public String getQName() { return this.qName; }

    public void setQName(String qName) { this.qName = qName; }

    public void setQText(String qText) {
        this.qText = qText;
    }

    public List<Answer> getAnswerList() { return this.answers; }

    abstract public String getLine(Saver saver);

    abstract public QuestionFrame getFrame();

    public float getDefaultGrade() { return defaultGrade; }

    public void setDefaultGrade(float defaultGrade) { this.defaultGrade = defaultGrade; }

    public float getPenalty() { return penalty; }

    public void setPenalty(float penalty) { this.penalty = penalty; }

    public void setAnswers(List<Answer> aList) {
        this.answers = aList;
    }

    public void save() {
        Test.getTest().remove(this); //удаляем существующий вопрос с таким же заголовком
        Test.getTest().add(this);
    }
}
