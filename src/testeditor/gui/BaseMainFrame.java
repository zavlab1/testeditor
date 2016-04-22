package testeditor.gui;

import testeditor.gui.ParentFrame;

import java.awt.*;

/**
 * Created by main on 18.04.16.
 */
abstract public class BaseMainFrame extends ParentFrame {
    public final int INITIAL_WIDTH_MIN = 900;
    public final int INITIAL_HEIGHT_MIN = 500;
    public final int WIDTH_MIN = 500;
    public final int HEIGHT_MIN = 300;

    public BaseMainFrame() {

        int width  = (int) (SCREEN_WIDTH  / 1.5);
        int height = (int) (SCREEN_HEIGHT / 1.5);
        int initialWidth =  width  < INITIAL_WIDTH_MIN  ? INITIAL_WIDTH_MIN  : width;
        int initialHeight = height < INITIAL_HEIGHT_MIN ? INITIAL_HEIGHT_MIN : height;

        setMinimumSize(new Dimension(WIDTH_MIN, HEIGHT_MIN));

        //------- Устанавливаем расположение и размер окна -------//
        setSize(initialWidth, initialHeight);
    }
}
