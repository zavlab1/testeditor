package testeditor.parser;

import com.sun.org.apache.xpath.internal.operations.Mult;
import testeditor.Test;
import testeditor.question.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by dimitry on 28.12.15.
 * Парсер для чтения файлов в формате XML
 */
public class XMLParser extends Parser {
        Document doc;

        public Test getTest(String filepath) throws IOException {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse(filepath);
            }

            catch (Exception e) {
                throw new IOException("Ошибка открытия XML документа");
            }

        Test test = new Test();
        int QuestionsCount = doc.getDocumentElement().getChildNodes().getLength();
        NodeList QuestionNodes = doc.getDocumentElement().getElementsByTagName("Question");

        for(int i = 0; i < QuestionsCount; i++) {
            Element QuestionElement = (Element)QuestionNodes.item(i);
            String questionType = QuestionElement.getAttribute("Type");

            switch (questionType) {
                case "MultiChoice":
                    test.add(parseMultiChoice(QuestionElement, String.valueOf(i)));
                    break;
                case "ShortAnswer":
                    test.add(parseShortAnswer(QuestionElement, String.valueOf(i)));
                    break;
                case "TrueFalse":
                    test.add(parseTrueFalse(QuestionElement, String.valueOf(i)));
                    break;
                case "Numerical":
                    test.add(parseNumerical(QuestionElement, String.valueOf(i)));
                    break;
            }
        }

        return test;
    }

    MultiChoice parseMultiChoice(Element questionElement, String id) {
        String head = getQuestionHead(questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        return new MultiChoice(id, head, answerList);
    }

    ShortAnswer parseShortAnswer(Element questionElement, String id) {
        String head = getQuestionHead(questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        return new ShortAnswer(id, head, answerList);
    }

    TrueFalse parseTrueFalse(Element questionElement, String id) {
        String head = getQuestionHead(questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        return new TrueFalse(id, head, answerList);
    }

    Numerical parseNumerical(Element questionElement, String id) {
        String head = getQuestionHead(questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        return new Numerical(id, head, answerList);
    }

    ArrayList<Answer> parseAnswerList(Element questionElement) {
        ArrayList<Answer> answerList = new ArrayList<Answer>();
        NodeList answerElements = questionElement.getElementsByTagName("Answer");

        for(int i = 0; i < answerElements.getLength(); i++) {
            Element answerElement = (Element)answerElements.item(i);

            String text = answerElement.getElementsByTagName("text").item(0).getNodeValue();
            float fraction = Float.parseFloat(answerElement.getAttribute("fraction"));

            answerList.add(new Answer(text, fraction));
        }

        return answerList;
    }

    String getQuestionHead(Element questionElement) {
        return ((Element)questionElement.getElementsByTagName("name").item(0))
                .getElementsByTagName("text").item(0)
                .getNodeValue();
    }
}
