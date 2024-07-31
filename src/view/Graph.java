package view;
import javafx.scene.layout.VBox;
import modele.entities.*;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class Graph extends VBox{

    // ---------------- Attributes ---------------- //

    private BarChart<String, Number> barChart;
    private Quartier quartier;
    private Compteur compteur;
    private DateInfo start;
    private DateInfo end;
    
    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the Graph class
     */
    public Graph() {

    }

    // ---------------- Methods ---------------- //

    /**
     * Update the graph
     * @param graphType the type of the graph
     * @param quartier the selected neighborhood
     * @param compteur the selected counter
     * @param start the start date
     * @param end the end date
     * @throws IllegalArgumentException if the argument is null
     */
    public void update(String graphType, Quartier quartier, Compteur compteur, DateInfo start, DateInfo end) throws IllegalArgumentException{
        if(graphType == null){
            throw new IllegalArgumentException("Graph: graphType cannot be null");
        }
        if(start == null){
            throw new IllegalArgumentException("Graph: start cannot be null");
        }
        if(end == null){
            throw new IllegalArgumentException("Graph: end cannot be null");
        }

        this.quartier = quartier;
        this.compteur = compteur;
        this.start = start;
        this.end = end;

        if(graphType == "totalPassages"){
            this.simplePassages(false);
        } else if (graphType == "averagePassages"){
            this.simplePassages(true);
        } else if(graphType == "totalPassagesPerHour"){
            this.passagesPerHour(false);
        } else if(graphType == "averagePassagesPerHour"){
            this.passagesPerHour(true);
        } else if(graphType == "totalPassagesPerDay"){
            this.passagesPerDay(false);
        } else if(graphType == "averagePassagesPerDay"){
            this.passagesPerDay(true);
        }
    }

    /**
     * Setup the chart
     * @param title the title of the chart
     * @param xAxisLabel the label of the x axis
     * @param yAxisLabel the label of the y axis
     * @throws IllegalArgumentException if one of the arguments is null
     */
    public void setupChart(String title, String xAxisLabel, String yAxisLabel) throws IllegalArgumentException{
        if(title == null || xAxisLabel == null || yAxisLabel == null){
            throw new IllegalArgumentException("Null argument");
        }

        this.getChildren().clear();
        this.barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        this.barChart.setTitle(title);
        this.barChart.getXAxis().setLabel(xAxisLabel);
        this.barChart.getYAxis().setLabel(yAxisLabel);
        this.barChart.setLegendVisible(false);
        this.barChart.setMinWidth(800);
        this.barChart.setMinHeight(576);
        this.getChildren().add(this.barChart);
        this.barChart.getData().clear();
    }

    /**
     * Add data to the chart
     * @param name the name of the data
     * @param value the value of the data
     * @throws IllegalArgumentException if the name is null
     */
    public void addData(String name, float value) throws IllegalArgumentException{
        if(name == null){
            throw new IllegalArgumentException("Null argument");
        }

        Series<String, Number> series = new Series<>();
        series.setName(name);
        series.getData().add(new Data<String, Number>(name, value));
        this.barChart.getData().add(series);
    }

    /**
     * Display the total or average passages
     * @param isAverage true if we want the average, false if we want the total
     */
    public void simplePassages(boolean isAverage) {
        // Tous les quartiers, tous les compteurs
        if(this.quartier == null && this.compteur == null){
            this.setupChart("Passages totaux", "Quartiers", "Passages");
            for(Quartier q : VeloNantes.quartierDao.getAll()){
                if(isAverage){
                    float average = q.averagePassages(this.start, this.end);
                    this.addData(q.getNomQuartier() + " " + q.getIdQuartier(), average);
                } else {
                    int total = q.totalPassages(this.start, this.end);
                    this.addData(q.getNomQuartier() + " " + q.getIdQuartier(), total);
                }
            }
        } 
        
        // Quartier sélectionné, tous les compteurs du quartier
        if (this.quartier != null && this.compteur == null){
            this.setupChart("Passages totaux", "Compteurs", "Passages");
            for(Compteur c : this.quartier.getLesCompteurs()){
                String counter = c.getNomCompteur() + c.getSens() + " " + c.getIdCompteur();
                if(isAverage){
                    float average = c.averagePassages(this.start, this.end);
                    this.addData(counter, average);
                } else {
                    int total = c.totalPassages(this.start, this.end);
                    this.addData(counter, total);
                }
            }
        } 
        
        // compteur sélectionné
        if (this.compteur != null){
            this.setupChart("Passages totaux", "Compteur", "Passages");
            String counter = this.compteur.getNomCompteur() + " " + this.compteur.getSens() + " " + this.compteur.getIdCompteur();
            if(isAverage){
                float average = this.compteur.averagePassages(this.start, this.end);
                this.addData(counter, average);
            } else {
                int total = this.compteur.totalPassages(this.start, this.end);
                this.addData(counter, total);
            }
        }
    }

    /**
     * Display the total or average passages per hour
     * @param isAverage true if we want the average, false if we want the total
     */
    public void passagesPerHour(boolean isAverage) {
        float[] totalPerHour = new float[24];
        this.setupChart("Passages par heure", "Heures", "Passages");

        // Tous les quartiers, tous les compteurs
        if(this.quartier == null && this.compteur == null){
            for(Quartier q : VeloNantes.quartierDao.getAll()){
                if(isAverage){
                    float[] average = q.averagePassagesPerHour(this.start, this.end);
                    for(int i = 0; i < 24; i++){
                        totalPerHour[i] += average[i];
                    }
                } else {
                    int[] total = q.totalPassagesPerHour(this.start, this.end);
                    for(int i = 0; i < 24; i++){
                        totalPerHour[i] += total[i];
                    }
                }
            }
        } 
        
        // Quartier sélectionné, tous les compteurs du quartier
        if (this.quartier != null && this.compteur == null){
            for(Compteur c : this.quartier.getLesCompteurs()){
                if(isAverage){
                    float[] average = c.averagePassagesPerHour(this.start, this.end);
                    for(int i = 0; i < 24; i++){
                        totalPerHour[i] += average[i];
                    }
                } else {
                    int[] total = c.totalPassagesPerHour(this.start, this.end);
                    for(int i = 0; i < 24; i++){
                        totalPerHour[i] += total[i];
                    }
                }
            }
        } 
        
        // compteur sélectionné
        if (this.compteur != null){
            if(isAverage){
                float[] average = this.compteur.averagePassagesPerHour(this.start, this.end);
                for(int i = 0; i < 24; i++){
                    totalPerHour[i] = average[i];
                }
            } else {
                int[] total = this.compteur.totalPassagesPerHour(this.start, this.end);
                for(int i = 0; i < 24; i++){
                    totalPerHour[i] = total[i];
                }
            }
        }

        for(int i = 0; i < 24; i++){
            this.addData(Integer.toString(i), totalPerHour[i]);
        }
    }

    /**
     * Display the total or average passages per day
     * @param isAverage true if we want the average, false if we want the total
     */
    public void passagesPerDay(boolean isAverage){
        float[] totalPerDay = new float[7];
        this.setupChart("Passages par jour", "Jours", "Passages");

        // Tous les quartiers, tous les compteurs
        if(this.quartier == null && this.compteur == null){
            for(Quartier q : VeloNantes.quartierDao.getAll()){
                if(isAverage){
                    float[] average = q.averagePassagesPerDay(this.start, this.end);
                    for(int i = 0; i < 7; i++){
                        totalPerDay[i] += average[i];
                    }
                } else {
                    int[] total = q.totalPassagesPerDay(this.start, this.end);
                    for(int i = 0; i < 7; i++){
                        totalPerDay[i] += total[i];
                    }
                }
            }
        } 
        
        // Quartier sélectionné, tous les compteurs du quartier
        if (this.quartier != null && this.compteur == null){
            for(Compteur c : this.quartier.getLesCompteurs()){
                if(isAverage){
                    float[] average = c.averagePassagesPerDay(this.start, this.end);
                    for(int i = 0; i < 7; i++){
                        totalPerDay[i] += average[i];
                    }
                } else {
                    int[] total = c.totalPassagesPerDay(this.start, this.end);
                    for(int i = 0; i < 7; i++){
                        totalPerDay[i] += total[i];
                    }
                }
            }
        } 
        
        // compteur sélectionné
        if (this.compteur != null){
            if(isAverage){
                float[] average = this.compteur.averagePassagesPerDay(this.start, this.end);
                for(int i = 0; i < 7; i++){
                    totalPerDay[i] += average[i];
                }
            } else {
                int[] total = this.compteur.totalPassagesPerDay(this.start, this.end);
                for(int i = 0; i < 7; i++){
                    totalPerDay[i] += total[i];
                }
            }
        }

        for(int i = 0; i < 7; i++){
            this.addData(Integer.toString(i), totalPerDay[i]);
        }
    }
}
