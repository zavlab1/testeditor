package testeditor.gui.services;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Arrays;

/**
 * Created by dimitry on 10.04.16.
 */
public class QListModel <T> extends DefaultListModel {

    public void update(JList<Question> list) {

        try {
            ListDataListener[] listeners = this.getListDataListeners();

            Arrays.stream(listeners).forEach(this::removeListDataListener);
                this.clear();
                Test.getTest().stream().forEach(this::addElement);
            Arrays.stream(listeners).forEach(this::addListDataListener);

            list.updateUI();

        } catch (Exception ex) {
            System.out.println(1);
            ex.printStackTrace();
        }
    }
}
