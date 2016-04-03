package testeditor.gui.QuestionFrames;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SERGEY on 01.04.2016.
 */
public class ParentFrame extends JFrame {
    public ParentFrame(){
        setIconImage(new ImageIcon("src/testeditor/gui/img/main.png").getImage());
        setTitle("TestEditor");
        setLocationAndSize();
    }

    protected void setLocationAndSize() {
        // определяем размер экрана
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;;
        int frameWidth = (int)(width/1.5); //размер окна
        int frameHeight = (int)(height/1.5);//по умолчанию

        //------- Устанавливаем расположение и размер окна -------//
        this.setLocation(width/6, height/6);
        this.setSize(frameWidth, frameHeight);
    }
}
