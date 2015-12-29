package testeditor.parser;

import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.question.Select;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser extends Parser {
    public HashSet<Question> getQuestions(String filepath) {
        HashSet<Question> questions = new HashSet<>();

        ArrayList<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Москва", true));
        answers1.add(new Answer("Санкт-Петербург", false));
        answers1.add(new Answer("Курск", false));

        Question q1 = new Select("Столица нашей Родины?", answers1);
        questions.add(q1);

        ArrayList<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("ФМФ", false));
        answers2.add(new Answer("Геофак", false));
        answers2.add(new Answer("ИПФ", true));

        Question q2 = new Select("Название нашего факультета?", answers2);
        questions.add(q2);

        return questions;
    }
}
