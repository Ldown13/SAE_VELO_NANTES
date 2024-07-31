package view;

import controller.UpdateDateListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.entities.Jour;
import modele.entities.Vacances;

public class UpdateDate extends GridPane {

    // ---------------- Attributes ---------------- //

    private Label menuLabel;
    private Label dateLabel;
    private Label tempMoyLabel;
    private Label jourLabel;
    private Label vacancesLabel;

    private DatePicker dateField;
    private TextField tempMoyField;
    private ComboBox<String> jourField;
    private ComboBox<String> vacancesField;

    private Button updateButton;
    private Label output;
    private UpdateDateListener listener;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of UpdateDate
     */
    public UpdateDate() {
        super();
        this.listener = new UpdateDateListener(this);
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

        this.menuLabel = new Label("Modifier une date");
        this.menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px 0px;");
        this.dateLabel = new Label("La date : ");
        this.dateField = new DatePicker();
        this.tempMoyLabel = new Label("Température moyenne : ");
        this.tempMoyField = new TextField();
        this.jourLabel = new Label("Jour : ");
        this.jourField = new ComboBox<String>();
        this.vacancesLabel = new Label("Vacances : ");
        this.vacancesField = new ComboBox<String>();
        this.updateButton = new Button("Modifier");
        this.output = new Label("");

        this.add(this.menuLabel, 0, 0, 2, 1);
        this.add(this.dateLabel, 0, 1);
        this.add(this.dateField, 1, 1);
        this.add(this.tempMoyLabel, 0, 2);
        this.add(this.tempMoyField, 1, 2);
        this.add(this.jourLabel, 0, 3);
        this.add(this.jourField, 1, 3);
        this.add(this.vacancesLabel, 0, 4);
        this.add(this.vacancesField, 1, 4);
        this.add(this.updateButton, 1, 5);
        this.add(this.output, 0, 6, 2, 1);

        this.dateField.valueProperty().addListener(this.listener);
        this.tempMoyField.textProperty().addListener(this.listener);
        this.jourField.valueProperty().addListener(this.listener);
        this.vacancesField.valueProperty().addListener(this.listener);
        this.updateButton.setOnAction(this.listener);
    }

    // ---------------- Getters and setters ---------------- //

    /**
     * Get the date
     * @return the date
     */
    public String getDate(){
        return this.dateField.getValue().toString();
    }

    /**
     * Get the tempMoyField
     * @return the tempMoyField
     */
    public TextField getTempMoyField(){
        return this.tempMoyField;
    }

    /**
     * Set the temperature
     * @param tempMoy the temperature
     */
    public void setTempMoyField(float tempMoy){
        this.tempMoyField.setText(tempMoy + "");
    }

    /**
     * Get the jourField
     * @return the jourField
     */
    public ComboBox<String> getJourField(){
        return this.jourField;
    }

    /**
     * Set the jour
     * @param jour the jour
     */
    public void setJourField(Jour jour) throws IllegalArgumentException{
        if(jour == null){
            throw new IllegalArgumentException("jour cannot be null");
        }

        this.jourField.setValue(jour.toString());
    }

    /**
     * Get the vacancesField
     * @return the vacancesField
     */
    public ComboBox<String> getVacancesField(){
        return this.vacancesField;
    }

    /**
     * Set the vacances
     * @param vacances the vacances
     * @throws IllegalArgumentException if vacances is null
     */
    public void setVacancesField(Vacances vacances) throws IllegalArgumentException{
        if(vacances == null){
            throw new IllegalArgumentException("vacances cannot be null");
        }

        this.vacancesField.setValue(vacances.toString());
    }

    public void setOutput(String output){
        this.output.setText(output);
    }
}
