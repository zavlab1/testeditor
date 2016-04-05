package testeditor;

import testeditor.parser.Parser;
import testeditor.question.Question;

import java.util.TreeSet;

/**
 * Created by dimitry on 28.12.15.
 * Singleton class for test question storage
 */
public class Test extends TreeSet<Question> {

    private static Test t = null;

    public static Test getTestFromFile(String filepath) {
        try {
            t = Parser.parse(filepath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return t;
    }

    public static Test getTest () {
        t = (t != null) ? t : new Test();
        return t;
    }

    public static Test createTest() {
        t = new Test();
        return t;
    }

    private Test() {
    }
}
