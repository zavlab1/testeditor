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
        System.out.println(t);
        return t;
    }

    public static Test createTest() {
        t = new Test();
        return t;
    }

    private Test() {
    }
}
