package com.example.demo.UI;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.controller.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * MainMenu is the entry point for the Sky Battle game.
 * It provides a main menu for the user to start the game,
 * view instructions, or quit the game. 
 * It also includes custom styling and cursors.
 */
public class MainMenu {

    private final Stage stage;
    private final Controller controller;
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/nature.jpg";
    private static final String MOUSE_ICON = "/com/example/demo/images/mouse_icon.png";
    private static final String TRIANGLE_MOUSE_ICON = "/com/example/demo/images/triangle_mouse.png";

    private final ImageCursor hoverCursor;
    private final ImageCursor mouseCursor;

    /**
     * Constructs the MainMenu with the specified stage and controller.
     * 
     * @param stage      The primary stage where the main menu is displayed.
     * @param controller The game controller to manage game transitions.
     */
    public MainMenu(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;

        // Load the custom cursor from the path
        Image cursorMouse = new Image(this.getClass().getResource(MOUSE_ICON).toExternalForm());
        this.hoverCursor = new ImageCursor(cursorMouse);

        // Load the custom cursor from the path
        Image cursorMouseT = new Image(this.getClass().getResource(TRIANGLE_MOUSE_ICON).toExternalForm());
        this.mouseCursor = new ImageCursor(cursorMouseT);
    }


    /**
     * Displays the main menu screen.
     */
    public void show() {

        // VBox for center alignment
        VBox vBox = new VBox(30);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        // Set the background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(this.getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)

        );
        vBox.setBackground(new Background(backgroundImage));

        // Title Label with default font
        Label titleLabel = new Label("Sky Battle");
        titleLabel.setStyle("-fx-font-size: 48px; -fx-text-fill: #000000;");
        titleLabel.setAlignment(Pos.CENTER);

        // Start Game Button
        Button startGameButton = createStyledButton("", "/com/example/demo/images/play_button.png", 200, 50);
        startGameButton.setOnAction(event -> startGame());
        startGameButton.setFocusTraversable(false);

        // Instructions Button
        Button instructionsButton = createStyledButton("Instructions", "/com/example/demo/images/wood_button.png", 200, 50);
        instructionsButton.setOnAction(event -> showInstructions());
        instructionsButton.setFocusTraversable(false);

        // Quit Game Button
        Button quitGameButton = createStyledButton("Quit Game", "/com/example/demo/images/wood_button.png", 200, 50);
        quitGameButton.setOnAction(event -> stage.close());
        quitGameButton.setFocusTraversable(false);

        // Add components to the VBox
        vBox.getChildren().addAll(titleLabel, startGameButton, instructionsButton, quitGameButton);

        // Create Scene
        Scene scene = new Scene(vBox, 800, 600);
        // Set the triangle cursor to the screen when not hovering
        scene.setCursor(mouseCursor);
        stage.setScene(scene);
        stage.show();
    }

     /**
     * Creates a styled button.
     * 
     * @param text      The text displayed on the button.
     * @param imagePath The background image for the button.
     * @param width     The width of the button.
     * @param height    The height of the button.
     * @return The styled Button object.
     */
    private Button createStyledButton(String text, String imagePath, int width, int height) {
        Button button = new Button(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle(
                "-fx-background-image: url('" + getClass().getResource(imagePath).toExternalForm() + "');" +
                        "-fx-background-size: 100% 100%;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-text-fill: #8B4513;" +
                        "-fx-alignment: center;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-width: 0;"
        );
        button.setOnMouseEntered(event -> button.setCursor(hoverCursor));
        button.setOnMouseExited(event -> button.setCursor(mouseCursor));
        return button;
    }

    /**
     * Starts the game by calling the launchGame method in Controller.
     */
    void startGame() {
        System.out.println("Starting the game");
        try {
            controller.launchGame();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(); // Print errors for debugging
        }
    }
     /**
     * Displays the instructions screen of the game.
     */
    void showInstructions() {
        Stage instructionsStage = new Stage();
        instructionsStage.initModality(Modality.APPLICATION_MODAL);
        instructionsStage.initStyle(StageStyle.TRANSPARENT);

        BorderPane rootLayout = new BorderPane();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(this.getClass().getResource("/com/example/demo/images/pause.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        rootLayout.setBackground(new Background(backgroundImage));

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));

        Button closeButton = new Button();
        closeButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-background-size: 50px 40px;");
        ImageView closeButtonImage = new ImageView(
                new Image(this.getClass().getResource("/com/example/demo/images/x_button.png").toExternalForm())
        );
        closeButtonImage.setFitWidth(30);
        closeButtonImage.setFitHeight(30);
        closeButton.setGraphic(closeButtonImage);
        closeButton.setOnAction(event -> instructionsStage.close());
        topBar.getChildren().add(closeButton);

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label instructionLabel = new Label("In Sky Battle, your mission is to defend yourself from incoming bullets.\n\n" +
                "• Use arrow keys to navigate your aircraft.\n" +
                "• Press spacebar to fire your weapons.\n\n" +
                "Survive and defeat the boss to win. Good luck!");
        instructionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #000000;");
        instructionLabel.setWrapText(true);

        content.getChildren().add(instructionLabel);

        rootLayout.setTop(topBar);
        rootLayout.setCenter(content);

        Scene instructionsScene = new Scene(rootLayout, 400, 250);
        instructionsScene.setFill(null);
        instructionsScene.setCursor(mouseCursor);
        instructionsStage.setScene(instructionsScene);
        instructionsStage.show();
    }

}
