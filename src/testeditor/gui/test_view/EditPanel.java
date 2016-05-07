package testeditor.gui.test_view;

import testeditor.gui.question_view.actions.CreateQuestionAction;
import testeditor.gui.question_view.actions.EditQuestionAction;
import testeditor.gui.question_view.actions.RemoveQuestionAction;
import testeditor.gui.services.EditPanelButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Панель управления элементами списка
 */
public class EditPanel extends JPanel {
    private JButton editButton, createButton , deleteButton, // Кнопки управления
    beginButton, endButton, upButton, downButton;

    private JList list;          // ссылка на список вопросов
    private JSpinner listSpinner;

    private int max; // макс. кол-во элементов в списке

    private ArrayList<JButton> buttons;

    public EditPanel(JList list) {
        this.list = list;

        max = ((DefaultListModel)list.getModel()).isEmpty() ? 1 : list.getModel().getSize();

        // Экземпляры групп кнопок для редактирования и перемещения
        EditingGroup editingGroup = new EditingGroup();
        MovingGroup movingGroup = new MovingGroup();

        GroupLayout editPanelLayout = new GroupLayout(this); // Групповой компоновщик для EditPanel

        setLayout(editPanelLayout);
        editPanelLayout.setAutoCreateContainerGaps(true);
        editPanelLayout.setAutoCreateGaps(true);

        editPanelLayout.setHorizontalGroup(editPanelLayout.createParallelGroup()
                .addComponent(editingGroup)
                .addComponent(movingGroup)
        );

        editPanelLayout.setVerticalGroup(editPanelLayout.createSequentialGroup()
                .addComponent(editingGroup, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(movingGroup, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );


        /* Редактировать вопрос по двойному клику по элементу списка.
           Размещен здесь, потому что одновременно есть доступ к списку вопросов и
           кнопке "Редактировать",клик на которую имитируется в слушателе.
         */
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) editButton.doClick();
            }
        });
    }

    public JButton getCreateButton() {
        return createButton;
    }
    public JSpinner getListSpinner() {
        return listSpinner;
    }
    public List<JButton> getButtons() {
        return buttons;
    }

    /**
     * Внутренний класс - Группа с кнопками редактирования, создания и удаления вопроса
     */
    public class EditingGroup extends JPanel {
        EditingGroup(){
            setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10),
                    new TitledBorder("Редактировать: ")));

            GroupLayout editingPanelLayout = new GroupLayout(this);

            setLayout(editingPanelLayout);
            editingPanelLayout.setAutoCreateContainerGaps(true);
            editingPanelLayout.setAutoCreateGaps(true);

            buttons = new ArrayList<>();

            editButton = new EditPanelButton(new EditQuestionAction(list));
            buttons.add(editButton);

            createButton = new EditPanelButton(new CreateQuestionAction(list));
            buttons.add(createButton);

            deleteButton = new EditPanelButton(new RemoveQuestionAction(list));
            buttons.add(deleteButton);

            editingPanelLayout.setHorizontalGroup(editingPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(editButton)
                    .addComponent(createButton)
                    .addComponent(deleteButton)
            );

            editingPanelLayout.setVerticalGroup(editingPanelLayout.createSequentialGroup()
                    .addComponent(editButton)
                    .addComponent(createButton)
                    .addComponent(deleteButton)
            );
        }
    }

    /**
     * Внутренний класс - Группа с кнопками перемещения вопроса в тесте
     */
    public class MovingGroup extends JPanel {
        MovingGroup(){
            setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10),
                    new TitledBorder("Переместить: ")));

            GroupLayout movingGroupLayout = new GroupLayout(this);

            setLayout(movingGroupLayout);
            movingGroupLayout.setAutoCreateContainerGaps(true);
            movingGroupLayout.setAutoCreateGaps(true);

            beginButton = new EditPanelButton("<html><font color='#4682B4' size=+1><b>&#9650;&nbsp;&nbsp;&nbsp;</b></font>В начало</html>");
            buttons.add(beginButton);

            upButton = new EditPanelButton("<html><font color='#4682B4' size=+1><b>&#8657;&nbsp;&nbsp;&nbsp;</b></font>Вверх</html>");
            buttons.add(upButton);

            downButton = new EditPanelButton("<html><font color='#4682B4' size=+1><b>&#8659;&nbsp;&nbsp;&nbsp;</b></font>Вниз</html>");
            buttons.add(downButton);

            endButton = new EditPanelButton("<html><font color='#4682B4' size=+1><b>&#9660;&nbsp;&nbsp;&nbsp;</b></font>В конец</html>");
            buttons.add(endButton);

            JLabel spinLabel = new JLabel("<html>Переместить<br>на позицию №:</html>");

            listSpinner = new JSpinner(new SpinnerNumberModel(1, 1, max, 1));

            listSpinner.addChangeListener((ChangeEvent event) -> {
                list.setSelectedIndex(((int)listSpinner.getValue())-1);//выделяем выбранный элемент
                list.ensureIndexIsVisible(list.getSelectedIndex());//делаем выделенный элемент видимым
            });

            movingGroupLayout.setHorizontalGroup(movingGroupLayout.createSequentialGroup()
                    .addGroup(movingGroupLayout.createParallelGroup()
                            .addComponent(beginButton)
                            .addComponent(upButton)
                            .addComponent(downButton)
                            .addComponent(endButton)
                            .addGroup(movingGroupLayout.createSequentialGroup()
                                    .addComponent(spinLabel)
                                    .addComponent(listSpinner))
                    )
            );

            movingGroupLayout.setVerticalGroup(movingGroupLayout.createSequentialGroup()
                    .addComponent(beginButton)
                    .addComponent(upButton)
                    .addComponent(downButton)
                    .addComponent(endButton)
                    .addGroup(movingGroupLayout.createParallelGroup()
                            .addComponent(spinLabel)
                            .addComponent(listSpinner)
                    ));
        }
    }

}