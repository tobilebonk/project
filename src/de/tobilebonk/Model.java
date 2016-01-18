package de.tobilebonk;

import de.tobilebonk.nucleotide3D.*;
import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
import de.tobilebonk.utils.Comparators;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;


/**
 * Created by Dappsen on 10.01.2016.
 */
public class Model {

    ListProperty<ResiduumType> modeledResidues = new SimpleListProperty<ResiduumType>(FXCollections.observableList(new ArrayList<ResiduumType>()));

    ListProperty<ResiduumAtomsSequenceNumberTriple> allTriples = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<ResiduumAtomsSequenceNumberTriple>()));
    ListProperty<ResiduumAtomsSequenceNumberTriple> adenineTriples = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<ResiduumAtomsSequenceNumberTriple>()));
    ListProperty<ResiduumAtomsSequenceNumberTriple> cytosinTriples = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<ResiduumAtomsSequenceNumberTriple>()));
    ListProperty<ResiduumAtomsSequenceNumberTriple> guanineTriples = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<ResiduumAtomsSequenceNumberTriple>()));
    ListProperty<ResiduumAtomsSequenceNumberTriple> uracilTriples = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<ResiduumAtomsSequenceNumberTriple>()));


    public Model(PdbReader reader){

        reader.getResidues().forEach(residue -> modeledResidues.add(residue));

        adenineTriples.addAll(reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.A));
        cytosinTriples.addAll(reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.C));
        guanineTriples.addAll(reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.G));
        uracilTriples.addAll(reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.U));
        allTriples.addAll(adenineTriples);
        allTriples.addAll(cytosinTriples);
        allTriples.addAll(guanineTriples);
        allTriples.addAll(uracilTriples);
        Collections.sort(allTriples, Comparators.getSequenceIdOnTriplesComparator());
    }

    public ObservableList<ResiduumType> getModeledResidues() {
        return modeledResidues.get();
    }

    public ListProperty<ResiduumType> modeledResiduesProperty() {
        return modeledResidues;
    }

    public void setModeledResidues(ObservableList<ResiduumType> modeledResidues) {
        this.modeledResidues.set(modeledResidues);
    }

    public void setModeledResidues(List<ResiduumType> modeledResidues){
        this.modeledResidues.set(new SimpleListProperty<ResiduumType>(FXCollections.observableList(modeledResidues)));
    }

    public ObservableList<ResiduumAtomsSequenceNumberTriple> getAllTriples() {
        return allTriples.get();
    }

    public ListProperty<ResiduumAtomsSequenceNumberTriple> allTriplesProperty() {
        return allTriples;
    }

    public void setAllTriples(ObservableList<ResiduumAtomsSequenceNumberTriple> allTriples) {
        this.allTriples.set(allTriples);
    }

    public void setTriples(List<ResiduumAtomsSequenceNumberTriple> triples){
        this.allTriples.set(new SimpleListProperty<ResiduumAtomsSequenceNumberTriple>(FXCollections.observableList(triples)));
    }

    public void addToModeledResidues(ResiduumType residue){
        this.modeledResidues.add(residue);
    }

    public ObservableList<ResiduumAtomsSequenceNumberTriple> getAdenineTriples() {
        return adenineTriples.get();
    }

    public ListProperty<ResiduumAtomsSequenceNumberTriple> adenineTriplesProperty() {
        return adenineTriples;
    }

    public void setAdenineTriples(ObservableList<ResiduumAtomsSequenceNumberTriple> adenineTriples) {
        this.adenineTriples.set(adenineTriples);
    }

    public ObservableList<ResiduumAtomsSequenceNumberTriple> getCytosinTriples() {
        return cytosinTriples.get();
    }

    public ListProperty<ResiduumAtomsSequenceNumberTriple> cytosinTriplesProperty() {
        return cytosinTriples;
    }

    public void setCytosinTriples(ObservableList<ResiduumAtomsSequenceNumberTriple> cytosinTriples) {
        this.cytosinTriples.set(cytosinTriples);
    }

    public ObservableList<ResiduumAtomsSequenceNumberTriple> getGuanineTriples() {
        return guanineTriples.get();
    }

    public ListProperty<ResiduumAtomsSequenceNumberTriple> guanineTriplesProperty() {
        return guanineTriples;
    }

    public void setGuanineTriples(ObservableList<ResiduumAtomsSequenceNumberTriple> guanineTriples) {
        this.guanineTriples.set(guanineTriples);
    }

    public ObservableList<ResiduumAtomsSequenceNumberTriple> getUracilTriples() {
        return uracilTriples.get();
    }

    public ListProperty<ResiduumAtomsSequenceNumberTriple> uracilTriplesProperty() {
        return uracilTriples;
    }

    public void setUracilTriples(ObservableList<ResiduumAtomsSequenceNumberTriple> uracilTriples) {
        this.uracilTriples.set(uracilTriples);
    }


}
