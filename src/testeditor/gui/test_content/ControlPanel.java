package testeditor.gui.test_content;

import testeditor.gui.actions.CreateAction;
import testeditor.gui.actions.OpenAction;
import testeditor.gui.actions.SaveAction;
import testeditor.gui.services.*;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Панель для управления созданием, открытием и сохранением теста
 */
public class ControlPanel extends JPanel {
    public ControlPanel(TestView testView, DefaultListModel<Question> listModel){

        setLayout(new FlowLayout(FlowLayout.LEFT,20,20));

        JButton createButton = new VerticalButton(new CreateAction());//кнопка создать тест
        createButton.setEnabled(false);
        add(createButton);

        JButton openButton = new VerticalButton(new OpenAction(testView,listModel));// кнопка открыть тест
        add(openButton);

        JButton saveAsButton = new VerticalButton(new SaveAction());// кнопка сохранить как
        add(saveAsButton);
        saveAsButton.setEnabled(false);
    }
}
