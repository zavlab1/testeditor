package testeditor;

import testeditor.parser.Parser;

import java.util.Map;

/**
 * Created by dimitry on 28.12.15.
 * Класс-синглтон для создания и хранения одного единственного объекта теста
 */
public class Test {
    Map<String, Map<String, Boolean>> questions;

    private static Test ourInstance = new Test();

    public static Test getInstance() {
        return ourInstance;
    }

    public static Test getInstance(Map<String, Map<String, Boolean>> questions) {
        ourInstance.questions = questions;
        return ourInstance;
    }

    public static Test getInstance(String filepath) {
        try{
            ourInstance.questions = Parser.parse(filepath);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return ourInstance;
    }

    private Test() {
    }
}
