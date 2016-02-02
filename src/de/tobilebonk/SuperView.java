package de.tobilebonk;


import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;


/**
 * Created by Dappsen on 16.01.2016.
 */
public class SuperView {

    private static final double PREF_SCENE_WIDTH = 900.0d;
    private static final double PREF_SCENE_HEIGHT = 600.0d;
    private static final double MIN_PRIMARY_HEIGHT = 40.0d;
    private static final double MIN_SECONDARY_HEIGHT = 350.0d;
    private static final double MIN_TOP_HEIGHT = 20d;
    private static final double MIN_BOTTOM_HEIGHT = PREF_SCENE_HEIGHT -MIN_PRIMARY_HEIGHT - MIN_SECONDARY_HEIGHT - MIN_TOP_HEIGHT;
    private static final double MIN_TERTIARY_HEIGHT = PREF_SCENE_HEIGHT - MIN_BOTTOM_HEIGHT - MIN_TOP_HEIGHT;

    private SimpleDoubleProperty primaryHeightProperty = new SimpleDoubleProperty(MIN_PRIMARY_HEIGHT);
    private SimpleDoubleProperty secondaryHeightProperty = new SimpleDoubleProperty(MIN_SECONDARY_HEIGHT);
    private SimpleDoubleProperty tertiaryHeightProperty = new SimpleDoubleProperty(MIN_TERTIARY_HEIGHT);
    private SimpleDoubleProperty leftWidthProperty = new SimpleDoubleProperty();
    private SimpleDoubleProperty rightWidthProperty = new SimpleDoubleProperty();

    private Scene scene;
    private StackPane primaryPane;
    private StackPane secondaryPane;
    private StackPane tertiaryPane;

    private MenuItem openFileMenuItem;
    private TextFlow loggingTextFlow;
    private ScrollPane scrollPane;

    final private Button showAButton;
    final private Button showCButton;
    final private Button showGButton;
    final private Button showUButton;
    final private Button showPurinesButton;
    final private Button showPyrimidinesButton;

    public SuperView(){

        //menu bar
        Menu fileMenu = new Menu("File");
        openFileMenuItem = new MenuItem("Load PDB...");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
        fileMenu.getItems().addAll(openFileMenuItem);


        //Containers
        BorderPane rootPane = new BorderPane();
        scene = new Scene(rootPane, PREF_SCENE_WIDTH, PREF_SCENE_HEIGHT);

        primaryPane = new StackPane();
        secondaryPane = new StackPane();
        tertiaryPane = new StackPane();
        BorderPane controlPane = new BorderPane();

        VBox primarySecondaryBox = new VBox();
        VBox tertiaryBox = new VBox();
        primarySecondaryBox.getChildren().addAll(primaryPane, secondaryPane);
        tertiaryBox.getChildren().add(tertiaryPane);

        loggingTextFlow = new TextFlow();
        controlPane.setBottom(loggingTextFlow);
        scrollPane = new ScrollPane(controlPane);

        // widths and heights
        // heights
        updateHeights();
        primaryPane.setMinHeight(MIN_PRIMARY_HEIGHT);
        secondaryPane.setMinHeight(MIN_SECONDARY_HEIGHT);
        tertiaryPane.setMinHeight(MIN_TERTIARY_HEIGHT);
        scrollPane.setPrefHeight(MIN_BOTTOM_HEIGHT-20);
        primaryPane.prefHeightProperty().bind(primaryHeightProperty);
        secondaryPane.prefHeightProperty().bind(secondaryHeightProperty);
        tertiaryPane.prefHeightProperty().bind(tertiaryHeightProperty);
        loggingTextFlow.setPrefHeight(MIN_BOTTOM_HEIGHT-20);
        //widths
        updateWidths();
        primaryPane.prefWidthProperty().bind(leftWidthProperty);
        secondaryPane.prefWidthProperty().bind(leftWidthProperty);
        BorderPane.setAlignment(secondaryPane, Pos.BOTTOM_CENTER);
        primarySecondaryBox.prefWidthProperty().bind(leftWidthProperty);
        tertiaryBox.prefWidthProperty().bind(rightWidthProperty);
        controlPane.setMinWidth(MIN_BOTTOM_HEIGHT);
        scrollPane.prefWidthProperty().bind(rightWidthProperty.add(leftWidthProperty));
        loggingTextFlow.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        //margins and paddings
        loggingTextFlow.setPadding(new Insets(0, 0, 15, 0));

        // buttons and button containers
        VBox showControlBox = new VBox();
        HBox acguBox = new HBox();
        HBox purinesPyrimidinesBox = new HBox();
        HBox colorSelectionBox = new HBox();
        colorSelectionBox.getChildren().addAll(acguBox, purinesPyrimidinesBox);
        showControlBox.getChildren().addAll(menuBar, colorSelectionBox);

        Label selectACGULabel = new Label("Select all...");
        Label selectPurinesPyrimidinesLabel = new Label("Select all...");
        showAButton = new Button("Adenine");
        showCButton = new Button("Cytonsin");
        showGButton = new Button("Guanine");
        showUButton = new Button("Uracil");
        showPurinesButton = new Button("Purines");
        showPyrimidinesButton = new Button("Pyrimidines");

        acguBox.getChildren().addAll(selectACGULabel, showAButton, showCButton, showGButton, showUButton);
        purinesPyrimidinesBox.getChildren().addAll(selectPurinesPyrimidinesLabel, showPurinesButton, showPyrimidinesButton);


        //add all to scene
        rootPane.setLeft(primarySecondaryBox);
        rootPane.setRight(tertiaryBox);
        rootPane.setTop(showControlBox);
        rootPane.setBottom(scrollPane);

        //resizing
        scene.widthProperty().addListener(event -> updateWidths());
        scene.heightProperty().addListener(event -> updateHeights());

        // styling
        scene.getStylesheets().add("styles/styles.css");
        primaryPane.getStyleClass().addAll("stack-pane", "primary-pane");
        secondaryPane.getStyleClass().addAll("stack-pane", "secondary-pane");
        tertiaryPane.getStyleClass().addAll("stack-pane", "tertiary");
        loggingTextFlow.getStyleClass().add("flow-pane");
        colorSelectionBox.getStyleClass().add("colorSelectionBox");

    }


