package testeditor.gui.test_view;

import testeditor.Test;
import testeditor.gui.services.ListRenderer;
import testeditor.gui.services.QListModel;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

/**
 * Панель, отображающая общий вид теста и кнопки управления его содержимым
 */
public class TestView extends JPanel {
    private DefaultListModel<Question> listModel;
    private JList<Question> questionList;
    private ControlPanel controlPanel;  // управление файлом теста
    private EditPanel    editPanel;     // управление элементами теста

    public TestView() {
        listModel = new QListModel<>(); // Модель для компонета JList со списком вопросов
        listModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent listDataEvent) {
                Test.getTest().update(Collections.list(listModel.elements()));

                if (!controlPanel.getSaveAsButton().isEnabled()) {
                    controlPanel.getSaveAsButton().setEnabled(true);
                    editPanel.getButtons().stream().forEach(b -> b.setEnabled(true));
                    editPanel.getListSpinner().setEnabled(true);
                    if (!Test.getTest().isEmpty())
                        controlPanel.getSaveButton().setEnabled(true);
                }
            }
            @Override
            public void intervalRemoved(ListDataEvent listDataEvent) {
                Test.getTest().update(Collections.list(listModel.elements()));
                if (listModel.isEmpty()) {
                    controlPanel.getSaveAsButton().setEnabled(false);
                    controlPanel.getSaveButton()  .setEnabled(false);
                    editPanel.getButtons().stream().forEach(b -> b.setEnabled(false));
                    editPanel.getListSpinner() .setEnabled(false);
                    editPanel.getCreateButton().setEnabled(true );
                }
            }
            @Override
            public void contentsChanged(ListDataEvent listDataEvent) {
                Test.getTest().update(Collections.list(listModel.elements()));
            }
        });

        //------- Создаем и настраиваем компоненты GUI -------//
        setLayout(new BorderLayout());

        questionList = new JList<>(this.listModel);
        questionList.setBackground(Color.GRAY);
        questionList.setCellRenderer(new ListRenderer());
        questionList.setFixedCellWidth(questionList.getWidth());

        questionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) editPanel.getEditButton().doClick();
            }
        });

        JScrollPane scrollPane = new JScrollPane(questionList); // полоса прокрутки для списка
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        controlPanel = new ControlPanel(questionList);
        editPanel    = new EditPanel   (questionList);
        editPanel.setVisible(false);

        this.add(new JPanel(), BorderLayout.WEST );
        this.add(new JPanel(), BorderLayout.SOUTH);
        this.add(scrollPane);
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(editPanel   , BorderLayout.EAST );
    }

    public JList<Question> getQuestionList() {
        return questionList;
    }
    public EditPanel getEditPanel() {
        return editPanel;
    }
    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}