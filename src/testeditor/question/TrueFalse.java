package testeditor.question;

import testeditor.gui.question_view.QuestionFrame;
import testeditor.gui.question_view.TrueFalseFrame;
import testeditor.saver.Saver;

import java.util.Arrays;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class TrueFalse extends Question {

    public TrueFalse(String qName, String qText, List<Answer> answers) {
        super("Верно/Неверно", qName, qText, answers);
    }
    public TrueFalse() {
        this("", "", Arrays.asList(new Answer("")));
    }

    public String getLine(Saver saver){
        return saver.doLineForTrueFalse(this);
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new TrueFalseFrame(this) : frame;
    }
}
