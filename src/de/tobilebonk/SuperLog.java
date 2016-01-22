package de.tobilebonk;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Dappsen on 16.01.2016.
 */
public class SuperLog {

    ListProperty<Text> logEntryList = new SimpleListProperty<Text>(FXCollections.observableList(new ArrayList<Text>()));

    public void addLogEntry(String logEntry){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Text logText = new Text(timestamp.toString().substring(0, timestamp.toString().lastIndexOf(":")) + ": LOG:    " + logEntry + "\n");
        logText.setFont(Font.font(java.awt.Font.MONOSPACED));
        logEntryList.add(logText);
    }

    public void addWarningEntry(String warning){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Text warningText = new Text(timestamp.toString().substring(0, timestamp.toString().lastIndexOf(":")) + " WARNING: " + warning + "\n");
        warningText.setFill(Color.CRIMSON);
        warningText.setFont(Font.font(java.awt.Font.MONOSPACED));
        logEntryList.add(warningText);
    }

    public void addInfoEntry(String info){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Text infoText = new Text(timestamp.toString().substring(0, timestamp.toString().lastIndexOf(":")) + " INFO:    " + info + "\n");
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
