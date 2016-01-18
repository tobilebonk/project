package de.tobilebonk.subpresenter;

import de.tobilebonk.Model;
import de.tobilebonk.ResiduumSelectionModel;
import de.tobilebonk.SuperLog;
import de.tobilebonk.subview.RnaTertiaryView;
import de.tobilebonk.subview.SubView;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;


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
            //show all nucleotides:
            view.initiate3DNucleotides(model.getAdenineTriples(), model.getCytosinTriples(), model.getGuanineTriples(), model.getUracilTriples());

        //show connections between nucleotides
        if (model.getAllTriples().size() > 1) {
            for (int i = 0; i < model.getAllTriples().size() - 1; ++i) {
                view.getNucleotide3DAllSortedList().get(i).connectPhosphorToPhosphorOf(view.getNucleotide3DAllSortedList().get(i + 1));
                view.getNucleotide3DAllSortedList().get(i).connectSugarToPhosphorOf(view.getNucleotide3DAllSortedList().get(i + 1));
            }
        }
        }

        // setup selections

        selectionModel.getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (int i = 0; i < selectionModel.getItems().length; i++) {
                    if (selectionModel.getSelectedIndices().contains(i)) {
                        view.getNucleotide3DAllSortedList().get(i).setColoring();
                    } else {
                        view.getNucleotide3DAllSortedList().get(i).resetColoring();
                    }
                }
            }
        });
        for (int i = 0; i < view.getNucleotide3DAllSortedList().size(); i++) {

            final int index = i;

            view.getNucleotide3DAllSortedList().get(index).getNucleotideGroup().setOnMouseClicked((e) -> {

                if (selectionModel.isSelected(index)) {
                    if(!e.isAltDown()){
                        selectionModel.clearSelection();
                        log.addInfoEntry("Cleared Selection");
                    }else {
                        selectionModel.clearSelection(index);
                        log.addInfoEntry("Deselected Nucleotide " +
                                view.getNucleotide3DAllSortedList().get(index).getSequenceNumber() +
                                " (" +
                                view.getNucleotide3DAllSortedList().get(index).getType() +
                                ")");
                    }
                } else {
                    if(!e.isAltDown()){
                        selectionModel.clearSelection();
                        log.addInfoEntry("Cleared Selection");
                    }
                    selectionModel.select(index);
                    log.addInfoEntry("Selected Nucleotide " +
                            view.getNucleotide3DAllSortedList().get(index).getSequenceNumber() +
                            " (" +
                            view.getNucleotide3DAllSortedList().get(index).getType() +
                            ")");
                }
            });
        }
    }


    public SubView getSubView() {
        return this.view;
    }

}