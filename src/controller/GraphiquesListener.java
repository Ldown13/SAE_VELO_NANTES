package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import view.Graphiques;

public class GraphiquesListener implements EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private Graphiques graphiques;
    private int currentIndex;
    private static final int MAX_INDEX = 8;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the GraphiquesListener class
     * @param graphiques the graphiques view
     * @throws IllegalArgumentException if graphiques is null
     */
    public GraphiquesListener(Graphiques graphiques) throws IllegalArgumentException{
        if(graphiques == null){
            throw new IllegalArgumentException("GraphiquesListener: graphiques cannot be null");
        }

        this.graphiques = graphiques;
        this.showImage(1);
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle the action event
     * @param event the event to handle
     */
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == this.graphiques.getPreviousButton()){
            int previousIndex = currentIndex - 1;
            if (previousIndex < 1) {
                previousIndex = MAX_INDEX;
            }
            this.showImage(previousIndex);
        }

        if(event.getSource() == this.graphiques.getNextButton()){
            int nextIndex = currentIndex + 1;
            if (nextIndex > MAX_INDEX) {
                nextIndex = 1;
            }
            this.showImage(nextIndex);
        }
    }

    /**
     * Show the image at the given index
     * @param index the index of the image to show
     * @throws IllegalArgumentException if index is not between 1 and MAX_INDEX
     */
    private void showImage(int index) throws IllegalArgumentException {
        if (index < 1 || index > MAX_INDEX) {
            throw new IllegalArgumentException("Index must be between 1 and " + MAX_INDEX);
        }

        String imagePath = "graphique" + index + ".png";
        Image image = new Image(this.getClass().getResourceAsStream("/data/" + imagePath));
        this.graphiques.setImage(image);
        currentIndex = index;
    }
}
