package testeditor.parser;

import com.sun.org.apache.xpath.internal.operations.Mult;
import com.sun.org.apache.xpath.internal.operations.Number;
import testeditor.Test;
import testeditor.question.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamResult;

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
        int questionsCount = doc.getDocumentElement().getElementsByTagName("question").getLength();
        NodeList questionNodes = doc.getDocumentElement().getElementsByTagName("question");

        for(int i = 0; i < questionsCount; i++) {
            Element questionElement = (Element)questionNodes.item(i);
            String questionType = questionElement.getAttribute("type");

            switch (questionType) {
                case "multichoice":
                    q = parseMultiChoice(questionElement);
                    test.add(q);

                    break;
                case "shortanswer":
                    q = parseShortAnswer(questionElement);
                    test.add(q);

                    break;
                case "truefalse":
                    q = parseTrueFalse(questionElement);
                    test.add(q);

                    break;
                case "numerical":
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
        r = (Matching)prepareQuestion(r, questionElement);

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

        MultiChoice q = new MultiChoice(name, head, answerList);
        q = (MultiChoice)prepareQuestion(q, questionElement);

        return q;
    }

    ShortAnswer parseShortAnswer(Element questionElement) {
        String name = getTextField("name", questionElement);
        String head = getTextField("questiontext", questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        ShortAnswer q = new ShortAnswer(name, head, answerList);
        q = (ShortAnswer)prepareQuestion(q, questionElement);

        return q;
    }

    TrueFalse parseTrueFalse(Element questionElement) {
        String name = getTextField("name", questionElement);
        String head = getTextField("questiontext", questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        TrueFalse q = new TrueFalse(name, head, answerList);
        q = (TrueFalse)prepareQuestion(q, questionElement);

        return q;
    }

    Numerical parseNumerical(Element questionElement) {
        String name = getTextField("name", questionElement);
        String head = getTextField("questiontext", questionElement);
        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        Numerical q = new Numerical(name, head, answerList);
        q = (Numerical)prepareQuestion(q, questionElement);

        return q;
    }

    Question prepareQuestion(Question q, Element questionElement) {
        float defaultGrade = Float.parseFloat(getField("defaultgrade", questionElement));
        float penalty = Float.parseFloat(getField("penalty", questionElement));

        q.setDefaultGrade(defaultGrade);
        q.setPenalty(penalty);

        return q;
    }

    ArrayList<Answer> parseAnswerList(Element questionElement) {
        ArrayList<Answer> answerList = new ArrayList<>();
        NodeList answerElements = questionElement.getElementsByTagName("answer");

        String correctFeedback = getTextField("correctfeedback", questionElement);
        String partiallyCorrectFeedback = getTextField("partiallycorrectfeedback", questionElement);
        String incorrectFeedback = getTextField("incorrectfeedback", questionElement);

        for(int i = 0; i < answerElements.getLength(); i++) {
            Element answerElement = (Element)answerElements.item(i);

            String text = answerElement.getElementsByTagName("text").item(0).getTextContent();
            int fraction = Integer.parseInt(answerElement.getAttribute("fraction"));

            String comment;

            if (fraction == Answer.MIN_DEGREE) comment = incorrectFeedback;
            else if (fraction == Answer.MAX_DEGREE) comment = correctFeedback;
            else comment = partiallyCorrectFeedback;

            answerList.add(new Answer(text, fraction, comment));
        }

        return answerList;
    }

    private String getTextField(String elementName, Element questionElement) {
        Element fieldElement = (Element)questionElement.getElementsByTagName(elementName).item(0);
        if(fieldElement == null) return "";

        Node textElement = fieldElement.getElementsByTagName("text").item(0);
        if(textElement == null) return "";

        return textElement.getTextContent().replaceAll("<[a-zA-Z\\s/+>]", "");
    }

    private String getField(String elementName, Element questionElement) {
        Element fieldElement = (Element)questionElement.getElementsByTagName(elementName).item(0);
        if(fieldElement == null) return "";

        return fieldElement.getTextContent().replaceAll("<[a-zA-Z\\s/+>]", "");
    }
}
