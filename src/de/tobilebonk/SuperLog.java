package de.tobilebonk;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.util.*;

/**
 * Created by Dappsen on 16.01.2016.
 */
public class SuperLog extends SimpleListProperty<Text>{

    ListProperty<Text> logEntryList = new SimpleListProperty<Text>(javafx.collections.FXCollections.observableList(new ArrayList<Text>()));

    public void addLogEntry(String logEntry){
        Text logText = new Text("LOG:     " + logEntry + "\n");
        logText.setFont(Font.font(java.awt.Font.MONOSPACED));
        logEntryList.add(logText);
    }

    public void addWarningEntry(String warning){
        Text warningText = new Text("WARNING: " + warning + "\n");
        warningText.setFill(Color.CRIMSON);
        warningText.setFont(Font.font(java.awt.Font.MONOSPACED));
        logEntryList.add(warningText);
    }

    public void addInfoEntry(String info){
        Text infoText = new Text("INFO:    " + info + "\n");
        infoText.setFill(Color.GREEN);
        infoText.setFont(Font.font(java.awt.Font.MONOSPACED));
        logEntryList.add(infoText);
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
