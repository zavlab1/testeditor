package testeditor.parser;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser extends Parser {
    public Map<String, Map<String, Boolean>> getQuestions(String filepath) {
        Map<String, Map<String, Boolean>> questions = new TreeMap<>();

        Map<String, Boolean> answers1 = new TreeMap<>();
        answers1.put("Москва", true);
        answers1.put("Санкт-Петербург", false);
        answers1.put("Курск", false);

        questions.put("Столица нашей Родины?", answers1);

        Map<String, Boolean> answers2 = new TreeMap<>();
        answers2.put("ФМФ", false);
        answers2.put("Геофак", false);
        answers2.put("ИПФ", true);

        questions.put("Название нашего факультета?", answers2);

        return questions;
    }
}
