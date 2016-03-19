package testeditor.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import testeditor.Interface.Actions.*;

/**
 * Главное окно
 */
public class MainFrame extends JFrame {
    private JPanel controlPanel;
    private JButton openButton;
    private JTextArea content; // содержимое теста
    private JMenuBar menuBar; // меню

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
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,30,30));

        openButton = new JButton(new OpenAction(content));
        controlPanel.add(openButton);

        content = new JTextArea();
        content.setLineWrap(true);//автоматический перенос строк

        JScrollPane scrollPane = new JScrollPane(content); // полоса прокрутки для TextArea

        //------- Создаем главное меню -------//
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenu = new JMenuItem(new OpenAction(content));
        fileMenu.add(openMenu);

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setIcon(new ImageIcon("src/testeditor/Interface/img/exit.png"));
        exitMenu.addActionListener((ActionEvent event) ->System.exit(0));
        fileMenu.add(exitMenu);

        menuBar.add(fileMenu);

        //-------- Добавляем на форму созданные компоненты GUI -------//
        this.setJMenuBar(menuBar);
        //add(content); - scrollPane является контейнером дя TextArea, поэтому нужно добавлять только его
        this.add(scrollPane);
        this.add(controlPanel,BorderLayout.SOUTH);
    }
}
