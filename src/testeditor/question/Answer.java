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

    /**
     * @param text - значение варианта ответа
     * @param degree - указывает на степень правильности варианта ответа (от 0 до 1 с точностью до сотых)
     * @param comment - Комментарий (если есть)
     */
    public Answer(String text, int degree, String comment) {
        aText = text;
        aComment = comment;
        this.degree = degree;
    }
    public Answer(String text, int degree) {
        this(text, degree, "");
    }
    public Answer(String text) {
        this(text, Answer.MAX_DEGREE, "");
    }

    /**
     * @return возвращает строку с вариантом ответа
     */
    public String getAText() {
        return this.aText;
    }

    /**
     * @return возвращает строку с комментарием для вариантом ответа
     */
    public String getAComment() {
        return this.aComment;
    }

    /**
     * @return возвращает правильность варианта в процентах
     */
    public int getDegree() {
        return degree;
    }
    public void setValue (String text, int degree, String comment) {
        aText = text;
        aComment = comment;
        this.degree = degree;
    }
    public void setAText(String aText) {
        this.aText = aText;
    }
    public void setDegree(int degree) {
        this.degree = degree;
    }
    public void setAComment(String aComment) {
        this.aComment = aComment;
    }
}
