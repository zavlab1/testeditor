package testeditor.gui.services;

import javax.swing.*;

/**
 * Created by main on 28.03.16.
 */
public class VerticalButton extends JButton {
	public VerticalButton(Action a) {
		super(a);
		setVerticalTextPosition(AbstractButton.BOTTOM);
		setHorizontalTextPosition(AbstractButton.CENTER);
	}
}
