package testeditor.question;

import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class MultiChoice extends Question {

    final public String TYPE = "Выбор";

    public MultiChoice(String qName, String qText, List<Answer> answers) {
        super(qName, qText, answers);
    }

    public String getLine(Saver saver){
        return saver.doLineForMultiChoice(this);
    }
}
