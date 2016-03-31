package testeditor.question;

import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by main on 14.12.15.
 * Класс, описывающий вопрос с выбором одного или нескольких вариантов ответа
 */
public class ShortAnswer extends Question {

    //final public String TYPE = "Короткий ответ";

    public ShortAnswer(String qName, String qText, List<Answer> answers) {
        super(qName, qText, answers);
        setType("Короткий ответ");
    }

    public String getLine(Saver saver){
        return saver.doLineForShortAnswer(this);
    }
}
