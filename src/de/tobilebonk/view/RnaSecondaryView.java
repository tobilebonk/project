package de.tobilebonk.view;

import de.tobilebonk.model.residue.Residue;
import java4bioinf.rna2d.Graph;
import java4bioinf.rna2d.SpringEmbedder;
import javafx.beans.property.SimpleDoubleProperty;
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
    private SimpleDoubleProperty[][] coordinates2DProperties;
    private List<Circle> circles;
    private List<Line> connections;
    private List<Line> bonds;

    private SimpleDoubleProperty widthProperty;
    private SimpleDoubleProperty heightProperty;

    private List<Residue> residues;
    private Graph graph;

    public RnaSecondaryView(SimpleDoubleProperty widthProperty, SimpleDoubleProperty heightProperty) {

        circleGroup = new Group();

        widthProperty.addListener(event -> {
            recalculate2DDrawing();
        });
        heightProperty.addListener(event -> {
            recalculate2DDrawing();
        });

        heightProperty.addListener(event -> stackPane.setPrefHeight(heightProperty.getValue()));
        widthProperty.addListener(event -> stackPane.setPrefWidth(widthProperty.getValue()));
        stackPane = new StackPane(circleGroup);
        circles = new ArrayList<>();
        connections = new ArrayList<>();
        bonds = new ArrayList<>();
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
    }


    public void initializeView(Graph graph){

        this.graph = graph;

        // initialize coordinate properties
        assert coordinates2D != null; // caller must have called setCoordinates2D() beforehand
        coordinates2DProperties = new SimpleDoubleProperty[coordinates2D.length][];
        for(int i = 0; i < coordinates2D.length; i++) {
            coordinates2DProperties[i] = new SimpleDoubleProperty[2];
            coordinates2DProperties[i][0] = new SimpleDoubleProperty();
            coordinates2DProperties[i][1] = new SimpleDoubleProperty();
        }

        recalculate2DDrawing();

        // draw edges
        for(int i = 0; i < graph.getEdges().length; i++){
            Line line = new Line(
                    coordinates2D[graph.getEdge(i)[0]][0],
                    coordinates2D[graph.getEdge(i)[0]][1],
                    coordinates2D[graph.getEdge(i)[1]][0],
                    coordinates2D[graph.getEdge(i)[1]][1]);
            line.startXProperty().bind(coordinates2DProperties[graph.getEdge(i)[0]][0]);
            line.startYProperty().bind(coordinates2DProperties[graph.getEdge(i)[0]][1]);
            line.endXProperty().bind(coordinates2DProperties[graph.getEdge(i)[1]][0]);
            line.endYProperty().bind(coordinates2DProperties[graph.getEdge(i)[1]][1]);
            if(i < residues.size() -1){
                line.setStroke(Color.BLACK);
                connections.add(line);
            }else{
                line.setStroke(Color.GREY.darker().darker());
                line.setStrokeWidth(3);
                bonds.add(line);
            }
            circleGroup.getChildren().add(line);
        }
        // draw nucleotides
        for (int i = 0; i < coordinates2D.length; i++) {
            Circle circle = new Circle(3);
            circle.setCenterX(coordinates2D[i][0]);
            circle.setCenterY(coordinates2D[i][1]);
            circle.centerXProperty().bind(coordinates2DProperties[i][0]);
            circle.centerYProperty().bind(coordinates2DProperties[i][1]);
            switch (residues.get(i).getType()) {
                case OTHER:
                    circle.setFill(Color.BLACK);
                    circle.setStroke(Color.BLACK);
                    break;
                default:
                    circle.setFill(Color.WHITE);
                    circle.setStroke(Color.AZURE);
                    break;
            }
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

    public void resetColoringOfResidueAt(int index){
        circles.get(index).setFill(Color.WHITE);
        circles.get(index).setStroke(Color.AZURE);
    }

    public void setColoringOfResidueAt(int index){
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
    }

    public void setColoringOfBond(int indexInBonds){
        bonds.get(indexInBonds).setStroke(Color.WHITE);
    }

    public void resetColoringOfBond(int indexInBonds){
        bonds.get(indexInBonds).setStroke(Color.GREY.darker().darker());
    }

    private void recalculate2DDrawing(){

        int xMin = (int)(widthProperty.getValue() * 0.1);
        int xMax = (int)(widthProperty.getValue() * 0.9);
        int yMin = (int)(heightProperty.getValue() * 0.1);
        int yMax = (int)(heightProperty.getValue() * 0.9);

        SpringEmbedder.centerCoordinates(coordinates2D, xMin, xMax, yMin, yMax);

        for(int i = 0; i < coordinates2D.length; i++){
            coordinates2DProperties[i][0].set(coordinates2D[i][0]);
            coordinates2DProperties[i][1].set(coordinates2D[i][1]);
        }

    }
}
