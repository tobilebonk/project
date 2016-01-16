package de.tobilebonk;

import de.tobilebonk.nucleotide3D.*;
import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Dappsen on 10.01.2016.
 */
public class Model {

    private SimpleBooleanProperty isShowA = new SimpleBooleanProperty(this, "isShowA", false);
    private SimpleBooleanProperty isShowC = new SimpleBooleanProperty(this, "isShowC", false);
    private SimpleBooleanProperty isShowG = new SimpleBooleanProperty(this, "isShowG", false);
    private SimpleBooleanProperty isShowU = new SimpleBooleanProperty(this, "isShowU", false);
    private SimpleBooleanProperty isDrawPhosphorBonds = new SimpleBooleanProperty(this, "isDrawPhosphorBonds", false);
    private SimpleBooleanProperty isColorA = new SimpleBooleanProperty(this, "isColorA", false);
    private SimpleBooleanProperty isColorC = new SimpleBooleanProperty(this, "isColorC", false);
    private SimpleBooleanProperty isColorG = new SimpleBooleanProperty(this, "isColorG", false);
    private SimpleBooleanProperty isColorU = new SimpleBooleanProperty(this, "isColorU", false);

    private List<Nucleotide3DProperty> adenine3DList = new ArrayList<>();
    private List<Nucleotide3DProperty> cytosin3DList = new ArrayList<>();
    private List<Nucleotide3DProperty> guanine3DList = new ArrayList<>();
    private List<Nucleotide3DProperty> uracil3DList = new ArrayList<>();
    private List<Nucleotide3DProperty> nucleotide3DAllSortedList = new ArrayList<>();

    public Model(PdbReader reader){
        for (ResiduumAtomsSequenceNumberTriple uracilTriple : reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.U)) {
            uracil3DList.add(new Nucleotide3DProperty(new Uracil3D(uracilTriple)));
        }
        for (ResiduumAtomsSequenceNumberTriple cytosinTriple : reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.C)) {
            cytosin3DList.add(new Nucleotide3DProperty(new Cytosine3D(cytosinTriple)));
        }
        for (ResiduumAtomsSequenceNumberTriple adenineTriple : reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.A)) {
            adenine3DList.add(new Nucleotide3DProperty(new Adenine3D(adenineTriple)));
        }
        for (ResiduumAtomsSequenceNumberTriple guanineTriple : reader.getResiduumTypeAtomsSequenceNumberTriple(ResiduumType.G)) {
            guanine3DList.add(new Nucleotide3DProperty(new Guanine3D(guanineTriple)));
        }
        nucleotide3DAllSortedList.addAll(uracil3DList);
        nucleotide3DAllSortedList.addAll(cytosin3DList);
        nucleotide3DAllSortedList.addAll(adenine3DList);
        nucleotide3DAllSortedList.addAll(guanine3DList);
        Collections.sort(nucleotide3DAllSortedList, new SequenceIdComparator());
    }

    public List<Nucleotide3DProperty> getAdenine3DList() {
        return adenine3DList;
    }

    public void setAdenine3DList(List<Nucleotide3DProperty> adenine3DList) {
        this.adenine3DList = adenine3DList;
    }

    public List<Nucleotide3DProperty> getCytosin3DList() {
        return cytosin3DList;
    }

    public void setCytosin3DList(List<Nucleotide3DProperty> cytosin3DList) {
        this.cytosin3DList = cytosin3DList;
    }

    public List<Nucleotide3DProperty> getGuanine3DList() {
        return guanine3DList;
    }

    public void setGuanine3DList(List<Nucleotide3DProperty> guanine3DList) {
        this.guanine3DList = guanine3DList;
    }

    public List<Nucleotide3DProperty> getUracil3DList() {
        return uracil3DList;
    }

    public void setUracil3DList(List<Nucleotide3DProperty> uracil3DList) {
        this.uracil3DList = uracil3DList;
    }

    public List<Nucleotide3DProperty> getNucleotide3DAllSortedList() {
        return nucleotide3DAllSortedList;
    }

    public void setNucleotide3DAllSortedList(List<Nucleotide3DProperty> nucleotide3DAllSortedList) {
        this.nucleotide3DAllSortedList = nucleotide3DAllSortedList;
    }

    public boolean getIsShowA() {
        return isShowA.get();
    }

    public SimpleBooleanProperty isShowAProperty() {
        return isShowA;
    }

    public void setIsShowA(boolean isShowA) {
        this.isShowA.set(isShowA);
    }

    public boolean getIsShowC() {
        return isShowC.get();
    }

    public SimpleBooleanProperty isShowCProperty() {
        return isShowC;
    }

    public void setIsShowC(boolean isShowC) {
        this.isShowC.set(isShowC);
    }

    public boolean getIsShowG() {
        return isShowG.get();
    }

    public SimpleBooleanProperty isShowGProperty() {
        return isShowG;
    }

    public void setIsShowG(boolean isShowG) {
        this.isShowG.set(isShowG);
    }

    public boolean getIsShowU() {
        return isShowU.get();
    }

    public SimpleBooleanProperty isShowUProperty() {
        return isShowU;
    }

    public void setIsShowU(boolean isShowU) {
        this.isShowU.set(isShowU);
    }

    public boolean getIsDrawPhosphorBonds() {
        return isDrawPhosphorBonds.get();
    }

    public SimpleBooleanProperty isDrawPhosphorBondsProperty() {
        return isDrawPhosphorBonds;
    }

    public void setIsDrawPhosphorBonds(boolean isDrawPhosphorBonds) {
        this.isDrawPhosphorBonds.set(isDrawPhosphorBonds);
    }

    public boolean getIsColorA() {
        return isColorA.get();
    }

    public SimpleBooleanProperty isColorAProperty() {
        return isColorA;
    }

    public void setIsColorA(boolean isColorA) {
        this.isColorA.set(isColorA);
    }

    public boolean getIsColorC() {
        return isColorC.get();
    }

    public SimpleBooleanProperty isColorCProperty() {
        return isColorC;
    }

    public void setIsColorC(boolean isColorC) {
        this.isColorC.set(isColorC);
    }

    public boolean getIsColorG() {
        return isColorG.get();
    }

    public SimpleBooleanProperty isColorGProperty() {
        return isColorG;
    }

    public void setIsColorG(boolean isColorG) {
        this.isColorG.set(isColorG);
    }

    public boolean getIsColorU() {
        return isColorU.get();
    }

    public SimpleBooleanProperty isColorUProperty() {
        return isColorU;
    }

    public void setIsColorU(boolean isColorU) {
        this.isColorU.set(isColorU);
    }

    private class SequenceIdComparator implements Comparator<Nucleotide3DProperty> {
        @Override
        public int compare(Nucleotide3DProperty n1, Nucleotide3DProperty n2) {
            int s1 = n1.getValue().getSequenceNumber();
            int s2 = n2.getValue().getSequenceNumber();
            return s1 < s2 ? -1 : s1 == s2 ? 0 : 1;
        }
    }
}
