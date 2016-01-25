package de.tobilebonk;


import javafx.beans.property.DoubleProperty;
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
    private static final double PREF_SCENE_HEIGHT = 500.0d;
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

    private SimpleDoubleProperty leftWidth = new SimpleDoubleProperty();
    private SimpleDoubleProperty rightWidth = new SimpleDoubleProperty();
    private SimpleDoubleProperty overallWidth = new SimpleDoubleProperty();

    private SimpleDoubleProperty topHeight = new SimpleDoubleProperty();
    private SimpleDoubleProperty bottomHeight = new SimpleDoubleProperty();
    private SimpleDoubleProperty overallHeight = new SimpleDoubleProperty();

    public SuperView(){

        //menu bar
        Menu fileMenu = new Menu("File");
        openFileMenuItem = new MenuItem("Load PDB...");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, menu2, menu3);
        fileMenu.getItems().addAll(openFileMenuItem);


        //Elements
        BorderPane rootPane = new BorderPane();
        scene = new Scene(rootPane, 800, 600);
        VBox primarySecondaryBox = new VBox();
        primaryPane = new StackPane();
        secondaryPane = new StackPane();
        tertiaryPane = new StackPane();
        primarySecondaryBox.getChildren().addAll(primaryPane, secondaryPane);
        BorderPane controlPane = new BorderPane();
        loggingTextFlow = new TextFlow();
        controlPane.setBottom(loggingTextFlow);
        scrollPane = new ScrollPane(controlPane);

        // buttons
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

        rootPane.setLeft(primarySecondaryBox);
        rootPane.setRight(tertiaryPane);
        rootPane.setTop(showControlBox);
        rootPane.setBottom(scrollPane);

        // widths and heights
        updateScreenSizes();
        //bind widths and heights
        menuBar.prefWidthProperty().bind(overallWidth);
        primaryPane.prefWidthProperty().bind(leftWidth);
        //primaryPane.setPrefHeight(topHeight.getValue() * 0.1);
        secondaryPane.prefWidthProperty().bind(leftWidth);
        BorderPane.setAlignment(secondaryPane, Pos.BOTTOM_CENTER);
        //secondaryPane.prefHeightProperty();
        primarySecondaryBox.prefWidthProperty().bind(leftWidth);
        primarySecondaryBox.prefHeightProperty().bind(topHeight);
        tertiaryPane.prefWidthProperty().bind(rightWidth);
        tertiaryPane.prefHeightProperty().bind(topHeight);

        scrollPane.prefWidthProperty().bind(overallWidth);
        scrollPane.prefHeightProperty().bind(bottomHeight);


        //margins and paddings
        loggingTextFlow.setPadding(new Insets(0, 0, 15, 0));

        //colors TODO: remove
        primaryPane.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        secondaryPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        tertiaryPane.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        //resizing
        scene.widthProperty().addListener(event -> {
            updateScreenSizes();
        });
    }

    private void updateScreenSizes(){
        double width = scene.getWidth() - scene.getX();
        double height = scene.getHeight() - scene.getY();
        leftWidth.set(0.49 * width);
        rightWidth.set(0.99 * (width -leftWidth.getValue()));
        overallWidth.set(0.9 * width);
        topHeight.set(0.65 * height);
        bottomHeight.set(0.3 * height);
        overallHeight.set(0.95 * height);
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

    public double getPrimaryPaneWidth(){
        return primaryPane.getWidth();
    }
    public double getPrimaryPaneHeight(){
        return primaryPane.getHeight();
    }
    public double getSecondaryPaneWidth(){
        return secondaryPane.getWidth();
    }
    public double getSecondaryPaneHeight(){
        return secondaryPane.getHeight();
    }
    public double getTertiaryPaneWidth(){
        return tertiaryPane.getWidth();
    }
    public double getTertiaryPaneHeight(){
        return tertiaryPane.getHeight();
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
