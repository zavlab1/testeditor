package testeditor.gui.question_view.actions;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by main on 04.04.16.
 */
public class MoveQuestionAction extends AbstractAction {
    private JList list;
    private int direction;

    public MoveQuestionAction(JList qList, int direction) {
        String button_title;
        String where;
        String icon;
        list = qList;
        this.direction = direction;

        if (direction < 0) {
            button_title = "Вверх";
            where = "выше";
            icon = "&#8657";
        } else {
            button_title = "Вниз";
            where = "ниже";
            icon = "&#8659";
        }

        this.putValue(Action.NAME,
                      "<html>" +
                          "<b><font color='#4682B4' size=+1>" + icon + "&nbsp;&nbsp;&nbsp;</font></b>" +
                          button_title +
                      "</html>");

        this.putValue(Action.SHORT_DESCRIPTION, "Переместить " + where);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        int indexOfSelected = list.getSelectedIndex();
        if (indexOfSelected + direction < 0 ||
            indexOfSelected + direction > list.getModel().getSize()-1) {
            return;
        }
        swapElements(indexOfSelected, indexOfSelected + direction);
        indexOfSelected = indexOfSelected + direction;
        list.setSelectedIndex(indexOfSelected);
        list.updateUI();
    }

    private void swapElements(int pos1, int pos2) {
        DefaultListModel<Question> listModel = (DefaultListModel)list.getModel();
        Question tmp = listModel.getElementAt(pos1);
        listModel.set(pos1, listModel.get(pos2));
        listModel.set(pos2, tmp);
    }
}