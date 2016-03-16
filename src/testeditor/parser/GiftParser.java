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
        List<List<String>> qBodies = getQuestionsBodies(lineList);
        for (List<String> qBody : qBodies) {
            Question q = getQuestion(qBody);
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
                !line.startsWith("//")) {
                    /*
                     * Ищем символы ~ или =, которые не первые в строке и перед которыми нет
                     * двойной @ (мы будем использовать @@ в качестве разделителя) или экранирующего слеша
                     * и вставляем перед ними разделитель
                     */
                    Pattern p1 = Pattern.compile("(?<!(^|(@@)|\\\\))(\\~|\\=)");
                    Matcher m1 = p1.matcher(line);
                    while (m1.find()) { //m1.replaceAll() здесь не подойдет, т.к. везде поставит тильду
                        line = m1.replaceFirst("@@"+m1.group(3));
                        m1.reset(line);
                    }
                    // разбиваем строку по разделителю
                    String [] sublines = line.split("@@");
                    for (int i=0; i<sublines.length; i++) {
                        splitByBracesAndAdd(sublines[i], lineList, p);
                    }
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

    private List<List<String>> getQuestionsBodies(List<String> lineList) {
        List<List<String>> qBodies = new ArrayList<>();
        ListIterator<String> it = lineList.listIterator();
        while (it.hasNext()) {
            String line = it.next();
            if (line.startsWith("::")) {
                List<String> qBody = new ArrayList<>();
                qBody.add(line);
                while (it.hasNext() && !lineList.get(it.nextIndex()).equals("}")) {
                    if (!lineList.get(it.nextIndex()).equals("{")) {
                        //System.out.println(lineList.get(it.nextIndex()));
                        qBody.add(it.next());
                    } else {
                        it.next();
                    }
                }
                qBodies.add(qBody);
            }
        }
        return qBodies;
    }

    private Question getQuestion(List<String> qBody) {

        Boolean[] html = new Boolean[] {new Boolean(false)}; // специально объектом, чтобы мог модифицироваться по
                                                             // ссылке в др. методах, но т.к. классы-обертки не могут
                                                             // менять значение, прячем значение в массив (финт ушами)
        // Получаем текст вопроса
        String[] nameAndQText = getNameAndQText(qBody.get(0), html);
        String qName = nameAndQText[0];
        String qText = nameAndQText[1];

        List<String> answerLines = qBody.subList(1, qBody.size());

        ListIterator<String> li = answerLines.listIterator();
        while (li.hasNext()) {
            String val = li.next();

            if (val.contains("->")) {
                if (li.hasNext()) {
                    String nextVal = answerLines.get(li.nextIndex());
                    if ( nextVal.startsWith("=") &&
                         nextVal.contains("->") ) {
                        return new Matching(qName, qText, getAnswers(answerLines, html));                 // вопрос на соответсвтие
                    }
                }
            } else if (answerLines.size() == 1 &&
                       (val.equals("TRUE") ||
                        val.equals("FALSE")) ) {
                return new TrueFalse(qName, qText, getAnswers(answerLines, html));                        // вопрос на Да/Нет
            } else if (val.startsWith("=%") && val.endsWith("#")) {
                return new ShortAnswer(qName, qText, getAnswers(answerLines, html));
            }
        }
        return new MultiChoice(qName, qText, getAnswers(answerLines, html));                              // вопрос на выбор
    }

    private String[] getNameAndQText(String line, Boolean[] html) {

        Pattern pattern = Pattern.compile("^::(.*)\\.?::(.*?)$");  //
        Matcher m = pattern.matcher(line);                           // убираем всякие "::1.::" в начале
        m.find();
        String[] nameAndQText = new String[2];
        nameAndQText[0] = m.group(1);
        nameAndQText[1] = m.group(2);
        if (html[0] = nameAndQText[1].startsWith("[html]")) {
            nameAndQText[1] = clean(nameAndQText[1]);
        }
        return nameAndQText;
    }

    private List<Answer> getAnswers(List<String> aLines, Boolean[] html) {
        List<Answer> answers = new ArrayList<>();
        for (String line : aLines) {
            if (html[0]) {
                line = clean(line);
            }
            if (line.toUpperCase().equals("TRUE") || line.toUpperCase().equals("T")
                || line.toUpperCase().equals("FALSE") || line.toUpperCase().equals("F")) {
                answers.add(new Answer(line.toUpperCase(), Boolean.parseBoolean(line)?1.0f:0.0f));
            } else if (line.startsWith("%", 1)) {
                Pattern pattern = Pattern.compile("^(\\=|\\~)\\%(.*)\\%(.+?)\\#?$");
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