package testeditor.parser;

import java.util.Map;

/**
 * Created by dimitry on 28.12.15.
 */
public class Parser {
    public static Map<String, Map.Entry<String, Boolean>> parse(String filepath) {
        Parser parser = Parser.getParser(filepath);
        return parser.getQuestions();
    }

    public static Parser getParser(String filepath) {
        String ext = filepath.substring(filepath.lastIndexOf('.') + 1).toLowerCase();
        switch (ext) {
            case "gift":
                return new GiftParser();
            case "xml":
                return new XMLParser();
            default:
                System.err.println("Такой формат файла не зарегистрирован в программе");
        }
    }

    private Map<String, Map.Entry<String, Boolean>> getQuestions() {
        return questions;
    }
}
