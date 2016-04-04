package testeditor.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SERGEY on 01.04.2016.
 */
public class ParentFrame extends JFrame {
    public final int INITIAL_WIDTH;
    public final int INITIAL_HEIGHT;

    public ParentFrame() {
        setIconImage(new ImageIcon("src/testeditor/gui/img/main.png").getImage());
        setTitle("TestEditor");
        // определяем размер экрана
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        INITIAL_WIDTH = (int) (width / 1.5); //размер окна
        INITIAL_HEIGHT = (int) (height / 1.5);//по умолчанию

        //------- Устанавливаем расположение и размер окна -------//
        this.setLocation(width / 6, height / 6);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
    }

}
