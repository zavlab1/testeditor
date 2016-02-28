package testeditor.parser;

import testeditor.Test;
import testeditor.question.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser extends Parser {
    public Test getTest(String filepath) throws IOException {
        Test test = new Test();
        List<String> lineList = getLineList(filepath);
        List<List<String>> qTexts = getQuestionsTexts(lineList);
        for (List<String> qText : qTexts) {
            Question q = getQuestion(qText);
            test.add(q);
        }
        return test;
    }

    private List<String> getLineList(String filepath) throws IOException {
        Scanner in = new Scanner(new File(filepath));
        List<String> lineList = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (!line.isEmpty()) {
                lineList.add(line.trim());
            }
        }
        return lineList;
    }

    private List<List<String>> getQuestionsTexts(List<String> lineList) {
        List<List<String>> qTexts = new ArrayList<>();
        for (String line : lineList) {
            if (line.startsWith("::")) {
                ArrayList<String> qText = new ArrayList<>();
                qText.add(line);
                String nextLine = null;
                ListIterator it = lineList.listIterator();
                while (it.hasNext() &&
                        !((nextLine = (String) it.next()).startsWith("::"))) {
                    qText.add(nextLine);
                    it.previous();
                }
                qTexts.add(qText);
            }
        }
        return qTexts;
    }

    private Question getQuestion(List<String> qText) {

        Boolean html = new Boolean(false); // специально объектом, чтобы мог модифицироваться по ссылке в др. методах

        String head = getHead(qText.get(0), html);                  // Получаем текст вопроса

        List<String> answerLines = qText.subList(1, qText.size());
        List<Answer> answers = getAnswers(answerLines, html);       // Получаем варианты ответа

        ListIterator li = answers.listIterator();
        for (Answer a : answers) {
            String val = a.getValue();
            if (val.startsWith("=") && val.contains("->")) {
                if (li.hasNext()) {
                    String nextVal = answers.get(li.nextIndex()).getValue();
                    if ( nextVal.startsWith("=") &&
                         nextVal.contains("->") ) {
                        return new Conformity(head, answers);                 // вопрос на соответсвтие
                    }
                }
            } else if ( val.startsWith("=") ||
                        val.startsWith("\\~\\%") ) {
                return new Select(head, answers);                             // вопрос на выбор
            } else if ( val.toUpperCase().endsWith("{TRUE}") ||
                        val.toUpperCase().endsWith("{FALSE}") ) {
                return new TrueFalse(head, answers);                          // вопрос на Да/Нет
            }
        }
        return new Select(head, answers);
    }


    private String getHead(String line, Boolean html) {

        Pattern pattern = Pattern.compile("^::(\\d+)\\.?::(.*?)\\{?$");  //
        Matcher m = pattern.matcher(line);                               // убираем всякие "::1.::" в начале
        m.find();                                                        // и "}" в конце строки
        String head = m.group(2);                                        //

        if (html = head.startsWith("[html]")) {         // если html-метка стоит
            head = head.replace("[html]", "");          // убираем html-метку
            head = head.replaceAll("\\<.*?>", "");      // удаляем все теги
            head = head.replaceAll("\\\\(?!\\\\)", ""); //удаляем все одиночные обратные слеши, а из двойных делаем одиночные
            //особенность java - приходится удваивать слеши
        }
        return head;
    }

    private List<Answer> getAnswers(List<String> aLines, Boolean html) {
        List<Answer> answers = new ArrayList<>();
        for (String line : aLines) {
            if (html) {
                line = line.replaceAll("\\<.*?>", "");      // удаляем все теги
                line = line.replaceAll("\\\\(?!\\\\)", ""); //удаляем все одиночные обратные слеши, а из двойных делаем одиночные
                //особенность java - приходится удваивать слеши
            }
            answers.add(new Answer(line, (line.startsWith("=")) || line.startsWith("\\~\\%")));
        }
        return answers;
    }
}