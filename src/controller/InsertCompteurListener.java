package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Compteur;
import modele.entities.Quartier;
import view.InsertCompteur;
import view.VeloNantes;

public class InsertCompteurListener implements ChangeListener<String>, EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private InsertCompteur insertCompteur;
    private String nomCompteur;
    private int idCompteur;
    private String sens;
    private float coordX;
    private float coordY;
    private Quartier quartier;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the InsertCompteurListener class
     * @param insertCompteur the insert compteur view
     * @throws IllegalArgumentException if insertCompteur is null
     */
    public InsertCompteurListener(InsertCompteur insertCompteur) throws IllegalArgumentException{
        if(insertCompteur == null){
            throw new IllegalArgumentException("InsertCompteurListener: insertCompteur cannot be null");
        }

        this.insertCompteur = insertCompteur;
        this.sens = "";
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle the changes events
     * @param observable the observable
     * @param before the value before
     * @param after the value after
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String before, String after) {
        this.insertCompteur.setOutput("");

        if(observable == insertCompteur.getNomCompteurField().textProperty()){
            if(after.length() > 0){
                this.nomCompteur = after;
            }else{
                this.insertCompteur.setOutput("Le nom du compteur ne peut pas être vide.");
            }
        }

        if(observable == insertCompteur.getIdCompteurField().textProperty()){
            try{
                this.idCompteur = Integer.parseInt(after);
            }catch(NumberFormatException e){
                this.insertCompteur.setOutput("L'id du compteur doit être un entier.");
            }
        }

        if(observable == insertCompteur.getSensField().textProperty()){
            this.sens = after;
        }

        if(observable == insertCompteur.getCoordXField().textProperty()){
            try{
                this.coordX = Float.parseFloat(after);
            }catch(NumberFormatException e){
                this.insertCompteur.setOutput("La coordonnée X doit être un nombre.");
            }
        }

        if(observable == insertCompteur.getCoordYField().textProperty()){
            try{
                this.coordY = Float.parseFloat(after);
            }catch(NumberFormatException e){
                this.insertCompteur.setOutput("La coordonnée Y doit être un nombre.");
            }
        }
        
        if(observable == insertCompteur.getQuartierField().valueProperty()){
            String[] quartierSplit = after.split(" ");
            int idQuartier = Integer.parseInt(quartierSplit[quartierSplit.length - 1]);
            this.quartier = VeloNantes.quartierDao.get(idQuartier);
        }
    }

    /**
     * Handle the action event
     * @param event the event to handle
     */
    @Override
    public void handle(ActionEvent event){
        try{
            Compteur compteur = new Compteur(this.idCompteur, this.nomCompteur, this.sens, this.coordX, this.coordY, this.quartier);
            VeloNantes.compteurDao.add(compteur);
            this.insertCompteur.setOutput("Compteur ajouté.");
        }catch(Exception e){
            System.out.println(e);
            this.insertCompteur.setOutput(e.getMessage());
        }
    }
}
