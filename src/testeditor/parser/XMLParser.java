package testeditor.parser;

import testeditor.Test;
import testeditor.question.Answer;
import testeditor.question.Question;
import testeditor.question.MultiChoice;

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

                    break;
            }
        }

        return test;
    }
}
