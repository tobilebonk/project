package de.tobilebonk.subpresenter;

import de.tobilebonk.Model;
import de.tobilebonk.ResiduumSelectionModel;
import de.tobilebonk.SuperLog;
import de.tobilebonk.subview.RnaPrimaryView;
import de.tobilebonk.subview.SubView;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
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

        view.addAllResiduesToView(model.getModeledResidues());

        selectionModel.getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (int i = 0; i < selectionModel.getItems().length; i++) {
                    int position = model.getAllNonDummyTriples().indexOf(model.getAllTriples().get(i));
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

        for(int i = 0; i < selectionModel.getItems().length; i++) {

            final int index = i;
            int position = model.getAllNonDummyTriples().indexOf(model.getAllTriples().get(i));

            if (position != -1) {
                view.getResidueTexts().get(index).setOnMouseClicked(e -> {
                    if (selectionModel.isSelected(index)) {
                        if (!e.isControlDown()) {
                            selectionModel.clearSelection();
                            log.addInfoEntry("Cleared Selection");
                        } else {
                            selectionModel.clearSelection(index);
                            log.addInfoEntry("Deselected Nucleotide " +
                                    model.getAllTriples().get(index).getSequenceNumber() +
                                    " (" +
                                    model.getAllTriples().get(index).getType() +
                                    ")");
                        }
                    } else {
                        if (!e.isControlDown()) {
                            selectionModel.clearSelection();
                            log.addInfoEntry("Cleared Selection");
                        }
                        selectionModel.select(index);
                        log.addInfoEntry("Selected Nucleotide " +
                                model.getAllTriples().get(index).getSequenceNumber() +
                                " (" +
                                model.getAllTriples().get(index).getType() +
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

    @Override
    public void setSubView(SubView view) {
        this.view = view;
    }
}
