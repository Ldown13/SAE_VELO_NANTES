package view;

import controller.InsertQuartierListener;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InsertQuartier extends GridPane {

    // ---------------- Attributes ---------------- //

    private Label menuLabel;
    private Label nomQuartierLabel;
    private Label idQuartierLabel;
    private Label longueurPisteVeloLabel;

    private TextField nomQuartierField;
    private TextField idQuartierField;
    private TextField longueurPisteVeloField;

    private Button insertButton;
    private Label output;
    private InsertQuartierListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of InsertQuartier
     */
    public InsertQuartier() {
        super();
        this.listener = new InsertQuartierListener(this);
        initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the view
     */
    public void initializeComponents() {
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(20));

        this.menuLabel = new Label("Insérer un quartier");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.nomQuartierLabel = new Label("Nom du quartier : ");
        this.nomQuartierField = new TextField();
        this.idQuartierLabel = new Label("Id du quartier : ");
        this.idQuartierField = new TextField();
        this.longueurPisteVeloLabel = new Label("Longueur de la piste cyclable : ");
        this.longueurPisteVeloField = new TextField();
        this.insertButton = new Button("Insérer");
        this.output = new Label("");

        this.nomQuartierField.textProperty().addListener(this.listener);
        this.idQuartierField.textProperty().addListener(this.listener);
        this.longueurPisteVeloField.textProperty().addListener(this.listener);
        this.insertButton.setOnAction(this.listener);

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.nomQuartierLabel, 0, 1);
        this.add(this.nomQuartierField, 1, 1);
        this.add(this.idQuartierLabel, 0, 2);
        this.add(this.idQuartierField, 1, 2);
        this.add(this.longueurPisteVeloLabel, 0, 3);
        this.add(this.longueurPisteVeloField, 1, 3);
        this.add(this.insertButton, 1, 4);
        this.add(this.output, 0, 5, 2, 1);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the nomQuartier field
     * @return the nomQuartier field
     */
    public TextField getNomQuartierField() {
        return this.nomQuartierField;
    }

    /**
     * Get the idQuartier field
     * @return the idQuartier field
     */
    public TextField getIdQuartierField() {
        return this.idQuartierField;
    }

    /**
     * Get the longueurPisteVelo field
     * @return the longueurPisteVelo field
     */
    public TextField getLongueurPisteVeloField() {
        return this.longueurPisteVeloField;
    }

    /**
     * Get the nomQuartier
     * @return the nomQuartier
     */
    public String getNomQuartier() {
        return this.nomQuartierField.getText();
    }

    /**
     * Get the idQuartier
     * @return the idQuartier
     */
    public String getIdQuartier() {
        return this.idQuartierField.getText();
    }

    /**
     * Get the longueurPisteVelo
     * @return the longueurPisteVelo
     */
    public String getLongueurPisteVelo() {
        return this.longueurPisteVeloField.getText();
    }

    /**
     * Set the output label
     * @param output the output
     */
    public void setOutput(String output) throws IllegalArgumentException {
        if (output == null) {
            throw new IllegalArgumentException("output is null");
        }
        
        this.output.setText(output);
    }
}
