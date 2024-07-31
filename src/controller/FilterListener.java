package controller;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;

import java.sql.Date;
import java.time.LocalDate;
import modele.entities.Compteur;
import modele.entities.DateInfo;
import modele.entities.Quartier;
import view.Filters;
import view.Graph;
import view.VeloNantes;

public class FilterListener implements ChangeListener<Object> {

    // ---------------- Attributes ---------------- //

    private Filters filters;
    private DateInfo start;
    private DateInfo end;
    private Compteur compteur;
    private Quartier quartier;
    private Graph graph;
    private String type;
    private boolean isUpdatingCompteurs = false;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the FilterListener class
     * @param filter the filter view
     * @param graph the graph view
     * @throws IllegalArgumentException if filter or graph is null
     */
    public FilterListener(Filters filter, Graph graph) throws IllegalArgumentException {
        if(filter == null){
            throw new IllegalArgumentException("FilterListener: filter cannot be null");
        }
        if(graph == null){
            throw new IllegalArgumentException("FilterListener: graph cannot be null");
        }

        this.filters = filter;
        this.graph = graph;
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle the changes events
     * @param observable the observable object
     * @param before the object before the change
     * @param after the object after the change
     */
    @Override
    public void changed(javafx.beans.value.ObservableValue<?> observable, Object before, Object after) {
        if(observable == this.filters.getQuartierField().valueProperty()){
            String nomQuartier = (String) after;
            if(nomQuartier.equals("Tous")){
                this.quartier = null;
            } else {
                String[] splitQuartier = nomQuartier.split(" ");
                int idQuartier = Integer.parseInt(splitQuartier[splitQuartier.length - 1]);
                this.quartier = VeloNantes.quartierDao.get(idQuartier);
            }
            this.updateCompteurs();
        }
        if(observable == this.filters.getCompteurField().valueProperty()){
            if(isUpdatingCompteurs == false){
                String nomCompteur = (String) after;
                if(nomCompteur.equals("Tous")){
                    this.compteur = null;
                } else {
                    String[] splitCompteur = nomCompteur.split(" ");
                    int idCompteur = Integer.parseInt(splitCompteur[splitCompteur.length - 1]);
                    this.compteur = VeloNantes.compteurDao.get(idCompteur);
                }
            }
        }
        if(observable == this.filters.getStartDatePicker().valueProperty()){
            Date date = Date.valueOf((LocalDate) after);
            this.start = VeloNantes.dateInfoDao.get(date);
        }
        if(observable == this.filters.getEndDatePicker().valueProperty()){
            Date date = Date.valueOf((LocalDate) after);
            this.end = VeloNantes.dateInfoDao.get(date);
        }
        if(observable == this.filters.getGroup().selectedToggleProperty()){
            this.type = ((Toggle) after).getUserData().toString();
        }

        this.updateGraph();
    }

    /**
     * Update the compteur field
     */
    private void updateCompteurs() {
        this.isUpdatingCompteurs = true;
        this.filters.getCompteurField().getItems().clear();
        this.filters.getCompteurField().getItems().add("Tous");
        for (Compteur compteur : VeloNantes.compteurDao.getAll()) {
            if (compteur.getLeQuartier().equals(this.quartier) || this.quartier == null) {
                String counter = compteur.getNomCompteur() + " " + compteur.getSens() + " " + compteur.getIdCompteur();
                this.filters.getCompteurField().getItems().add(counter);
            }
        }
        this.isUpdatingCompteurs = false;
        this.filters.getCompteurField().setValue("Tous");
    }

    /**
     * Update the graph
     */
    private void updateGraph() {
        if(this.start != null && this.end != null && this.type != null){
            this.graph.update(this.type, this.quartier, this.compteur, this.start, this.end);
        }
    }
}
