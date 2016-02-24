package testeditor.parser;

import testeditor.Test;
import testeditor.question.Answer;
import testeditor.question.Order;
import testeditor.question.Question;
import testeditor.question.Select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                ArrayList<String> qText = new ArrayList<>();
                qText.add(line);
                String nextLine = null;
                while ( in.hasNextLine() &&
                        !( (nextLine = in.nextLine()).startsWith("::")) ) {
                    qText.add(nextLine);
                }
                qTexts.add(qText);
            }
        }
        return qTexts;
    }

    private Question getQuestion(List<String> qText) {
        //здесь будет парсер строк вопроса

        Boolean html = new Boolean(false); // специально объектом, чтобы мог модифицироваться по ссылке в др. методах

        String head = getHead(qText.get(0), html);                  // Получаем текствопроса

        List<String> answerLines = qText.subList(1, qText.size());
        List<Answer> answers = getAnswers(answerLines, html);       // Получаем варианты ответа

        String type = getType(answerLines);

        switch (type) {
            case ("select"): return new Select(head, answers);
            case ("order"):  return new Order(head, answers);
        }
        return new Select(head, answers);
    }


    private String getHead(String line, Boolean html) {

        Pattern pattern = Pattern.compile("^::(\\d+)\\.?::(.*?)\\{?$");  //
        Matcher m = pattern.matcher(line);                               // убираем всякие "::1.::" в начале
        m.find();                                                        // и "}" в конце строки
        String head = m.group(2);                                        //

        if (html = head.startsWith("[html]")) {           // если html-метка стоит
            head = head.replace("[html]", "");     // убираем html-метку
            head = head.replaceAll("\\<.*?>", ""); // удаляем все теги
            head = head.replaceAll("\\\\(?!\\\\)", ""); //удаляем все одиночные обратные слеши, а из двойных делаем одиночные
                                                        //особенность java - приходится удваивать слеши
        }
        return head;
    }

    private List<Answer> getAnswers(List<String> aLines, Boolean html) {
        List<Answer> answers = new ArrayList<>();
        for (String line : aLines) {
            if (html) {
                line = line.replaceAll("\\<.*?>", ""); // удаляем все теги
                line = line.replaceAll("\\\\(?!\\\\)", ""); //удаляем все одиночные обратные слеши, а из двойных делаем одиночные
                                                            //особенность java - приходится удваивать слеши
            }
            answers.add(new Answer(line, (line.startsWith("=")) || line.startsWith("\\~\\%")));
        }
        return answers;
    }

    private String getType(List<String> aLines) {
        for (String line : aLines) {
            if (line.startsWith("=")) {
                return "select";
            }
            else if(line.startsWith("\\~\\%")) {
                return "multiselect"; // и т.д., и т.п.
            }
        }
        return "select";
    }
}

