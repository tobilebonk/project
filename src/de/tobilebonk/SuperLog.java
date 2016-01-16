package de.tobilebonk;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.util.*;

/**
 * Created by Dappsen on 16.01.2016.
 */
public class SuperLog extends SimpleListProperty<Text>{

    ListProperty<Text> logEntryList = new SimpleListProperty<Text>(javafx.collections.FXCollections.observableList(new ArrayList<Text>()));

    public void addLogEntry(String logEntry){
        logEntryList.add(new Text(logEntry + "\n"));
    }

    public ObservableList<Text> getLogEntryList() {
        return logEntryList.get();
    }

    public ListProperty<Text> logEntryListProperty() {
        return logEntryList;
    }

    public void setLogEntryList(ObservableList<Text> logEntryList) {
        this.logEntryList.set(logEntryList);
    }
}
