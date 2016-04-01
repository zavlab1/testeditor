package testeditor.gui.panels;

import testeditor.gui.Actions.CreateAction;
import testeditor.gui.Actions.OpenAction;
import testeditor.gui.Actions.SaveAction;
import testeditor.gui.services.*;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Панель для управления созданием, открытием и сохранением теста
 */
public class ControlPanel extends JPanel {
    public ControlPanel(DefaultListModel<Question> listModel){

        setLayout(new FlowLayout(FlowLayout.LEFT,20,20));

        JButton createButton = new VerticalButton(new CreateAction());//кнопка создать тест
        createButton.setEnabled(false);
        add(createButton);

        JButton openButton = new VerticalButton(new OpenAction(listModel));// кнопка открыть тест
        add(openButton);

        JButton saveAsButton = new VerticalButton(new SaveAction());// кнопка сохранить как
        add(saveAsButton);
        saveAsButton.setEnabled(false);
    }
}
