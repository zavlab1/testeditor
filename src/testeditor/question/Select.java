package testeditor.question;

import testeditor.saver.Saver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class Select extends Question {

    public Select(int number, String head, List<Answer> answers) {
        super(number, head, answers);
    }

    public String getLine(Saver saver){
        return saver.doLineForSelect(this);
    }
}
