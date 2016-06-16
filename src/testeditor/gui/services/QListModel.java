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

    private Vector<Question> fullList;
    private List<Question> filteredList;

    private String filter = "";

    public QListModel(){
        fullList  = new Vector<>();
        filteredList = new ArrayList<>();
    }

    public void addElement (Question question){
        fullList.addElement(question);
        filter(filter);
        fireIntervalAdded(this, fullList.size(), fullList.size());
    }

    public void remove (int index){
        Question removed = filteredList.get(index);
        if (removed != null){
            for ( int i = 0; i < fullList.size(); i++ )
                if (removed.equals(fullList.elementAt(i)))
                    fullList.remove(i);
            filteredList.remove(index);
            fireIntervalRemoved(this, index, index);
        }
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
        fullList.clear();
        filteredList.clear();
    }

    public boolean isEmpty(){
       return fullList.isEmpty();
    }

    public Enumeration<Question> elements() {
        return fullList.elements();
    }
    public void filter (String search){
        filteredList.clear();

        for (Question question: fullList){
            if (question.getQHead().toLowerCase().indexOf(search.toLowerCase(), 0) != -1)
                filteredList.add(question);
        }
        fireContentsChanged(this, 0, getSize());
    }

    // Реализация Document Listener: методы insertUpdate, removeUpdate, changedUpdated
    public void insertUpdate (DocumentEvent event){
        Document document = event.getDocument();
        try{
            filter = document.getText(0, document.getLength());
            filter(filter);
        }
        catch (BadLocationException ble){
            ble.printStackTrace();
        }
    }

    public void removeUpdate (DocumentEvent event){
        Document document = event.getDocument();
        try{
            filter = document.getText(0, document.getLength());
            filter(filter);
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
            //обработчики этих событий были погашены вместе с остальными
            fireIntervalAdded(list, 0, Test.getTest().size()-1);
            list.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String getFilter(){
        return filter;
    }
}

