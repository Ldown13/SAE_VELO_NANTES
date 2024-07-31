package controller;

import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Comptage;
import modele.entities.Compteur;
import modele.entities.DateInfo;
import view.RemoveComptage;
import view.VeloNantes;

public class RemoveComptageListener implements ChangeListener<Object>, EventHandler<ActionEvent> {

    // ---------------- Attributes ---------------- //

    private RemoveComptage removeComptage;
    private Compteur compteur;
    private DateInfo dateInfo;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the RemoveComptageListener class
     * @param removeComptage the remove comptage view
     * @throws IllegalArgumentException if removeComptage is null
     */
    public RemoveComptageListener(RemoveComptage removeComptage) throws IllegalArgumentException {
        if (removeComptage == null) {
            throw new IllegalArgumentException("RemoveComptageListener: removeComptage cannot be null");
        }

        this.removeComptage = removeComptage;
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle the changes events
     * @param observable the observable object
     * @param before the object before the change
     * @param after the object after the change
     */
    @Override
    public void changed(ObservableValue<?> observable, Object before, Object after) {
        if (after instanceof String) {
            String compteurString = (String) after;
            String[] splitCompteur = compteurString.split(" ");
            int idCompteur = Integer.parseInt(splitCompteur[splitCompteur.length - 1]);
            this.compteur = VeloNantes.compteurDao.get(idCompteur);
            this.update();
        } else if (after instanceof LocalDate) {
            LocalDate date = (LocalDate) after;
            this.dateInfo = VeloNantes.dateInfoDao.get(Date.valueOf(date));
            this.update();
        }
    }

    /**
     * Update the view
     */
    public void update(){
        if(this.compteur != null && this.dateInfo != null){
            Comptage comptage = VeloNantes.comptageDao.get(this.dateInfo, this.compteur);
            if(comptage == null){
                this.removeComptage.setOutput("Ce comptage n'est pas dans la base de données");
            } else {
                this.removeComptage.setAnomalieField(comptage.getAnomalie());
                this.removeComptage.setPassagesFields(comptage.getPassages());
            }
        }
        if(this.dateInfo == null){
            this.removeComptage.setOutput("Cette date n'est pas dans la base de données");
        }
    }
    
    /**
     * Handle the action events
     * @param event the event
     */
    @Override
    public void handle(ActionEvent event) {
        try{
            Comptage comptage = VeloNantes.comptageDao.get(this.dateInfo, this.compteur);
            VeloNantes.comptageDao.remove(comptage);
            this.removeComptage.setOutput("Comptage supprimé");
        }catch(Exception e){
            System.out.println(e);
            this.removeComptage.setOutput(e.getMessage());
        }
    }
}
