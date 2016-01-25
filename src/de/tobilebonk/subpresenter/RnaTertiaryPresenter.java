package de.tobilebonk.subpresenter;

import de.tobilebonk.Model;
import de.tobilebonk.ResiduumSelectionModel;
import de.tobilebonk.SuperLog;
import de.tobilebonk.nucleotide3D.Nucleotide3D;
import de.tobilebonk.nucleotide3D.Residue;
import de.tobilebonk.nucleotide3D.ResiduumType;
import de.tobilebonk.subview.RnaTertiaryView;
import de.tobilebonk.subview.SubView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.*;


/**
 * Created by Dappsen on 10.01.2016.
 */
public class RnaTertiaryPresenter implements Subpresenter {

    private SubView view;
    private Model model;
    private ResiduumSelectionModel<Group> selectionModel;
    private SuperLog log;

    public RnaTertiaryPresenter(Model model, RnaTertiaryView view, ResiduumSelectionModel selectionModel, SuperLog log) {

        this.view = view;
        this.model = model;
        this.selectionModel = selectionModel;
        this.log = log;

        if (model != null) {

            //TODO: center!
            view.setup3DNucleotidesFromNonDummyResidueList(model.getAllNonDummyResidues());


            //show connections between nucleotides
            if (model.getAllResidues().size() > 1) {
                for (int i = 0; i < model.getAllResidues().size() - model.getDummyResidues().size() - 1; ++i) {
                    if(view.getNucleotide3DAllSortedList().get(i).getSequenceNumber() + 1 == view.getNucleotide3DAllSortedList().get(i+1).getSequenceNumber()){
                        view.getNucleotide3DAllSortedList().get(i).connectPhosphorToPhosphorOf(view.getNucleotide3DAllSortedList().get(i + 1));
                        view.getNucleotide3DAllSortedList().get(i).connectSugarToPhosphorOf(view.getNucleotide3DAllSortedList().get(i + 1));
                    }
                }
            }
        }

        // setup selections
        // coloring reacting to selection model
        selectionModel.getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (int i = 0; i < selectionModel.getItems().length; i++) {
                    int position = model.getAllNonDummyResidues().indexOf(model.getAllResidues().get(i));
                    if (position != -1) {
                        if (selectionModel.getSelectedIndices().contains(i)) {
                            view.getNucleotide3DAllSortedList().get(position).setColoring();
                        } else {
                            view.getNucleotide3DAllSortedList().get(position).resetColoring();
                        }
                    }
                }
            }
        });

        // selection logic
        // mouse click
        for (int i = 0; i < model.getAllResidues().size(); i++) {

            final int index = i;
            int position = model.getAllNonDummyResidues().indexOf(model.getAllResidues().get(i));

            if(position != -1) {
                view.getNucleotide3DAllSortedList().get(position).getNucleotideGroup().setOnMouseClicked((e) -> {

                    if (selectionModel.isSelected(index)) {
                        if (!e.isControlDown()) {
                            selectionModel.clearSelection();
                            log.addLogEntry("Cleared Selection");
                        } else {
                            selectionModel.clearSelection(index);
                            log.addLogEntry("Deselected Nucleotide " +
                                    model.getAllResidues().get(index).getSequenceNumber() +
                                    " (" +
                                    model.getAllResidues().get(index).getType() +
                                    ")");
                        }
                    } else {
                        if (!e.isControlDown()) {
                            selectionModel.clearSelection();
                            log.addLogEntry("Cleared Selection");
                        }
                        selectionModel.select(index);
                        log.addLogEntry("Selected Nucleotide " +
                                model.getAllResidues().get(index).getSequenceNumber() +
                                " (" +
                                model.getAllResidues().get(index).getType() +
                                ")");
                    }
                });
            }
        }
        
    }

    @Override
    public SubView getSubView() {
        return this.view;
    }
    @Override
    public void setSubView(SubView view) {
        this.view = view;
    }

}