package testeditor.gui.actions;

import testeditor.Test;
import testeditor.parser.GiftParser;
import testeditor.parser.Parser;
import testeditor.question.Question;
import testeditor.saver.GiftSaver;
import testeditor.saver.Saver;
import testeditor.saver.XmlSaver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

/**
 * Класс-слушатель для события открытия файла
 */
public class SaveAction extends AbstractAction {

    public SaveAction(){

        this.putValue(Action.NAME,"Сохранить как");
        this.putValue(Action.SHORT_DESCRIPTION,"Сохранить тест");
        URL imageURL = getClass().getResource("/testeditor/gui/img/save.png");
        this.putValue(Action.SMALL_ICON, new ImageIcon(imageURL));
    }

    public void actionPerformed(ActionEvent event){
        Test test; // Объект теста
        JFileChooser saveDialog = new JFileChooser(); // объект диалогового окна

        //------- Настраиваем диалоговое окно -------//
        saveDialog.setCurrentDirectory(new File(".")); //корневая дирректория по умолчанию
        saveDialog.setAcceptAllFileFilterUsed(false); //убираем в фильтрах "All files"
        saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("Все поддерживаемые форматы (*.gift, *.xml)","gift","xml"));
        saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("GIFT Moodle test (*.gift)","gift"));
        saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("XML Moodle test (*.xml)","xml"));

        //------- Обрабатываем файл теста -------//
        int result = saveDialog.showSaveDialog(null);;
        if (result == JFileChooser.APPROVE_OPTION){
            try {
                String path = saveDialog.getSelectedFile().getAbsolutePath();
                if (path.toLowerCase().endsWith(".gift")) {
                    Saver s = new GiftSaver(path);
                    s.save();
                } else if (path.toLowerCase().endsWith(".xml")) {
                    Saver s = new XmlSaver(path);
                    s.save();
                } else {
                    throw new Exception("Выбранное разрешение не соответствует поддерживаемым форматам файлов");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
