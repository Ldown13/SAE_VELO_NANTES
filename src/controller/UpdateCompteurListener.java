package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Compteur;
import modele.entities.Quartier;
import view.UpdateCompteur;
import view.VeloNantes;

public class UpdateCompteurListener implements ChangeListener<String>, EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private UpdateCompteur updateCompteur;
    private Compteur compteur;
    private String nomCompteur;
    private int idCompteur;
    private String sens;
    private float coordX;
    private float coordY;
    private Quartier quartier;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the UpdateCompteurListener class
     * @param updateCompteur the update compteur view
     * @throws IllegalArgumentException if updateCompteur is null
     */
    public UpdateCompteurListener(UpdateCompteur updateCompteur) throws IllegalArgumentException{
        if(updateCompteur == null){
            throw new IllegalArgumentException("UpdateCompteurListener: updateCompteur cannot be null");
        }

        this.updateCompteur = updateCompteur;
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle changes events
     * @param observable the observable
     * @param before the value before
     * @param after the value after
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String before, String after) {
        this.updateCompteur.setOutput("");

        if(observable == updateCompteur.getCompteurField().valueProperty()){
            String[] splitCompteur = after.split(" ");
            this.idCompteur = Integer.parseInt(splitCompteur[splitCompteur.length - 1]);
            this.compteur = VeloNantes.compteurDao.get(this.idCompteur);
            this.nomCompteur = this.compteur.getNomCompteur();
            this.sens = this.compteur.getSens();
            this.coordX = this.compteur.getCoordX();
            this.coordY = this.compteur.getCoordY();
            this.quartier = this.compteur.getLeQuartier();

            this.updateCompteur.setNomCompteurField(this.compteur.getNomCompteur());
            this.updateCompteur.setIdCompteurField(this.compteur.getIdCompteur());
            this.updateCompteur.setSensField(this.compteur.getSens());
            this.updateCompteur.setCoordXField(this.compteur.getCoordX());
            this.updateCompteur.setCoordYField(this.compteur.getCoordY());
            this.updateCompteur.setQuartierField(this.compteur.getLeQuartier().getNomQuartier() + " " + this.compteur.getLeQuartier().getIdQuartier());
        }

        if(observable == updateCompteur.getNomCompteurField().textProperty()){
            if(after.length() > 0){
                this.nomCompteur = after;
            } else {
                this.updateCompteur.setOutput("Le nom du compteur ne peut pas être vide");
            }
        }

        if(observable == updateCompteur.getSensField().textProperty()){
            this.sens = after;
        }

        if(observable == updateCompteur.getCoordXField().textProperty()){
            try{
                this.coordX = Float.parseFloat(after);
            } catch(Exception e){
                this.updateCompteur.setOutput("La coordonnée X doit être un nombre");
            }
        }

        if(observable == updateCompteur.getCoordYField().textProperty()){
            try{
                this.coordY = Float.parseFloat(after);
            } catch(Exception e){
                this.updateCompteur.setOutput("La coordonnée Y doit être un nombre");
            }
        }

        if(observable == updateCompteur.getQuartierField().valueProperty()){
            String[] splitQuartier = after.split(" ");
            int idQuartier = Integer.parseInt(splitQuartier[splitQuartier.length - 1]);
            this.quartier = VeloNantes.quartierDao.get(idQuartier);
        }
    }

    /**
     * Handle action events
     * @param event the event
     */
    @Override
    public void handle(ActionEvent event){
        try{
            this.compteur.setNomCompteur(this.nomCompteur);
            this.compteur.setSens(this.sens);
            this.compteur.setCoordX(this.coordX);
            this.compteur.setCoordY(this.coordY);
            this.compteur.setLeQuartier(this.quartier);

            VeloNantes.compteurDao.update(this.compteur);

            this.updateCompteur.setOutput("Le compteur a été mis à jour");
        } catch(Exception e){
            System.out.println(e);
            this.updateCompteur.setOutput(e.getMessage());
        }
    }
}
