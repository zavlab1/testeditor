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
        Question q;
        int QuestionsCount = doc.getDocumentElement().getChildNodes().getLength();
        NodeList QuestionNodes = doc.getDocumentElement().getElementsByTagName("Question");

        for(int i = 0; i < QuestionsCount; i++) {
            Element QuestionElement = (Element)QuestionNodes.item(i);
            String questionType = QuestionElement.getAttribute("Type");

            switch (questionType) {
                case "MultiChoice":
                    q = parseMultiChoice(QuestionElement);
                    test.add(q);

                    break;
                case "ShortAnswer":
                    q = parseShortAnswer(QuestionElement);
                    test.add(q);

                    break;
                case "TrueFalse":
                    q = parseTrueFalse(QuestionElement);
                    test.add(q);

                    break;
                case "Numerical":
                    q = parseNumerical(QuestionElement);
                    test.add(q);

                    break;
            }
        }

        return test;
    }

    Matching parseMatching(Element questionElement) {
        String Name = getTextField("name", questionElement);
        String Head = getTextField("questiontext", questionElement);
        ArrayList<Answer> answerList = new ArrayList<>();

        Matching r = new Matching(Name, Head, answerList);

        NodeList SubQuestions =
                questionElement.getElementsByTagName("subquestion");

        for(int n = 0; n < SubQuestions.getLength(); n++) {
            Element SubQuestionElement =
                    (Element)SubQuestions.item(n);
            Element SubQuestionTextElement =
                    (Element)(SubQuestionElement.
                            getElementsByTagName("text").item(0));
            String SubQuestionText =
                    SubQuestionTextElement.getTextContent();

            Element AnswerElement =
                    (Element)(SubQuestionElement.
                            getElementsByTagName("answer").item(0));
            Element AnswerTextElement =
                    (Element)(AnswerElement.
                            getElementsByTagName("text").item(0));
            String AnswerText =
                    AnswerTextElement.getTextContent();

            Answer a = new Answer(AnswerText, Answer.MAX_DEGREE);
            ArrayList<Answer> answerList2 = new ArrayList<>();
            answerList2.add(a);

            MatchingSubQuestion SubQuestion =
                    new MatchingSubQuestion("", SubQuestionText,
                            answerList2);

            r.addSubQuestion(SubQuestion);
        }

        r = (Matching)fillQuestion(questionElement, r);

        return r;
    }

    private Question fillQuestion(Element questionElement, Question q) {
        String GeneralFeedback = getTextField("generalfeedback",
                questionElement);
        String CorrectFeedback = getTextField("correctfeedback",
                questionElement);
        String PartiallyCorrectFeedback =
                getTextField("partiallycorrectfeedback",
                questionElement);
        String IncorrectFeedback = getTextField("incorrectfeedback",
                questionElement);

        q.setFeedback(GeneralFeedback, CorrectFeedback,
                PartiallyCorrectFeedback, IncorrectFeedback);

        return q;
    }

    MultiChoice parseMultiChoice(Element questionElement) {
        String Name = getTextField("name", questionElement);
        String Head = getTextField("questiontext", questionElement);

        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        MultiChoice q = new MultiChoice(Name, Head, answerList);
        q = (MultiChoice)fillQuestion(questionElement, q);

        return q;
    }

    ShortAnswer parseShortAnswer(Element questionElement) {
        String Name = getTextField("name", questionElement);
        String Head = getTextField("questiontext", questionElement);

        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        ShortAnswer q = new ShortAnswer(Name, Head, answerList);
        q = (ShortAnswer)fillQuestion(questionElement, q);

        return q;
    }

    TrueFalse parseTrueFalse(Element questionElement) {
        String Name = getTextField("name", questionElement);
        String Head = getTextField("questiontext", questionElement);

        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        TrueFalse q = new TrueFalse(Name, Head, answerList);
        q = (TrueFalse)fillQuestion(questionElement, q);

        return q;
    }

    Numerical parseNumerical(Element questionElement) {
        String Name = getTextField("name", questionElement);
        String Head = getTextField("questiontext", questionElement);

        ArrayList<Answer> answerList = parseAnswerList(questionElement);

        Numerical q = new Numerical(Name, Head, answerList);
        q = (Numerical)fillQuestion(questionElement, q);

        return q;
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

    private String getTextField(String elementName,
                        Element questionElement) {
        return ((Element)questionElement.getElementsByTagName(elementName).item(0))
                .getElementsByTagName("text").item(0)
                .getNodeValue();
    }
}
