package testeditor.gui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Панель управления элементами списка
 */
public class EditPanel extends JPanel {
    public EditPanel(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JButton editButton = new JButton("Редактировать");
        add(editButton);

        JButton createButton = new JButton("Создать");
        add(createButton);

        JButton deleteButton = new JButton("Удалить");
        add(deleteButton);

        JButton beginButton = new JButton("В начало");
        add(beginButton);

        JButton upButton = new JButton("Вверх");
        add(upButton);

        JButton downButton = new JButton("Вниз");
        add(downButton);

        JButton endButton = new JButton("В конец");
        add(endButton);
    }
}
