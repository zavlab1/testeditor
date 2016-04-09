package testeditor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import testeditor.gui.services.GBC;
import testeditor.gui.test_view.TestView;
import testeditor.gui.test_view.actions.OpenTestAction;
import testeditor.question.Question;

/**
 * Главное окно
 */
public class MainFrame extends ParentFrame {
    private TestView testView; // панель со списком вопросов и кнопками управляния ими

    public MainFrame() {

        //------- Создаем и настраиваем компоненты GUI -------//
        DefaultListModel<Question> listModel = new DefaultListModel<>();// Модель для компонета JList со списком вопросов

        setLayout(new GridBagLayout());

        testView = new TestView(listModel); // добавляем панель с содержимым теста
        add(testView, new GBC(0, 0, 1, 1, 0, 0, 100, 100).setFill(GBC.BOTH));

        //------- Создаем главное меню -------//
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenu = new JMenuItem(new OpenTestAction(testView, listModel));
        openMenu.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));// оперативные клавиши
        fileMenu.add(openMenu);

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setIcon(new ImageIcon("src/testeditor/gui/img/exit.png"));
        exitMenu.addActionListener((ActionEvent event) -> System.exit(0));
        fileMenu.add(exitMenu);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }
}
