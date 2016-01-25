package de.tobilebonk;

import de.tobilebonk.nucleotide3D.ResiduumType;
import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.subpresenter.RnaPrimaryPresenter;
import de.tobilebonk.subpresenter.RnaSecondaryPresenter;
import de.tobilebonk.subpresenter.RnaTertiaryPresenter;
import de.tobilebonk.subpresenter.Subpresenter;
import de.tobilebonk.subview.RnaPrimaryView;
import de.tobilebonk.subview.RnaSecondaryView;
import de.tobilebonk.subview.RnaTertiaryView;
import de.tobilebonk.nucleotide3D.Residue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.input.MouseEvent;
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
    SuperLog log;

    public SuperController(){

        //TODO fix scrolling
        //setup logger
        log = new SuperLog();
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
                superView.putIntoPrimaryPane(rnaPrimaryPresenter.getSubView().getViewPane());

                // add secondary view
                RnaSecondaryPresenter rnaSecondaryPresenter = new RnaSecondaryPresenter(model, new RnaSecondaryView(superView.getSecondaryPaneWidth(), superView.getSecondaryPaneHeight()) , selectionModel, log);
                superView.clearSecondaryPane();
                superView.putIntoSecondaryPane(rnaSecondaryPresenter.getSubView().getViewPane());

                // add tertiary view
                System.out.println(superView.getTertiaryPaneWidth() + "   " + superView.getTertiaryPaneHeight());
                Subpresenter rnaTertiaryPresenter = new RnaTertiaryPresenter(model, new RnaTertiaryView(superView.getTertiaryPaneWidth(), superView.getTertiaryPaneHeight()), selectionModel, log);
                superView.clearTertiaryPane();
                superView.putIntoTertiaryPane(rnaTertiaryPresenter.getSubView().getViewPane());

                selectionModel.getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {
                    superView.getScrollPane().setVvalue(1.0d);
                });

                // Checkboxes

                superView.getShowAButton().setOnMouseClicked(e -> {
                    connectSelectionModelToButtonForType(e, ResiduumType.A);
                });
                superView.getShowCButton().setOnMouseClicked(e -> {
                    connectSelectionModelToButtonForType(e,ResiduumType.C);
                });
                superView.getShowGButton().setOnMouseClicked(e -> {
                    connectSelectionModelToButtonForType(e,ResiduumType.G);
                });
                superView.getShowUButton().setOnMouseClicked(e -> {
                    connectSelectionModelToButtonForType(e,ResiduumType.U);
                });
                superView.getShowPurinesButton().setOnMouseClicked(e -> {
                    connectSelectionModelToButtonForType(e, ResiduumType.A, ResiduumType.G);
                });
                superView.getShowPyrimidinesButton().setOnMouseClicked(e -> {
                    connectSelectionModelToButtonForType(e, ResiduumType.C, ResiduumType.U);
                });

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


    private void connectSelectionModelToButtonForType(MouseEvent e, ResiduumType... types){

        if (!e.isControlDown()) {
            selectionModel.clearSelection();
            log.addLogEntry("Cleared Selection");
        }
        for(ResiduumType type : types) {
            model.getResiduesOfType(type).forEach(residue -> {
                int index = model.getAllResidues().indexOf(residue);
                selectionModel.select(index);
                log.addLogEntry("Selected Nucleotide " +
                        model.getAllResidues().get(index).getSequenceNumber() +
                        " (" +
                        model.getAllResidues().get(index).getType() +
                        ")");
            });
        }

    }



}


