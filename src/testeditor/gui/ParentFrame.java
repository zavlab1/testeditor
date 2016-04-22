package testeditor.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SERGEY on 01.04.2016.
 */
public class ParentFrame extends JFrame {

    public final String APP_NAME = "Test editor";
    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;
    public final int DEFAULT_WIDTH = 600;
    public final int DEFAULT_HEIGHT = 600;

    protected int initialWidth;
    protected int initialHeight;

    public ParentFrame() {
        // определяем размер экрана
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        SCREEN_WIDTH = screenSize.width;
        SCREEN_HEIGHT = screenSize.height;

        setIconImage(new ImageIcon("src/testeditor/gui/img/main.png").getImage());
        setTitle(APP_NAME);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    //------- Устанавливаем расположение и размер окна -------//
    public void setSize(int initialWidth, int initialHeight) {
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        super.setSize(initialWidth, initialHeight);
        setLocation((SCREEN_WIDTH  - initialWidth ) / 2,
                    (SCREEN_HEIGHT - initialHeight) / 2);
    }
}
