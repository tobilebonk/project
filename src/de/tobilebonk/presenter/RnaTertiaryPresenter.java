package de.tobilebonk.presenter;

import de.tobilebonk.model.Model;
import de.tobilebonk.model.ResiduumSelectionModel;
import de.tobilebonk.view.SuperLog;
import de.tobilebonk.view.RnaTertiaryView;
import de.tobilebonk.view.SubView;
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

            view.setup3DNucleotidesFromNonDummyResidueList(model.getAllNonDummyResidues(), model.getMeanX(), model.getMeanY(), model.getMeanZ());

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

        // setup Buttons
        view.getResetViewButton().setOnAction(event -> {
            view.resetCameraAndRotation();
        });

        // setup selections
        // coloring reacting to selection model
        selectionModel.getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (int i = 0; i < selectionModel.getItems().length; i++) {
                    int position = model.getAllNonDummyResidues().indexOf(model.getAllResidues().get(i));
                    if (position != -1) {
                        if (selectionModel.getSelectedIndices().contains(i)) {
                            view.setColoringOfResidueAt(position);
                        } else {
                            view.resetColoringOfResidueAt(position);
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

}