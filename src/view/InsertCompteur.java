package view;

import controller.InsertCompteurListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Quartier;

public class InsertCompteur extends GridPane {

    // ---------------- Attributes ---------------- //

    private Label menuLabel;
    private Label nomCompteurLabel;
    private Label idCompteurLabel;
    private Label sensLabel;
    private Label coordXLabel;
    private Label coordYLabel;
    private Label quartierLabel;

    private TextField nomCompteurField;
    private TextField idCompteurField;
    private TextField sensField;
    private TextField coordXField;
    private TextField coordYField;
    private ComboBox<String> quartierField;

    private Button insertButton;
    private Label output;
    private InsertCompteurListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of InsertCompteur
     */
    public InsertCompteur() {
        super();
        this.listener = new InsertCompteurListener(this);
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

        this.menuLabel = new Label("Insérer un compteur");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.nomCompteurLabel = new Label("Nom du compteur : ");
        this.nomCompteurField = new TextField();
        this.idCompteurLabel = new Label("Id du compteur : ");
        this.idCompteurField = new TextField();
        this.sensLabel = new Label("Sens : ");
        this.sensField = new TextField();
        this.coordXLabel = new Label("Coordonnée X : ");
        this.coordXField = new TextField();
        this.coordYLabel = new Label("Coordonnée Y : ");
        this.coordYField = new TextField();
        this.quartierLabel = new Label("Quartier : ");
        this.quartierField = new ComboBox<String>();
        this.insertButton = new Button("Insérer");
        this.output = new Label("");

        for(Quartier quartier : VeloNantes.quartierDao.getAll()){
            this.quartierField.getItems().add(quartier.getNomQuartier() + " " + quartier.getIdQuartier());
        }

        this.nomCompteurField.textProperty().addListener(this.listener);
        this.idCompteurField.textProperty().addListener(this.listener);
        this.sensField.textProperty().addListener(this.listener);
        this.coordXField.textProperty().addListener(this.listener);
        this.coordYField.textProperty().addListener(this.listener);
        this.quartierField.valueProperty().addListener(this.listener);
        this.insertButton.setOnAction(this.listener);

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.nomCompteurLabel, 0, 1);
        this.add(this.nomCompteurField, 1, 1);
        this.add(this.idCompteurLabel, 0, 2);
        this.add(this.idCompteurField, 1, 2);
        this.add(this.sensLabel, 0, 3);
        this.add(this.sensField, 1, 3);
        this.add(this.coordXLabel, 0, 4);
        this.add(this.coordXField, 1, 4);
        this.add(this.coordYLabel, 0, 5);
        this.add(this.coordYField, 1, 5);
        this.add(this.quartierLabel, 0, 6);
        this.add(this.quartierField, 1, 6);
        this.add(this.insertButton, 1, 7);
        this.add(this.output, 0, 8, 2, 1);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the field of the name of the compteur
     * @return the field of the name of the compteur
     */
    public TextField getNomCompteurField() {
        return nomCompteurField;
    }

    /**
     * Get the field of the id of the compteur
     * @return the field of the id of the compteur
     */
    public TextField getIdCompteurField() {
        return idCompteurField;
    }

    /**
     * Get the field of the sens of the compteur
     * @return the field of the sens of the compteur
     */
    public TextField getSensField() {
        return sensField;
    }

    /**
     * Get the field of the coordX of the compteur
     * @return the field of the coordX of the compteur
     */
    public TextField getCoordXField() {
        return coordXField;
    }

    /**
     * Get the field of the coordY of the compteur
     * @return the field of the coordY of the compteur
     */
    public TextField getCoordYField() {
        return coordYField;
    }

    /**
     * Get the field of the quartier of the compteur
     * @return the field of the quartier of the compteur
     */
    public ComboBox<String> getQuartierField() {
        return quartierField;
    }

    /**
     * Set the output
     * @param output the output
     */
    public void setOutput(String output) throws IllegalArgumentException{
        if(output == null){
            throw new IllegalArgumentException("output is null");
        }

        this.output.setText(output);
    }
}
