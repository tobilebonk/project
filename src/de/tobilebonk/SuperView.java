package de.tobilebonk;


import javafx.geometry.Insets;
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

    private Scene scene;
    private StackPane primaryPane;
    private StackPane secondaryPane;
    private StackPane tertiaryPane;

    private Menu fileMenu;
    private MenuItem openFileMenuItem;
    private TextFlow loggingTextFlow;
    private ScrollPane scrollPane;

    private static int SCENE_WIDTH = 800;
    private static int SCENE_HEIGHT = 500;

    public SuperView(){

        //menu bar
        fileMenu = new Menu("File");
        openFileMenuItem = new MenuItem("Load PDB...");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, menu2, menu3);
        fileMenu.getItems().addAll(openFileMenuItem);

        //Elements
        FlowPane rootPane = new FlowPane();
        // TODO handle magic numbers
        scene = new Scene(rootPane, SCENE_WIDTH + 20, SCENE_HEIGHT + 30);
        VBox primarySecondaryBox = new VBox();
        primaryPane = new StackPane();
        secondaryPane = new StackPane();
        tertiaryPane = new StackPane();
        primarySecondaryBox.getChildren().addAll(primaryPane, secondaryPane);
        BorderPane controlPane = new BorderPane();
        loggingTextFlow = new TextFlow();
        controlPane.setBottom(loggingTextFlow);
        scrollPane = new ScrollPane(controlPane);



        //rootPane.getChildren().add(primarySecondaryBox);
        rootPane.getChildren().addAll(menuBar, primarySecondaryBox, tertiaryPane, scrollPane);

        // widths
        menuBar.setPrefWidth(SCENE_WIDTH);
        primaryPane.setPrefWidth(SCENE_WIDTH * 0.45);
        primaryPane.setPrefHeight(SCENE_HEIGHT * 0.3);
        secondaryPane.setPrefWidth(SCENE_WIDTH * 0.45);
        secondaryPane.setPrefHeight(SCENE_HEIGHT * 0.3);
        primarySecondaryBox.setPrefWidth(SCENE_WIDTH * 0.45);
        primarySecondaryBox.setPrefHeight(SCENE_HEIGHT * 0.6);
        tertiaryPane.setPrefWidth(SCENE_WIDTH * 0.55);
        tertiaryPane.setPrefHeight(SCENE_HEIGHT * 0.6);
        scrollPane.setPrefWidth(SCENE_WIDTH);
        scrollPane.setPrefHeight(SCENE_HEIGHT * 0.4);

        //margins and paddings
        loggingTextFlow.setPadding(new Insets(0, 0, 15, 0));

        //colors TODO: remove
        primaryPane.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        secondaryPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        tertiaryPane.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
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

}
