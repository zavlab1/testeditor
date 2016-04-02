package testeditor.gui.test_content;

import testeditor.gui.services.ListRenderer;
import testeditor.question.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Панель, отображающая общий вид теста и кнопки управления его содержимым
 */
public class TestView extends JPanel {
    private DefaultListModel<Question> listModel;
    private JList<Question> questionList;
    private ControlPanel controlPanel; //управление файлом теста
    private EditPanel editPanel; // управление элементами теста

    public TestView(DefaultListModel<Question> listModel){
        this.listModel = listModel;

        //------- Создаем и настраиваем компоненты GUI -------//
        setLayout(new BorderLayout());
        this.add(new JPanel(), BorderLayout.WEST); //левый бордер
        this.add(new JPanel(), BorderLayout.SOUTH);// нижний бордер

        questionList = new JList(this.listModel); // Output List
        questionList.setBackground(Color.GRAY);
        questionList.setCellRenderer(new ListRenderer());

        JScrollPane scrollPane = new JScrollPane(questionList); // полоса прокрутки для списка
        this.add(scrollPane);

        controlPanel = new ControlPanel(this, this.listModel);
        this.add(controlPanel, BorderLayout.NORTH);// Панель с кнопками "Открыть","Создать","Save as"
    }

    public void addEditPanel(){
        if (editPanel!=null) remove(editPanel);
            editPanel = new EditPanel(questionList);
            this.add(editPanel, BorderLayout.EAST);
            this.updateUI();
    }
}
