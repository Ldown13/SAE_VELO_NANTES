package view;

import controller.UpdateComptageListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Compteur;
import modele.entities.PresenceAnomalie;

public class UpdateComptage extends GridPane {

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
    private Button updateButton;
    private Label output;
    private UpdateComptageListener listener;
    
    // ---------------- Constructor ---------------- //

    /**
     * Constructor of InsertComptage
     */
    public UpdateComptage() {
        super();
        this.listener = new UpdateComptageListener(this);
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

        this.menuLabel = new Label("Modifier un comptage");
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
        this.updateButton = new Button("Modifier");
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
        this.updateButton.setOnAction(this.listener);

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.dateLabel, 0, 1);
        this.add(this.dateField, 1, 1);
        this.add(this.compteurLabel, 0, 2);
        this.add(this.compteurField, 1, 2);
        this.add(this.anomalieLabel, 0, 3);
        this.add(this.anomalieField, 1, 3);
        this.add(this.passagesLabel, 0, 4);
        this.add(this.passagesPane, 0, 5, 2, 1);
        this.add(this.updateButton, 1, 6);
        this.add(this.output, 1, 7, 2, 1);
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
     * Get the dateField
     * @return the dateField
     */
    public DatePicker getDateField(){
        return this.dateField;
    }

    /**
     * Get the date
     * @return the date
     */
    public String getDate(){
        return this.dateField.getValue().toString();
    }

    /**
     * Get the anomalieField
     * @return the anomalieField
     */
    public ComboBox<String> getAnomalieField(){
        return this.anomalieField;
    }

    /**
     * Set the anomalie of the field
     * @param anomalie the anomalie
     * @throws IllegalArgumentException if anomalie is null
     */
    public void setAnomalieField(PresenceAnomalie anomalie) throws IllegalArgumentException{
        if(anomalie == null){
            throw new IllegalArgumentException("anomalie cannot be null");
        }

        this.anomalieField.setValue(anomalie.toString());
    }

    /**
     * Get the passagesFields
     * @return the passagesFields
     */
    public TextField[] getPassagesFields(){
        return this.passagesField;
    }

    /**
     * Set the passagesFields
     * @param passages the passages
     * @throws IllegalArgumentException if passages is null, or if passages.length != 24
     */
    public void setPassagesFields(int[] passages) throws IllegalArgumentException{
        if(passages == null){
            throw new IllegalArgumentException("passages cannot be null");
        }
        if(passages.length != 24){
            throw new IllegalArgumentException("passages.length must be 24");
        }

        for(int i = 0; i < 24; i++){
            this.passagesField[i].setText(Integer.toString(passages[i]));
        }
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
