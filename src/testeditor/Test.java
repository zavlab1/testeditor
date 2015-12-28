package testeditor;

import testeditor.parser.Parser;
import testeditor.question.Question;

import java.util.TreeSet;

/**
 * Created by dimitry on 28.12.15.
 * Класс-синглтон для создания и хранения одного единственного объекта теста
 */
public class Test {
    TreeSet<Question> questions;

    private static Test ourInstance = new Test();

    public static Test getInstance() {
        return ourInstance;
    }

    public static Test getInstance(String title, TreeSet<Question> questions) {
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

    public TreeSet<Question> getQuestions() {
        return questions;
    }

    private Test() {
    }
}
