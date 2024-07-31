package view;

import controller.RemoveQuartierListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Quartier;

public class RemoveQuartier extends GridPane {

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

    private Button removeButton;
    private Label warning;
    private Label output;
    private RemoveQuartierListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of RemoveQuartier
     */
    public RemoveQuartier() {
        super();
        this.listener = new RemoveQuartierListener(this);
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

        this.menuLabel = new Label("Supprimer un quartier");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.quartierLabel = new Label("Nom du quartier : ");
        this.quartierField = new ComboBox<String>();
        this.nomQuartierLabel = new Label("Nom du quartier : ");
        this.nomQuartierField = new TextField();
        this.nomQuartierField.setDisable(true);
        this.idQuartierLabel = new Label("Id du quartier : ");
        this.idQuartierField = new TextField();
        this.idQuartierField.setDisable(true);
        this.longueurPisteVeloLabel = new Label("Longueur de la piste cyclable : ");
        this.longueurPisteVeloField = new TextField();
        this.longueurPisteVeloField.setDisable(true);
        this.removeButton = new Button("Supprimer");
        this.warning = new Label("Attention, cela supprimera aussi les compteurs et les comptages de ce quartier.");
        this.output = new Label("");

        for(Quartier quartier : VeloNantes.quartierDao.getAll()){
            this.quartierField.getItems().add(quartier.getNomQuartier() + " " + quartier.getIdQuartier());
        }

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.quartierLabel, 0, 1);
        this.add(this.quartierField, 1, 1);
        this.add(this.nomQuartierLabel, 0, 2);
        this.add(this.nomQuartierField, 1, 2);
        this.add(this.idQuartierLabel, 0, 3);
        this.add(this.idQuartierField, 1, 3);
        this.add(this.longueurPisteVeloLabel, 0, 4);
        this.add(this.longueurPisteVeloField, 1, 4);
        this.add(this.removeButton, 1, 5);
        this.add(this.warning, 0, 6, 2, 1);
        this.add(this.output, 0, 7, 2, 1);

        this.quartierField.valueProperty().addListener(this.listener);
        this.removeButton.setOnAction(this.listener);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Set the output
     * @param output the output
     * @throws IllegalArgumentException if output is null
     */
    public void setOutput(String output) throws IllegalArgumentException{
        if(output == null){
            throw new IllegalArgumentException("Output can't be null");
        }

        this.output.setText(output);
    }

    /**
     * Set the nomQuartier of the field
     * @param nomQuartier the nomQuartier
     * @throws IllegalArgumentException if nomQuartier is null
     */
    public void setNomQuartierField(String nomQuartier) throws IllegalArgumentException{
        if(nomQuartier == null){
            throw new IllegalArgumentException("NomQuartier can't be null");
        }

        this.nomQuartierField.setText(nomQuartier);
    }

    /**
     * Set the idQuartier of the field
     * @param idQuartier the idQuartier
     */
    public void setIdQuartierField(int idQuartier){
        this.idQuartierField.setText(idQuartier + "");
    }

    /**
     * Set the longueurPisteVelo of the field
     * @param longueurPisteVelo the longueurPisteVelo
     */
    public void setLongueurPisteVeloField(float longueurPisteVelo){
        this.longueurPisteVeloField.setText(longueurPisteVelo + "");
    }
}
