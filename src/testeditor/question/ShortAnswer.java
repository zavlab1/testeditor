package testeditor.question;

import testeditor.gui.question_view.QuestionFrame;
import testeditor.gui.question_view.ShortAnswerFrame;
import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class ShortAnswer extends Question {

    private QuestionFrame frame = null;

    public ShortAnswer(String qName, String qText, List<Answer> answers) {
        super("Короткий ответ", qName, qText, answers);
    }

    public String getLine(Saver saver){
        return saver.doLineForShortAnswer(this);
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new ShortAnswerFrame(this) : frame;
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        return aList;
    }
}
