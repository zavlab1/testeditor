package testeditor.question;

import testeditor.gui.question_view.MatchingFrame;
import testeditor.gui.question_view.QuestionFrame;
import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class Matching extends Question {

    private ArrayList<Question> subQuestions;

    public Matching(String qName, String qText, List<Answer> answers) {
        super("Соответствие", qName, qText, answers);
        subQuestions = new ArrayList<>();
    }
    public Matching() {
        this("", "", Arrays.asList(new Answer("")));
    }
    public String getLine(Saver saver){
        return saver.doLineForMatching(this);
    }

    public void addSubQuestion(Question subQuestion){
        subQuestions.add(subQuestion);
    }

    public ArrayList<Question> getSubQuestions(){
        return subQuestions;
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new MatchingFrame(this) : frame;
    }
}
