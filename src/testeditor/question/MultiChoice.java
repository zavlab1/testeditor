package testeditor.question;

import testeditor.gui.QuestionFrames.MultiChoiceFrame;
import testeditor.gui.QuestionFrames.QuestionFrame;
import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class MultiChoice extends Question {

    private QuestionFrame frame = null;

    public MultiChoice(String qName, String qText, List<Answer> answers) {
        super("Выбор", qName, qText, answers);
    }

    public String getLine(Saver saver){
        return saver.doLineForMultiChoice(this);
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new MultiChoiceFrame() : frame;
    }
}
