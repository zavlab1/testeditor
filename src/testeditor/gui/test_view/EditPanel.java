package testeditor.gui.test_view;

import testeditor.gui.question_view.actions.CreateQuestionAction;
import testeditor.gui.question_view.actions.EditQuestionAction;
import testeditor.gui.question_view.actions.MoveQuestionAction;
import testeditor.gui.question_view.actions.RemoveQuestionAction;
import testeditor.gui.services.EditPanelButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Панель управления элементами списка
 */
public class EditPanel extends JPanel {
    private JButton editButton   ,
                    createButton ,
                    deleteButton ,
                    beginButton  ,
                    endButton    ,
                    upButton     ,
                    downButton   ;

    private JList    list;
    private JSpinner listSpinner;

    private int max; // макс. кол-во элементов в списке

    private ArrayList<JButton> buttons;

    public EditPanel(JList list) {
        this.list = list;

        max = ((DefaultListModel)list.getModel()).isEmpty() ? 1 : list.getModel().getSize();

        // Экземпляры групп кнопок для редактирования и перемещения
        EditingGroup editingGroup = new EditingGroup();
        MovingGroup  movingGroup  = new MovingGroup();

        GroupLayout editPanelLayout = new GroupLayout(this); // Групповой компоновщик для EditPanel

        setLayout(editPanelLayout);
        editPanelLayout.setAutoCreateContainerGaps(true);
        editPanelLayout.setAutoCreateGaps(true);

        editPanelLayout.setHorizontalGroup(editPanelLayout.createParallelGroup()
                                                          .addComponent(editingGroup)
                                                          .addComponent(movingGroup)
        );

        editPanelLayout.setVerticalGroup(editPanelLayout.createSequentialGroup()
                                                        .addComponent(editingGroup,
                                                                      GroupLayout.PREFERRED_SIZE,
                                                                      GroupLayout.PREFERRED_SIZE,
                                                                      GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(movingGroup,
                                                                      GroupLayout.PREFERRED_SIZE,
                                                                      GroupLayout.PREFERRED_SIZE,
                                                                      GroupLayout.PREFERRED_SIZE)
        );
    }

    public JButton getCreateButton() {
        return createButton;
    }
    public JButton getEditButton() {
        return editButton;
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
            setBorder(BorderFactory.createCompoundBorder(new EmptyBorder (10, 10, 10, 10),
                                                         new TitledBorder("Редактировать: ")));

            GroupLayout editingPanelLayout = new GroupLayout(this);
            setLayout(editingPanelLayout);

            editingPanelLayout.setAutoCreateContainerGaps(true);
            editingPanelLayout.setAutoCreateGaps(true);

            editButton   = new EditPanelButton(new EditQuestionAction  (list));
            createButton = new EditPanelButton(new CreateQuestionAction(list));
            deleteButton = new EditPanelButton(new RemoveQuestionAction(list));

            buttons = new ArrayList<>();
            buttons.add(editButton);
            buttons.add(createButton);
            buttons.add(deleteButton);

            editingPanelLayout.setHorizontalGroup(
                    editingPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                      .addComponent(editButton)
                                      .addComponent(createButton)
                                      .addComponent(deleteButton)
            );

            editingPanelLayout.setVerticalGroup(
                    editingPanelLayout.createSequentialGroup()
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
            setBorder(BorderFactory.createCompoundBorder(new EmptyBorder (10, 10, 10, 10),
                                                         new TitledBorder("Переместить: ")));

            GroupLayout movingGroupLayout = new GroupLayout(this);

            setLayout(movingGroupLayout);
            movingGroupLayout.setAutoCreateContainerGaps(true);
            movingGroupLayout.setAutoCreateGaps(true);

            beginButton = new EditPanelButton("<html><font color='#4682B4' size=+1><b>&#9650;&nbsp;&nbsp;&nbsp;</b></font>В начало</html>");
            upButton    = new EditPanelButton(new MoveQuestionAction(list, -1));
            downButton  = new EditPanelButton(new MoveQuestionAction(list, 1));
            endButton   = new EditPanelButton("<html><font color='#4682B4' size=+1><b>&#9660;&nbsp;&nbsp;&nbsp;</b></font>В конец </html>");

            buttons.add(beginButton);
            buttons.add(upButton);
            buttons.add(downButton);
            buttons.add(endButton);

            JLabel spinLabel = new JLabel("<html>Переместить<br>на позицию №:</html>");

            listSpinner = new JSpinner(new SpinnerNumberModel(1, 1, max, 1));

            listSpinner.addChangeListener((ChangeEvent event) -> {
                list.setSelectedIndex(((int)listSpinner.getValue())-1);//выделяем выбранный элемент
                list.ensureIndexIsVisible(list.getSelectedIndex());//делаем выделенный элемент видимым
            });

            movingGroupLayout.setHorizontalGroup(
                    movingGroupLayout.createSequentialGroup()
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

            movingGroupLayout.setVerticalGroup(
                    movingGroupLayout.createSequentialGroup()
                                     .addComponent(beginButton)
                                     .addComponent(upButton)
                                     .addComponent(downButton)
                                     .addComponent(endButton)
                                     .addGroup(movingGroupLayout.createParallelGroup()
                                                                .addComponent(spinLabel)
                                                                .addComponent(listSpinner)
                                     )
            );
        }
    }

}