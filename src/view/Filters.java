package view;
import java.time.LocalDate;

import controller.FilterListener;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.entities.Quartier;

public class Filters extends VBox{

    // ---------------- Attributes ---------------- //

    private GridPane topFiltersGrid;
    private GridPane bottomFiltersGrid;

    private Label startDateLabel;
    private Label compteurLabel;
    private Label quartierLabel;
    private Label endDateLabel;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ComboBox<String> quartierField;
    private ComboBox<String> compteurField;

    private ToggleGroup group;

    private Label totalPassagesLabel;
    private Label totalPassagesPerDayLabel;
    private Label averagePassagesPerHourLabel;
    private Label totalPassagesPerHourLabel;
    private Label averagePassagesLabel;
    private Label averagePassagesPerDayLabel;

    private RadioButton totalPassages;
    private RadioButton averagePassages;
    private RadioButton totalPassagesPerHour;
    private RadioButton averagePassagesPerHour;
    private RadioButton totalPassagesPerDay;
    private RadioButton averagePassagesPerDay;

    private FilterListener listener;
    private Graph graph;

    // ---------------- Constructors ---------------- //

    /**
     * Constructor of the Filters class
     * @param graph the graph of the application
     * @throws IllegalArgumentException if graph is null    
     */
    public Filters(Graph graph) throws IllegalArgumentException{
        if(graph == null){
            throw new IllegalArgumentException("Graph cannot be null");
        }

        this.graph = graph;

        this.initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the listeners of the Filters class
     */
    public void initializeComponents(){
        this.topFiltersGrid = new GridPane();
        this.topFiltersGrid.setHgap(10);
        this.topFiltersGrid.setVgap(10);
        this.topFiltersGrid.setPadding(new Insets(0, 10, 0, 10));

        this.bottomFiltersGrid = new GridPane();
        this.bottomFiltersGrid.setHgap(10);
        this.bottomFiltersGrid.setVgap(10);
        this.bottomFiltersGrid.setPadding(new Insets(0, 10, 0, 10));

        this.startDateLabel = new Label("Date début:");
        this.startDateLabel.setStyle("-fx-text-fill: #ffffff;");
        this.startDatePicker = new DatePicker();        

        this.endDateLabel = new Label("Date fin:");
        this.endDateLabel.setStyle("-fx-text-fill: #ffffff;");
        this.endDatePicker = new DatePicker();

        this.quartierLabel = new Label("Quartier:");
        this.quartierLabel.setStyle("-fx-text-fill: #ffffff;");
        this.quartierField = new ComboBox<>();
        this.quartierField.setPrefWidth(200);
        this.quartierField.getItems().add("Tous");
        for(Quartier quartier : VeloNantes.quartierDao.getAll()) {
            this.quartierField.getItems().add(quartier.getNomQuartier() + " " + quartier.getIdQuartier());
        }

        this.compteurLabel = new Label("Compteur:");
        this.compteurLabel.setStyle("-fx-text-fill: #ffffff;");
        this.compteurField = new ComboBox<>();
        this.compteurField.setPrefWidth(200);
        this.compteurField.getItems().add("Tous");

        this.group = new ToggleGroup();

        this.totalPassagesLabel = new Label("Total passages:");
        this.totalPassagesLabel.setStyle("-fx-text-fill: #ffffff;");
        this.totalPassages = new RadioButton();
        this.totalPassages.setUserData("totalPassages");
        this.totalPassages.setToggleGroup(this.group);
        
        this.averagePassagesLabel = new Label("Moyenne passages:");
        this.averagePassagesLabel.setStyle("-fx-text-fill: #ffffff;");
        this.averagePassages = new RadioButton();
        this.averagePassages.setUserData("averagePassages");
        this.averagePassages.setToggleGroup(this.group);
        
        this.totalPassagesPerHourLabel = new Label("Total passages par heure:");
        this.totalPassagesPerHourLabel.setStyle("-fx-text-fill: #ffffff;");
        this.totalPassagesPerHour = new RadioButton();
        this.totalPassagesPerHour.setUserData("totalPassagesPerHour");
        this.totalPassagesPerHour.setToggleGroup(this.group);
        
        this.averagePassagesPerHourLabel = new Label("Moyenne passages par heure:");
        this.averagePassagesPerHourLabel.setStyle("-fx-text-fill: #ffffff;");
        this.averagePassagesPerHour = new RadioButton();
        this.averagePassagesPerHour.setUserData("averagePassagesPerHour");
        this.averagePassagesPerHour.setToggleGroup(this.group);
        
        this.totalPassagesPerDayLabel = new Label("Total passage par jour:");
        this.totalPassagesPerDayLabel.setStyle("-fx-text-fill: #ffffff;");
        this.totalPassagesPerDay = new RadioButton();
        this.totalPassagesPerDay.setUserData("totalPassagesPerDay");
        this.totalPassagesPerDay.setToggleGroup(this.group);
        
        this.averagePassagesPerDayLabel = new Label("Moyenne passages par jour:");
        this.averagePassagesPerDayLabel.setStyle("-fx-text-fill: #ffffff;");
        this.averagePassagesPerDay = new RadioButton();
        this.averagePassagesPerDay.setUserData("averagePassagesPerDay");
        this.averagePassagesPerDay.setToggleGroup(this.group);

        this.topFiltersGrid.add(this.startDateLabel, 0, 0);
        this.topFiltersGrid.add(this.startDatePicker, 1, 0);
        this.topFiltersGrid.add(this.endDateLabel, 0, 1);
        this.topFiltersGrid.add(this.endDatePicker, 1, 1);
        this.topFiltersGrid.add(this.quartierLabel, 0, 2);
        this.topFiltersGrid.add(this.quartierField, 1, 2);
        this.topFiltersGrid.add(this.compteurLabel, 0, 3);
        this.topFiltersGrid.add(this.compteurField, 1, 3);
        this.bottomFiltersGrid.add(this.totalPassagesLabel, 0, 4);
        this.bottomFiltersGrid.add(this.totalPassages, 1, 4);
        this.bottomFiltersGrid.add(this.averagePassagesLabel, 0, 5);
        this.bottomFiltersGrid.add(this.averagePassages, 1, 5);
        this.bottomFiltersGrid.add(this.totalPassagesPerHourLabel, 0, 6);
        this.bottomFiltersGrid.add(this.totalPassagesPerHour, 1, 6);
        this.bottomFiltersGrid.add(this.averagePassagesPerHourLabel, 0, 7);
        this.bottomFiltersGrid.add(this.averagePassagesPerHour, 1, 7);
        this.bottomFiltersGrid.add(this.totalPassagesPerDayLabel, 0, 8);
        this.bottomFiltersGrid.add(this.totalPassagesPerDay, 1, 8);
        this.bottomFiltersGrid.add(this.averagePassagesPerDayLabel, 0, 9);
        this.bottomFiltersGrid.add(this.averagePassagesPerDay, 1, 9);

        this.getChildren().add(this.topFiltersGrid);
        this.getChildren().add(this.bottomFiltersGrid);

        this.listener = new FilterListener(this, this.graph);

        this.startDatePicker.valueProperty().addListener(this.listener);
        this.endDatePicker.valueProperty().addListener(this.listener);
        this.quartierField.valueProperty().addListener(this.listener);
        this.compteurField.valueProperty().addListener(this.listener);
        this.group.selectedToggleProperty().addListener(this.listener);

        this.quartierField.setValue("Tous");
        this.compteurField.setValue("Tous");
        this.startDatePicker.setValue(LocalDate.of(2020, 1, 1));
        this.endDatePicker.setValue(LocalDate.of(2023, 01, 24));
        this.group.selectToggle(this.totalPassages);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the date of the start date picker
     * @return the date of the start date picker
     */
    public LocalDate getStartDate() {
        return this.startDatePicker.getValue();
    }

    /**
     * Get the date of the end date picker
     * @return the date of the end date picker
     */
    public LocalDate getEndDate() {
        return this.endDatePicker.getValue();
    }

    /**
     * Get the neighborhood selected
     * @return the neighborhood selected
     */
    public String getQuartier() {
        return this.quartierField.getValue();
    }

    /**
     * Get the counter selected
     * @return the counter selected
     */
    public ComboBox<String> getQuartierField() {
        return this.quartierField;
    }

    /**
     * Set the quartier selected
     * @param quartier the neighborhood selected
     * @throws IllegalArgumentException if the neighborhood doesn't exist
     */
    public void setQuartierField(String quartier) throws IllegalArgumentException{
        if (!this.quartierField.getItems().contains(quartier)) {
            throw new IllegalArgumentException("Le quartier n'existe pas");
        }

        this.quartierField.setValue(quartier);
    }

    /**
     * Get the compteur selected
     * @return the counter selected
     */
    public ComboBox<String> getCompteurField() {
        return this.compteurField;
    }

    /**
     * Set the compteur selected
     * @param compteur the counter selected
     * @throws IllegalArgumentException if the counter doesn't exist
     */
    public void setCompteurField(String compteur) {
        this.compteurField.setValue(compteur);
    }

    /**
     * Get the start date picker
     * @return the start date picker
     */
    public DatePicker getStartDatePicker() {
        return this.startDatePicker;
    }

    /**
     * Get the end date picker
     * @return the end date picker
     */
    public DatePicker getEndDatePicker() {
        return this.endDatePicker;
    }

    /**
     * Get the ToggleGroup of the radio buttons
     * @return the ToggleGroup of the radio buttons
     */
    public ToggleGroup getGroup() {
        return this.group;
    }

    /**
     * Get the compteur selected
     * @return the compteur selected
     */
    public String getCompteur() {
        return this.compteurField.getValue();
    }

    /**
     * Get the type of the graph selected
     * @return the type of the graph selected
     */
    public String getType() {
        return (String)this.group.getSelectedToggle().getUserData();
    }
}
