package testeditor.gui.services;

import javax.swing.*;

import testeditor.question.*;

import java.awt.*;

/**
 * Created by SERGEY on 23.03.2016.
 */
public class ListRenderer extends JPanel implements ListCellRenderer<Question> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Question> list, Question value, int index, boolean isSelected, boolean cellHasFocus) {

        setLayout(new GridBagLayout());

        setBackground(isSelected ? new Color(252, 252, 252): new Color(230, 230, 230));

        removeAll();

        String qNumber = "Вопрос №" + Integer.toString(index + 1);
        String qText   = colorSelection(list, "<html><p><b>" + value.getQName() + "</b><br>" + value.getQText() + "</p><br></html>");
        String qType   = "<html><p align='right'><b>Тип вопроса:</b><br>" + value.TYPE + "</p><br></html>";

        JLabel labelNumber   = new JLabel(qNumber);
        JLabel labelQuestion = new JLabel(qText  );
        JLabel labelType     = new JLabel(qType  );

        labelNumber  .setFont(new Font("Sans-Serif", Font.BOLD , 12));
        labelQuestion.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        labelType    .setFont(new Font("Sans-Serif", Font.PLAIN, 12));

        add(labelNumber,
            new GBC(0, 0, 1, 1, 0, 0,   0, 0).setFill(GBC.HORIZONTAL)
                                             .setInsets(10, 10, 10, 10));

        add(new JSeparator(JSeparator.VERTICAL),
            new GBC(1, 0, 1, 1, 0, 0,   0, 0).setFill(GBC.VERTICAL));

        add(labelQuestion,
            new GBC(2, 0, 1, 1, 0, 0, 100, 0).setFill(GBC.BOTH)
                                             .setInsets(10, 5, 10, 10));

        add(labelType,
            new GBC(3, 0, 1, 1, 0, 0,   0, 0).setFill(GBC.BOTH)
                                             .setInsets(10, 20, 10, 5));

        JSeparator lineSeparator = new JSeparator();
        lineSeparator.setBorder(BorderFactory.createEmptyBorder());
        add(lineSeparator,
            new GBC(0, 1, 5, 1, 0, 0, 100, 0).setFill(GBC.HORIZONTAL));

        return this;
    }

    /**
     * Выделение искомого текста цветом в имени и тексте вопроса
     * @param jList - фильтруемый список
     * @param qText - заголовок вопроса
     */
    private String colorSelection (JList jList, String qText) {
        // replaceAll не подходит, т.к. не учитывается, что менять надо на тот же регистр, в котором вхождение
        QListModel qListModel = (QListModel)jList.getModel();
        String filter = qListModel.getFilter();
        if ( ! filter.isEmpty() ) {

            String selectionOpened = "<span style='background-color: yellow'>";
            String selectionClosed = "</span>";

            int insertLength = (selectionOpened + filter + selectionClosed).length();
            int filterLength = filter.length();
            String filterLowCase = filter.toLowerCase();

            StringBuffer withSelection = new StringBuffer(qText);

            int index = qText.toLowerCase().indexOf(filterLowCase);

            while (index > -1) {
                withSelection.insert(index + filterLength, selectionClosed);
                withSelection.insert(index, selectionOpened);

                index = withSelection.toString().toLowerCase()
                        .indexOf(filterLowCase,
                                 index + insertLength);
            }

            return withSelection.toString();

        } else {

            return qText;

        }
    }
}
