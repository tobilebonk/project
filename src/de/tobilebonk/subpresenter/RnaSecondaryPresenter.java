package de.tobilebonk.subpresenter;

import de.tobilebonk.Model;
import de.tobilebonk.ResiduumSelectionModel;
import de.tobilebonk.SuperLog;
import de.tobilebonk.subview.RnaSecondaryView;
import de.tobilebonk.subview.SubView;
import java4bioinf.rna2d.Graph;
import java4bioinf.rna2d.SpringEmbedder;

import java.io.IOException;

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
        double[][] coordinates2D = SpringEmbedder.computeSpringEmbedding(5000, graph.getNumberOfNodes(), graph.getEdges(), null);
        view.setCoordinates2D(coordinates2D);
        view.setResidues(model.getAllResidues());
        view.initializeView(graph);
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
