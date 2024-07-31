package controller;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import modele.csv.ReadComptageCsv;
import modele.entities.Comptage;
import modele.entities.Compteur;
import modele.entities.DateInfo;
import modele.entities.PresenceAnomalie;
import view.InsertComptage;
import view.VeloNantes;

public class InsertComptageListener implements ChangeListener<Object>, EventHandler<ActionEvent>{

    // ---------------- Attributes ---------------- //

    private InsertComptage insertComptage;
    private Compteur compteur;
    private DateInfo dateInfo;
    private PresenceAnomalie anomalie;
    private int[] passages;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the InsertComptageListener class
     * @param insertComptage the insert comptage view
     * @throws IllegalArgumentException if insertComptage is null
     */
    public InsertComptageListener(InsertComptage insertComptage) throws IllegalArgumentException{
        if(insertComptage == null){
            throw new IllegalArgumentException("InsertComptageListener: insertComptage cannot be null");
        }

        this.insertComptage = insertComptage;
        this.passages = new int[24];
    }
    
    // ---------------- Methods ---------------- //

    /**
     * Handle the changes events
     * @param observable the observable object
     * @param before the object before the change
     * @param after the object after the change
     */
    @Override
    public void changed(ObservableValue<? extends Object> observable, Object before, Object after){
        this.insertComptage.setOutput("");

        if(observable == this.insertComptage.getDateField().valueProperty()){
            LocalDate date = (LocalDate) after;
            this.dateInfo = VeloNantes.dateInfoDao.get(Date.valueOf(date));
        }

        if(observable == this.insertComptage.getCompteurField().valueProperty()){
            String compteurString = (String) after;
            String[] splitCompteur = compteurString.split(" ");
            int idCompteur = Integer.parseInt(splitCompteur[splitCompteur.length - 1]);
            this.compteur = VeloNantes.compteurDao.get(idCompteur);
        }

        if(observable == this.insertComptage.getAnomalieField().valueProperty()){
            String anomalieString = (String) after;
            this.anomalie = PresenceAnomalie.valueOf(anomalieString);
        }

        TextField[] passagesField = this.insertComptage.getPassagesFields();
        for(int i = 0; i < 24; i++){
            if(observable == passagesField[i].textProperty()){
                try{
                    this.passages[i] = Integer.parseInt(passagesField[i].getText());
                }catch(NumberFormatException e){
                    this.insertComptage.setOutput("Le nombre de passages doit être un entier");
                }
            }
        }
    }

    /**
     * Handle the action event
     * @param event the event to handle
     */
    @Override
    public void handle(ActionEvent event){
        if(event.getSource() == this.insertComptage.getInsertButton()){
            try{
                Comptage comptage = new Comptage(passages, anomalie, compteur, dateInfo);
                VeloNantes.comptageDao.add(comptage);
                this.insertComptage.setOutput("Le comptage a été ajouté");
            }catch(Exception e){
                System.out.println(e);
                this.insertComptage.setOutput(e.getMessage());
            }
        }

        if(event.getSource() == this.insertComptage.getInsertCsvButton()){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ouvrir un fichier CSV");
            File file = fileChooser.showOpenDialog(VeloNantes.scene.getWindow());
            if (file != null) {
                ReadComptageCsv readComptageCsv = new ReadComptageCsv(VeloNantes.dateInfoDao, VeloNantes.compteurDao, VeloNantes.comptageDao);
                try{
                    readComptageCsv.read(file);
                    this.insertComptage.setOutput("Le fichier CSV a été ajouté");
                }catch(Exception e){
                    System.out.println(e);
                    this.insertComptage.setOutput(e.getMessage());
                }
            }
        }
    }
}
