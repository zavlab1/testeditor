package testeditor.parser;

import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.question.Select;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser extends Parser {
    public TreeSet<Question> getQuestions(String filepath) {
        TreeSet<Question> questions = new TreeSet<>();

        ArrayList<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Москва", true));
        answers1.add(new Answer("Санкт-Петербург", false));
        answers1.add(new Answer("Курск", false));

        questions.add(new Select("Столица нашей Родины?", answers1));

        ArrayList<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("ФМФ", false));
        answers2.add(new Answer("Геофак", false));
        answers2.add(new Answer("ИПФ", true));

        questions.add(new Select("Название нашего факультета?", answers2));

        return questions;
    }
}
