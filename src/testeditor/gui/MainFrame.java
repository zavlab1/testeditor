package testeditor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;

import testeditor.gui.Actions.*;
import testeditor.gui.panels.ControlPanel;
import testeditor.gui.panels.EditPanel;
import testeditor.gui.services.ListRenderer;
import testeditor.gui.services.VerticalButton;
import testeditor.question.Question;

/**
 * Главное окно
 */
public class MainFrame extends JFrame {

    public MainFrame(){
        // определяем размер экрана
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;;
        int frameWidth = (int)(width/1.5); //размер окна
        int frameHeight = (int)(height/1.5);//по умолчанию

        //------- Устанавливаем расположение и размер окна -------//
        this.setLocation(width/6,height/6);
        this.setSize(frameWidth,frameHeight);
        this.setIconImage(new ImageIcon("src/testeditor/gui/img/main.png").getImage());// путь к файлу нужно указывать не относительно текущего пакета, а относительно корня проекта

        //------- Создаем и настраиваем компоненты GUI -------//
        this.add(new JPanel(),BorderLayout.WEST); //левый бордер
        this.add(new JPanel(),BorderLayout.SOUTH);// нижний бордер

        DefaultListModel<Question> listModel = new DefaultListModel<>();// Модель для компонета contentList
        JList<Question> questionList = new JList(listModel); // Output List
        questionList.setBackground(Color.GRAY);
        questionList.setCellRenderer(new ListRenderer());

        JScrollPane scrollPane = new JScrollPane(questionList); // полоса прокрутки для списка
        this.add(scrollPane);

        this.add(new ControlPanel(listModel),BorderLayout.NORTH);// Панель с кнопками "Открыть","Создать","Save as"

        this.add(new EditPanel(questionList), BorderLayout.EAST); // Панель редактирования списка

        //------- Создаем главное меню -------//
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenu = new JMenuItem(new OpenAction(listModel));
        openMenu.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));// оперативные клавиши
        fileMenu.add(openMenu);

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setIcon(new ImageIcon("src/testeditor/gui/img/exit.png"));
        exitMenu.addActionListener((ActionEvent event) ->System.exit(0));
        fileMenu.add(exitMenu);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }
}
