package testeditor.gui.question_view.actions;

import testeditor.gui.services.QListModel;
import testeditor.question.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс-слушатель для события открытия файла
 */
public class CreateQuestionAction extends AbstractAction {

    private JList<Question> list;
    private Question q = null;

    public CreateQuestionAction(JList<Question> qList) {
        list = qList;
        this.putValue (Action.NAME, "<html>" +
                                        "<font color='green' size=+1>" +
                                            "<b>&#10010;&nbsp;&nbsp;&nbsp;</b>" +
                                        "</font>" +
                                        "Создать" +
                                    "</html>"
                       );
        this.putValue(Action.SHORT_DESCRIPTION, "Создать новый тест");
    }

    public void actionPerformed(ActionEvent event) {

        Object[] types = {
                              "Выбор",
                              "Короткий ответ",
                              "Верно/Неверно",
                              "Соответствие",
                              "Числовой"
                         };
        String s = (String)JOptionPane.showInputDialog(
                null,
                "",
                "Выберите тип вопроса:",
                JOptionPane.PLAIN_MESSAGE,
                null,
                types,
                "Выбор");

        if ((s != null) && (s.length() > 0)) {

            switch (s) {
                case "Выбор":
                    q = new MultiChoice();
                    break;
                case "Короткий ответ":
                    q = new ShortAnswer();
                    break;
                case "Верно/Неверно":
                    q = new TrueFalse();
                    break;
                case "Соответствие":
                    q = new Matching();
                    break;
                case "Числовой":
                    q = new Numerical();
                    break;
            }

            JFrame qFrame = q.getFrame();

            qFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent event) {
                    QListModel<Question> listModel = (QListModel<Question>) list.getModel();
                    listModel.addElement(CreateQuestionAction.this.q);
                    list.setSelectedIndex(listModel.getSize()-1);
                }
            });
        }
    }
}
