package testeditor.question;

import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by Максим on 29.03.2016.
 */
public class MatchingSubQuestion extends Question {
    public MatchingSubQuestion
            (String qName, String qText, List<Answer> answers) {
        super(qName, qText, answers);
    }

    public String getLine(Saver saver){
        return null;
    }
}
