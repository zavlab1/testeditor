package testeditor.gui.question_view.actions;

import testeditor.Test;
import testeditor.question.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс-слушатель для события открытия файла
 */
public class RemoveQuestionAction extends AbstractAction {

    private JList list;
    private Question q = null;

    public RemoveQuestionAction(JList qList) {
        list = qList;
        this.putValue (Action.NAME, "<html>" +
                                        "<font color='green' size=+1>" +
                                            "<b>&#10006;&nbsp;&nbsp;&nbsp;</b>" +
                                        "</font>" +
                                        "Удалить" +
                                    "</html>"
                       );
        this.putValue(Action.SHORT_DESCRIPTION,"Создать новый тест");
    }

    public void actionPerformed(ActionEvent event) {
        int index = list.getSelectedIndex();
        Question q = (Question) Test.getTest().toArray()[index];
        Test.getTest().remove(q);
        DefaultListModel<Question> listModel = (DefaultListModel<Question>) list.getModel();
        listModel.removeElement(q);
        if (!listModel.isEmpty()) {
            list.setSelectedIndex(0);
        }
    }
}