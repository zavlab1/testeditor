package testeditor.saver;

import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.Test;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import testeditor.question.*;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Максим on 21.03.2016.
 */
public class XmlSaver extends Saver {
    Document doc;

    public XmlSaver(String filepath) throws Exception {
        super(filepath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
    }

    @Override
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

        return xmlToString(questionElement);
    }

    @Override
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

        return xmlToString(questionElement);
    }

    @Override
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

        return xmlToString(questionElement);
    }

    @Override
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

        return xmlToString(questionElement);
    }

    @Override
    public String doLineForNumerical(Question q){
        throw new NullPointerException();
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
