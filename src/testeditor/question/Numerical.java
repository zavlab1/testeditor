package testeditor.question;

import testeditor.gui.question_view.NumericalFrame;
import testeditor.gui.question_view.QuestionFrame;
import testeditor.saver.Saver;

import java.util.Arrays;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class Numerical extends Question {

    public Numerical(String qName, String qText, List<Answer> answers) {
        super("Числовой", qName, qText, answers);
    }
    public Numerical() {
        this("", "", Arrays.asList(new Answer("")));
    }
    public String getLine(Saver saver){
        return saver.doLineForNumerical(this);
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new NumericalFrame(this) : frame;
    }

}