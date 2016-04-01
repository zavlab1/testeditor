package testeditor.gui.services;

import java.awt.*;

/**
 * Вспомогательный класс для сеточно-контейнерной компоновки
 */
public class GBC extends GridBagConstraints {
	public GBC(int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, double weightx, double weighty){
		this.gridx = gridx;//x
		this.gridy = gridy;//y
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
		this.ipadx = ipadx;//внутренний отступ
		this.ipady = ipady;
		this.weightx = weightx;
		this.weighty = weighty;
	}

	public GBC setFill(int fill){
		this.fill = fill;
		return this;
	}

	public GBC setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}

	public GBC setInsets (int top, int right, int bottom, int left){
		this.insets = new Insets(top, left, bottom, right);
		return this;
	}
}
