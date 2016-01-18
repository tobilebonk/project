package de.tobilebonk;

import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.subpresenter.RnaPrimaryPresenter;
import de.tobilebonk.subpresenter.RnaTertiaryPresenter;
import de.tobilebonk.subpresenter.Subpresenter;
import de.tobilebonk.subview.RnaPrimaryView;
import de.tobilebonk.subview.RnaTertiaryView;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
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
    ResiduumSelectionModel<Group> selectionModel;

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
                        superView.getScrollPane().setVvalue(1.0);
                    }
                }
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

                //TODO: reset views

                // setup model
                model = new Model(new PdbReader(selectedFile.getPath()));
                log.addLogEntry("Loaded File: " + selectedFile.getPath());

                // setup selection model
                selectionModel = new ResiduumSelectionModel<>();
                Group[] nucleotides3D =  new Group[model.getNucleotide3DAllSortedList().size()];
                for(int i = 0; i < nucleotides3D.length; i++){
                    nucleotides3D[i] = model.getNucleotide3DAllSortedList().get(i).getValue().getNucleotideGroup();
                }
                selectionModel.setItems(nucleotides3D);

                // add primary view
                RnaPrimaryPresenter rnaPrimaryPresenter = new RnaPrimaryPresenter(model, new RnaPrimaryView(superView.getPrimaryPaneWidth(), superView.getPrimaryPaneHeight()), selectionModel, log);
                superView.fillPrimaryPane(rnaPrimaryPresenter.getSubView().getStackPane());

                // add tertiary view
                Subpresenter rnaTertiaryPresenter = new RnaTertiaryPresenter(model, new RnaTertiaryView(superView.getTertiaryPaneWidth(), superView.getTertiaryPaneHeight()), selectionModel, log);
                superView.fillTertiaryPane(rnaTertiaryPresenter.getSubView().getStackPane());


            }

        });



        // greetings to the user
        log.addInfoEntry("Welcome! Please choose your PDB File! Therefore, go to the menu bar, choose \"File\" and then \"Load PDB...\"");

    }

    public SuperView getSuperView(){
        return superView;
    }


}
