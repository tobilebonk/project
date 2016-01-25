package de.tobilebonk;

import de.tobilebonk.nucleotide3D.*;
import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.nucleotide3D.Residue;
import de.tobilebonk.utils.Comparators;
import de.tobilebonk.utils.ComputationUtils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;


/**
 * Created by Dappsen on 10.01.2016.
 */
public class Model {

    private ListProperty<Residue> dummyResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));
    private ListProperty<Residue> adenineResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));
    private ListProperty<Residue> cytosinResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));
    private ListProperty<Residue> guanineResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));
    private ListProperty<Residue> uracilResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));

    private ListProperty<Residue> allResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));
    private ListProperty<Residue> allNonDummyResidues = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Residue>()));

    public Model(PdbReader reader){

        adenineResidues.addAll(reader.getResiduesOfType(ResiduumType.A));
        cytosinResidues.addAll(reader.getResiduesOfType(ResiduumType.C));
        guanineResidues.addAll(reader.getResiduesOfType(ResiduumType.G));
        uracilResidues.addAll(reader.getResiduesOfType(ResiduumType.U));
        dummyResidues.addAll(reader.getResiduesOfType(ResiduumType._));

        allNonDummyResidues.addAll(adenineResidues);
        allNonDummyResidues.addAll(cytosinResidues);
        allNonDummyResidues.addAll(guanineResidues);
        allNonDummyResidues.addAll(uracilResidues);
        Collections.sort(allNonDummyResidues, Comparators.residueSequenceIdComparator());

        allResidues.addAll(allNonDummyResidues);
        allResidues.addAll(dummyResidues);
        Collections.sort(allResidues, Comparators.residueSequenceIdComparator());
    }

    public ObservableList<Residue> getAllResidues() {
        return allResidues.get();
    }

    public ListProperty<Residue> allResiduesProperty() {
        return allResidues;
    }

    public void setAllResidues(ObservableList<Residue> allResidues) {
        this.allResidues.set(allResidues);
    }

    public void setResidues(List<Residue> residues){
        this.allResidues.set(new SimpleListProperty<Residue>(FXCollections.observableList(residues)));
    }

    public ObservableList<Residue> getAdenineResidues() {
        return adenineResidues.get();
    }

    public ListProperty<Residue> adenineRes0iduesProperty() {
        return adenineResidues;
    }

    public void setAdenineResidues(ObservableList<Residue> adenineResidues) {
        this.adenineResidues.set(adenineResidues);
    }

    public ObservableList<Residue> getCytosinResidues() {
        return cytosinResidues.get();
    }

    public ListProperty<Residue> cytosinResiduesProperty() {
        return cytosinResidues;
    }

    public void setCytosinResidues(ObservableList<Residue> cytosinResidues) {
        this.cytosinResidues.set(cytosinResidues);
    }

    public ObservableList<Residue> getGuanineResidues() {
        return guanineResidues.get();
    }

    public ListProperty<Residue> guanineResiduesProperty() {
        return guanineResidues;
    }

    public void setGuanineResidues(ObservableList<Residue> guanineResidues) {
        this.guanineResidues.set(guanineResidues);
    }

    public ObservableList<Residue> getUracilResidues() {
        return uracilResidues.get();
    }

    public ListProperty<Residue> uracilResiduesProperty() {
        return uracilResidues;
    }

    public void setUracilResidues(ObservableList<Residue> uracilResidues) {
        this.uracilResidues.set(uracilResidues);
    }


    public ObservableList<Residue> getDummyResidues() {
        return dummyResidues.get();
    }

    public ListProperty<Residue> dummyResiduesProperty() {
        return dummyResidues;
    }

    public void setDummyResidues(ObservableList<Residue> dummyResidues) {
        this.dummyResidues.set(dummyResidues);
    }

    public ObservableList<Residue> getAllNonDummyResidues() {
        return allNonDummyResidues.get();
    }

    public ListProperty<Residue> allNonDummyResiduesProperty() {
        return allNonDummyResidues;
    }

    public void setAllNonDummyResidues(ObservableList<Residue> allNonDummyResidues) {
        this.allNonDummyResidues.set(allNonDummyResidues);
    }

    public ObservableList<Residue> getResiduesOfType(ResiduumType type) {

        assert (type != ResiduumType._);
        switch (type) {
            case A: {
                return adenineResidues;
            }
            case C: {
                return cytosinResidues;
            }
            case G: {
                return guanineResidues;
            }
            case U: {
                return uracilResidues;
            }
        }
        // will not be reached, but is necessary for language reasons
        return null;
    }

}
