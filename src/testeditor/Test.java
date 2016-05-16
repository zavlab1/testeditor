package testeditor;

import testeditor.parser.Parser;
import testeditor.question.Question;

import javax.swing.event.ChangeListener;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by dimitry on 28.12.15.
 * Singleton class for test question storage
 */
public class Test extends LinkedHashSet<Question> {
    private static String filePath = "";
    private static Test t = null;

    public static Test getTestFromFile(String filepath) {
        try {
            t = Parser.parse(filepath);
            filePath = filepath;
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

    public String getFilePath() {
        return filePath;
    }

    public void update(List list) {
        this.clear();
        this.addAll(list);
    }
}
