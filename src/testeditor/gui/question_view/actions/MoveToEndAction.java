package testeditor.gui.question_view.actions;

import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by main on 04.04.16.
 */
public class MoveToEndAction extends MoveAction {

    public MoveToEndAction(JList<Question> qList, String title, String html_icon) {
        super(qList, title, html_icon);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        swapElements(list.getSelectedIndex(), list.getModel().getSize()-1);
        list.setSelectedIndex(list.getModel().getSize()-1);
        list.updateUI();
    }
}