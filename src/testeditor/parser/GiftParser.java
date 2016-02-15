package testeditor.parser;

import testeditor.Test;
import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.question.Select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser extends Parser {
    public Test getTest(String filepath) throws IOException {
        Test test = new Test();
        /*
        ArrayList<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Москва", true));
        answers1.add(new Answer("Санкт-Петербург", false));
        answers1.add(new Answer("Курск", false));

        Question q1 = new Select("Столица нашей Родины?", answers1);
        test.add(q1);

        ArrayList<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("ФМФ", false));
        answers2.add(new Answer("Геофак", false));
        answers2.add(new Answer("ИПФ", true));

        Question q2 = new Select("Название нашего факультета?", answers2);
        test.add(q2);*/

        List<String> lineList = new ArrayList<>();
        Scanner in = new Scanner(new File(filepath));
        while (in.hasNextLine())
            lineList.add(in.nextLine());

        return test;
    }
}
