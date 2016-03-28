package testeditor;

import testeditor.gui.*;

import javax.swing.*;
import java.awt.*;


public class TestEditor {
    public static void main(String[] args) {
        /*
        Test test =	Test.getTestFromFile("test.gift");
        Saver s = new GiftSaver(test, "Test1.gift");
        for (Question q : test) {

                s.save(q);

            break; // только для тестирования. Т.к. для сохранения даже одного вопроса файл переписывается полностью,
                   // то нет смысла гонять весь цикл.
        }
        */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame testFrame = new MainFrame();

                testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                testFrame.setTitle("TestEditor");
                testFrame.setVisible(true);
            }
        });

    }
}