package testeditor.saver;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.Test;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import testeditor.question.*;

import java.io.StringWriter;
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
        Element questionElement = doc.createElement("question");
        questionElement.setAttribute("type", "multichoice");

        questionElement = setTextField(questionElement, "name", q.getQName());
        questionElement = setTextFieldWithCData(questionElement, "name", q.getQHead());

        List<Answer> answerList = q.getAnswerList();

        for(int i = 0; i < answerList.size(); i++) {
            Element AnswerElement = doc.createElement("answer");
            AnswerElement.setAttribute("fraction", String.valueOf(answerList.get(i).getDegree()));

            Element AnswerTextElement = doc.createElement("text");
            CDATASection AnswerTextSection = doc.createCDATASection(String.valueOf(answerList.get(i).getDegree()));
            AnswerTextElement.appendChild(AnswerTextSection);

            AnswerElement.appendChild(AnswerTextElement);
            questionElement.appendChild(AnswerElement);
        }

        questionElement = fillQuestion(questionElement, q);

        return xmlToString(questionElement);
    }

    public String doLineForTrueFalse(Question q) {
        Element questionElement = doc.createElement("question");
        questionElement.setAttribute("type", "truefalse");

        questionElement = setTextField(questionElement, "name", q.getQName());
        questionElement = setTextFieldWithCData(questionElement, "name", q.getQHead());

        List<Answer> answerList = q.getAnswerList();

        for(int i = 0; i < answerList.size(); i++) {
            Element AnswerElement = doc.createElement("answer");
            AnswerElement.setAttribute("fraction", (answerList.get(i).getDegree() == 1.0 ? "true" : "false"));

            Element AnswerTextElement = doc.createElement("text");
            AnswerTextElement.setNodeValue(String.valueOf(answerList.get(i).getDegree()));

            AnswerElement.appendChild(AnswerTextElement);
            questionElement.appendChild(AnswerElement);
        }

        questionElement = fillQuestion(questionElement, q);

        return xmlToString(questionElement);
    }

    public String doLineForMatching(Question q) {
        Element questionElement = doc.createElement("question");
        questionElement.setAttribute("type", "matching");

        questionElement = setTextField(questionElement, "name", q.getQName());
        questionElement = setTextFieldWithCData(questionElement, "name", q.getQHead());

        ArrayList<Question> SubQuestions =
                ((Matching)q).getSubQuestions();

        for(int n = 0; n < SubQuestions.size(); n++){
            Element SubquestionElement =
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
                    (String.valueOf(q.getAnswerList().get(0).getDegree()));
            AnswerElement.appendChild(AnswerTextElement);

            SubquestionElement.appendChild(QuestionTextElement);
            SubquestionElement.appendChild(AnswerElement);
            questionElement.appendChild(SubquestionElement);
        }

        questionElement = fillQuestion(questionElement, q);

        return xmlToString(questionElement);
    }

    public String doLineForShortAnswer(Question q){
        Element questionElement = doc.createElement("question");
        questionElement.setAttribute("type", "shortanswer");

        questionElement = setTextField(questionElement, "name", q.getQName());
        questionElement = setTextFieldWithCData(questionElement, "name", q.getQHead());

        List<Answer> answerList = q.getAnswerList();

        for(int i = 0; i < answerList.size(); i++) {
            Element AnswerElement = doc.createElement("answer");
            AnswerElement.setAttribute("fraction", String.valueOf(answerList.get(i).getDegree()));

            Element AnswerTextElement = doc.createElement("text");
            AnswerTextElement.setNodeValue(String.valueOf(answerList.get(i).getDegree()));

            AnswerElement.appendChild(AnswerTextElement);
            questionElement.appendChild(AnswerElement);
        }

        questionElement = fillQuestion(questionElement, q);

        return xmlToString(questionElement);
    }

    private Element fillQuestion(Element questionElement,
                                 Question q) {
        questionElement = setTextField(questionElement,
                "generalfeedback", q.getFeedback("general"));
        questionElement = setTextField(questionElement,
                "correctfeedback", q.getFeedback("correct"));
        questionElement = setTextField(questionElement,
                "partiallycorrectfeedback", q.getFeedback("partial"));
        questionElement = setTextField(questionElement,
                "incorrectfeedback", q.getFeedback("incorrect"));

        return questionElement;
    }

    public String doLineForNumerical(Question q){
        throw new NotImplementedException();
    }

    @Override public void toFile(String answersText) {
        //String text = "<quiz>";
        //text += answersText;
        //text += "</quiz>";

        super.toFile(answersText);
    }

    String xmlToString(Element questionElement) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer tr = tf.newTransformer();
            //tr.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter wr = new StringWriter();
            tr.transform(new DOMSource(questionElement), new StreamResult(wr));
            String output = wr.getBuffer().toString();

            return output;
        }

        catch (TransformerConfigurationException e1) {
            return "";
        }

        catch (TransformerException e2) {
            return "";
        }
    }

    Element setTextField(Element questionElement, String fieldName,
                          String value) {
        Element NameElement = doc.createElement(fieldName);
        Element NameTextElement = doc.createElement("text");
        NameTextElement.setTextContent(value);
        NameElement.appendChild(NameTextElement);
        questionElement.appendChild(NameElement);

        return questionElement;
    }

    Element setTextFieldWithCData(Element questionElement,
                                  String fieldName, String value) {
        Element HeadElement = doc.createElement(fieldName);
        HeadElement.setAttribute("format", "html");
        Element HeadTextElement = doc.createElement("text");
        CDATASection HeadTextSection = doc.createCDATASection(value);
        HeadTextElement.appendChild(HeadTextSection);
        HeadElement.appendChild(HeadTextElement);
        questionElement.appendChild(HeadElement);

        return questionElement;
    }
}
