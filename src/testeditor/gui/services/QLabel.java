package testeditor.gui.services;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SERGEY on 04.04.2016.
 */
public class QLabel extends JLabel {
    public QLabel (String s){
        super(s);

        setFont(new Font("Sans-Serif", Font.PLAIN, 12));
    }
}
