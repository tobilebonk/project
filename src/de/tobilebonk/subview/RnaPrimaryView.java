package de.tobilebonk.subview;

import de.tobilebonk.atom.Atom;
import de.tobilebonk.nucleotide3D.ResiduumType;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Collection;

/**
 * Created by Dappsen on 18.01.2016.
 */
public class RnaPrimaryView implements SubView {

    private StackPane stackPane;
    private TextFlow textFlow;

    public RnaPrimaryView(double width, double height){

        stackPane = new StackPane();
        stackPane.setPrefWidth(width);
        stackPane.setPrefHeight(height);
        textFlow = new TextFlow();
        stackPane.getChildren().add(textFlow);

    }

    @Override
    public StackPane getStackPane() {
        return stackPane;
    }

    public void addResidueToView(ResiduumType residue){
        this.textFlow.getChildren().add(new Text(residue.toString()));
    }

    public void addAllResiduesToView(Collection<ResiduumType> residues){
        residues.forEach(residue -> addResidueToView(residue));
    }

}
