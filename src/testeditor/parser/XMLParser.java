package testeditor.parser;

import testeditor.Test;
import testeditor.question.*;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import testeditor.saver.Saver;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате XML
 */
public class XMLParser extends Parser {
    Document doc;
    Test resultTest;

    public Test getTest(String filepath) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(filepath);
        }

        catch (Exception e) { throw new IOException("Ошибка открытия XML файла: " + e.toString()); }

        resultTest = new Test();
        Element documentElement = doc.getDocumentElement();
        NodeList questionNodes = doc.getChildNodes();

        for(int n = 0; n < questionNodes.getLength(); n++) {
            Element questionElement = (Element)questionNodes.item(0);

            try {
                resultTest.add(getQuestion(questionElement, n));
            }

            catch (Exception e) {
            }
        }

        return resultTest;
    }

    public Question getQuestion(Element questionElement, int Number) throws Exception {
        NodeList answerElements = questionElement.getChildNodes();
        ArrayList<Answer> answers = new ArrayList<>();

        for(int n = 0; n < answerElements.getLength(); n++) {
            Element answerElement = (Element)answerElements.item(n);
            answers.add(new Answer(answerElement.getAttribute("Name"),
                    Boolean.parseBoolean(answerElement.getAttribute("Right"))));
        }

        switch (questionElement.getAttribute("Type")) {
            case "Order":
                return new Order(Number, questionElement.getAttribute("Head"),
                        answers);
            case "Select":
                return new Select(Number, questionElement.getAttribute("Head"),
                        answers);
            case "Conformity":
                return new Conformity(Number, questionElement.getAttribute("Head"),
                        answers);
            case "TrueFalse":
                return new TrueFalse(Number, questionElement.getAttribute("Head"),
                        answers);
            default:
                throw new Exception("Неправильный тип вопроса.");
        }
    }
}
