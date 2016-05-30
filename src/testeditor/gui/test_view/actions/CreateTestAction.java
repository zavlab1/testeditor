package testeditor.gui.test_view.actions;

import testeditor.Test;
import testeditor.gui.MainFrame;
import testeditor.gui.test_view.EditPanel;
import testeditor.gui.test_view.TestView;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Класс-слушатель для события открытия файла
 */
public class CreateTestAction extends AbstractAction {
    private JList<Question> qList;
    public CreateTestAction(JList<Question> qList) {
        this.qList = qList;

        this.putValue(Action.NAME, "Создать");
        this.putValue(Action.SHORT_DESCRIPTION, "Создать новый тест");
        this.putValue(Action.SMALL_ICON, UIManager.getIcon("FileView.fileIcon"));
    }

    public void actionPerformed(ActionEvent event) {

        Test.getTest().clear();
        ((DefaultListModel) qList.getModel()).clear();

        MainFrame frame = (MainFrame) SwingUtilities.getRoot(qList);
        frame.setTitle("new test - " + frame.APP_NAME);

        TestView tv = (TestView) qList.getParent().getParent().getParent();
        if (!tv.getEditPanel().isVisible())
            tv.getEditPanel().setVisible(true);

        EditPanel ep = tv.getEditPanel();
        ep.getButtons().stream().forEach(b -> b.setEnabled(false));
        ep.getCreateButton().setEnabled(true);
    }
}
