package testeditor.gui;

import testeditor.gui.ParentFrame;

import java.awt.*;

/**
 * Created by main on 18.04.16.
 */
public class BaseMainFrame extends ParentFrame {
    public final int INITIAL_WIDTH;
    public final int INITIAL_HEIGHT;
    public final int INITIAL_WIDTH_MIN = 900;
    public final int INITIAL_HEIGHT_MIN = 500;
    public final int WIDTH_MIN = 500;
    public final int HEIGHT_MIN = 300;
    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;

    public BaseMainFrame() {
        // определяем размер экрана
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        SCREEN_WIDTH = screenSize.width;
        SCREEN_HEIGHT = screenSize.height;

        int width  = (int) (SCREEN_WIDTH  / 1.5);
        int height = (int) (SCREEN_HEIGHT / 1.5);
        INITIAL_WIDTH =  width  < INITIAL_WIDTH_MIN  ? INITIAL_WIDTH_MIN  : width;
        INITIAL_HEIGHT = height < INITIAL_HEIGHT_MIN ? INITIAL_HEIGHT_MIN : height;

        setMinimumSize(new Dimension(WIDTH_MIN, HEIGHT_MIN));

        //------- Устанавливаем расположение и размер окна -------//
        setLocation( (SCREEN_WIDTH  - INITIAL_WIDTH ) / 2,
                     (SCREEN_HEIGHT - INITIAL_HEIGHT) / 2);

        setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
    }
}
