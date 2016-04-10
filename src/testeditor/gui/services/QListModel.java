package testeditor.gui.services;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;

/**
 * Created by dimitry on 10.04.16.
 */
public class QListModel <T> extends DefaultListModel {
    public void update() {
        this.clear();
        try {
            Test test = Test.getTest();
            for (Question question: test) {
                this.addElement(question);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
