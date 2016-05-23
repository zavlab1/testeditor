package testeditor.gui.question_view.actions;

import testeditor.question.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by main on 23.05.16.
 */
abstract public class MoveAction extends AbstractAction {
	protected String button_title;
	protected String where;
	protected String icon;
	protected JList<Question> list;

	public MoveAction(JList<Question> qList, String title, String html_icon) {
		list = qList;
		button_title = title;
		where = button_title.substring(0, 1).toLowerCase() + button_title.substring(1);
		icon = html_icon;

		this.putValue(Action.NAME,
			"<html>" +
				"<b><font color='#4682B4' size=+1>" + icon + "&nbsp;&nbsp;&nbsp;</font></b>" +
				button_title +
			"</html>");

		this.putValue(Action.SHORT_DESCRIPTION, "Переместить " + where);
	}

	@Override
	abstract public void actionPerformed(ActionEvent event);

	protected void swapElements(int pos1, int pos2) {
		DefaultListModel<Question> listModel = (DefaultListModel)list.getModel();
		Question tmp = listModel.getElementAt(pos1);
		listModel.set(pos1, listModel.get(pos2));
		listModel.set(pos2, tmp);
	}
}
