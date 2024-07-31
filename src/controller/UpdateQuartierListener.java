package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Quartier;
import view.UpdateQuartier;
import view.VeloNantes;

public class UpdateQuartierListener implements ChangeListener<String>, EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private UpdateQuartier updateQuartier;
    private Quartier quartier;
    private String nomQuartier;
    private int idQuartier;
    private float longueurPisteVelo;

    // ---------------- Constructor ---------------- //
    
    /**
     * Constructor of the UpdateQuartierListener class
     * @param updateQuartier the update quartier view
     * @throws IllegalArgumentException if updateQuartier is null
     */
    public UpdateQuartierListener(UpdateQuartier updateQuartier) throws IllegalArgumentException{
        if(updateQuartier == null){
            throw new IllegalArgumentException("UpdateQuartierListener: updateQuartier cannot be null");
        }

        this.updateQuartier = updateQuartier;
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
        this.updateQuartier.setOutput("");

        if(observable == this.updateQuartier.getQuartierField().valueProperty()){
            String[] splitQuartier = after.split(" ");
            this.idQuartier = Integer.parseInt(splitQuartier[splitQuartier.length - 1]);
            this.updateQuartier.setIdQuartierField(idQuartier);
            this.quartier = VeloNantes.quartierDao.get(this.idQuartier);
            this.updateQuartier.setOutput("Quartier sélectionné: " + this.quartier);

            this.updateQuartier.setNomQuartierField(this.quartier.getNomQuartier());
            this.updateQuartier.setLongueurPisteVeloField(this.quartier.getLongueurPisteVelo());
        }

        if(observable == this.updateQuartier.getNomQuartierField().textProperty()){
            if(after.length() > 0){
                this.nomQuartier = after;
            }else{
                this.updateQuartier.setOutput("Le nom du quartier ne peut pas être vide.");
            }
        }

        if(observable == this.updateQuartier.getLongueurPisteVeloField().textProperty()){
            try{
                this.longueurPisteVelo = Float.parseFloat(after);
            }catch(NumberFormatException e){
                this.updateQuartier.setOutput("La longueur de la piste cyclable doit être un nombre.");
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
            this.quartier.setNomQuartier(this.nomQuartier);
            this.quartier.setLongueurPisteVelo(this.longueurPisteVelo);
            VeloNantes.quartierDao.update(quartier);
            this.updateQuartier.setOutput("Quartier mis à jour: " + this.quartier);
        }catch(Exception e){
            System.out.println(e);
            this.updateQuartier.setOutput(e.getMessage());
        }
    }
}
