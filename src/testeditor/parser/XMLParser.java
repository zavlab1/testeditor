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
        int questionsCount = doc.getDocumentElement().getChildNodes().getLength();
        NodeList questionNodes = doc.getDocumentElement().getElementsByTagName("Question");

        for(int i = 0; i < questionsCount; i++) {
            Element QuestionElement = (Element)questionNodes.item(i);
            String questionType = QuestionElement.getAttribute("Type");

            switch (questionType) {
                case "MultiChoice":
                    test.add(parseMultiChoice(QuestionElement));
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

    Matching parseMatching(Element questionElement) {
        String name = getQuestionName(questionElement);
        String head = getQuestionHead(questionElement);
        ArrayList<Answer> answerList = new ArrayList<>();

        Matching r = new Matching(name, head, answerList);

        NodeList subQuestions =
                questionElement.getElementsByTagName("subquestion");

        for(int n = 0; n < subQuestions.getLength(); n++) {
            Element subQuestionElement =
                    (Element)subQuestions.item(n);
            Element subQuestionTextElement =
                    (Element) subQuestionElement.
                              getElementsByTagName("text").item(0);
            String subQuestionText =
                    subQuestionTextElement.getTextContent();

            Element answerElement =
                    (Element) subQuestionElement.
                              getElementsByTagName("answer").item(0);
            Element answerTextElement =
                    (Element) answerElement.
                              getElementsByTagName("text").item(0);
            String AnswerText =
                    answerTextElement.getTextContent();

            Answer a = new Answer(AnswerText, Answer.MAX_DEGREE);
            ArrayList<Answer> answerList2 = new ArrayList<>();
            answerList2.add(a);

            MatchingSubQuestion subQuestion =
                    new MatchingSubQuestion("", subQuestionText,
                            answerList2);

            r.addSubQuestion(subQuestion);
        }

        return r;
    }

    MultiChoice parseMultiChoice(Element questionElement) {
        String name = getQuestionName(questionElement);
        String head = getQuestionHead(questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        return new MultiChoice(name, head, answerList);
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
            int fraction = Integer.parseInt(answerElement.getAttribute("fraction"));

            answerList.add(new Answer(text, fraction));
        }

        return answerList;
    }

    String getQuestionName(Element questionElement) {
        return ((Element)questionElement.getElementsByTagName("name").item(0))
                .getElementsByTagName("text").item(0)
                .getNodeValue();
    }

    String getQuestionHead(Element questionElement) {
        return ((Element)questionElement.getElementsByTagName("questiontext").item(0))
                .getElementsByTagName("text").item(0)
                .getNodeValue();
    }
}
