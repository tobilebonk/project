package de.tobilebonk.presenter;

import de.tobilebonk.model.Model;
import de.tobilebonk.model.ResiduumSelectionModel;
import de.tobilebonk.model.residue.ResiduumType;
import de.tobilebonk.model.reader.PdbReader;
import de.tobilebonk.view.*;
import de.tobilebonk.model.residue.Residue;
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
    RnaPrimaryPresenter rnaPrimaryPresenter;

    public SuperController(){


        // setup view
        superView = new SuperView();
        //setup logger (user communication)
        setupLogger();

        // setup model when new pdf file is loaded
        superView.getOpenFileMenuItem().setOnAction(event -> {

            // open file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Pdb File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdb Files", "*.pdb"));
            File selectedFile = fileChooser.showOpenDialog(superView.getScene().getWindow());
            if (selectedFile != null) {

                // setup model
                String path = selectedFile.getPath();
                log.addInfoEntry("Loading pdb file " + path + ". This could take some seconds...");
                try{
                    model = new Model(new PdbReader(path));

                // setup selection model
                setupSelectionModel();
                log.addLogEntry("Successfully loaded File: " + path);

                // add primary view
                log.addInfoEntry("Setting up views. This could take some seconds...");
                setupPrimaryView();
                // add secondary view
                setupSecondaryView();
                // add tertiary view
                setupTertiaryView();
                log.addLogEntry("Successfully setup views");
                }catch (RuntimeException e){
                    log.addWarningEntry("Error reading the pdb file. Does it match the pdb format? Does it exist?");

                }
            }

        });



        // greetings to the user
        log.addInfoEntry("Welcome! Please choose your PDB File!");
        log.addInfoEntry("Go to the menu bar, choose \"File\" and then \"Load PDB...\"!");
        log.addInfoEntry("Once the file is loaded, select nucleotides by clicking on them!");
        log.addInfoEntry("Hold CTRL to select more than one nucleotide!");
    }

    private void setupSelectionModel() {
        selectionModel = new ResiduumSelectionModel<>();
        Residue[] selectionResidues = new Residue[model.getAllResidues().size()];
        for (int i = 0; i < selectionResidues.length; i++) {
            selectionResidues[i] = model.getAllResidues().get(i);
        }
        selectionModel.setItems(selectionResidues);
        System.out.println("Selection model initialized");

        // connect log to selection model
        selectionModel.getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {
            superView.getScrollPane().setVvalue(1.0d);
        });
        // connect buttons to selection model
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

    private void setupPrimaryView() {
        rnaPrimaryPresenter = new RnaPrimaryPresenter(model, new RnaPrimaryView(superView.getPrimaryPaneWidthProperty().getValue(), superView.getPrimaryPaneHeightProperty().getValue()), selectionModel, log);
        superView.clearPrimaryPane();
        superView.putIntoPrimaryPane(rnaPrimaryPresenter.getSubView().getViewPane());
        System.out.println("Primary view initialized");
    }

    private void setupSecondaryView(){
        RnaSecondaryPresenter rnaSecondaryPresenter = new RnaSecondaryPresenter(model, new RnaSecondaryView(superView.getSecondaryPaneWidthProperty(), superView.getSecondaryPaneHeightProperty()) , selectionModel, log);
        superView.clearSecondaryPane();
        superView.putIntoSecondaryPane(rnaSecondaryPresenter.getSubView().getViewPane());
        System.out.println("Secondary View initialized");
    }

    private void setupTertiaryView(){
        Subpresenter rnaTertiaryPresenter = new RnaTertiaryPresenter(model, new RnaTertiaryView(superView.getTertiaryPaneWidthProperty(), superView.getTertiaryPaneHeightProperty()), selectionModel, log);
        superView.clearTertiaryPane();
        superView.putIntoTertiaryPane(rnaTertiaryPresenter.getSubView().getViewPane());
        System.out.println("Tertiary View initialized");
    }

    private void setupLogger() {

        log = new SuperLog();

        log.getLogEntryList().addListener((ListChangeListener<Text>) c -> {
            while (c.next()) {
                //if log entry was added
                if (c.wasAdded()) {
                    List<? extends Text> logs = c.getAddedSubList();
                    // add log entry to log
                    superView.getLoggingTextFlow().getChildren().addAll(logs);
                }
            }
        });

        superView.getLoggingTextFlow().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                superView.getScrollPane().setVvalue(superView.getScrollPane().getVmax());
            }
        });

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


