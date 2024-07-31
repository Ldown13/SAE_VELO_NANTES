package view;

import controller.GraphiquesListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Graphiques extends VBox {

    // ---------------- Attributes ---------------- //

    private StackPane stackPane;
    private HBox buttonsBox;
    private ImageView imageView;
    private Button previousButton;
    private Button nextButton;
    private Pane spacer;
    private GraphiquesListener graphiquesListener;

    public Graphiques() {
        initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the Graphiques class
     */
    private void initializeComponents() {
        this.spacer = new Pane();
        this.spacer.setMinHeight(20);

        this.previousButton = new Button("Précédent");
        this.nextButton = new Button("Suivant");

        this.buttonsBox = new HBox();
        this.buttonsBox.setAlignment(Pos.CENTER);
        this.buttonsBox.setSpacing(10);
        this.buttonsBox.getChildren().addAll(previousButton, nextButton);

        this.imageView = new ImageView();
        this.imageView.setPreserveRatio(true);

        this.stackPane = new StackPane();
        this.stackPane.setPrefWidth(800);
        this.stackPane.setPrefHeight(600);
        this.stackPane.getChildren().add(imageView);

        this.getChildren().addAll(spacer, stackPane, buttonsBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        this.graphiquesListener = new GraphiquesListener(this);

        this.previousButton.setOnAction(graphiquesListener);
        this.nextButton.setOnAction(graphiquesListener);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the previousButton button
     * @return the previousButton button
     */
    public Button getPreviousButton() {
        return previousButton;
    }

    /**
     * Get the nextButton button
     * @return the nextButton button
     */
    public Button getNextButton() {
        return nextButton;
    }

    /**
     * Set the image of the imageView
     * @param image the image to set
     */
    public void setImage(Image image) throws IllegalArgumentException{
        if(image == null){
            throw new IllegalArgumentException("Image cannot be null");
        }
        
        this.imageView.setImage(image);
    }
}
