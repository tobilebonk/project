package de.tobilebonk.subview;

import de.tobilebonk.nucleotide3D.*;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
import de.tobilebonk.utils.Comparators;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dappsen on 10.01.2016.
 */
public class RnaTertiaryView implements SubView {


    private List<Nucleotide3DTemplate> adenine3DList = new ArrayList<>();
    private List<Nucleotide3DTemplate> cytosin3DList = new ArrayList<>();
    private List<Nucleotide3DTemplate> guanine3DList = new ArrayList<>();
    private List<Nucleotide3DTemplate> uracil3DList = new ArrayList<>();
    private List<Nucleotide3DTemplate> nucleotide3DAllSortedList = new ArrayList<>();

    //Scene and panes
    final StackPane stackPane;
    final Group world;
    final BorderPane topPane = new BorderPane();

    // Rotation Reminders
    private double mouseDownX, mouseDownY;

    // rotation and translation
    private Rotate cameraRotateX = new Rotate(0, new Point3D(1, 0, 0));
    private Rotate cameraRotateY = new Rotate(0, new Point3D(0, 1, 0));
    private Translate cameraTranslate = new Translate(0, 0, -100);

    public RnaTertiaryView(double width, double height) {

        // setup camera:
        final PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);

        camera.getTransforms().addAll(cameraTranslate);

        //setup world
        world = new Group();
        world.getTransforms().addAll(cameraRotateX, cameraRotateY);

        // setup panes and scene
        final SubScene subScene = new SubScene(world, width, height, true, SceneAntialiasing.BALANCED);
        stackPane = new StackPane(topPane, subScene);
        StackPane.setAlignment(topPane, Pos.CENTER);

        // and subscene
        subScene.widthProperty().bind(stackPane.widthProperty());
        subScene.setCamera(camera);
        StackPane.setAlignment(subScene, Pos.BOTTOM_CENTER);

        //gui elements
        HBox showControlBox = new HBox();
        HBox drawControlBox = new HBox();
        HBox colorControlBox = new HBox();
        VBox controlBox = new VBox();
        controlBox.getChildren().addAll(showControlBox, drawControlBox, colorControlBox);
        Label showLabel = new Label("Show...");
        Label drawLabel = new Label("Draw...");
        Label colorLabel = new Label("Color...");

        topPane.setCenter(controlBox);
        topPane.setPickOnBounds(false);

        // Handlers
        //TODO better rotations
        //TODO: names
        // rotation handlers
        stackPane.setOnMousePressed((me) -> {
            mouseDownX = me.getSceneX();
            mouseDownY = me.getSceneY();
        });
        stackPane.setOnMouseDragged((me) -> {
            double mouseDeltaX = me.getSceneX() - mouseDownX;
            double mouseDeltaY = me.getSceneY() - mouseDownY;


            if (me.isShiftDown()) {
                cameraTranslate.setZ(cameraTranslate.getZ() + mouseDeltaY);
            } else {
                cameraRotateY.setAngle(cameraRotateY.getAngle() + mouseDeltaX);
                cameraRotateX.setAngle(cameraRotateX.getAngle() - mouseDeltaY);
            }
            mouseDownX = me.getSceneX();
            mouseDownY = me.getSceneY();
        });

    }

    // handlers to be set by controller

    public void add3DNucleotide(Nucleotide3DTemplate nucleotide3D) {
        world.getChildren().addAll(nucleotide3D.getNucleotideGroup());
    }

    public void remove3DNucleotide(Nucleotide3DTemplate nucleotide3D) {
        world.getChildren().removeAll(nucleotide3D.getNucleotideGroup());
    }

    // getters

    public StackPane getSubView() {
        return stackPane;
    }

    public Rotate getCameraRotateX() {
        return cameraRotateX;
    }

    public Rotate getCameraRotateY() {
        return cameraRotateY;
    }

    public Translate getCameraTranslate() {
        return cameraTranslate;
    }


    //TODO don't repeat myself
    private void createAdenineGroups(List<ResiduumAtomsSequenceNumberTriple> adenineTriples) {
        adenine3DList.addAll(adenineTriples.stream().map(Adenine3D::new).collect(Collectors.toList()));
        adenine3DList.forEach(n -> world.getChildren().add(n.getNucleotideGroup()));
    }

    private void createCytosinGroups(List<ResiduumAtomsSequenceNumberTriple> cytosinTriples) {
        cytosin3DList.addAll(cytosinTriples.stream().map(Cytosine3D::new).collect(Collectors.toList()));
        cytosin3DList.forEach(n -> world.getChildren().add(n.getNucleotideGroup()));

    }

    private void createGuanineGroups(List<ResiduumAtomsSequenceNumberTriple> guanineTriples) {
        guanine3DList.addAll(guanineTriples.stream().map(Guanine3D::new).collect(Collectors.toList()));
        guanine3DList.forEach(n -> world.getChildren().add(n.getNucleotideGroup()));

    }

    private void createUracilGroups(List<ResiduumAtomsSequenceNumberTriple> uracilTriples) {
        uracil3DList.addAll(uracilTriples.stream().map(Uracil3D::new).collect(Collectors.toList()));
        uracil3DList.forEach(n -> world.getChildren().add(n.getNucleotideGroup()));

    }
    
    public void initiate3DNucleotides(List<ResiduumAtomsSequenceNumberTriple> adenineTriples,
                                      List<ResiduumAtomsSequenceNumberTriple> cytosinTriples,
                                      List<ResiduumAtomsSequenceNumberTriple> guanineTriples,
                                      List<ResiduumAtomsSequenceNumberTriple> uracilTriples) {
        createAdenineGroups(adenineTriples);
        createCytosinGroups(cytosinTriples);
        createGuanineGroups(guanineTriples);
        createUracilGroups(uracilTriples);
        nucleotide3DAllSortedList.addAll(adenine3DList);
        nucleotide3DAllSortedList.addAll(cytosin3DList);
        nucleotide3DAllSortedList.addAll(guanine3DList);
        nucleotide3DAllSortedList.addAll(uracil3DList);
        Collections.sort(nucleotide3DAllSortedList, Comparators.getSequenceIdOnNucleotidesComparator());
    }
    public List<Nucleotide3DTemplate> getNucleotide3DAllSortedList() {
        return nucleotide3DAllSortedList;
    }




}
