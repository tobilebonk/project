package de.tobilebonk.view;

import de.tobilebonk.model.residue.ResiduumType;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

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
        Text text = new Text(residue.toString());
        text.setFill(Color.WHITE);
        text.setFont(javafx.scene.text.Font.font("Monospaced", FontWeight.LIGHT, 14));
        this.textFlow.getChildren().add(text);
    }

    public void setColoringOfResidueAt(int index){
        Text text = ((Text)textFlow.getChildren().get(index));
        text.setFont(javafx.scene.text.Font.font("Monospaced", FontWeight.EXTRA_BOLD, 14));
        switch (text.getText().toString()){
            case "A":
                text.setFill(Color.FORESTGREEN);
                break;
            case "C":
                text.setFill(Color.CRIMSON);
                break;
            case "G":
                text.setFill(Color.YELLOW.darker());
                break;
            case "U":
                text.setFill(Color.MEDIUMPURPLE);
                break;
        }

    }

    public void resetColoringOfResidueAt(int index){
        Text text = ((Text)textFlow.getChildren().get(index));
        text.setFont(javafx.scene.text.Font.font("Monospaced", FontWeight.LIGHT, 14));
        text.setFill(Color.WHITE);
    }


    public List<Node> getResidueTexts(){
        return textFlow.getChildren();
    }

}
