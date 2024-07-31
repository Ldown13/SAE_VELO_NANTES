package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.entities.Comptage;
import modele.entities.Compteur;
import modele.entities.Quartier;
import view.RemoveQuartier;
import view.VeloNantes;

public class RemoveQuartierListener implements ChangeListener<String>, EventHandler<ActionEvent> {

    // ---------------- Attributes ---------------- //

    private RemoveQuartier removeQuartier;
    private Quartier quartier;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the RemoveQuartierListener class
     * @param removeQuartier the remove quartier view
     * @throws IllegalArgumentException if removeQuartier is null
     */
    public RemoveQuartierListener(RemoveQuartier removeQuartier) throws IllegalArgumentException{
        if(removeQuartier == null){
            throw new IllegalArgumentException("RemoveQuartierListener: removeQuartier cannot be null");
        }

        this.removeQuartier = removeQuartier;
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
        String[] splitQuartier = after.split(" ");
        int idQuartier = Integer.parseInt(splitQuartier[splitQuartier.length - 1]);
        this.quartier = VeloNantes.quartierDao.get(idQuartier);
        System.out.println("RemoveQuartierListener: " + quartier);
        this.removeQuartier.setNomQuartierField(this.quartier.getNomQuartier());
        this.removeQuartier.setIdQuartierField(this.quartier.getIdQuartier());
        this.removeQuartier.setLongueurPisteVeloField(this.quartier.getLongueurPisteVelo());
    }
    
    /**
     * handle the action events
     * @param event the event
     */
    @Override
    public void handle(ActionEvent event) {
        try{
            for(Compteur compteur : quartier.getLesCompteurs()){
                for(Comptage comptage : compteur.getLesComptages()){
                    VeloNantes.comptageDao.remove(comptage);
                }
                VeloNantes.compteurDao.remove(compteur);
            }
            VeloNantes.quartierDao.remove(this.quartier);

            this.removeQuartier.setOutput("Le quartier a été supprimé");
        }catch(Exception e){
            System.out.println(e);
            this.removeQuartier.setOutput(e.getMessage());
        }
    }
}
