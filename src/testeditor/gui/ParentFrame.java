package testeditor.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SERGEY on 01.04.2016.
 */
public class ParentFrame extends JFrame {

    public final String APP_NAME = "Test editor";

    public ParentFrame() {
        setIconImage(new ImageIcon("src/testeditor/gui/img/main.png").getImage());
        setTitle(APP_NAME);
    }
}
