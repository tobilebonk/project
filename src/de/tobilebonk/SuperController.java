package de.tobilebonk;

import de.tobilebonk.reader.PdbReader;
import de.tobilebonk.subcontroller.Rna3DPresenter;
import de.tobilebonk.subview.Rna3DView;
import javafx.collections.ListChangeListener;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dappsen on 16.01.2016.
 */
public class SuperController {

    SuperView superView;
    Model model;

    public SuperController(){

        //setup logger
        SuperLog log = new SuperLog();

        // setup view
        superView = new SuperView();

        //menu bar
        superView.getOpenFileMenuItem().setOnAction(event -> {
            // open file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Pdb File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdb Files", "*.pdb"));
            File selectedFile = fileChooser.showOpenDialog(superView.getScene().getWindow());
            if (selectedFile != null) {
                // if succesful, create model from file
                model = new Model(new PdbReader(selectedFile.getPath()));
                log.addLogEntry("Loaded File: " + selectedFile.getPath());

                Rna3DView rna3DView = new Rna3DView(superView.getPrimaryPaneWidth(), superView.getPrimaryPaneHeight());
                Rna3DPresenter rna3DPresenter = new Rna3DPresenter(model, rna3DView);
                superView.fillTertiaryPane(rna3DView.getStackPane());


            }

        });
        //setup logger
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


        // greetings to the user
        log.addInfoEntry("Welcome! Please choose your PDB File! Therefore, go to the menu bar, choose \"File\" and then \"Load PDB...\"");

    }

    public SuperView getSuperView(){
        return superView;
    }


}
