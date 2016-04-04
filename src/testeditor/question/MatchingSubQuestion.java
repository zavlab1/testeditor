package testeditor.question;

import testeditor.gui.QuestionFrames.MatchingFrame;
import testeditor.gui.QuestionFrames.QuestionFrame;
import testeditor.saver.Saver;

import java.util.List;

/**
 * Created by Максим on 29.03.2016.
 */
public class MatchingSubQuestion extends Question {

    private QuestionFrame frame = null;

    public MatchingSubQuestion
            (String qName, String qText, List<Answer> answers) {
        super("", qName, qText, answers);
    }

    public String getLine(Saver saver){
        return null;
    }

    public QuestionFrame getFrame() {
        return (frame == null) ? new MatchingFrame(this) : frame;
    }
}
