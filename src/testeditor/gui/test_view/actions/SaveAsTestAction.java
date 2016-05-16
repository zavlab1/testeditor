package testeditor.gui.test_view.actions;

import testeditor.Test;
import testeditor.gui.MainFrame;
import testeditor.gui.services.QListModel;
import testeditor.gui.test_view.ControlPanel;
import testeditor.parser.GiftParser;
import testeditor.parser.Parser;
import testeditor.question.Question;
import testeditor.saver.GiftSaver;
import testeditor.saver.Saver;
import testeditor.saver.XmlSaver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

/**
 * Класс-слушатель для события открытия файла
 */
public class SaveAsTestAction extends AbstractAction {
    JList qList;

    public SaveAsTestAction(JList qList) {
        this.qList = qList;

        this.putValue(Action.NAME,"Сохранить как...");
        this.putValue(Action.SHORT_DESCRIPTION,"Сохранить тест как...");
        URL imageURL = getClass().getResource("/testeditor/gui/img/save.png");
        this.putValue(Action.SMALL_ICON, new ImageIcon(imageURL));
    }

    public void actionPerformed(ActionEvent event) {
        JFileChooser saveAsDialog = new JFileChooser(); // объект диалогового окна

        //------- Настраиваем диалоговое окно -------//
        saveAsDialog.setCurrentDirectory(new File(".")); //корневая дирректория по умолчанию
        saveAsDialog.setAcceptAllFileFilterUsed(false); //убираем в фильтрах "All files"
        saveAsDialog.addChoosableFileFilter(new FileNameExtensionFilter("Все поддерживаемые форматы (*.gift, *.xml)","gift","xml"));
        saveAsDialog.addChoosableFileFilter(new FileNameExtensionFilter("GIFT Moodle test (*.gift)","gift"));
        saveAsDialog.addChoosableFileFilter(new FileNameExtensionFilter("XML Moodle test (*.xml)","xml"));
        saveAsDialog.setDialogTitle("Сохранить как...");
        saveAsDialog.setApproveButtonToolTipText("Сохранить тест");

        UIManager.put("FileChooser.cancelButtonText"       , "Отмена");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отмена");
        SwingUtilities.updateComponentTreeUI(saveAsDialog);

        MainFrame parentFrame = (MainFrame) SwingUtilities.getRoot((Component)event.getSource());

        //------- Обрабатываем файл теста -------//
        int result = saveAsDialog.showDialog(parentFrame, "Сохранить");
        if (result == JFileChooser.APPROVE_OPTION) {
            Saver s;
            String path = saveAsDialog.getSelectedFile().getAbsolutePath();
            try {
                if (path.toLowerCase().endsWith(".gift")) {
                    s = new GiftSaver(path);
                } else if (path.toLowerCase().endsWith(".xml")) {
                    s = new XmlSaver(path);
                } else {
                    throw new Exception("Выбранное разрешение не соответствует поддерживаемым форматам файлов");
                }
                s.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Test.getTestFromFile(path);
            ((QListModel) qList.getModel()).update(qList);
            qList.setSelectedIndex(0);

            parentFrame.setTitle(saveAsDialog.getSelectedFile().getName() + " - " + parentFrame.APP_NAME);

            ControlPanel cp = (ControlPanel)((Component)event.getSource()).getParent();
            cp.getSaveButton().setEnabled(true);
        }
    }
}