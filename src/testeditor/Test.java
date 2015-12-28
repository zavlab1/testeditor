package testeditor;

import testeditor.parser.Parser;

import java.util.Map;

/**
 * Created by dimitry on 28.12.15.
 */
public class Test {
    Map<String, Map.Entry<String, Boolean>> questions;

    private static Test ourInstance = new Test();

    public static Test getInstance() {
        return ourInstance;
    }

    public static Test getInstance(Map<String, Map.Entry<String, Boolean>> questions) {
        ourInstance.questions = questions;
        return ourInstance;
    }

    public static Test getInstance(String filepath) {
        ourInstance.questions = Parser.parse(filepath);
        return ourInstance;
    }

    private Test() {
    }
}
