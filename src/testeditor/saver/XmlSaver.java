package testeditor.saver;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.Test;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import testeditor.question.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Максим on 21.03.2016.
 */
public class XmlSaver extends Saver {
    Document doc;

    public XmlSaver(Test test, String filepath) throws Exception {
        super(test, filepath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
    }

    public String doLineForMultiChoice(Question q){
        Element QuestionElement = doc.createElement("question");
        QuestionElement.setAttribute("type", "multichoice");

        QuestionElement = setQuestionName(QuestionElement, q.getQName());
        QuestionElement = setQuestionHead(QuestionElement, q.getQHead());

        List<Answer> answerList = q.getAnswerList();

        for(int i = 0; i < answerList.size(); i++) {
            Element AnswerElement = doc.createElement("answer");
            AnswerElement.setAttribute("fraction", String.valueOf(answerList.get(i).getDegree()));

            Element AnswerTextElement = doc.createElement("text");
            CDATASection AnswerTextSection = doc.createCDATASection(answerList.get(i).getAText());
            AnswerTextElement.appendChild(AnswerTextSection);

            AnswerElement.appendChild(AnswerTextElement);
            QuestionElement.appendChild(AnswerElement);
        }

        return QuestionElement.toString();
    }

    public String doLineForTrueFalse(Question q) {
        Element QuestionElement = doc.createElement("question");
        QuestionElement.setAttribute("type", "truefalse");

        QuestionElement = setQuestionName(QuestionElement, q.getQName());
        QuestionElement = setQuestionHead(QuestionElement, q.getQHead());

        List<Answer> answerList = q.getAnswerList();

        for(int i = 0; i < answerList.size(); i++) {
            Element AnswerElement = doc.createElement("answer");
            AnswerElement.setAttribute("fraction", (answerList.get(i).getDegree() == 1.0 ? "true" : "false"));

            Element AnswerTextElement = doc.createElement("text");
            AnswerTextElement.setNodeValue(answerList.get(i).getAText());

            AnswerElement.appendChild(AnswerTextElement);
            QuestionElement.appendChild(AnswerElement);
        }

        return QuestionElement.toString();
    }

    public String doLineForMatching(Question q) {
        Element QuestionElement = doc.createElement("question");
        QuestionElement.setAttribute("type", "matching");

        QuestionElement = setQuestionName(QuestionElement, q.getQName());
        QuestionElement = setQuestionHead(QuestionElement, q.getQHead());

        ArrayList<Question> SubQuestions =
                ((Matching)q).getSubQuestions();

        for(int n = 0; n < SubQuestions.size(); n++){
            Element SubQuestionElement =
                    doc.createElement("subquestion");
            Element QuestionTextElement =
                    doc.createElement("text");
            CDATASection QuestionTextSection =
                    doc.createCDATASection(q.getQText());
            QuestionTextElement.appendChild(QuestionTextSection);

            Element AnswerElement =
                    doc.createElement("answer");
            Element AnswerTextElement =
                    doc.createElement("text");
            AnswerTextElement.setNodeValue
                    (q.getAnswerList().get(0).getAText());
            AnswerElement.appendChild(AnswerTextElement);

            SubQuestionElement.appendChild(QuestionTextElement);
            SubQuestionElement.appendChild(AnswerElement);
            QuestionElement.appendChild(SubQuestionElement);
        }

        return QuestionElement.toString();
    }

    public String doLineForShortAnswer(Question q){
        Element QuestionElement = doc.createElement("question");
        QuestionElement.setAttribute("type", "shortanswer");

        QuestionElement = setQuestionName(QuestionElement, q.getQName());
        QuestionElement = setQuestionHead(QuestionElement, q.getQHead());

        List<Answer> answerList = q.getAnswerList();

        for(int i = 0; i < answerList.size(); i++) {
            Element AnswerElement = doc.createElement("answer");
            AnswerElement.setAttribute("fraction", String.valueOf(answerList.get(i).getDegree()));

            Element AnswerTextElement = doc.createElement("text");
            AnswerTextElement.setNodeValue(answerList.get(i).getAText());

            AnswerElement.appendChild(AnswerTextElement);
            QuestionElement.appendChild(AnswerElement);
        }

        return QuestionElement.toString();
    }

    public String doLineForNumerical(Question q){
        throw new NotImplementedException();
    }

    Element setQuestionName(Element questionElement, String name) {
        Element NameElement = doc.createElement("name");
        Element NameTextElement = doc.createElement("text");
        NameTextElement.setNodeValue(name);
        NameElement.appendChild(NameTextElement);
        questionElement.appendChild(NameElement);

        return questionElement;
    }

    Element setQuestionHead(Element questionElement, String head) {
        Element HeadElement = doc.createElement("questiontext");
        HeadElement.setAttribute("format", "html");
        Element HeadTextElement = doc.createElement("text");
        CDATASection HeadTextSection = doc.createCDATASection(head);
        HeadTextElement.appendChild(HeadTextSection);
        HeadElement.appendChild(HeadTextElement);
        questionElement.appendChild(HeadElement);

        return questionElement;
    }
}
