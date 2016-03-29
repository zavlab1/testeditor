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

        try {
            if (isWindows()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (isMac()) {
                UIManager.setLookAndFeel("com.apple.mrj.swing.MacLookAndFeel");
            } else if (isUnix()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println(e.getClass() + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getClass() + ": " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getClass() + ": " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getClass() + ": " + e.getMessage());
        }
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

    public static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "win" ) >= 0);
    }

    public static boolean isMac(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "mac" ) >= 0);
    }

    public static boolean isUnix (){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "nix") >= 0 || os.indexOf( "nux") >= 0);
    }
}