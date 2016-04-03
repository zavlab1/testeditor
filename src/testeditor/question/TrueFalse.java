package testeditor.question;

import testeditor.gui.QuestionFrames.QuestionFrame;
import testeditor.gui.QuestionFrames.TrueFalseFrame;
import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class TrueFalse extends Question {

    private QuestionFrame frame = null;

    public TrueFalse(String qName, String qText, List<Answer> answers) {
        super("Верно/Неверно", qName, qText, answers);
    }

    public String getLine(Saver saver){
        return saver.doLineForTrueFalse(this);
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new TrueFalseFrame() : frame;
    }
}