    private void updateWidths(){
        double width = scene.getWidth() - scene.getX();
        leftWidthProperty.set(0.5 * width);
        rightWidthProperty.set(leftWidthProperty.getValue());
    }

    private void updateHeights(){
        double height = scene.getHeight() - scene.getY();
        primaryHeightProperty.set(0.1 * (height - MIN_TOP_HEIGHT - MIN_BOTTOM_HEIGHT));
        secondaryHeightProperty.set(0.9 * (height - MIN_TOP_HEIGHT - MIN_BOTTOM_HEIGHT));
        tertiaryHeightProperty.set(height - MIN_TOP_HEIGHT - MIN_BOTTOM_HEIGHT);
    }

    public Scene getScene(){
        return scene;
    }

    public void putIntoPrimaryPane(Node node){
        primaryPane.getChildren().add(node);
    }
    public void putIntoSecondaryPane(Node node){
        secondaryPane.getChildren().add(node);
    }
    public void putIntoTertiaryPane(Node node){
        tertiaryPane.getChildren().add(node);
    }
    public void clearPrimaryPane(){
        primaryPane.getChildren().clear();
    }
    public void clearSecondaryPane(){
        secondaryPane.getChildren().clear();
    }
    public void clearTertiaryPane(){
        tertiaryPane.getChildren().clear();
    }

    public SimpleDoubleProperty getPrimaryPaneHeightProperty(){
        return primaryHeightProperty;
    }
    public SimpleDoubleProperty getPrimaryPaneWidthProperty(){
        return leftWidthProperty;
    }
    public SimpleDoubleProperty getSecondaryPaneHeightProperty(){
        return secondaryHeightProperty;
    }
    public SimpleDoubleProperty getSecondaryPaneWidthProperty(){
        return leftWidthProperty;
    }
    public SimpleDoubleProperty getTertiaryPaneHeightProperty(){
        return tertiaryHeightProperty;
    }
    public SimpleDoubleProperty getTertiaryPaneWidthProperty(){
        return rightWidthProperty;
    }

    public MenuItem getOpenFileMenuItem(){
        return openFileMenuItem;
    }
    public TextFlow getLoggingTextFlow(){
        return loggingTextFlow;
    }
    public ScrollPane getScrollPane(){
        return scrollPane;
    }

    public Button getShowAButton() {
        return showAButton;
    }

    public Button getShowCButton() {
        return showCButton;
    }

    public Button getShowGButton() {
        return showGButton;
    }

    public Button getShowUButton() {
        return showUButton;
    }

    public Button getShowPurinesButton() {
        return showPurinesButton;
    }

    public Button getShowPyrimidinesButton() {
        return showPyrimidinesButton;
    }

}
