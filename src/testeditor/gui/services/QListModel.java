package testeditor.gui.services;

import testeditor.Test;
import testeditor.question.Question;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.*;


/**
 * Created by dimitry on 10.04.16.
 */
public class QListModel extends AbstractListModel<Question> implements DocumentListener {

    private List<Question> defaultList;
    private List<Question> filteredList;

    private String lastFilter = "";

    public QListModel(){
        defaultList  = new ArrayList<>();
        filteredList = new ArrayList<>();
    }

    public void addElement (Question question){
        defaultList.add(question);
        filter(lastFilter);
        //fireIntervalAdded(this, defaultList.size(), defaultList.size());
    }

    public int getSize(){
        return filteredList.size();
    }

    public Question getElementAt (int index){
        Question returnValue;
        if (index < filteredList.size()) {
            returnValue = filteredList.get(index);
        } else {
            returnValue = null;
        }
        return returnValue;
    }

    public void clear (){
        defaultList.clear();
        filteredList.clear();
    }

    public boolean isEmpty(){
       return defaultList.isEmpty();
    }

    public Enumeration<Question> elements() {
        //return defaultList;
    }
    public void filter (String search){
        filteredList.clear();

        for (Question question: defaultList){
            if (question.getQHead().toLowerCase().indexOf(search.toLowerCase(), 0) != -1)
                filteredList.add(question);
        }
        fireContentsChanged(this, 0, getSize());
    }

    // Реализация Document Listener: методы insertUpdate, removeUpdate, changedUpdated
    public void insertUpdate (DocumentEvent event){
        Document document = event.getDocument();
        try{
            lastFilter = document.getText(0, document.getLength());
            filter(lastFilter);
        }
        catch (BadLocationException ble){
            ble.printStackTrace();
        }
    }

    public void removeUpdate (DocumentEvent event){
        Document document = event.getDocument();
        try{
            lastFilter = document.getText(0, document.getLength());
            filter(lastFilter);
        }
        catch (BadLocationException ble){
            ble.printStackTrace();
        }
    }

    public void changedUpdate (DocumentEvent event){}


    public void update(JList<Question> list) {

        try {
            ListDataListener[] listeners = this.getListDataListeners();

            //гасим обработку событий и работаем со списком. Потом снова включаем обработку событий
            Arrays.stream(listeners).forEach(this::removeListDataListener);
                this.clear();
            Test.getTest().stream().forEach(this::addElement);
            Arrays.stream(listeners).forEach(this::addListDataListener);

            //генерируем событие добавления и обновляем UI вручную, т.к. во время обновления списка
            //эти события были погашены вместе с остальными
            fireIntervalAdded(list, 0, Test.getTest().size()-1); // Here the event is fired
            list.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}