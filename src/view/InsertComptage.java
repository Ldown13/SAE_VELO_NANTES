package view;

import controller.InsertComptageListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Compteur;
import modele.entities.PresenceAnomalie;

public class InsertComptage extends GridPane {

    // ---------------- Attributes ---------------- //

    private Label menuLabel;
    private Label dateLabel;
    private Label compteurLabel;
    private Label anomalieLabel;
    private Label passagesLabel;
    private Label[] passagesLabels;
    
    private DatePicker dateField;
    private ComboBox<String> compteurField;
    private ComboBox<String> anomalieField;
    private TextField[] passagesField;

    private GridPane passagesPane;
    private Button insertButton;
    private Button insertCsvButton;
    private Label output;
    private InsertComptageListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of InsertComptage
     */
    public InsertComptage() {
        super();
        this.listener = new InsertComptageListener(this);
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

        this.menuLabel = new Label("Insérer un comptage");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.dateLabel = new Label("Date : ");
        this.dateField = new DatePicker();
        this.compteurLabel = new Label("Compteur : ");
        this.compteurField = new ComboBox<String>();
        this.anomalieLabel = new Label("Anomalie : ");
        this.anomalieField = new ComboBox<String>();
        this.passagesLabel = new Label("Passages : ");
        this.passagesLabels = new Label[24];
        this.passagesField = new TextField[24];
        this.passagesPane = new GridPane();
        this.insertButton = new Button("Insérer");
        this.insertCsvButton = new Button("Insérer CSV");
        this.output = new Label("");

        for(Compteur compteur : VeloNantes.compteurDao.getAll()){
            this.compteurField.getItems().add(compteur.getNomCompteur() + " " + compteur.getIdCompteur());
        }

        for(PresenceAnomalie anomalie : PresenceAnomalie.values()){
            this.anomalieField.getItems().add(anomalie.toString());
        }

        for(int i = 0; i < 24; i++){
            this.passagesLabels[i] = new Label("h" + i + " : ");
        }

        for(int i = 0; i < 24; i++){
            this.passagesField[i] = new TextField();
            this.passagesField[i].setPrefWidth(100);
        }

        for(int i = 0; i < 24; i++){
            this.passagesPane.add(this.passagesLabels[i], i%4, i/4*2);
            this.passagesPane.add(this.passagesField[i], i%4, i/4*2 + 1); 
        }

        this.dateField.valueProperty().addListener(this.listener);
        this.compteurField.valueProperty().addListener(this.listener);
        this.anomalieField.valueProperty().addListener(this.listener);
        for(int i = 0; i < 24; i++){
            this.passagesField[i].textProperty().addListener(this.listener);
        }
        this.insertButton.setOnAction(this.listener);
        this.insertCsvButton.setOnAction(this.listener);

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.dateLabel, 0, 1);
        this.add(this.dateField, 1, 1);
        this.add(this.compteurLabel, 0, 2);
        this.add(this.compteurField, 1, 2);
        this.add(this.anomalieLabel, 0, 3);
        this.add(this.anomalieField, 1, 3);
        this.add(this.passagesLabel, 0, 4);
        this.add(this.passagesPane, 0, 5, 2, 1);
        this.add(this.insertButton, 1, 6);
        this.add(this.insertCsvButton, 1, 7);
        this.add(this.output, 1, 8, 2, 1);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the date field
     * @return the date field
     */
    public DatePicker getDateField() {
        return dateField;
    }

    /**
     * Get the compteur field
     * @return the compteur field
     */
    public ComboBox<String> getCompteurField() {
        return compteurField;
    }

    /**
     * Get the anomalie field
     * @return the anomalie field
     */
    public ComboBox<String> getAnomalieField() {
        return anomalieField;
    }

    /**
     * Get the passages fields
     * @return the passages fields
     */
    public TextField[] getPassagesFields() {
        return passagesField;
    }

    /**
     * Get the insert button
     * @return the insert button
     */
    public Button getInsertButton() {
        return insertButton;
    }

    /**
     * Get the insert csv button
     * @return the insert csv button
     */
    public Button getInsertCsvButton() {
        return insertCsvButton;
    }

    /**
     * Set the output
     * @param output the output
     */
    public void setOutput(String output) throws IllegalArgumentException{
        if(output == null){
            throw new IllegalArgumentException("output cannot be null");
        }
        
        this.output.setText(output);
    }
}
