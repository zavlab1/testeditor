package testeditor.gui.QuestionFrames;

import testeditor.question.Answer;
import testeditor.question.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimitry on 03.04.16.
 */
public class MatchingFrame extends QuestionFrame {
    public MatchingFrame(Question q) {
        super(q);
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        return aList;
    }
}
