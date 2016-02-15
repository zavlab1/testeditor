package testeditor;

import testeditor.parser.Parser;
import testeditor.question.Question;

import java.util.HashSet;

/**
 * Created by dimitry on 28.12.15.
 * Класс-синглтон для создания и хранения одного единственного объекта теста
 */
public class Test extends HashSet<Question> {

    /*
    public static Test getInstance(String title, HashSet<Question> questions) {
        ourInstance.questions = questions;
        return ourInstance;
    }*/

    public static Test getTestFromFile(String filepath) {
        try{
            Parser.parse(filepath);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    /*
    public HashSet<Question> getQuestions() {
        return questions;
    }*/

    public Test() {
    }
}
