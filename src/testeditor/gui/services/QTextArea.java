package testeditor.gui.services;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
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

    public void changeSize(){
        try {
            Rectangle rect = this.modelToView( this.getDocument().getLength() );
            this.setPreferredSize(new Dimension( 0, rect.y + rect.height + 5) );
        }
        catch (BadLocationException ex){
            ex.getMessage();
        }
    }
}
