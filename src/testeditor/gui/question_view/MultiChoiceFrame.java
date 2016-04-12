package testeditor.gui.question_view;

import testeditor.gui.services.GBC;
import testeditor.gui.services.QTextArea;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimitry on 03.04.16.
 */
public class MultiChoiceFrame extends QuestionFrame {
    private List<Answer> aList;
    private List<JCheckBox> checkBoxList = new ArrayList<>();
    private List<JSpinner> spinnerList = new ArrayList<>();
    private JPanel answers = new JPanel();
    private int aCount;
    private int offset = 0;


    public MultiChoiceFrame(Question q) {
        super(q);
        GridBagLayout gbl = new GridBagLayout();
        answers.setLayout(gbl);

        // ------ Загловки для полей ------
        JLabel emptyLabel = new JLabel(); //для одинакового количества виджетов во всех рядах
        answers.add(emptyLabel, new GBC(0, 0, 9, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));
        JLabel trueLabel = new JLabel("<html><p><b>Верно/<br>Неверно</b></p></html>");
        answers.add(trueLabel, new GBC(0, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(1, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));
        JLabel answerLabel = new JLabel("<html><p><b>Вариант ответа<b></p></html>");
        answers.add(answerLabel, new GBC(2, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(3, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));
        JLabel commentLabel = new JLabel("<html><p><b>Комментарий</b></p></html>");
        answers.add(commentLabel, new GBC(4, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(5, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));
        JLabel weightLabel = new JLabel("<html><p><b>Веc, %</b></p></html>");
        answers.add(weightLabel, new GBC(6, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(7, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));
        JLabel delLabel = new JLabel("<html><p><b>Удалить</b></p></html>");
        answers.add(delLabel, new GBC(8, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));

        aList = q.getAnswerList();
        aCount = aList.size();

        for(int i=0; i < aCount; i++) {
            offset++;
            addAnswer(i+1, aList.get(i).getAText(), aList.get(i).getAComment(), aList.get(i).getDegree());
        }

        updateAnswers();

        JButton addButton = new JButton("<html><font color='green' size=+1>&nbsp;<b>&#10010;&nbsp;</b></font>Добавить&nbsp;</html>");

        answerPanel.add(answers, BorderLayout.NORTH);

        JPanel addButtonPanel = new JPanel(new FlowLayout());
        addButtonPanel.add(addButton);
        addButton.addActionListener(e -> {
                                            addAnswer(aCount+1,"","",0);
                                            answers.updateUI();
                                            aCount++;
                                            aScrollPane.setViewportView(answerPanel);
                                            offset++;
                                            updateAnswers();
                                          });

        answerPanel.add(addButtonPanel, BorderLayout.CENTER);
    }

    public void addAnswer (int pos, String text, String comment, int degree){
        answers.add(new JSeparator(), new GBC(0, pos, 9, 1, 0, 0, 0, 0).setFill(GBC.BOTH).setInsets(0, 0, 5, 0));
        JCheckBox check = new JCheckBox();
        check.setSelected(degree > 0);
        checkBoxList.add(check);

        check.addChangeListener(event -> updateAnswers());

        answers.add(check, new GBC(0, pos, 1, 1, 0, 0, 0, 0).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(1, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        QTextArea answerText = new QTextArea(text);
        answerText.setLineWrap(true);
        fields.add(answerText);
        answers.add(answerText, new GBC(2, pos, 1, 1, 0, 0, 100, 0).setFill(GBC.BOTH).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(3, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        QTextArea commentText = new QTextArea(comment);
        commentText.setLineWrap(true);
        answers.add(commentText, new GBC(4, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.BOTH).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(5, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(0, 0, 100, 1);

        JSpinner degreeSpinner = new JSpinner(spinnerNumberModel);
        degreeSpinner.setValue(degree);

        degreeSpinner.addChangeListener(event -> {
            hintLabel.setText(DEFAULT_MESSAGE);
            getSaveButton().setEnabled(true);
            int sumDegree = spinnerList.stream().mapToInt(x -> x.isEnabled() ? (int)x.getValue() : 0).sum();
            if (sumDegree != 100) {
                hintLabel.setText("<html><p><font color='red'><b>Сумма весов правильных вариантов ответа не равна 100%! " +
                                  "Пожалуйста, проверьте вес каждого варианта!</font></b></p></html>");
                getSaveButton().setEnabled(false);
            } else {
                hintLabel.setText("<html><p>Вы можете добавлять новые, изменять или удалять имеющиеся варианты ответа.<br>" +
                                  "Сумма весов правильных вариантов ответа должна быть равна 100%</p></html>");

            }
            if ((int)degreeSpinner.getValue() == 0) {
                hintLabel.setText("<html><p><font color='red'><b>" +
                                      "Правильный ответ не может иметь вес, равный 0" +
                                  "</font></b></p></html>");
                getSaveButton().setEnabled(false);
            }
        });

        spinnerList.add(degreeSpinner);
        answers.add(degreeSpinner, new GBC(6, pos, 1, 1, 0, 0, 0, 0).setAnchor(GBC.BASELINE).setFill(GBC.HORIZONTAL).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(7, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        JButton delButton = new JButton("<html><font color='red'><b>&nbsp;&#10006;&nbsp;</b></font></html>");
        delButton.addActionListener(e -> deleteAnswer(answers.getComponentZOrder(delButton)));
        answers.add(delButton, new GBC(8, pos, 1, 1, 0, 0, 0, 0).setAnchor(GBC.BASELINE).setInsets(5, 10, 5, 5));
    }

    public void deleteAnswer (int delButtonIndex){
        answers.remove(delButtonIndex+1);
        for(int i=0; i < getColsNumber(); i++) {
            answers.remove(delButtonIndex - i);
        }
        answers.updateUI();
        offset--;
    }

    // Обновление панели вариантов, в зависимости от их весов
    public void updateAnswers() {
        int countSelected = 0;

        for (JCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                countSelected += 1;
            }
        }

        getSaveButton().setEnabled(true);
        hintLabel.setText(DEFAULT_MESSAGE);
        if (countSelected < 2) {
            spinnerList.stream().forEach(s -> s.setEnabled(false));
            if (countSelected == 0) {
                getSaveButton().setEnabled(false);
                hintLabel.setText("<html><p><font color='red'><b>" +
                                      "Хотя бы один вариант ответа должен быть отмечен, как правильный" +
                                  "</font></b></p></html>");
            }
        } else {
            for (int i = 0; i < spinnerList.size(); i++) {
                JCheckBox cb = checkBoxList.get(i);
                JSpinner sp = spinnerList.get(i);
                if (cb.isSelected()) {
                    sp.setEnabled(true);
                    if ((int) sp.getValue() == 0) {
                        getSaveButton().setEnabled(false);
                        hintLabel.setText("<html><p><font color='red'><b>" +
                                             "Правильный ответ не может иметь вес, равный 0" +
                                          "</font></b></p></html>");
                    }
                }
            }
        }
    }

    protected List<Answer> collectAnswers() {
        List<Answer> aList = new ArrayList<>();
        int cols = getColsNumber()+1;  //-1 потому что не учитываем последнюю строку (это разделитель)
        int rows = getRowsNumber();
        for (int i=1; i <= rows; i++) {  //начинаем со второго ряда, т.к. первый - заголовки
            String text ="";
            int degree = Answer.MIN_DEGREE;
            String comment = "";
            int textCompCount = 0;
            int index = (i-1)*cols-1; //индекс последнего элемента из предыдущего ряда

            for (int j=1; j <= cols; j++) {
                Component comp = answers.getComponent(index + j);
                if (comp instanceof JTextComponent) {
                    if (textCompCount == 0) {
                        text = ((JTextComponent) comp).getText();
                        textCompCount++;
                    } else {
                        comment = ((JTextComponent) comp).getText();
                    }
                } else if (comp instanceof JSpinner) {
                    if (checkBoxList.get(spinnerList.indexOf(comp)).isSelected()) {
                        if (comp.isEnabled()) {
                            degree = (int)((JSpinner)comp).getValue();
                        } else {
                            degree = Answer.MAX_DEGREE;
                        }
                    }
                }
            }
            if (!text.isEmpty()) {
                aList.add(new Answer(text, degree, comment));
            }
        }
        return aList;
    }

    private int getColsNumber() {
        GridBagLayout gbl = (GridBagLayout)answers.getLayout();
        int[][] dim = gbl.getLayoutDimensions();
        return dim[0].length;
    }
    private int getRowsNumber() {
        GridBagLayout gbl = (GridBagLayout)answers.getLayout();
        int[][] dim = gbl.getLayoutDimensions();
        return dim[1].length;
    }
}