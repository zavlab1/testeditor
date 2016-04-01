package testeditor.question;

import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class ShortAnswer extends Question {

    public ShortAnswer(String qName, String qText, List<Answer> answers) {
        super("Короткий ответ", qName, qText, answers);
    }

    public String getLine(Saver saver){
        return saver.doLineForShortAnswer(this);
    }
}
