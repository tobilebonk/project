package de.tobilebonk.subpresenter;

import de.tobilebonk.Model;
import de.tobilebonk.ResiduumSelectionModel;
import de.tobilebonk.SuperLog;
import de.tobilebonk.nucleotide3D.Nucleotide3DTemplate;
import de.tobilebonk.subview.RnaTertiaryView;
import de.tobilebonk.subview.SubView;
import de.tobilebonk.utils.SelectionUtils;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;


/**
 * Created by Dappsen on 10.01.2016.
 */
public class RnaTertiaryPresenter implements Subpresenter {

    private SubView view;
    ;
    private Model model;
    private ResiduumSelectionModel<Group> selectionModel;
    private SuperLog log;

    public RnaTertiaryPresenter(Model model, RnaTertiaryView view, ResiduumSelectionModel selectionModel, SuperLog log) {

        this.view = view;
        this.model = model;
        this.selectionModel = selectionModel;
        this.log = log;

        //show all nucleotides:
        if (model != null) {
            model.getNucleotide3DAllSortedList().forEach(n -> view.add3DNucleotide(n.getValue()));
        }

        //show connections between nucleotides
        if (model.getNucleotide3DAllSortedList().size() > 1) {
            for (int i = 0; i < model.getNucleotide3DAllSortedList().size() - 1; ++i) {
                model.getNucleotide3DAllSortedList().get(i).getValue()
                        .connectPhosphorToPhosphorOf(model.getNucleotide3DAllSortedList().get(i + 1).getValue());
                model.getNucleotide3DAllSortedList().get(i).getValue()
                        .connectSugarToPhosphorOf(model.getNucleotide3DAllSortedList().get(i + 1).getValue());
            }
        }

        // setup selections

        selectionModel.getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (int i = 0; i < selectionModel.getItems().length; i++) {
                    if (selectionModel.getSelectedIndices().contains(i)) {
                        model.getNucleotide3DAllSortedList().get(i).getValue().setColoring();
                    } else {
                        model.getNucleotide3DAllSortedList().get(i).getValue().resetColoring();
                    }
                }
            }
        });
        for (int i = 0; i < model.getNucleotide3DAllSortedList().size(); i++) {

            final int index = i;

            model.getNucleotide3DAllSortedList().get(index).getValue().getNucleotideGroup().setOnMouseClicked((e) -> {

                if (selectionModel.isSelected(index)) {
                    if(!e.isAltDown()){
                        selectionModel.clearSelection();
                        log.addInfoEntry("Cleared Selection");
                    }else {
                        selectionModel.clearSelection(index);
                        log.addInfoEntry("Deselected Nucleotide " +
                                model.getNucleotide3DAllSortedList().get(index).getValue().getSequenceNumber() +
                                " (" +
                                model.getNucleotide3DAllSortedList().get(index).getValue().getType() +
                                ")");
                    }
                } else {
                    if(!e.isAltDown()){
                        selectionModel.clearSelection();
                        log.addInfoEntry("Cleared Selection");
                    }
                    selectionModel.select(index);
                    log.addInfoEntry("Selected Nucleotide " +
                            model.getNucleotide3DAllSortedList().get(index).getValue().getSequenceNumber() +
                            " (" +
                            model.getNucleotide3DAllSortedList().get(index).getValue().getType() +
                            ")");
                }
            });
        }


/*

        //coloring checkboxes
        view.getCheckBoxColorA().setOnAction(event -> {
            if (view.getCheckBoxColorA().isSelected()){
                model.getAdenine3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            }else{
                model.getAdenine3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
        view.getCheckBoxColorC().setOnAction(event -> {
            if(view.getCheckBoxColorC().isSelected()){
                model.getCytosin3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            } else {
                model.getCytosin3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
        view.getCheckBoxColorG().setOnAction(event -> {
            if (view.getCheckBoxColorG().isSelected()){
                model.getGuanine3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            }else{
                model.getGuanine3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });
        view.getCheckBoxColorU().setOnAction(event -> {
            if(view.getCheckBoxColorU().isSelected()){
                model.getUracil3DList().forEach(n -> {
                    n.getValue().setColoring();
                });
            }else{
                model.getUracil3DList().forEach(n -> {
                    n.getValue().resetColoring();
                });
            }
        });

*/
    }


    public SubView getSubView() {
        return this.view;
    }

}