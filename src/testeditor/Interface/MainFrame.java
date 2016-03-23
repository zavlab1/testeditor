package testeditor.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import testeditor.Interface.Actions.*;
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
        this.setIconImage(new ImageIcon("src/testeditor/Interface/img/main.png").getImage());// путь к файлу нужно указывать не относительно текущего пакета, а относительно корня проекта

        //------- Создаем и настраиваем компоненты GUI -------//
        DefaultListModel<Question> listModel = new DefaultListModel<>();// Модель для компонета contentList
        JList<Question> questionList = new JList(listModel); // Output List
        questionList.setBackground(Color.GRAY);
        questionList.setCellRenderer(new ListRenderer());

        JScrollPane scrollPane = new JScrollPane(questionList); // полоса прокрутки для списка
        this.add(scrollPane);

        JPanel controlPanel = new JPanel(); // Панель с кнопками
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,30,30));

        JButton openButton = new JButton(new OpenAction(listModel));
        controlPanel.add(openButton);
        this.add(controlPanel,BorderLayout.SOUTH);

        //------- Создаем главное меню -------//
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenu = new JMenuItem(new OpenAction(listModel));
        openMenu.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));// оперативные клавиши
        fileMenu.add(openMenu);

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setIcon(new ImageIcon("src/testeditor/Interface/img/exit.png"));
        exitMenu.addActionListener((ActionEvent event) ->System.exit(0));
        fileMenu.add(exitMenu);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }
}
