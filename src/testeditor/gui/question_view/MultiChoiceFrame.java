package testeditor.gui.question_view;

import testeditor.gui.services.GBC;
import testeditor.gui.services.QTextArea;
import testeditor.gui.services.exceptions.SaveQuestionException;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
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

    public MultiChoiceFrame(Question q) {
        super(q);

        GridBagLayout gbl = new GridBagLayout();
        answers.setLayout(gbl);

        String [] titles = {
            "Верно/<br>Неверно",
            "Вариант ответа",
            "Комментарий",
            "Веc, %",
            "Удалить"
        };
        addTitles(titles);

        answers.add(new JSeparator(), new GBC(0, 1, 9, 1, 0, 0, 0, 0).setFill(GBC.BOTH).setInsets(0, 0, 5, 0));

        aList = q.getAnswerList();

        addAnswers();
        checkAnswers();

        JButton addButton = new JButton("<html><font color='green' size=+1>&nbsp;<b>&#10010;&nbsp;</b></font>Добавить&nbsp;</html>");

        answerPanel.add(answers, BorderLayout.NORTH);

        JPanel addButtonPanel = new JPanel(new FlowLayout());
        addButtonPanel.add(addButton);

        aCount = aList.size();
        addButton.addActionListener(e -> {
                                            addAnswer(aCount*2+1, "", "", 0);
                                            answers.add(new JSeparator(), new GBC(0, aCount*2+2, 9, 1, 0, 0, 0, 0).setFill(GBC.BOTH).setInsets(0, 0, 5, 0));
                                            aScrollPane.getViewport()
                                                       .setViewPosition(
                                                               new Point(aScrollPane.getX(),
                                                                       aScrollPane.getHeight()));
                                            answers.updateUI();
                                            aCount++;
                                            checkAnswers();
                                          });

        answerPanel.add(addButtonPanel, BorderLayout.CENTER);
    }

    private void addTitles(String[] titles) {
        for (int colNum=0; colNum < titles.length; colNum++) {
            answers.add(new JLabel("<html><p><b>" + titles[colNum] + "</b></p></html>"),
                    new GBC(colNum+colNum, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.HORIZONTAL).setInsets(0, 5, 0, 5));
            if (colNum+1 != titles.length) {
                answers.add(new JSeparator(JSeparator.VERTICAL),
                        new GBC(colNum + colNum + 1, 0, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));
            }
        }
    }

    private void addAnswers() {
        for(int i=0; i < aList.size(); i++) {
            addAnswer(i+i+2, aList.get(i).getAText(), aList.get(i).getAComment(), aList.get(i).getDegree());
            answers.add(new JSeparator(), new GBC(0, i+i+3, 9, 1, 0, 0, 0, 0).setFill(GBC.BOTH).setInsets(0, 0, 5, 0));
        }
    }

    private void addAnswer (int pos, String text, String comment, int degree){
        JCheckBox check = new JCheckBox();
        check.setSelected(degree > 0);
        checkBoxList.add(check);

        check.addChangeListener(event -> checkAnswers());

        answers.add(check, new GBC(0, pos, 1, 1, 0, 0, 0, 0).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(1, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        QTextArea answerText = new QTextArea(text);
        answerText.setPreferredSize(new Dimension (0, 70));

        fields.add(answerText);
        answers.add(answerText, new GBC(2, pos, 1, 1, 0, 0, 100, 100).setFill(GBC.BOTH).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(3, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        QTextArea commentText = new QTextArea(comment);
        answers.add(commentText, new GBC(4, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.BOTH).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(5, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(0, 0, 100, 1);

        JSpinner degreeSpinner = new JSpinner(spinnerNumberModel);
        degreeSpinner.setValue(degree);
        spinnerList.add(degreeSpinner);
        degreeSpinner.setEnabled(degree > 0);

        degreeSpinner.addChangeListener(event -> {
            hintLabel.info(DEFAULT_MESSAGE);
            getSaveButton().setEnabled(true);

            int sumDegree = spinnerList.stream().mapToInt(x -> x.isEnabled() ? (int)x.getValue() : 0).sum();
            if (sumDegree != 100) {
                hintLabel.warning("Сумма весов правильных вариантов ответа не равна 100%! " +
                                  "Пожалуйста, проверьте вес каждого варианта!");
                getSaveButton().setEnabled(false);
                spinnerList.stream().filter(s -> isEnabled()).forEach(s -> s.getEditor().getComponent(0).setForeground(Color.RED));
            } else if ((int)degreeSpinner.getValue() == 0) {
                hintLabel.warning("Правильный ответ не может иметь вес, равный 0");
                getSaveButton().setEnabled(false);
                spinnerList.stream().filter(s -> isEnabled()).forEach(s -> s.getEditor().getComponent(0).setForeground(Color.RED));
            } else {
                hintLabel.info("Вы можете добавлять новые, изменять или удалять имеющиеся варианты ответа.<br>" +
                                  "Сумма весов правильных вариантов ответа должна быть равна 100%");
                spinnerList.stream().filter(s -> isEnabled()).forEach(s -> s.getEditor().getComponent(0).setForeground(Color.BLACK));
            }
        });

        answers.add(degreeSpinner, new GBC(6, pos, 1, 1, 0, 0, 0, 0).setAnchor(GBC.BASELINE).setFill(GBC.HORIZONTAL).setInsets(5, 5, 5, 5));
        answers.add(new JSeparator(JSeparator.VERTICAL), new GBC(7, pos, 1, 1, 0, 0, 0, 0).setFill(GBC.VERTICAL));

        JButton delButton = new JButton("<html><font color='red'><b>&nbsp;&#10006;&nbsp;</b></font></html>");
        delButton.addActionListener(e -> deleteAnswer(answers.getComponentZOrder(delButton)));
        answers.add(delButton, new GBC(8, pos, 1, 1, 0, 0, 0, 0).setAnchor(GBC.BASELINE).setInsets(5, 10, 5, 5));

    }

    private void deleteAnswer (int delButtonIndex){
        for(int i=-1; i < getColsNumber(); i++) {
            answers.remove(delButtonIndex - i);
        }
        updateCheckBoxAndSpinners();
        answers.updateUI();
        aCount--;
        checkAnswers();
    }

    private void updateCheckBoxAndSpinners() {
        checkBoxList.clear();
        spinnerList.clear();
        for (int i=0; i < answers.getComponentCount(); i++) {
            JComponent comp = (JComponent) answers.getComponent(i);
            if (comp instanceof JCheckBox) {
                checkBoxList.add((JCheckBox) comp);
            } else if (comp instanceof JSpinner) {
                spinnerList.add((JSpinner) comp);
            }
        }
    }

    private void checkAnswers() {
        int countSelected = 0;
        for (JCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                countSelected += 1;
            }
        }
        getSaveButton().setEnabled(true);
        hintLabel.info(DEFAULT_MESSAGE);
        if (countSelected < 2) {
            spinnerList.stream().forEach(s -> s.setEnabled(false));
            if (countSelected == 0) {
                getSaveButton().setEnabled(false);
                hintLabel.warning("Хотя бы один вариант ответа должен быть отмечен, как правильный");
            }
        } else {
            if (checkBoxList.stream().allMatch(JCheckBox::isSelected)) {
                getSaveButton().setEnabled(false);
                hintLabel.warning("Все варианты ответа не могут быть правильными");
            }
            for (int i = 0; i < spinnerList.size(); i++) {
                JCheckBox cb = checkBoxList.get(i);
                JSpinner sp = spinnerList.get(i);
                if (cb.isSelected()) {
                    sp.setEnabled(true);
                    if ((int) sp.getValue() == 0) {
                        getSaveButton().setEnabled(false);
                        hintLabel.warning("Правильный ответ не может иметь вес, равный 0");
                        spinnerList.stream().filter(s -> isEnabled()).forEach(s -> s.getEditor().getComponent(0).setForeground(Color.RED));
                    }
                } else {
                    sp.setEnabled(false);
                }
            }
        }
    }

    protected List<Answer> collectAnswers() throws SaveQuestionException {
        List<Answer> aList = new ArrayList<>();
        int cols = getColsNumber();
        int rows = getRowsNumber();
        int compsNumber = 0;
        for (int i=0; i < rows; i++) {
            if (compsNumber == answers.getComponentCount()) {
                break;
            }
            if (answers.getComponent(compsNumber) instanceof JSeparator) {
                compsNumber++;
                continue;
            }
            String text ="";
            int degree = Answer.MIN_DEGREE;
            String comment = "";
            int textCompCount = 0;

            for (int j=0; j < cols; j++, compsNumber++) {
                Component comp = answers.getComponent(compsNumber);
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

        if (aList.isEmpty())
            throw new SaveQuestionException("Нет ни одного заполненного варианта ответа");
        if (aList.stream().noneMatch(answer -> answer.getDegree() > 0)) {
            throw new SaveQuestionException("Среди заполненных вариантов ответа нет ни одного правильного");
        }
        if (aList.size() == 1)
            throw new SaveQuestionException("Для этого типа вопроса не допустим только один вариант ответа");

        return  aList;
    }

    private int getColsNumber() {
        GridBagLayout gbl = (GridBagLayout) answers.getLayout();
        int[][] dim = gbl.getLayoutDimensions();
        return dim[0].length;
    }
    private int getRowsNumber() {
        GridBagLayout gbl = (GridBagLayout) answers.getLayout();
        int[][] dim = gbl.getLayoutDimensions();
        return dim[1].length;
    }
}