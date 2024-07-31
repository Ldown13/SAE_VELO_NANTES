package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Quartier;
import view.InsertQuartier;
import view.VeloNantes;

public class InsertQuartierListener implements ChangeListener<String>, EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private InsertQuartier insertQuartier;
    private String nomQuartier;
    private int idQuartier;
    private float longueurPisteVelo;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the InsertQuartierListener class
     * @param insertQuartier the insert quartier view
     * @throws IllegalArgumentException if insertQuartier is null
     */
    public InsertQuartierListener(InsertQuartier insertQuartier) throws IllegalArgumentException{
        if(insertQuartier == null){
            throw new IllegalArgumentException("InsertQuartierListener: insertQuartier cannot be null");
        }

        this.insertQuartier = insertQuartier;
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
        this.insertQuartier.setOutput("");

        if(observable == this.insertQuartier.getNomQuartierField().textProperty()){
            if(after.length() > 0){
                this.nomQuartier = after;
            }else{
                this.insertQuartier.setOutput("Le nom du quartier ne peut pas être vide.");
            }
        }

        if(observable == this.insertQuartier.getIdQuartierField().textProperty()){
            try{
                this.idQuartier = Integer.parseInt(after);
            }catch(NumberFormatException e){
                this.insertQuartier.setOutput("L'id du quartier doit être un entier.");
            }
        }

        if(observable == this.insertQuartier.getLongueurPisteVeloField().textProperty()){
            try{
                this.longueurPisteVelo = Float.parseFloat(after);
            }catch(NumberFormatException e){
                this.insertQuartier.setOutput("La longueur de la piste cyclable doit être un nombre.");
            }
        }
    }

    /**
     * Handle the action event
     * @param event the event to handle
     */
    @Override
    public void handle(ActionEvent event) {
        try{
            Quartier quartier = new Quartier(this.idQuartier, this.nomQuartier, this.longueurPisteVelo);
            VeloNantes.quartierDao.add(quartier);
            this.insertQuartier.setOutput("Le quartier a bien été ajouté.");
        }catch(Exception e){
            System.out.println(e);
            this.insertQuartier.setOutput(e.getMessage());
        }
    }
}
