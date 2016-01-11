package testeditor.parser;

import testeditor.question.Question;

import java.util.HashSet;

/**
 * Created by dimitry on 28.12.15.
 * Базовый класс для парсеров различных форматов
 */
public interface Parser {

    static HashSet<Question> parse(String filepath) throws Exception {
        Parser parser = Parser.getParser(filepath);
        return parser.getQuestions(filepath);
    }

    static Parser getParser(String filepath) throws Exception {
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

    HashSet<Question> getQuestions(String filepath);
}
