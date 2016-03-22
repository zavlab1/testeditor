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

    private Question getQuestion(String qName, String qText, String aLine, String qFormat) throws Exception {
        Question q = null;
        if (qFormat.equals("html")) {
            qText = clean(qText);
            aLine = clean(aLine);
        }
        List<String> aLinesList = split(aLine);
        aLinesList = prepareList(aLinesList);

        if (isNumerical(aLinesList)) {
            List<Answer> aList;
            if (aLinesList.size() == 1) {
                aList = Arrays.asList(getAnswer(aLinesList.get(0).substring(1)));
            } else {
                aList = getMultiAnswers(aLinesList.stream().skip(1).collect(Collectors.toList()));
            }
            q = new Numerical(qName, qText, aList);

        } else if (isTrueFalse(aLinesList)) {
            q = new TrueFalse(qName, qText, 
                              Arrays.asList(getAnswer(aLine,
                                                       Boolean.parseBoolean(aLine) ? Answer.MAX_DEGREE
                                                                                   : Answer.MIN_DEGREE)));

        } else if (isMatching(aLinesList)) {
            q = new Matching(qName, qText,
                             aLinesList.stream().map(x -> getAnswer(x.substring(1)))
                             .collect(Collectors.toList()));

        } else if (isShortAnswer(aLinesList)) {
            List<Answer> aList = getMultiAnswers(aLinesList);
            q = new ShortAnswer(qName, qText, aList);

        } else if (isMultiChoice(aLinesList)) {
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
                if (degree < Answer.MIN_DEGREE || degree > Answer.MAX_DEGREE) {
                    System.err.printf("Процент %d в строке \"%s\" вне допустимого диапазона",
                                      degree, line);
                }
                if (m.group(1).equals("~")) {
                    degrees.add(degree);
                }
                return getAnswer(m.group(4), degree);
            } else {
                return getAnswer(line.substring(1), line.startsWith("~") ? Answer.MIN_DEGREE :
                                                                           Answer.MAX_DEGREE);
            }
        });
        if (!(degrees.isEmpty()) && degrees.stream().mapToInt(x -> x).sum() != Answer.MAX_DEGREE) {
            throw new Exception("Сумма процентов за ответы для вопроса множественного выбора не равна "
                                + Answer.MAX_DEGREE);
        }
        return stream.collect(Collectors.toList());
    }

    // дробим строки по тильде или равно с сохранением этих символов
    private List<String> split(String line) {
        Pattern p = Pattern.compile("(?<!(^|(" + System.lineSeparator() + ")|\\\\))(\\~|\\=)");
        Matcher m = p.matcher(line);
        while (m.find()) { //m1.replaceAll() здесь не подойдет, т.к. везде поставит тильду
            line = m.replaceFirst(System.lineSeparator() + m.group(3));
            m.reset(line);
        }
        String[] aLines = line.split(System.lineSeparator());
        return Arrays.asList(aLines);
    }

    private String clean(String line) {
        line = line.replaceAll("\\\\n", "");        // удаляем переносы строк
        line = line.replaceAll("\\<.*?>", "");      // удаляем все теги
        line = line.replaceAll("\\\\(?!\\\\)", ""); // удаляем все одиночные обратные слеши, а из двойных делаем одиночные
        line = line.replaceAll("[\\s]{2,}", " ");   // удаляем лишние пробелы
        //особенность java - приходится удваивать слеши
        return line;
    }

    private List<String> prepareList(List<String> list) {
        // избавляемся от лишних пробелов и фильтруем пустые строки
        list = list.stream().map(String::trim).filter(x->!x.isEmpty()).collect(Collectors.toList());
        // если хотя бы один элемент начинается с равно или тильды, то избавляется от тех строк,
        // которые не начинаются с равно, тильды или решетки
        if (list.stream().anyMatch(x -> x.startsWith("=") || x.startsWith("~"))) {
            list = list.stream().filter(x -> "~=#".contains(x.substring(0, 1)))
                   .collect(Collectors.toList());
        }
        return list;
    }

    // дробим строку на текст вопроса и комментарий, после чего делаем Answer
    private Answer getAnswer(String line) {
        return getAnswer(line, Answer.MAX_DEGREE);
    }
    private Answer getAnswer(String line, int degree) {
        String[] value = line.split("#");
        return new Answer(value[0], degree, value.length == 2 ? value[1] : "");
    }

    private boolean isNumerical(List<String> lines) {
        // если первая строка ответов начинается с "#"
        if (lines.get(0).startsWith("#")) {
            // если строк больше одной и не все (кроме первой) начинаются с "="
            if (lines.size() > 1 &&
                !lines.subList(1, lines.size()).stream().allMatch(x -> x.startsWith("="))) {
                throw new NullPointerException();
            }
            return true;
        } else {
            return false;
        }
    }
    private boolean isTrueFalse(List<String> lines) {
        //если значение ответа равно одному из обозначений boolean в gift
        return lines.size() == 1 && asList("TRUE", "FALSE", "T", "F").contains(lines.get(0).toUpperCase().split("#")[0]);
    }
    private boolean isMatching(List<String> lines) {
        //если все элементы начинаются с "=" и содержат "->"
        return lines.stream().allMatch(x -> (x.startsWith("=") && x.contains("->")));
    }
    private boolean isShortAnswer(List<String> lines) {
        //если все элементы начинаются с "=" и не содержат "->"
        return lines.stream().allMatch(x -> (x.startsWith("=") && !x.contains("->")));
    }
    private boolean isMultiChoice(List<String> lines){
        //если количество элементов, начинающихся с "~", равно или на один меньше, чем общее количество элементов
        return lines.stream().filter(x -> x.startsWith("~")).toArray().length > lines.size() - 2;
    }
}