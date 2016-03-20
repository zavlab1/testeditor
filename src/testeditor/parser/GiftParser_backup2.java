package testeditor.parser;

import testeditor.Test;
import testeditor.question.Answer;
import testeditor.question.Matching;
import testeditor.question.Question;
import testeditor.question.TrueFalse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser_backup2 extends Parser {

    private List <String> boolvals = asList(new String[] {"TRUE", "FALSE", "T", "F"});


    public Test getTest(String filepath) throws IOException {
        Test test = new Test();
        FileInputStream inFile = new FileInputStream(filepath);
        byte[] str = new byte[inFile.available()];
        inFile.read(str);
        String text = new String(str);
        String nl = System.lineSeparator();
        String[] qBodies = text.split(nl+nl);
        Pattern p = Pattern.compile("^((\\/\\/)(.*)$\\s)?(::(.*?)::)?(\\[(.*?)\\])?(.+)((?<!\\\\)\\{(.*?)(?<!\\\\)\\})(.*)$",
                                    Pattern.MULTILINE | Pattern.DOTALL);
        for (String qBody : qBodies) {
            Matcher m = p.matcher(qBody.trim());
            if (m.find()) {
                String qName = (m.group(5) != null) ? m.group(5) : "";
                String qFormat = (m.group(7) != null) ? m.group(7) : "";
                String qText = m.group(8);
                String aLine = m.group(10);
                String qTextTail = m.group(11);

                Question q = getQuestion(qName, qText, aLine.trim(), qFormat, qTextTail);
                try {
                    test.add(q);
                } catch(NullPointerException ex) {
                    System.err.println("Невозможно определить тип вопроса \"" + qText + "\" \nПропускаем...");
                }
            }
        }
        return test;
    }

    private Question getQuestion(String qName, String qText, String aLine, String qFormat, String qTextTail) {
        Question q = null;
        if (qFormat.equals("html")) {
            aLine = clean(aLine);
        }
        List<String> aLinesList = split(aLine, System.lineSeparator());

        if (boolvals.contains(aLine.trim().toUpperCase())) {

            q = new TrueFalse(qName, qText, Arrays.asList(new Answer(aLine, Boolean.parseBoolean(aLine) ? Answer.MAX_DEGREE : 0)));

        } else if (getALineStream(aLinesList).allMatch(x -> (x.startsWith("=") && x.contains("->")))) {

            q = new Matching(qName, qText, getALineStream(aLinesList).map(x -> new Answer(x.substring(1), Answer.MAX_DEGREE)).collect(Collectors.toList()));

        }
        return q;
    }

    private Stream<String> getALineStream(List<String> aLinesList){
        return aLinesList.stream().map(String::trim).filter(x->!x.isEmpty());
    }

    private List<String> split(String line, String separator) {
        Pattern p = Pattern.compile("(?<!(^|(" + separator + ")|\\\\))(\\~|\\=)");
        Matcher m = p.matcher(line);
        while (m.find()) { //m1.replaceAll() здесь не подойдет, т.к. везде поставит тильду
            line = m.replaceFirst(separator + m.group(3));
            m.reset(line);
        }
        String[] aLines = line.split(System.lineSeparator());
        List<String> aLinesList = Arrays.asList(aLines);
        return aLinesList;
    }

    private List<Answer> getAnswers(String aLine, String qFormat) {
        List<Answer> answers = new ArrayList<>();
        if (qFormat.equals("html")) {
            aLine = clean(aLine);
        }
        Pattern p1 = Pattern.compile("(?<!(^|(@@)|\\\\))(\\~|\\=)");
        Matcher m1 = p1.matcher(aLine);
        while (m1.find()) { //m1.replaceAll() здесь не подойдет, т.к. везде поставит тильду
            aLine = m1.replaceFirst("@@"+m1.group(3));
            m1.reset(aLine);
        }
        // разбиваем строку по разделителю
        String [] sublines = aLine.split("@@");
        //System.out.println(Arrays.toString(sublines));
        for (int i=0; i<sublines.length; i++) {
            if (sublines[0].equals("#")) continue;
            answers.add(new Answer(prepareALine(sublines[i]), getDegree(sublines[i])));
        }
        return answers;
    }

    private int getDegree(String line) {
        int degree = Answer.MAX_DEGREE;
        if (boolvals.contains(line.toUpperCase())) {
            degree = Boolean.parseBoolean(line) ? Answer.MAX_DEGREE : 0;
        } else if (line.startsWith("%", 1)) {
            //System.out.println(123);
            Pattern pattern = Pattern.compile("^(\\=|\\~)\\%(.*)\\%(.+?)\\#?$");
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                //System.out.println(1);
                degree = Integer.parseInt(m.group(2));
                //System.out.println(m.group(2));
            }
        } else {
            degree = (line.startsWith("=") || line.startsWith("\\~\\%")) ? Answer.MAX_DEGREE : 0;
        }
        return degree;
    }

    private String prepareALine(String aLine) {
        aLine = aLine.trim();
        if (boolvals.contains(aLine.toUpperCase())) {
            return aLine.toUpperCase();
        }
        Pattern pattern = Pattern.compile("(\\~|\\=)?(.+?)\\#?$");
        Matcher m = pattern.matcher(aLine);
        if (m.find()) {
            //System.out.println(aLine);
            for (int i = 0; i <= m.groupCount(); i++) {
                //System.out.printf("group %d: %s\n", i, m.group(i).trim());
            }
            aLine = m.group(2);
        } else {
            System.out.println("not matching");
        }
        return aLine;
    }


    private String clean(String line) {
        line = line.replaceAll("\\\\n", "");        // удаляем переносы строк
        line = line.replaceAll("\\<.*?>", "");      // удаляем все теги
        line = line.replaceAll("\\\\(?!\\\\)", ""); // удаляем все одиночные обратные слеши, а из двойных делаем одиночные
        line = line.replaceAll("[\\s]{2,}", " ");   // удаляем лишние пробелы
        //особенность java - приходится удваивать слеши
        return line;
    }
}