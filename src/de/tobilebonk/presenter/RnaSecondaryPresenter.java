package de.tobilebonk.presenter;

import de.tobilebonk.model.Model;
import de.tobilebonk.model.ResiduumSelectionModel;
import de.tobilebonk.view.SuperLog;
import de.tobilebonk.view.RnaSecondaryView;
import de.tobilebonk.view.SubView;
import java4bioinf.rna2d.Graph;
import java4bioinf.rna2d.SpringEmbedder;
import javafx.collections.ListChangeListener;

/**
 * Created by Dappsen on 21.01.2016.
 */
public class RnaSecondaryPresenter implements Subpresenter{

    private SubView view;
    private Model model;
    private ResiduumSelectionModel selectionModel;
    private SuperLog log;

    public RnaSecondaryPresenter(Model model, RnaSecondaryView view,  ResiduumSelectionModel selectionModel, SuperLog log) {

        this.view = view;
        this.model = model;
        this.selectionModel = selectionModel;
        this.log = log;

        Graph graph = new Graph();
        graph.createEdgesFromResidueList(model.getAllResidues());
        double[][] coordinates2D = SpringEmbedder.computeSpringEmbedding(500, graph.getNumberOfNodes(), graph.getEdges(), null);
        view.setCoordinates2D(coordinates2D);
        view.setResidues(model.getAllResidues());
        view.initializeView(graph);

        //bind view to selection model
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
                for(int i = 0; i < view.getBonds().size(); i++){
                    int ingoingIndex = graph.getEdges()[i + view.getConnections().size()][0];
                    int outgoingIndex = graph.getEdges()[i + view.getConnections().size()][1];
                    if(selectionModel.isSelected(ingoingIndex) && selectionModel.isSelected(outgoingIndex)){
                        view.setColoringOfBond(i);
                    }else{
                        view.resetColoringOfBond(i);
                    }
                }
            }
        });
        // selection logic
        // circles
        for(int i = 0; i < selectionModel.getItems().length; i++) {

            final int index = i;
            int position = model.getAllNonDummyResidues().indexOf(model.getAllResidues().get(i));

            if (position != -1) {
                view.getCircles().get(index).setOnMouseClicked(e -> {
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
        //bonds
        for(int i = 0; i < view.getBonds().size(); i++) {

            final int index = i + view.getConnections().size();
            final int ingoingIndex = graph.getEdge(index)[0];
            final int outgoingIndex = graph.getEdge(index)[1];

            view.getBonds().get(i).setOnMouseClicked(e -> {
                if(selectionModel.isSelected(ingoingIndex) || selectionModel.isSelected(outgoingIndex)) {
                    if (!e.isControlDown()) {
                        selectionModel.clearSelection();
                        log.addLogEntry("Cleared Selection");
                    }else {
                        if (selectionModel.isSelected(ingoingIndex)) {
                            selectionModel.clearSelection(ingoingIndex);
                            log.addLogEntry("Deselected Nucleotide " +
                                            model.getAllResidues().get(ingoingIndex).getSequenceNumber() +
                                            " (" +
                                            model.getAllResidues().get(ingoingIndex).getType() +
                                            ")"
                            );
                        }
                        if (selectionModel.isSelected(outgoingIndex)) {
                            selectionModel.clearSelection(outgoingIndex);
                            log.addLogEntry("Deselected Nucleotide " +
                                            model.getAllResidues().get(outgoingIndex).getSequenceNumber() +
                                            " (" +
                                            model.getAllResidues().get(outgoingIndex).getType() +
                                            ")"
                            );
                        }
                    }
                }else{
                    if (!e.isControlDown()) {
                        selectionModel.clearSelection();
                        log.addLogEntry("Cleared Selection");
                    }
                    selectionModel.select(ingoingIndex);
                    selectionModel.select(outgoingIndex);
                    log.addLogEntry("Selected Nucleotide " +
                            model.getAllResidues().get(ingoingIndex).getSequenceNumber() +
                            " (" +
                            model.getAllResidues().get(ingoingIndex).getType() +
                            ") and " +
                            model.getAllResidues().get(outgoingIndex).getSequenceNumber() +
                            " (" +
                            model.getAllResidues().get(outgoingIndex).getType() +
                            ")");

                }
            });

        }

    }


    @Override
    public SubView getSubView() {
        return this.view;
    }

}
