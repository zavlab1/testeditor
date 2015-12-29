package testeditor;

import testeditor.parser.Parser;
import testeditor.question.Question;

import java.util.HashSet;

/**
 * Created by dimitry on 28.12.15.
 * Класс-синглтон для создания и хранения одного единственного объекта теста
 */
public class Test {
    HashSet<Question> questions;

    private static Test ourInstance = new Test();

    public static Test getInstance() {
        return ourInstance;
    }

    public static Test getInstance(String title, HashSet<Question> questions) {
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

    public HashSet<Question> getQuestions() {
        return questions;
    }

    private Test() {
    }
}
