package testeditor.gui.question_view;

import testeditor.gui.ParentFrame;
import testeditor.gui.services.HintLabel;
import testeditor.gui.services.QLabel;
import testeditor.gui.services.QTextArea;
import testeditor.gui.services.exceptions.SaveQuestionException;
import testeditor.question.Answer;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

/**
 * Created by dimitry on 03.04.16.
 */
abstract public class QuestionFrame extends ParentFrame {
    protected JPanel answerPanel  = new JPanel();
    protected ArrayList<JTextComponent> fields = new ArrayList<>();
    protected JScrollPane aScrollPane;
    protected Question q;
    protected HintLabel hintLabel;
    private QTextArea nameTextArea;
    private QTextArea qTextArea;
    private JButton saveButton;
    public final String DEFAULT_MESSAGE = "Вы можете добавлять новые, изменять или удалять имеющиеся варианты ответа";

    public QuestionFrame(Question q) {
        this.q = q;
        setSize((int)(INITIAL_WIDTH / 1.5), INITIAL_HEIGHT);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout(30, 30));

        JPanel north = new JPanel();
        GroupLayout northLayout = new GroupLayout(north);
        north.setLayout(northLayout);
        northLayout.setAutoCreateContainerGaps(true);
        northLayout.setAutoCreateGaps(true);
        TitledBorder northBorder = BorderFactory.createTitledBorder("Тип вопроса: " + q.TYPE);
        northBorder.setTitleJustification(TitledBorder.CENTER);
        north.setBorder(northBorder);

        QLabel labelName = new QLabel("<html><b>Название:</b></html>");
        QLabel labelQuestion = new QLabel("<html><b>Вопрос:</b></html>");
        nameTextArea = new QTextArea(q.getQName());
        qTextArea = new QTextArea(q.getQText());
        fields.add(nameTextArea);
        fields.add(qTextArea);

        northLayout.setHorizontalGroup(northLayout.createSequentialGroup()
                .addGroup(northLayout.createParallelGroup(LEADING)
                        .addComponent(labelName)
                        .addComponent(labelQuestion))
                .addGroup(northLayout.createParallelGroup(LEADING)
                        .addComponent(nameTextArea)
                        .addComponent(qTextArea))
        );

        northLayout.linkSize(SwingConstants.HORIZONTAL, labelName, labelQuestion);

        northLayout.setVerticalGroup(northLayout.createSequentialGroup()
                .addGroup(northLayout.createParallelGroup(BASELINE)
                        .addComponent(labelName)
                        .addComponent(nameTextArea))
                .addGroup(northLayout.createParallelGroup(BASELINE)
                        .addComponent(labelQuestion)
                        .addComponent(qTextArea))
        );

        add(north, BorderLayout.NORTH);

        answerPanel.setLayout(new BorderLayout(10, 10));

        aScrollPane = new JScrollPane(answerPanel);
        aScrollPane.setViewportBorder(null);
        aScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        TitledBorder aScrollPaneBorder = BorderFactory.createTitledBorder("Варианты ответа");
        aScrollPaneBorder.setTitleJustification(TitledBorder.CENTER);
        aScrollPane.setBorder(aScrollPaneBorder);
        add(aScrollPane);

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 10));

        saveButton = new JButton("Сохранить", new ImageIcon("src/testeditor/gui/img/save.png"));
        saveButton.addActionListener(e -> saveQuestion());

        JButton cancelButton = new JButton("Отмена", UIManager.getIcon("FileChooser.cancelIcon"));
        cancelButton.addActionListener(e -> this.setVisible(false));

        savePanel.add(saveButton);
        savePanel.add(cancelButton);

        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

        hintLabel = new HintLabel();
        hintPanel.add(hintLabel);
        hintPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                hintLabel.setSize(hintPanel.getWidth()-10, hintLabel.getHeight());
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(savePanel, BorderLayout.NORTH);
        bottomPanel.add(hintPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void saveQuestion() {
        try {
            String name = nameTextArea.getText();
            String text = qTextArea.getText();
            List<Answer> aList = collectAnswers();
            aList.removeIf(a -> a.getAText().isEmpty());

            if (name.isEmpty()) {
                name = "";
            }
            if (text.isEmpty()) {
                throw new SaveQuestionException("Поле \"Название\" должно быть заполнено");
            }
            if (aList.isEmpty()) {
                throw new SaveQuestionException("Вопрос должен иметь хотя бы один вариант ответа");
            }
            q.setQName(name);
            q.setQText(text);
            q.setAnswers(aList);
        } catch (SaveQuestionException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Ошибка!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.dispose();
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    abstract protected List<Answer> collectAnswers() throws SaveQuestionException;
}
