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

        Test test = Test.createTest();
        Question q;
        int questionsCount = doc.getDocumentElement().getChildNodes().getLength();
        NodeList questionNodes = doc.getDocumentElement().getElementsByTagName("Question");

        for(int i = 0; i < questionsCount; i++) {
            Element questionElement = (Element)questionNodes.item(i);
            String questionType = questionElement.getAttribute("Type");

            switch (questionType) {
                case "MultiChoice":
                    q = parseMultiChoice(questionElement);
                    test.add(q);

                    break;
                case "ShortAnswer":
                    q = parseShortAnswer(questionElement);
                    test.add(q);

                    break;
                case "TrueFalse":
                    q = parseTrueFalse(questionElement);
                    test.add(q);

                    break;
                case "Numerical":
                    q = parseNumerical(questionElement);
                    test.add(q);

                    break;
            }
        }

        return test;
    }

    Matching parseMatching(Element questionElement) {
        String name = getTextField("name", questionElement);
        String head = getTextField("questiontext", questionElement);
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
        String name = getTextField("name", questionElement);
        String head = getTextField("questiontext", questionElement);

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
        ArrayList<Answer> answerList = new ArrayList<>();
        NodeList answerElements = questionElement.getElementsByTagName("Answer");

        for(int i = 0; i < answerElements.getLength(); i++) {
            Element answerElement = (Element)answerElements.item(i);

            String text = answerElement.getElementsByTagName("text").item(0).getNodeValue();
            int fraction = Integer.parseInt(answerElement.getAttribute("fraction"));

            answerList.add(new Answer(text, fraction));
        }

        return answerList;
    }

    private String getTextField(String elementName,
                        Element questionElement) {
        return ((Element)questionElement.getElementsByTagName(elementName).item(0))
                .getElementsByTagName("text").item(0)
                .getNodeValue();
    }
}
