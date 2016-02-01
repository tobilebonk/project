package de.tobilebonk.subview;

import de.tobilebonk.nucleotide3D.*;
import de.tobilebonk.nucleotide3D.Residue;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dappsen on 10.01.2016.
 */
public class RnaTertiaryView implements SubView {



    private List<Nucleotide3D> adenine3DList = new ArrayList<>();
    private List<Nucleotide3D> cytosin3DList = new ArrayList<>();
    private List<Nucleotide3D> guanine3DList = new ArrayList<>();
    private List<Nucleotide3D> uracil3DList = new ArrayList<>();
    private List<Nucleotide3D> nucleotide3DAllSortedList = new ArrayList<>();

    //Scene and panes
    final private StackPane stackPane;
    final private Group world;
    final private BorderPane topPane = new BorderPane();


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
        camera.getTransforms().addAll(cameraRotateX, cameraRotateY, cameraTranslate);

        //setup world
        world = new Group();

        // setup panes and scene
        final SubScene subScene = new SubScene(world, width, height, true, SceneAntialiasing.BALANCED);
        stackPane = new StackPane(topPane, subScene);
        StackPane.setAlignment(topPane, Pos.CENTER);

        // and subscene
        subScene.widthProperty().bind(stackPane.widthProperty());
        subScene.setCamera(camera);
        StackPane.setAlignment(subScene, Pos.BOTTOM_CENTER);
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

    public void add3DNucleotide(Nucleotide3D nucleotide3D) {
        world.getChildren().addAll(nucleotide3D.getNucleotideGroup());
    }

    public void remove3DNucleotide(Nucleotide3D nucleotide3D) {
        world.getChildren().removeAll(nucleotide3D.getNucleotideGroup());
    }

    // getters

    public StackPane getViewPane() {
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

    public void setup3DNucleotidesFromNonDummyResidueList(List<Residue> allNonDummyResidues, double meanX, double meanY, double meanZ) {
        nucleotide3DAllSortedList.addAll(allNonDummyResidues.stream().map(r -> {

            ResiduumType type = r.getType();
            // an dummy nucleotide should not be drawn (and has no 3D representation):
            assert (type != ResiduumType._);

            switch (type) {
                case A: {

                    Nucleotide3D n = new Adenine3D(r);
                    adenine3DList.add(n);
                    return n;
                }
                case C: {
                    Nucleotide3D n = new Cytosine3D(r);
                    cytosin3DList.add(n);
                    return n;
                }
                case G: {
                    Nucleotide3D n = new Guanine3D(r);
                    guanine3DList.add(n);
                    return n;
                }
                case U: {
                    Nucleotide3D n = new Uracil3D(r);
                    uracil3DList.add(n);
                    return n;
                }
            }
            // will not be reached, but is necessary for language reasons
            return null;
        }).collect(Collectors.toList()));
        nucleotide3DAllSortedList.forEach(n -> {
            n.centerAll3DElements(meanX, meanY, meanZ);
            world.getChildren().add(n.getNucleotideGroup());
        });

    }

    public List<Nucleotide3D> getNucleotide3DAllSortedList() {
        return nucleotide3DAllSortedList;
    }
    public Group getWorld(){return this.world;}


}
