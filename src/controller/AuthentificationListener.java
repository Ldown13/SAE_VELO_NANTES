package controller;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import view.Authentification;
import view.VeloNantes;


public class AuthentificationListener implements EventHandler<ActionEvent> {
    
    // ---------------- Attributes ---------------- //

    private Authentification authentification;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the AuthentificationListener class
     * @param authentification the authentification view
     * @throws IllegalArgumentException if authentification is null
     */
    public AuthentificationListener(Authentification authentification) throws IllegalArgumentException{
        if(authentification == null){
            throw new IllegalArgumentException("ButtonListener: authentification cannot be null");
        }

        this.authentification = authentification;
    }

    // ---------------- Methods ---------------- //

    /**
     * Handle the action event
     * @param event the event to handle
     */
    @Override
    public void handle(ActionEvent event){
        String login = this.authentification.getLogin().getText();
        String password = this.authentification.getPassword().getText();

        try{
            VeloNantes.database.openWriteConnection(login, password);
            this.authentification.setError("Authentification réussie.");
            this.authentification.getLeftBar().toConnected(login);
        }catch(Exception e){
            this.authentification.setError(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
