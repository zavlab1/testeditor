package testeditor.gui.services;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by SERGEY on 04.04.2016.
 */
public class QTextArea extends JTextArea {
    public QTextArea(String s){
        super(s);

        setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1, true);
        setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
