package testeditor.parser;

import testeditor.Test;
import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.question.Select;
import testeditor.saver.Saver;

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
        Scanner in = new Scanner(new File(filepath));
        List<String> lineList = getLineList(in);
        List<List<String>> qTexts = getQuestionsTexts(in, lineList);
        for (List<String> qText : qTexts) {
            Question q = getQuestion(qText);
            test.add(q);
        }
        return test;
    }

    private List<String> getLineList(Scanner in) {
        List<String> lineList = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (!line.isEmpty()) {
                lineList.add(line.trim());
            }
        }
        return lineList;
    }

    private List<List<String>> getQuestionsTexts(Scanner in, List<String> lineList) {
        List<List<String>> qTexts = new ArrayList<>();
        for (String line : lineList) {
            if (line.startsWith("::")) {
                ArrayList<String> qStrings = new ArrayList<>();
                qStrings.add(line);
                String nextLine = null;
                while ( in.hasNextLine() &&
                        !( (nextLine = in.nextLine()).startsWith("::")) ) {
                    qStrings.add(nextLine);
                }
                qTexts.add(qStrings);
            }
        }
        return qTexts;
    }

    private Question getQuestion(List<String> qText) {
        //здесь будет парсер строк вопроса


        return new Question();
    }
}
