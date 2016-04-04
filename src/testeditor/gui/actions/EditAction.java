package testeditor.gui.actions;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by main on 04.04.16.
 */
public class EditAction extends AbstractAction {
    private JList list;

    public EditAction (JList qList){
        list = qList;

        this.putValue(Action.NAME,"Редактировать");
        this.putValue(Action.SHORT_DESCRIPTION,"Открыть редактор вопроса");
    }

    public void actionPerformed(ActionEvent event){
        int index = list.getSelectedIndex();
        Question q = (Question) Test.getTest().toArray()[index];
        q.getFrame().setVisible(true);
    }
}
