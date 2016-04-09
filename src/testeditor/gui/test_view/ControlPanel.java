package testeditor.gui.test_view;

import testeditor.gui.test_view.actions.CreateTestAction;
import testeditor.gui.test_view.actions.OpenTestAction;
import testeditor.gui.test_view.actions.SaveTestAction;
import testeditor.gui.services.*;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Панель для управления созданием, открытием и сохранением теста
 */
public class ControlPanel extends JPanel {
    public ControlPanel(TestView testView, DefaultListModel<Question> listModel){

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JButton createButton = new VerticalButton(new CreateTestAction());//кнопка создать тест
        createButton.setEnabled(true);
        add(createButton);

        JButton openButton = new VerticalButton(new OpenTestAction(testView,listModel));// кнопка открыть тест
        add(openButton);

        JButton saveAsButton = new VerticalButton(new SaveTestAction());// кнопка сохранить как
        add(saveAsButton);
        saveAsButton.setEnabled(true);
    }
}
