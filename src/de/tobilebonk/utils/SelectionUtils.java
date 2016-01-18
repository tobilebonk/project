package de.tobilebonk.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Window;

/**
 * some utilities
 * Created by huson on 12/1/15.
 */
public class SelectionUtils {


    /**
     * create a bounding box that is bound to user determined transformations
     */
    public static Rectangle createBoundingBoxWithBinding(Group group, final Rotate worldRotateX, final Rotate worldRotateY, Translate cameraTranslate, boolean visible) {
        final Rectangle boundingBox = new Rectangle();
        boundingBox.setStroke(Color.GREEN);
        boundingBox.setFill(Color.TRANSPARENT);
        boundingBox.setMouseTransparent(true);
        boundingBox.setVisible(visible);

        final ObjectBinding<Rectangle> binding = createBoundingBoxBinding(group, worldRotateX, worldRotateY, cameraTranslate);

        boundingBox.xProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getX();
            }
        });
        boundingBox.yProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getY();
            }
        });
        boundingBox.widthProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            protected double computeValue() {
                return binding.get().getWidth();
            }
        });
        boundingBox.heightProperty().bind(new DoubleBinding() {
            {
                bind(binding);
            }

            @Override
            protected double computeValue() {
                return binding.get().getHeight();
            }
        });

        return boundingBox;
    }

    /**
     * creates bounding box binding
     *
     * @param group
     * @param worldRotateX
     * @param worldRotateY
     * @param cameraTranslate
     * @return binding
     */
    private static ObjectBinding<Rectangle> createBoundingBoxBinding(Group group, final Rotate worldRotateX, final Rotate worldRotateY, Translate cameraTranslate) {
        return new ObjectBinding<Rectangle>() {
            {
                bind(worldRotateX.angleProperty(), worldRotateY.angleProperty(), cameraTranslate.zProperty());
            }

            @Override
            protected Rectangle computeValue() {

                return new Rectangle(
                        group.getBoundsInLocal().getMinX(),
                        group.getBoundsInLocal().getMinY(),
                        group.getBoundsInLocal().getWidth(),
                        group.getBoundsInLocal().getHeight()
                );

            }
        };
    }
}
