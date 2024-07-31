package view;

import controller.UpdateQuartierListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Quartier;

public class UpdateQuartier extends GridPane {

    // ---------------- Attributes ---------------- //

    private Label menuLabel;
    private Label quartierLabel;
    private Label nomQuartierLabel;
    private Label idQuartierLabel;
    private Label longueurPisteVeloLabel;

    private ComboBox<String> quartierField;
    private TextField nomQuartierField;
    private TextField idQuartierField;
    private TextField longueurPisteVeloField;

    private Button updateButton;
    private Label output;
    private UpdateQuartierListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of UpdateQuartier
     */
    public UpdateQuartier() {
        super();
        this.listener = new UpdateQuartierListener(this);
        initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the view
     */
    public void initializeComponents(){
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(20));

        this.menuLabel = new Label("Modifier un quartier");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.quartierLabel = new Label("Quartier : ");
        this.quartierField = new ComboBox<String>();
        this.nomQuartierLabel = new Label("Nom du quartier : ");
        this.nomQuartierField = new TextField();
        this.idQuartierLabel = new Label("Id du quartier : ");
        this.idQuartierField = new TextField();
        this.idQuartierField.setDisable(true);
        this.longueurPisteVeloLabel = new Label("Longueur de la piste cyclable : ");
        this.longueurPisteVeloField = new TextField();
        this.updateButton = new Button("Modifier");
        this.output = new Label("");

        for(Quartier quartier : VeloNantes.quartierDao.getAll()){
            this.quartierField.getItems().add(quartier.getNomQuartier() + " " + quartier.getIdQuartier());
        }

        this.quartierField.valueProperty().addListener(this.listener);
        this.nomQuartierField.textProperty().addListener(this.listener);
        this.longueurPisteVeloField.textProperty().addListener(this.listener);
        this.updateButton.setOnAction(this.listener);

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.quartierLabel, 0, 1);
        this.add(this.quartierField, 1, 1);
        this.add(this.nomQuartierLabel, 0, 2);
        this.add(this.nomQuartierField, 1, 2);
        this.add(this.idQuartierLabel, 0, 3);
        this.add(this.idQuartierField, 1, 3);
        this.add(this.longueurPisteVeloLabel, 0, 4);
        this.add(this.longueurPisteVeloField, 1, 4);
        this.add(this.updateButton, 1, 5);
        this.add(this.output, 0, 6, 2, 1);
    }

    // ---------------- Getters and setters ---------------- //

    /**
     * Get the quartier ComboBox
     * @return the quartier ComboBox
     */
    public ComboBox<String> getQuartierField() {
        return this.quartierField;
    }

    /**
     * Get the nomQuartier TextField
     * @return the nomQuartier TextField
     */
    public TextField getNomQuartierField() {
        return this.nomQuartierField;
    }

    /**
     * Set the nomQuartier TextField
     * @param nomQuartier the nomQuartier
     * @throws NullPointerException if nomQuartier is null
     */
    public void setNomQuartierField(String nomQuartier) throws NullPointerException{
        if(nomQuartier == null){
            throw new NullPointerException("nomQuartier is null");
        }

        this.nomQuartierField.setText(nomQuartier);
    }

    /**
     * Set the idQuartier 
     * @param idQuartier the idQuartier
     */
    public void setIdQuartierField(int idQuartier) {
        this.idQuartierField.setText(idQuartier + "");
    }

    /**
     * Get the longueurPisteVelo TextField
     * @return the longueurPisteVelo TextField
     */
    public TextField getLongueurPisteVeloField() {
        return this.longueurPisteVeloField;
    }

    /**
     * Set the longueurPisteVelo
     * @param longueurPisteVelo the longueurPisteVelo
     */
    public void setLongueurPisteVeloField(float longueurPisteVelo) {
        this.longueurPisteVeloField.setText(longueurPisteVelo + "");
    }

    /**
     * Set the output
     * @param output the output
     * @throws IllegalArgumentException if output is null
     */
    public void setOutput(String output) throws IllegalArgumentException{
        if(output == null){
            throw new IllegalArgumentException("output is null");
        }

        this.output.setText(output);
    }
}