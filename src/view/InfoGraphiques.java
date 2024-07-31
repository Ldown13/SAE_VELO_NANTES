package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoGraphiques extends VBox{

    // ---------------- Attributes ---------------- //

    private Label title;
    private Label info;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the InfoGraphiques class
     */
    public InfoGraphiques(){
        initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the InfoGraphiques class
     */
    public void initializeComponents(){
        this.title = new Label("Graphiques");
        this.title.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        this.info = new Label("Ces graphiques représentent les données de la ville de Nantes. Vous pouvez naviguer entre les graphiques à l'aide des boutons \"Précédent\" et \"Suivant\"");
        this.info.setText(this.info.getText() + "\n\n" + "Ces graphiques ont été réalisés en Python à l'aide de la librairie matplotlib, ou sur Excel.");
        this.info.setStyle("-fx-text-fill: white;");
        this.info.setPrefWidth(300);
        this.info.setWrapText(true);
        this.getChildren().addAll(this.title, this.info);
    }
}
