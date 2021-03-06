package testeditor.question;

import testeditor.gui.question_view.MultiChoiceFrame;
import testeditor.gui.question_view.QuestionFrame;
import testeditor.saver.Saver;

import java.util.Arrays;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class MultiChoice extends Question {

    public MultiChoice(String qName, String qText, List<Answer> answers) {
        super("Выбор", qName, qText, answers);
    }

    public MultiChoice() {
        this("", "", Arrays.asList(new Answer("")));
    }

    public String getLine(Saver saver){
        return saver.doLineForMultiChoice(this);
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new MultiChoiceFrame(this) : frame;
    }
}
