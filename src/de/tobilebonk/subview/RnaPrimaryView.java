package de.tobilebonk.subview;

import de.tobilebonk.atom.Atom;
import de.tobilebonk.nucleotide3D.ResiduumType;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.util.*;

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
    public StackPane getViewPane() {
        return stackPane;
    }

    public void addResidueToView(ResiduumType residue){
        this.textFlow.getChildren().add(new Text(residue.toString()));
    }

    public void addAllResiduesToView(Collection<ResiduumType> residues){
        residues.forEach(residue -> addResidueToView(residue));
    }

    public void setColoringOfResidueAt(int index){
        ((Text)textFlow.getChildren().get(index)).setStyle("-fx-background-color: #564862;");
        ((Text)textFlow.getChildren().get(index)).setFill(Color.ALICEBLUE);

    }

    public void resetColoringOfResidueAt(int index){
        ((Text)textFlow.getChildren().get(index)).setStyle("");
        ((Text)textFlow.getChildren().get(index)).setFill(Color.BLACK);

    }


    public java.util.List<Node> getResidueTexts(){
        return textFlow.getChildren();
    }

}
