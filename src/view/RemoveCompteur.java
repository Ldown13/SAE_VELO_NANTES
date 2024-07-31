package view;

import controller.RemoveCompteurListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Compteur;

public class RemoveCompteur extends GridPane {

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

    private Button removeButton;
    private Label output;
    private Label warning;
    private RemoveCompteurListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of RemoveCompteur
     */
    public RemoveCompteur() {
        super();
        this.listener = new RemoveCompteurListener(this);
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

        this.menuLabel = new Label("Supprimer un compteur");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.compteurLabel = new Label("Compteur : ");
        this.compteurField = new ComboBox<String>();
        this.nomCompteurLabel = new Label("Nom du compteur : ");
        this.nomCompteurField = new TextField();
        this.nomCompteurField.setDisable(true);
        this.idCompteurLabel = new Label("Id du compteur : ");
        this.idCompteurField = new TextField();
        this.idCompteurField.setDisable(true);
        this.sensLabel = new Label("Sens : ");
        this.sensField = new TextField();
        this.sensField.setDisable(true);
        this.coordXLabel = new Label("Coordonnée X : ");
        this.coordXField = new TextField();
        this.coordXField.setDisable(true);
        this.coordYLabel = new Label("Coordonnée Y : ");
        this.coordYField = new TextField();
        this.coordYField.setDisable(true);
        this.quartierLabel = new Label("Quartier : ");
        this.quartierField = new ComboBox<String>();
        this.quartierField.setDisable(true);
        this.removeButton = new Button("Supprimer");
        this.output = new Label("");
        this.warning = new Label("Attention, cela supprimera aussi les comptages associés à ce compteur.");

        for(Compteur compteur : VeloNantes.compteurDao.getAll()){
            this.compteurField.getItems().add(compteur.getNomCompteur() + " " + compteur.getIdCompteur());
        }

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
        this.add(this.removeButton, 1, 8, 2, 1);
        this.add(this.warning, 0, 9, 2, 1);
        this.add(this.output, 0, 10, 2, 1);

        this.compteurField.valueProperty().addListener(this.listener);
        this.removeButton.setOnAction(this.listener);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the compteurField
     * @return the compteurField
     */
    public ComboBox<String> getCompteurField(){
        return this.compteurField;
    }

    /**
     * Set the nomCompteur of the field
     * @param nomCompteur the nomCompteur to set
     * @throws IllegalArgumentException if nomCompteur is null
     */
    public void setNomCompteurField(String nomCompteur) throws IllegalArgumentException{
        if(nomCompteur == null){
            throw new IllegalArgumentException("nomCompteur can't be null");
        }

        this.nomCompteurField.setText(nomCompteur);
    }

    /**
     * Set the idCompteur of the field
     * @param idCompteur the idCompteur to set
     */
    public void setIdCompteurField(int idCompteur){
        this.idCompteurField.setText(idCompteur + "");
    }

    /**
     * Set the sens of the field
     * @param sens the sens to set
     * @throws IllegalArgumentException if sens is null
     */
    public void setSensField(String sens) throws IllegalArgumentException{
        if(sens == null){
            throw new IllegalArgumentException("sens can't be null");
        }

        this.sensField.setText(sens);
    }

    /**
     * Set the coordX of the field
     * @param coordX the coordX to set
     */
    public void setCoordXField(float coordX){
        this.coordXField.setText(coordX + "");
    }

    /**
     * Set the coordY of the field
     * @param coordY the coordY to set
     */
    public void setCoordYField(float coordY){
        this.coordYField.setText(coordY + "");
    }

    /**
     * Set the quartier of the field
     * @param quartier the quartier to set
     * @throws IllegalArgumentException if quartier is null
     */
    public void setQuartierField(String quartier) throws IllegalArgumentException{
        if(quartier == null){
            throw new IllegalArgumentException("quartier can't be null");
        }

        this.quartierField.setValue(quartier);
    }

    /**
     * Set the output of the field
     * @param output the output to set
     * @throws IllegalArgumentException if output is null
     */
    public void setOutput(String output) throws IllegalArgumentException{
        if(output == null){
            throw new IllegalArgumentException("output can't be null");
        }

        this.output.setText(output);
    }
}
