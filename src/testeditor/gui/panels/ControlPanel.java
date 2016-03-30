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

        setLayout(new GridBagLayout());

        JButton createButton = new VerticalButton(new CreateAction());//кнопка создать тест
        createButton.setEnabled(false);
        add(createButton,new GBC(0,0,1,1,10,10,1,0).setFill(GridBagConstraints.HORIZONTAL).setInsets(20,0,20,20));

        JButton openButton = new VerticalButton(new OpenAction(listModel));// кнопка открыть тест
        add(openButton,new GBC(1,0,1,1,10,10,1,0).setFill(GridBagConstraints.HORIZONTAL).setInsets(20,0,20,20));

        JButton saveAsButton = new VerticalButton(new SaveAction());// кнопка сохранить как
        add(saveAsButton,new GBC(2,0,1,1,10,10,1,0).setFill(GridBagConstraints.HORIZONTAL).setInsets(20,0,20,20));
        saveAsButton.setEnabled(false);

        JPanel emptyPanel = new JPanel(); // пустая панель для сдвига кнопок влево
        add(emptyPanel, new GBC(3,0,1,1,10,10,1,0).setFill(GridBagConstraints.HORIZONTAL).setInsets(20,20,20,20));
    }
}
