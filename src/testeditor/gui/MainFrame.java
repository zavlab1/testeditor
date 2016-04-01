package testeditor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import testeditor.gui.actions.*;
import testeditor.gui.question_content.QuestionView;
import testeditor.gui.services.GBC;
import testeditor.gui.test_content.TestView;
import testeditor.question.Question;

/**
 * Главное окно
 */
public class MainFrame extends ParentFrame {
    private TestView testView; // панель со списком вопросов и кнопками управляния ими
    private QuestionView questionView; // панель с содержимым выбранного вопроса

    public MainFrame(){
        super();

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

        //------- Создаем и настраиваем компоненты GUI -------//
        DefaultListModel<Question> listModel = new DefaultListModel<>();// Модель для компонета JList со списком вопросов

        setLayout(new GridBagLayout());

        testView = new TestView(listModel); // добавляем панель с содержимым теста
        add(testView, new GBC(0,0,1,1,0,0,100,100).setFill(GBC.BOTH));

        questionView = new QuestionView(); // добавляем панель с контентом конкретного вопроса
        questionView.setVisible(false); // делаем изначально ее невидимой
        add(questionView, new GBC(0,0,1,1,0,0,100,100).setFill(GBC.BOTH));

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
