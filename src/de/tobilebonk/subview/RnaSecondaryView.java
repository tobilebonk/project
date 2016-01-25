package de.tobilebonk.subview;

import de.tobilebonk.nucleotide3D.Residue;
import de.tobilebonk.nucleotide3D.ResiduumType;
import de.tobilebonk.subview.SubView;
import java4bioinf.rna2d.Graph;
import java4bioinf.rna2d.SpringEmbedder;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.List;

/**
 * Created by Dappsen on 25.01.2016.
 */
public class RnaSecondaryView implements SubView {

    private StackPane stackPane;
    private Group circleGroup;
    private double[][] coordinates2D;
    private List<Residue> residues;
    public RnaSecondaryView(double width, double height) {
        circleGroup = new Group();
        stackPane = new StackPane(circleGroup);
        stackPane.setPrefWidth(width);
        stackPane.setPrefHeight(height);
    }

    public void initializeView(Graph graph){

        SpringEmbedder.centerCoordinates(coordinates2D, 20, 500, 20, 500);

        // draw edges
        for(int i = 0; i < graph.getEdges().length; i++){
            System.out.println(graph.getEdges().length + "   " + residues.size());
            Line line = new Line(
                    coordinates2D[graph.getEdge(i)[0]][0],
                    coordinates2D[graph.getEdge(i)[0]][1],
                    coordinates2D[graph.getEdge(i)[1]][0],
                    coordinates2D[graph.getEdge(i)[1]][1]);
            if(i < residues.size()){
                line.setStroke(Color.BLACK);
            }else{
                line.setStroke(Color.WHITE);
            }
            circleGroup.getChildren().add(line);
        }
        // draw nucleotides
        for (int i = 0; i < coordinates2D.length; i++) {
            Circle circle = new Circle(3);
            circle.setCenterX(coordinates2D[i][0]);
            circle.setCenterY(coordinates2D[i][1]);
            switch (residues.get(i).getType()) {
                case A:
                    circle.setFill(Color.FORESTGREEN);
                    break;
                case C:
                    circle.setFill(Color.CRIMSON);
                    break;
                case G:
                    circle.setFill(Color.YELLOW.darker());
                    break;
                case U:
                    circle.setFill(Color.MEDIUMPURPLE);
                    break;
                case _:
                    circle.setFill(Color.GREY);
                    break;
            }
            circle.setStroke(Color.BLACK);
            //add tooltip
            Tooltip t = new Tooltip("Nucleotide: " + residues.get(i).getType() + "\nPosition: " + i);
            Tooltip.install(circle, t);
            circleGroup.getChildren().add(circle);
        }

    }

    @Override
    public StackPane getViewPane() {
        return this.stackPane;
    }


    public double[][] getCoordinates2D() {
        return coordinates2D;
    }

    public void setCoordinates2D(double[][] coordinates2D) {
        this.coordinates2D = coordinates2D;
    }

    public void setResidues(List<Residue> residues){
        this.residues = residues;
    }
}