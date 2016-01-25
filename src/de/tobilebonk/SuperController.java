package de.tobilebonk;

import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.subpresenter.RnaPrimaryPresenter;
import de.tobilebonk.subpresenter.RnaTertiaryPresenter;
import de.tobilebonk.subpresenter.Subpresenter;
import de.tobilebonk.subview.RnaPrimaryView;
import de.tobilebonk.subview.RnaTertiaryView;
import de.tobilebonk.nucleotide3D.Residue;
import de.tobilebonk.utils.ComputationUtils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 * Created by Dappsen on 16.01.2016.
 */
public class SuperController {

    SuperView superView;
    Model model;
    ResiduumSelectionModel<Residue> selectionModel;

    public SuperController(){

        //TODO fix scrolling
        //setup logger
        SuperLog log = new SuperLog();
        log.getLogEntryList().addListener((ListChangeListener<Text>) c -> {
            while (c.next()) {
                //if log entry was added
                if (c.wasAdded()) {
                    List<? extends Text> logs = c.getAddedSubList();
                    // add log entry to log
                    superView.getLoggingTextFlow().getChildren().addAll(c.getAddedSubList());
                }
            }

        });

        // setup view
        superView = new SuperView();
        superView.getLoggingTextFlow().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                superView.getScrollPane().setVvalue(superView.getScrollPane().getVmax());
            }
        });

        // setup model
        superView.getOpenFileMenuItem().setOnAction(event -> {

            // open file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Pdb File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdb Files", "*.pdb"));
            File selectedFile = fileChooser.showOpenDialog(superView.getScene().getWindow());
            if (selectedFile != null) {

                // setup model
                model = new Model(new PdbReader(selectedFile.getPath()));
                log.addLogEntry("Loaded File: " + selectedFile.getPath());

                // setup selection model
                selectionModel = new ResiduumSelectionModel<>();
                Residue[] selectionResidues = new Residue[model.getAllResidues().size()];
                for (int i = 0; i < selectionResidues.length; i++) {
                    selectionResidues[i] = model.getAllResidues().get(i);
                }
                selectionModel.setItems(selectionResidues);
                // add primary view
                RnaPrimaryPresenter rnaPrimaryPresenter = new RnaPrimaryPresenter(model, new RnaPrimaryView(superView.getPrimaryPaneWidth(), superView.getPrimaryPaneHeight()), selectionModel, log);
                superView.clearPrimaryPane();
                superView.putIntoPrimaryPane(rnaPrimaryPresenter.getSubView().getSubView());

                // add tertiary view
                Subpresenter rnaTertiaryPresenter = new RnaTertiaryPresenter(model, new RnaTertiaryView(superView.getTertiaryPaneWidth(), superView.getTertiaryPaneHeight()), selectionModel, log);
                superView.clearTertiaryPane();
                superView.putIntoTertiaryPane(rnaTertiaryPresenter.getSubView().getSubView());

                selectionModel.getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {
                    superView.getScrollPane().setVvalue(1.0d);
                });
/*
                System.out.println("AU");
                ComputationUtils.isResidueConnectedToResidue(model.getAllNonDummyResidues().get(1), model.getAllNonDummyResidues().get(2));
                ComputationUtils.isResidueConnectedToResidue(model.getAllNonDummyResidues().get(2), model.getAllNonDummyResidues().get(1));

                /*
                System.out.println("GC");
                ComputationUtils.isResidueConnectedToResidue(model.getAllNonDummyResidues().get(0), model.getAllNonDummyResidues().get(3));
*/
                System.out.println(ComputationUtils.createBracketNotation(model.getAllNonDummyResidues()));
            }

        });



        // greetings to the user
        log.addInfoEntry("Welcome! Please choose your PDB File!");
        log.addInfoEntry("Go to the menu bar, choose \"File\" and then \"Load PDB...\"!");
        log.addInfoEntry("Once the file is loaded, select nucleotides by clicking on them!");
        log.addInfoEntry("Hold CTRL to select more than one nucleotide!");
    }

    public SuperView getSuperView(){
        return superView;
    }


}
