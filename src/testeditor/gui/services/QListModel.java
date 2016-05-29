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

            //гасим обработку событий и работаем со списком. Потом снова включаем обработку событий
            Arrays.stream(listeners).forEach(this::removeListDataListener);
                this.clear();
            Test.getTest().stream().forEach(this::addElement);
            Arrays.stream(listeners).forEach(this::addListDataListener);

            //генерируем событие добавления и обновляем UI вручную, т.к. во время обновления списка
            //эти события были погашены вместе с остальными
            fireIntervalAdded(list, 0, Test.getTest().size()-1); // Here the event is fired
            list.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
