package testeditor.gui.actions;

import testeditor.Test;
import testeditor.gui.test_content.TestView;
import testeditor.parser.*;
import testeditor.question.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Класс-слушатель для события открытия файла
 */
public class OpenAction extends AbstractAction {
    private DefaultListModel<Question> questionList;
    private TestView testView;

    /**
     * @param listModel - модель списка для JList, куда добавляем вопросы
     */
    public OpenAction( TestView testView, DefaultListModel<Question> listModel){
        this.testView = testView;
        questionList = listModel;

        this.putValue(Action.NAME,"Открыть");
        this.putValue(Action.SHORT_DESCRIPTION,"Открыть файл теста");
        this.putValue(Action.SMALL_ICON, new ImageIcon("src/testeditor/gui/img/open.png"));
    }

    public void actionPerformed(ActionEvent event){
        Test test; // Объект теста
        JFileChooser openDialog = new JFileChooser(); // объект диалогового окна

        //------- Настраиваем диалоговое окно -------//
        openDialog.setCurrentDirectory(new File(".")); //корневая дирректория по умолчанию
        openDialog.setAcceptAllFileFilterUsed(false); //убираем в фильтрах "All files"
        openDialog.addChoosableFileFilter(new FileNameExtensionFilter("Все поддерживаемые форматы (*.gift, *.xml)","gift","xml"));
        openDialog.addChoosableFileFilter(new FileNameExtensionFilter("GIFT Moodle test (*.gift)","gift"));
        openDialog.addChoosableFileFilter(new FileNameExtensionFilter("XML Moodle test (*.xml)","xml"));

        //------- Обрабатываем файл теста -------//
        int result = openDialog.showDialog(null,"Открыть файл");
        if (result == JFileChooser.APPROVE_OPTION){
            questionList.clear();
            try {
                test = Parser.parse(openDialog.getSelectedFile().getAbsolutePath());

                for (Question question: test){
                    questionList.addElement(question);
                }
            }
            catch (Exception ex) {ex.printStackTrace();}
            testView.addEditPanel();
        }


    }
}
