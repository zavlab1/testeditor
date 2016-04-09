package testeditor.gui.test_view.actions;

import testeditor.Test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Класс-слушатель для события открытия файла
 */
public class CreateTestAction extends AbstractAction {

    public CreateTestAction(){
        this.putValue(Action.NAME,"Создать");
        this.putValue(Action.SHORT_DESCRIPTION,"Создать новый тест");
        this.putValue(Action.SMALL_ICON, UIManager.getIcon("FileView.fileIcon"));
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
    }
}
