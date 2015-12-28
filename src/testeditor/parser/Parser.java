package testeditor.parser;

import testeditor.question.Question;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by dimitry on 28.12.15.
 * Базовый класс для парсеров различных форматов
 */
abstract public class Parser {

    public static TreeSet<Question> parse(String filepath) throws Exception {
        Parser parser = Parser.getParser(filepath);
        return parser.getQuestions(filepath);
    }

    public static Parser getParser(String filepath) throws Exception {
        String ext = filepath.substring(filepath.lastIndexOf('.') + 1).toLowerCase();
        switch (ext) {
            case "gift":
                return new GiftParser();
            case "xml":
                return new XMLParser();
            default:
                throw new Exception("Такой формат файла не зарегистрирован в программе");
        }
    }

    abstract public TreeSet<Question> getQuestions(String filepath);
}
