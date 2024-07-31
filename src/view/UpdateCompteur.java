package view;

import controller.UpdateCompteurListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Compteur;
import modele.entities.Quartier;

public class UpdateCompteur extends GridPane {

    // ---------------- Attributes ---------------- //

    private Label menuLabel;
    private Label compteurLabel;
    private Label nomCompteurLabel;
    private Label idCompteurLabel;
    private Label sensLabel;
    private Label coordXLabel;
    private Label coordYLabel;
    private Label quartierLabel;

    private ComboBox<String> compteurField;
    private TextField nomCompteurField;
    private TextField idCompteurField;
    private TextField sensField;
    private TextField coordXField;
    private TextField coordYField;
    private ComboBox<String> quartierField;

    private Button updateButton;
    private Label output;
    private UpdateCompteurListener listener;

    // ---------------- Constructor ---------------- //
    
    /**
     * Constructor of UpdateCompteur
     */
    public UpdateCompteur() {
        super();
        this.listener = new UpdateCompteurListener(this);
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

        this.menuLabel = new Label("Modifier un compteur");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.compteurLabel = new Label("Compteur : ");
        this.compteurField = new ComboBox<String>();
        this.nomCompteurLabel = new Label("Nom du compteur : ");
        this.nomCompteurField = new TextField();
        this.idCompteurLabel = new Label("Id du compteur : ");
        this.idCompteurField = new TextField();
        this.idCompteurField.setDisable(true);
        this.sensLabel = new Label("Sens : ");
        this.sensField = new TextField();
        this.coordXLabel = new Label("Coordonnée X : ");
        this.coordXField = new TextField();
        this.coordYLabel = new Label("Coordonnée Y : ");
        this.coordYField = new TextField();
        this.quartierLabel = new Label("Quartier : ");
        this.quartierField = new ComboBox<String>();
        this.updateButton = new Button("Modifier");
        this.output = new Label("");
        
        for(Compteur compteur : VeloNantes.compteurDao.getAll()){
            this.compteurField.getItems().add(compteur.getNomCompteur() + " " + compteur.getIdCompteur());
        }

        for(Quartier quartier : VeloNantes.quartierDao.getAll()){
            this.quartierField.getItems().add(quartier.getNomQuartier() + " " + quartier.getIdQuartier());
        }

        this.compteurField.valueProperty().addListener(listener);
        this.nomCompteurField.textProperty().addListener(listener);
        this.sensField.textProperty().addListener(listener);
        this.coordXField.textProperty().addListener(listener);
        this.coordYField.textProperty().addListener(listener);
        this.quartierField.valueProperty().addListener(listener);
        this.updateButton.setOnAction(listener);        

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.compteurLabel, 0, 1);
        this.add(this.compteurField, 1, 1);
        this.add(this.nomCompteurLabel, 0, 2);
        this.add(this.nomCompteurField, 1, 2);
        this.add(this.idCompteurLabel, 0, 3);
        this.add(this.idCompteurField, 1, 3);
        this.add(this.sensLabel, 0, 4);
        this.add(this.sensField, 1, 4);
        this.add(this.coordXLabel, 0, 5);
        this.add(this.coordXField, 1, 5);
        this.add(this.coordYLabel, 0, 6);
        this.add(this.coordYField, 1, 6);
        this.add(this.quartierLabel, 0, 7);
        this.add(this.quartierField, 1, 7);
        this.add(this.updateButton, 1, 8, 2, 1);
        this.add(this.output, 0, 9, 2, 1);
    }

    // ---------------- Getters and setters ---------------- //

    /**
     * Get the compteurField
     * @return the compteurField
     */
    public ComboBox<String> getCompteurField() {
        return compteurField;
    }

    /**
     * Get the nomCompteurField
     * @return the nomCompteurField
     */
    public TextField getNomCompteurField() {
        return nomCompteurField;
    }

    /**
     * Set the nomCompteur of the field
     * @param nom the nomCompteur
     * @throws IllegalArgumentException if nom is null
     */
    public void setNomCompteurField(String nom) throws IllegalArgumentException{
        if(nom == null){
            throw new IllegalArgumentException("nom cannot be null");
        }

        this.nomCompteurField.setText(nom);
    }

    /**
     * Set the idCompteur of the field
     * @param id the idCompteur
     */
    public void setIdCompteurField(int id) {
        this.idCompteurField.setText(id + "");
    }

    /**
     * Get the sensField
     * @return the sensField
     */
    public TextField getSensField() {
        return sensField;
    }

    /**
     * Set the sens of the field
     * @param sens the sens
     * @throws IllegalArgumentException if sens is null
     */
    public void setSensField(String sens) throws IllegalArgumentException{ 
        if(sens == null){
            throw new IllegalArgumentException("sens cannot be null");
        }

        this.sensField.setText(sens);
    }

    /**
     * Get the coordXField
     * @return the coordXField
     */
    public TextField getCoordXField() {
        return coordXField;
    }

    /**
     * Set the coordX of the field
     * @param coordX the coordX
     */
    public void setCoordXField(float coordX) {
        this.coordXField.setText(coordX + "");
    }

    /**
     * Get the coordYField
     * @return the coordYField
     */
    public TextField getCoordYField() {
        return coordYField;
    }

    /**
     * Set the coordY of the field
     * @param coordY the coordY
     */
    public void setCoordYField(float coordY) {
        this.coordYField.setText(coordY + "");
    }

    /**
     * Get the quartierField
     * @return the quartierField
     */
    public ComboBox<String> getQuartierField() {
        return quartierField;
    }

    /**
     * Set the quartier of the field
     * @param quartier the quartier
     * @throws IllegalArgumentException if quartier is null
     */
    public void setQuartierField(String quartier) throws IllegalArgumentException{
        if(quartier == null){
            throw new IllegalArgumentException("quartier cannot be null");
        }

        this.quartierField.setValue(quartier);
    }

    /** 
     * Set the output
     * @param output the output
     * @throws IllegalArgumentException if output is null
     */
    public void setOutput(String output) throws IllegalArgumentException{
        if(output == null){
            throw new IllegalArgumentException("output cannot be null");
        }

        this.output.setText(output);
    }
}
