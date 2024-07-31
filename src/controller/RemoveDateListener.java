package controller;

import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Comptage;
import modele.entities.DateInfo;
import view.RemoveDate;
import view.VeloNantes;

public class RemoveDateListener implements ChangeListener<LocalDate>, EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private RemoveDate removeDate;
    private DateInfo dateInfo;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the RemoveDateListener class
     * @param removeDate the remove date view
     * @throws IllegalArgumentException if removeDate is null
     */
    public RemoveDateListener(RemoveDate removeDate) throws IllegalArgumentException{
        if(removeDate == null){
            throw new IllegalArgumentException("RemoveDateListener: RemoveDate cannot be null");
        }
        this.removeDate = removeDate;
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle the changes events
     * @param observable the observable object
     * @param before the object before the change
     * @param after the object after the change
     */
    @Override
    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate before, LocalDate after) {
        this.removeDate.setOutput("");
        
        this.dateInfo = VeloNantes.dateInfoDao.get(Date.valueOf(after));
        if(this.dateInfo == null){
            this.removeDate.setOutput("Cette date n'est pas dans la base de données");
        } else {        
            this.removeDate.setTempMoyField(dateInfo.getTempMoy());
            this.removeDate.setJourField(dateInfo.getJour());
            this.removeDate.setVacancesField(dateInfo.getVacances());
        }
        System.out.println("RemoveDateListener: " + dateInfo);
    }

    /**
     * Handle the action events
     * @param event the event
     */
    @Override
    public void handle(ActionEvent event) {
        try{
            for(Comptage comptage : dateInfo.getLesComptages()){
                VeloNantes.comptageDao.remove(comptage);
            }
            VeloNantes.dateInfoDao.remove(this.dateInfo);
            this.removeDate.setOutput("La date a été supprimée");
        }catch(Exception e){
            System.out.println(e);
            this.removeDate.setOutput(e.getMessage());
        }
    }
    
}
