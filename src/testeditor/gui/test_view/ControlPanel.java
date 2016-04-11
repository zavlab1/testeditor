package testeditor.gui.test_view;

import testeditor.gui.test_view.actions.CreateTestAction;
import testeditor.gui.test_view.actions.OpenTestAction;
import testeditor.gui.test_view.actions.SaveAsTestAction;
import testeditor.gui.services.*;
import testeditor.gui.test_view.actions.SaveTestAction;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Панель для управления созданием, открытием и сохранением теста
 */
public class ControlPanel extends JPanel {
    private JButton createButton;
    private JButton openButton;
    private JButton saveAsButton;
    private JButton saveButton;

    public ControlPanel(JList questionList) {

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        createButton = new VerticalButton(new CreateTestAction(questionList)); //кнопка создать тест
        add(createButton);

        openButton = new VerticalButton(new OpenTestAction(questionList)); // кнопка открыть тест
        add(openButton);

        saveAsButton = new VerticalButton(new SaveAsTestAction(questionList)); // кнопка сохранить
        saveAsButton.setEnabled(false);
        add(saveAsButton);

        saveButton = new VerticalButton(new SaveTestAction(saveAsButton)); // кнопка сохранить как
        saveButton.setEnabled(false);
        add(saveButton);
    }
    public JButton getCreateButton() {
        return createButton;
    }
    public JButton getOpenButton() {
        return openButton;
    }
    public JButton getSaveAsButton() {
        return saveAsButton;
    }
    public JButton getSaveButton() {
        return saveButton;
    }
}