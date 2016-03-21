package testeditor.parser;

import testeditor.Test;
import testeditor.question.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
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
                try {
                    Question q = getQuestion(qName, qText, aLine.trim(), qFormat);
                    test.add(q);
                } catch (NullPointerException ex) {
                    System.err.printf("Невозможно определить тип вопроса \"%s\" \nПропускаем...", qText);
                } catch (Exception ex) {
                    System.err.printf("%s \"%s.\" \nПропускаем...",
                            ex.getMessage(), qText);
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

    private Question getQuestion(String qName, String qText, String aLine, String qFormat) throws Exception{
        System.out.println(aLine);
        Question q = null;
        final List <String> boolvals = asList(new String[] {"TRUE", "FALSE", "T", "F"});
        if (qFormat.equals("html")) {
            qText = clean(qText);
            aLine = clean(aLine);
        }
        List<String> aLinesList = split(aLine, System.lineSeparator());
        aLinesList = aLinesList.stream().map(String::trim).filter(x->!x.isEmpty()).collect(Collectors.toList());
        if (aLinesList.stream().anyMatch(x -> x.startsWith("=") || x.startsWith("~"))) {
            aLinesList = aLinesList.stream().filter(x -> x.startsWith("=") ||
                                                         x.startsWith("~") ||
                                                         x.startsWith("#"))
                                                        .collect(Collectors.toList());
        }
        //если строка ответов начинается с "#", значит, создаем числовой вопрос
        if (aLine.startsWith("#")) {
            List<Answer> aList;
            if (aLinesList.size() == 1) {
                aList = Arrays.asList(new Answer(aLinesList.get(0).substring(1)));
            } else {
                aList = getMultiAnswers(aLinesList.stream().skip(1).collect(Collectors.toList()));
            }
            q = new Numerical(qName, qText, aList);

        // если значиение ответа равно одному из обозначений boolean в gift, то это вопрос Верно/Неверно
        } else if (boolvals.contains(aLine.trim().toUpperCase())) {
            q = new TrueFalse(qName, qText, Arrays.asList(new Answer(aLine, Boolean.parseBoolean(aLine) ? 100 : 0)));

        // если все элементы начинаются с "=" и содержат "->", то это - вопрос на соответствие
        } else if (aLinesList.stream().allMatch(x -> (x.startsWith("=") && x.contains("->")))) {
            q = new Matching(qName, qText, aLinesList.stream().map(x -> new Answer(x.substring(1))).collect(Collectors.toList()));

        // если все элементы начинаются с "=", то это - вопрос "Короткий ответ"
        } else if (aLinesList.stream().allMatch(x -> (x.startsWith("=")))) {
            List<Answer> aList = getMultiAnswers(aLinesList);
            q = new ShortAnswer(qName, qText, aList);

        // если количество элементов, начинающихся с "~", равно или на один меньше, чем общее количество элементов - вопрос на множественный выбор
        } else if (aLinesList.stream().filter(x -> x.startsWith("~")).toArray().length > aLinesList.size() - 2) {
            List<Answer> aList = getMultiAnswers(aLinesList);
            q = new MultiChoice(qName, qText, aList);
        }

        // если ни один вариант не сработал, будет возвращен null
        return q;
    }

    //метод для создания списка ответов из тех строк, которые содержат проценты за правильный ответ
    private List<Answer> getMultiAnswers(List<String> lines) throws Exception {
        ArrayList<Integer> degrees = new ArrayList<>();
        Pattern p = Pattern.compile("^(\\=|\\~)(\\%(\\d+)\\%)(.+?)(\\#.*)?$");
        Stream <Answer> stream = lines.stream().map(line -> {
            Matcher m = p.matcher(line);
            if (m.find()) {
                Integer degree = Integer.parseInt(m.group(3));
                if (degree < 0 || degree > 100) {
                    System.err.printf("Процент %d в строке \"%s\" вне допустимого диапазона", degree, line);
                }
                if (m.group(1).equals("~")) {
                    degrees.add(degree);
                }
                return new Answer(m.group(4), degree);
            } else {
                return new Answer(line.substring(1), line.startsWith("~") ? 0 : 100);
            }
        });

        if (!(degrees.isEmpty()) && degrees.stream().mapToInt(x -> x).sum() != 100) {
            throw new Exception("Сумма процентов за ответы для вопроса множественного выбора не равна 100");
        }
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