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
            if (!line.isEmpty() &&
                !line.startsWith("//") &&
                !line.startsWith("#")) {
                    lineList.add(line.trim());
            }
        }
        return lineList;
    }

    private List<List<String>> getQuestionsTexts(List<String> lineList) {
        List<List<String>> qTexts = new ArrayList<>();
		ListIterator<String> it = lineList.listIterator();
        while (it.hasNext()) {
			String line = it.next();
            if (line.startsWith("::")) {
                List<String> qText = new ArrayList<>();
                qText.add(line);
                while (it.hasNext() &&
                        (lineList.get(it.nextIndex()).startsWith("{")) ||
                        !(lineList.get(it.nextIndex()).endsWith("}"))) {
                    qText.add(it.next());
                }
                qTexts.add(qText);
            }
        }
		return qTexts;
    }

    private Question getQuestion(List<String> qText) {

        Boolean[] html = new Boolean[] {new Boolean(false)}; // специально объектом, чтобы мог модифицироваться по ссылке в др. методах

        String head = getHead(qText.get(0), html);                  // Получаем текст вопроса

        List<String> answerLines = qText.subList(1, qText.size());
        List<Answer> answers = getAnswers(answerLines, html);       // Получаем варианты ответа

        if ( head.toUpperCase().endsWith("{TRUE}") ||
             head.toUpperCase().endsWith("{FALSE}") ) {
                Pattern pattern = Pattern.compile("\\{(.+)\\}$");  //
                Matcher m = pattern.matcher(head);
                m.find();
                answers.add(new Answer(m.group(1), Boolean.parseBoolean("true")));
                return new TrueFalse(head.replaceAll("\\{.+\\}$", ""), answers);                     // вопрос на Да/Нет
        }

        ListIterator li = answers.listIterator();
        while (li.hasNext()) {

            Answer a = (Answer) li.next();
            String val = a.getValue();System.out.println(val.substring(1, val.length()-1));
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
            } else if ( val == "{TRUE}" ||
                        val == "{FALSE}" ) {
                System.out.println(val.substring(1, val.length()-1));
                Pattern pattern = Pattern.compile("\\{(.+)\\}$");
                Matcher m = pattern.matcher(val);
                m.find();
                answers.add(new Answer(m.group(1), Boolean.parseBoolean("true")));
                return new TrueFalse(head, answers);                          // вопрос на Да/Нет
            }
        }
        return new Select(head, answers);
    }

    private String getHead(String line, Boolean[] html) {

        Pattern pattern = Pattern.compile("^::(\\d+)\\.?::(.*?)\\{?$");  //
        Matcher m = pattern.matcher(line);                               // убираем всякие "::1.::" в начале
        m.find();                                                        // и "}" в конце строки
        String head = m.group(2);
        if (html[0] = head.startsWith("[html]")) {
			head = clean(head);
        }
        return head;
    }

    private List<Answer> getAnswers(List<String> aLines, Boolean[] html) {
		List<Answer> answers = new ArrayList<>();
        for (String line : aLines) {
            if (html[0]) {
                line = clean(line);
            }
            answers.add(new Answer(line.substring(1), (line.startsWith("=")) || line.startsWith("\\~\\%")));
        }
        return answers;
    }
    
    private String clean(String line) {
        line = line.replace("[html]", "");          // убираем html-метку
        line = line.replaceAll("\\\\n", "");        // удаляем переносы строк
        line = line.replaceAll("\\<.*?>", "");      // удаляем все теги
        line = line.replaceAll("\\\\(?!\\\\)", ""); // удаляем все одиночные обратные слеши, а из двойных делаем одиночные
        line = line.replaceAll("[\\s]{2,}", " ");   // удаляем лишние пробелы
        //особенность java - приходится удваивать слеши

        return line;
    }
}