package de.tobilebonk.subview;

import de.tobilebonk.nucleotide3D.Residue;
import java4bioinf.rna2d.Graph;
import java4bioinf.rna2d.SpringEmbedder;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dappsen on 25.01.2016.
 */
public class RnaSecondaryView implements SubView {

    private StackPane stackPane;
    private Group circleGroup;
    private double[][] coordinates2D;
    private List<Circle> circles;
    private List<Line> connections;
    private List<Line> bonds;

    private List<Residue> residues;
    public RnaSecondaryView(double width, double height) {
        circleGroup = new Group();
        stackPane = new StackPane(circleGroup);
        stackPane.setPrefWidth(width);
        stackPane.setPrefHeight(height);
        circles = new ArrayList<>();
        connections = new ArrayList<>();
        bonds = new ArrayList<>();
    }


    public void initializeView(Graph graph){

        SpringEmbedder.centerCoordinates(coordinates2D, 20, 500, 20, 500);

        // draw edges
        for(int i = 0; i < graph.getEdges().length; i++){
            Line line = new Line(
                    coordinates2D[graph.getEdge(i)[0]][0],
                    coordinates2D[graph.getEdge(i)[0]][1],
                    coordinates2D[graph.getEdge(i)[1]][0],
                    coordinates2D[graph.getEdge(i)[1]][1]);
            if(i < residues.size() -1){
                line.setStroke(Color.BLACK);
                connections.add(line);
            }else{
                line.setStroke(Color.GREY);
                bonds.add(line);
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
                    break;
            }
            circle.setStroke(Color.BLACK);
            //add tooltip
            Tooltip t = new Tooltip("Nucleotide: " + residues.get(i).getType() + "\nPosition: " + residues.get(i).getSequenceNumber());
            Tooltip.install(circle, t);
            circles.add(circle);
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

    public List<Circle> getCircles() {
        return circles;
    }

    public List<Line> getConnections() {
        return connections;
    }

    public List<Line> getBonds() {
        return bonds;
    }

    public void setColoringOfResidueAt(int index){
        circles.get(index).setFill(Color.WHITE);
        circles.get(index).setStroke(Color.AZURE);
    }

    public void resetColoringOfResidueAt(int index){
        switch (residues.get(index).getType()){
            case A:
                circles.get(index).setFill(Color.FORESTGREEN);
                break;
            case C:
                circles.get(index).setFill(Color.CRIMSON);
                break;
            case G:
                circles.get(index).setFill(Color.YELLOW.darker());
                break;
            case U:
                circles.get(index).setFill(Color.MEDIUMPURPLE);
            break;
        }
        circles.get(index).setStroke(Color.BLACK);
    }

    public void setColoringOfBond(int indexInBonds){
        bonds.get(indexInBonds).setStroke(Color.WHITE);
    }

    public void resetColoringOfBond(int indexInBonds){
        bonds.get(indexInBonds).setStroke(Color.GREY);
    }
}
