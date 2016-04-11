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
    public ControlPanel(JList questionList){

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JButton createButton = new VerticalButton(new CreateTestAction());//кнопка создать тест
        add(createButton);

        JButton openButton = new VerticalButton(new OpenTestAction(questionList));// кнопка открыть тест
        add(openButton);

        JButton saveAsButton = new VerticalButton(new SaveAsTestAction(questionList));// кнопка сохранить
        add(saveAsButton);

        JButton saveButton = new VerticalButton(new SaveTestAction(saveAsButton));// кнопка сохранить как
        add(saveButton);
    }
}
