package testeditor;

import testeditor.parser.Parser;
import testeditor.question.Question;

import java.util.TreeSet;

/**
 * Created by dimitry on 28.12.15.
 */
public class Test extends TreeSet<Question> {
    /*
    public static Test getInstance(String title, HashSet<Question> questions) {
        ourInstance.questions = questions;
        return ourInstance;
    }*/

    public static Test getTestFromFile(String filepath) {
        Test t = null;
        try {
            t = Parser.parse(filepath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return t;
    }

    public Test() {
    }
}
