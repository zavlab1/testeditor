package testeditor.parser;

import testeditor.Test;
import testeditor.question.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.*;

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
        Pattern p = Pattern.compile("(.*?)((?<!\\\\)\\{|(?<!\\\\)\\})(.*)$");
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();

            if (!line.isEmpty() &&
                    !line.startsWith("//") &&
                    !line.startsWith("#")) {
                splitByBracesAndAdd(line, lineList, p);
            }
        }
        return lineList;
    }

    private void splitByBracesAndAdd(String line, List<String> lineList, Pattern p) {
        Matcher m = p.matcher(line);

        if (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                if (!m.group(i).isEmpty()) {
                    if (i<3) {
                        lineList.add(m.group(i));
                    } else {
                        splitByBracesAndAdd(m.group(i), lineList, p);
                    }
                }
            }
        } else {
            lineList.add(line);
        }
    }

    private List<List<String>> getQuestionsTexts(List<String> lineList) {
        List<List<String>> qTexts = new ArrayList<>();
        ListIterator<String> it = lineList.listIterator();
        while (it.hasNext()) {
            String line = it.next();
            if (line.startsWith("::")) {
                List<String> qText = new ArrayList<>();
                qText.add(line);
                while (it.hasNext() && !lineList.get(it.nextIndex()).equals("}")) {
                    if (!lineList.get(it.nextIndex()).equals("{")) {
                        //System.out.println(lineList.get(it.nextIndex()));
                        qText.add(it.next());
                    } else {
                        it.next();
                    }
                }
                qTexts.add(qText);
            }
        }
        return qTexts;
    }

    private Question getQuestion(List<String> qText) {

        Boolean[] html = new Boolean[] {new Boolean(false)}; // специально объектом, чтобы мог модифицироваться по
                                                             // ссылке в др. методах, но т.к. классы-обертки не могут
                                                             // менять значение, прячем значение в массив (финт ушами)

        String[] NumAndHead = getNumberAndHead(qText.get(0), html);              // Получаем текст вопроса
        int num = Integer.parseInt(NumAndHead[0]);
        String head = NumAndHead[1];

        List<String> answerLines = qText.subList(1, qText.size());
        //List<Answer> answers = getAnswers(answerLines, html);                    // Получаем варианты ответа
        //ListIterator<Answer> li = answers.listIterator();
        ListIterator<String> li = answerLines.listIterator();
        while (li.hasNext()) {
            String val = li.next();
            //String val = a.getValue();
            if (val.contains("->")) {
                if (li.hasNext()) {
                    String nextVal = answerLines.get(li.nextIndex());
                    if ( nextVal.startsWith("=") &&
                         nextVal.contains("->") ) {
                        return new Matching(num, head, getAnswers(answerLines, html));                 // вопрос на соответсвтие
                    }
                }
            } else if (answerLines.size() == 1 &&
                       (val.equals("TRUE") ||
                        val.equals("FALSE")) ) {
                return new TrueFalse(num, head, getAnswers(answerLines, html));                        // вопрос на Да/Нет
            } else if (val.startsWith("=%") && val.endsWith("#")) {
                return new ShortAnswer(num, head, getAnswers(answerLines, html));
            }
        }
        return new MultiChoice(num, head, getAnswers(answerLines, html));                              // вопрос на выбор
    }

    private String[] getNumberAndHead(String line, Boolean[] html) {

        Pattern pattern = Pattern.compile("^::(\\d+)\\.?::(.*?)$");  //
        Matcher m = pattern.matcher(line);                           // убираем всякие "::1.::" в начале
        m.find();
        String[] NumAndHead = new String[2];
        NumAndHead[0] = m.group(1);
        NumAndHead[1] = m.group(2);
        if (html[0] = NumAndHead[1].startsWith("[html]")) {
            NumAndHead[1] = clean(NumAndHead[1]);
        }
        return NumAndHead;
    }

    private List<Answer> getAnswers(List<String> aLines, Boolean[] html) {
        List<Answer> answers = new ArrayList<>();
        for (String line : aLines) {
            if (html[0]) {
                line = clean(line);
            }
            if (line.equals("TRUE") || line.equals("FALSE")) {
                answers.add(new Answer(line, Boolean.parseBoolean(line)?1.0f:0.0f));
            } else if (line.startsWith("%", 1)) {
                Pattern pattern = Pattern.compile("^(\\=|\\~)\\%(\\d+)\\%(.+?)\\#?$");
                Matcher m = pattern.matcher(line);
                m.find();
                answers.add(new Answer(m.group(3), Float.parseFloat(m.group(2))/100));
            } else {
                answers.add(new Answer(
                                        line.endsWith("#") ? line.substring(1, line.length()-1) : line.substring(1),
                                        (line.startsWith("=") || line.startsWith("\\~\\%")) ? 1.f : 0.f
                                      )
                            );
            }
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