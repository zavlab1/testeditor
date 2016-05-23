package testeditor.gui.question_view.actions;

import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by main on 04.04.16.
 */
public class MoveUpAction extends MoveAction {

    public MoveUpAction(JList<Question> qList, String title, String html_icon) {
        super(qList, title, html_icon);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        int indexOfSelected = list.getSelectedIndex();
        if (indexOfSelected - 1 < 0) {
            return;
        }
        swapElements(indexOfSelected, indexOfSelected - 1);
        list.setSelectedIndex(indexOfSelected - 1);
        list.updateUI();
    }

}