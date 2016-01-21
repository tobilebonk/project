package de.tobilebonk;

import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.subpresenter.RnaPrimaryPresenter;
import de.tobilebonk.subpresenter.RnaSecondaryPresenter;
import de.tobilebonk.subpresenter.RnaTertiaryPresenter;
import de.tobilebonk.subpresenter.Subpresenter;
import de.tobilebonk.subview.RnaPrimaryView;
import de.tobilebonk.subview.RnaTertiaryView;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
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
    ResiduumSelectionModel<ResiduumAtomsSequenceNumberTriple> selectionModel;

    public SuperController(){

        //setup logger
        SuperLog log = new SuperLog();
        log.getLogEntryList().addListener(new ListChangeListener<Text>() {
            @Override
            public void onChanged(Change<? extends Text> c) {
                while (c.next()) {
                    //if log entry was added
                    if (c.wasAdded()) {
                        List<? extends Text> logs = c.getAddedSubList();
                        // add log entry to log
                        superView.getLoggingTextFlow().getChildren().addAll(c.getAddedSubList());
                        // set log scrollbar to the very bottom
                    }
                }
                superView.getScrollPane().setVvalue(1.0d);
            }
        });

        // setup view
        superView = new SuperView();

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
                ResiduumAtomsSequenceNumberTriple[] selectionTriples =  new ResiduumAtomsSequenceNumberTriple[model.getAllTriples().size()];
                for(int i = 0; i < selectionTriples.length; i++){
                    selectionTriples[i] = model.getAllTriples().get(i);
                }
                selectionModel.setItems(selectionTriples);
                // add primary view
                RnaPrimaryPresenter rnaPrimaryPresenter = new RnaPrimaryPresenter(model, new RnaPrimaryView(superView.getPrimaryPaneWidth(), superView.getPrimaryPaneHeight()), selectionModel, log);
                superView.clearPrimaryPane();
                superView.putIntoPrimaryPane(rnaPrimaryPresenter.getSubView().getSubView());

                // add tertiary view
                Subpresenter rnaTertiaryPresenter = new RnaTertiaryPresenter(model, new RnaTertiaryView(superView.getTertiaryPaneWidth(), superView.getTertiaryPaneHeight()), selectionModel, log);
                superView.clearTertiaryPane();
                superView.putIntoTertiaryPane(rnaTertiaryPresenter.getSubView().getSubView());


            }

        });



        // greetings to the user
        log.addInfoEntry("Welcome! Please choose your PDB File!");
        log.addInfoEntry("Go to the menu bar, choose \"File\" and then \"Load PDB...\"!");
        log.addInfoEntry("Once the file is loaded, select nucleotides by clicking on them!");
        log.addInfoEntry("Hold ALT to select more than one nucleotide!");
    }

    public SuperView getSuperView(){
        return superView;
    }


}
