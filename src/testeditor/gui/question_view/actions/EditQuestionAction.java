package testeditor.gui.question_view.actions;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by main on 04.04.16.
 */
public class EditQuestionAction extends AbstractAction {
    private JList list;

    public EditQuestionAction(JList qList){
        list = qList;

        this.putValue(Action.NAME,
                      "<html>" +
                          "<b><font color='#A52A2A' size=+1>&#9998;&nbsp;&nbsp;&nbsp;</font></b>" +
                          "Редактировать" +
                      "</html>");
        this.putValue(Action.SHORT_DESCRIPTION,"Открыть редактор вопроса");
    }

    public void actionPerformed(ActionEvent event){
        int index = list.getSelectedIndex();
        Question q = (Question) Test.getTest().toArray()[index];
        JFrame qFrame = q.getFrame();
        qFrame.addWindowListener(new WindowAdapter() {
                                     @Override
                                     public void windowClosed(WindowEvent event) {
                                         list.repaint();
                                     }
                                 });
        qFrame.setVisible(true);
    }
}