package testeditor.gui.test_view.actions;

import testeditor.Test;
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
public class SaveTestAction extends AbstractAction {
    private JButton saveButton;

    public SaveTestAction(JButton saveButton) {
        this.saveButton = saveButton;

        this.putValue(Action.NAME,"Сохранить");
        this.putValue(Action.SHORT_DESCRIPTION,"Сохранить тест");
        URL imageURL = getClass().getResource("/testeditor/gui/img/save.png");
        this.putValue(Action.SMALL_ICON, new ImageIcon(imageURL));
    }

    public void actionPerformed(ActionEvent event) {
        String path = Test.getTest().getFilePath();
        try {
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
