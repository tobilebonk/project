package de.tobilebonk.presenter;

import de.tobilebonk.model.Model;
import de.tobilebonk.model.ResiduumSelectionModel;
import de.tobilebonk.view.SuperLog;
import de.tobilebonk.view.RnaPrimaryView;
import de.tobilebonk.view.SubView;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;

/**
 * Created by Dappsen on 18.01.2016.
 */
public class RnaPrimaryPresenter implements Subpresenter{

    private SubView view;
    private Model model;
    private ResiduumSelectionModel<Group> selectionModel;
    private SuperLog log;

    public RnaPrimaryPresenter(Model model, RnaPrimaryView view,  ResiduumSelectionModel selectionModel, SuperLog log) {

        this.view = view;
        this.model = model;
        this.selectionModel = selectionModel;
        this.log = log;

        model.getAllResidues().forEach(r -> view.addResidueToView(r.getType()));

        // setup selections
        // coloring reacting to selection model
        selectionModel.getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (int i = 0; i < selectionModel.getItems().length; i++) {
                    int position = model.getAllNonDummyResidues().indexOf(model.getAllResidues().get(i));
                    if (position != -1) {
                        if (selectionModel.getSelectedIndices().contains(i)) {
                            view.setColoringOfResidueAt(i);
                        } else {
                            view.resetColoringOfResidueAt(i);
                        }
                    }
                }
            }
        });

        // selection logic
        // mouse click
        for(int i = 0; i < selectionModel.getItems().length; i++) {

            final int index = i;
            int position = model.getAllNonDummyResidues().indexOf(model.getAllResidues().get(i));

            if (position != -1) {
                view.getResidueTexts().get(index).setOnMouseClicked(e -> {
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
        return view;
    }
}
