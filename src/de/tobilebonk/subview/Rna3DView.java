package de.tobilebonk.subview;

import de.tobilebonk.nucleotide3D.Nucleotide3DTemplate;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Arrays;

/**
 * Created by Dappsen on 10.01.2016.
 */
public class Rna3DView {

    //Scene and panes
    final StackPane stackPane;
    final Group world;
    final BorderPane topPane = new BorderPane();

    // Rotation Reminders
    private double mouseDownX, mouseDownY;
    // buttons etc.
    private CheckBox checkBoxShowA = new CheckBox("Adenine");
    private CheckBox checkBoxShowC = new CheckBox("Cytosine");
    private CheckBox checkBoxShowG = new CheckBox("Guanine");
    private CheckBox checkBoxShowU = new CheckBox("Uracil");
    private CheckBox checkBoxPhosphorBonds = new CheckBox("Phosphor to Phosphor Bonds");
    private CheckBox checkBoxSugarPhosphorBonds = new CheckBox("Phosphor to Sugar Bonds");
    private CheckBox checkBoxColorA = new CheckBox("Adenine");
    private CheckBox checkBoxColorC = new CheckBox("Cytosine");
    private CheckBox checkBoxColorG = new CheckBox("Guanine");
    private CheckBox checkBoxColorU = new CheckBox("Uracil");

    public Rna3DView(double width, double height) {

        // setup camera:
        final PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);

        final Rotate cameraRotateX = new Rotate(0, new Point3D(1, 0, 0));
        final Rotate cameraRotateY = new Rotate(0, new Point3D(0, 1, 0));
        final Translate cameraTranslate = new Translate(0, 0, -100);
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

        //gui elements
        HBox showControlBox = new HBox();
        HBox drawControlBox = new HBox();
        HBox colorControlBox = new HBox();
        VBox controlBox = new VBox();
        controlBox.getChildren().addAll(showControlBox, drawControlBox, colorControlBox);
        Label showLabel = new Label("Show...");
        Label drawLabel = new Label("Draw...");
        Label colorLabel = new Label("Color...");

        showControlBox.getChildren().addAll(showLabel, checkBoxShowA, checkBoxShowC, checkBoxShowG, checkBoxShowU);
        drawControlBox.getChildren().addAll(drawLabel, checkBoxSugarPhosphorBonds, checkBoxPhosphorBonds);
        colorControlBox.getChildren().addAll(colorLabel, checkBoxColorA, checkBoxColorC, checkBoxColorG, checkBoxColorU);

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
            } else // rotate
            {
                cameraRotateY.setAngle(cameraRotateY.getAngle() + mouseDeltaX);
                cameraRotateX.setAngle(cameraRotateX.getAngle() - mouseDeltaY);
            }
            mouseDownX = me.getSceneX();
            mouseDownY = me.getSceneY();
        });

        Arrays.asList(checkBoxColorA, checkBoxColorC, checkBoxColorG, checkBoxColorU, checkBoxSugarPhosphorBonds,
                checkBoxPhosphorBonds, checkBoxShowA, checkBoxShowC, checkBoxShowG, checkBoxShowU)
                .forEach(cb -> HBox.setMargin(cb, new Insets(2.5, 15, 2.5, 15)));
        BorderPane.setMargin(controlBox, new Insets(5));

    }

    // handlers to be set by controller

    public void add3DNucleotide(Nucleotide3DTemplate nucleotide3D){
        world.getChildren().addAll(nucleotide3D.getNucleotideGroup());
    }

    public void remove3DNucleotide(Nucleotide3DTemplate nucleotide3D){
        world.getChildren().removeAll(nucleotide3D.getNucleotideGroup());
    }

    public void reset(){
        world.getChildren().clear();
        Arrays.asList(
                checkBoxShowA, checkBoxShowC, checkBoxShowG, checkBoxColorU,
                checkBoxColorA, checkBoxColorC, checkBoxColorG, checkBoxColorU,
                checkBoxPhosphorBonds, checkBoxSugarPhosphorBonds).forEach(
                c -> c.setSelected(false)
        );
    }

    // getters

    public CheckBox getCheckBoxShowA() {
        return checkBoxShowA;
    }

    public CheckBox getCheckBoxShowC() {
        return checkBoxShowC;
    }

    public CheckBox getCheckBoxShowG() {
        return checkBoxShowG;
    }

    public CheckBox getCheckBoxShowU() {
        return checkBoxShowU;
    }

    public CheckBox getCheckBoxPhosphorBonds() {
        return checkBoxPhosphorBonds;
    }

    public CheckBox getCheckBoxSugarPhosphorBonds(){
        return checkBoxSugarPhosphorBonds;
    }

    public CheckBox getCheckBoxColorA() {
        return checkBoxColorA;
    }

    public CheckBox getCheckBoxColorC() {
        return checkBoxColorC;
    }

    public CheckBox getCheckBoxColorG() {
        return checkBoxColorG;
    }

    public CheckBox getCheckBoxColorU() {
        return checkBoxColorU;
    }

    public StackPane getStackPane(){
        return stackPane;
    }
}
