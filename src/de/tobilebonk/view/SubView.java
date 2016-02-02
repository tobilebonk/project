package de.tobilebonk.view;

import javafx.scene.layout.StackPane;

/**
 * Created by Dappsen on 18.01.2016.
 */
public interface SubView {

    StackPane getViewPane();
    void setColoringOfResidueAt(int index);
    void resetColoringOfResidueAt(int index);

}
