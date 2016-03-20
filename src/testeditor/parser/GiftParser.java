package testeditor.parser;

import testeditor.Test;
import testeditor.question.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.*;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате Gift
 */
public class GiftParser extends Parser {

    private final List <String> boolvals = asList(new String[] {"TRUE", "FALSE", "T", "F"});

    public Test getTest(String filepath) throws IOException {
        Test test = new Test();
        String text = readFile(filepath);
        String nl = System.lineSeparator();
        String[] qBodies = text.split(nl+nl);
        Pattern p = Pattern.compile("^((\\/\\/)(.*)$\\s)?(::(.*?)::)?(\\[(.*?)\\])?(.+)((?<!\\\\)\\{(.*?)(?<!\\\\)\\})(.*)$",
                                    Pattern.MULTILINE | Pattern.DOTALL);
        for (String qBody : qBodies) {
            Matcher m = p.matcher(qBody.trim());
            if (m.find()) {
                String qName = (m.group(5) != null) ? m.group(5) : "";
                String qFormat = (m.group(7) != null) ? m.group(7) : "";
                String qText = m.group(11).isEmpty() ? m.group(8) : m.group(8) + " _______ " + m.group(11);
                String aLine = m.group(10);

                Question q = getQuestion(qName, qText, aLine.trim(), qFormat);
                try {
                    test.add(q);
                } catch(NullPointerException ex) {
                    System.err.println("Невозможно определить тип вопроса \"" + qText + "\" \nПропускаем...");
                }
            }
        }
        return test;
    }

    public String readFile(String filepath) throws IOException {

        FileInputStream inFile = new FileInputStream(filepath);
        byte[] str = new byte[inFile.available()];
        inFile.read(str);

        return new String(str);
    }

    private Question getQuestion(String qName, String qText, String aLine, String qFormat) {
        Question q = null;
        if (qFormat.equals("html")) {
            qText = clean(qText);
            aLine = clean(aLine);
        }
        List<String> aLinesList = split(aLine, System.lineSeparator());


        //если строка ответов начинается с "#", значит, создаем числовой вопрос
        if (aLine.startsWith("#")) {
            List<Answer> aList;
            if (aLinesList.size() == 1) {
                aList = Arrays.asList(new Answer(aLinesList.get(0).substring(1), 1.f));
            } else {
                aList = getMultiAnswers(aLinesList.subList(1, aLinesList.size()));
            }
            q = new Numerical(qName, qText, aList);

        // если значиение ответа равно одному из обозначений boolean в gift, то это вопрос Верно/Неверно
        } else if (boolvals.contains(aLine.trim().toUpperCase())) {
            q = new TrueFalse(qName, qText, Arrays.asList(new Answer(aLine, Boolean.parseBoolean(aLine) ? 1.f : 0.f)));

        // если все элементы начинаются с "=" и содержат "->", то это - вопрос на соответствие
        } else if (getALineStream(aLinesList).allMatch(x -> (x.startsWith("=") && x.contains("->")))) {
            q = new Matching(qName, qText, getALineStream(aLinesList).map(x -> new Answer(x.substring(1), 1.f)).collect(Collectors.toList()));

        // если все элементы начинаются с "=", то это - вопрос "Короткий ответ"
        } else if (getALineStream(aLinesList).allMatch(x -> (x.startsWith("=")))) {
            List<Answer> aList = getMultiAnswers(aLinesList);
            q = new ShortAnswer(qName, qText, aList);

        // если количество элементов, начинающихся с "~", равно или на один меньше, чем общее количество элементов - вопрос на множественный выбор
        } else if (getALineStream(aLinesList).filter(x -> x.startsWith("~")).toArray().length > aLinesList.size() - 2) {
            List<Answer> aList = getMultiAnswers(aLinesList);
            q = new MultiChoice(qName, qText, aList);
        }

        // если ни один вариант не сработал, будет возвращен null
        return q;
    }

    private Stream<String> getALineStream(List<String> aLinesList){
        return aLinesList.stream().map(String::trim).filter(x->!x.isEmpty());
    }

    //метод для создания списка ответов из тех строк, которые содержат проценты за правильный ответ
    private List<Answer> getMultiAnswers(List<String> lines) {
        Pattern p = Pattern.compile("^(\\=|\\~)(\\%(\\d+)\\%)(.+?)(\\#.*)?$");
        Stream <Answer> stream = getALineStream(lines).map(line -> {
            Matcher m = p.matcher(line);
            return  new Answer (
                    line.substring(1),
                    m.find() ? Float.parseFloat(m.group(4)) / 100 :
                               line.startsWith("~") ? 0.f : 1.f
            );
        });
        return stream.collect(Collectors.toList());
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

    private String clean(String line) {
        line = line.replaceAll("\\\\n", "");        // удаляем переносы строк
        line = line.replaceAll("\\<.*?>", "");      // удаляем все теги
        line = line.replaceAll("\\\\(?!\\\\)", ""); // удаляем все одиночные обратные слеши, а из двойных делаем одиночные
        line = line.replaceAll("[\\s]{2,}", " ");   // удаляем лишние пробелы
        //особенность java - приходится удваивать слеши
        return line;
    }
}