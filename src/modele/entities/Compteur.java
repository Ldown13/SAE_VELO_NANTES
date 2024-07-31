package modele.entities;

import java.sql.Date;
import java.util.ArrayList;

/**
 * The Compteur class which represents the Compteur table in the database
 * It's a model who can contain all attributes and methods to access at data
 * @author Groupe 4B2
 */
public class Compteur{

    // ---------------- Attributes ---------------- //

    /**
     * L'id du compteur
     * On utilise un int comme dans la base de données
     */
    private int idCompteur;
    /**
     * Le nom du compteur
     * On utilise un String car c'est un VARCHAR dans la base de données
     */
    private String nomCompteur;
    /**
     * Le sens du compteur
     * On utilise un String car c'est un VARCHAR dans la base de données
     * On utilise pas d'enum car il y a plus de sens que les simples 4 directions
     */
    private String sens;
    /**
     * La coordonnée X (latitude) du compteur
     * On utilise un float car c'est un FLOAT dans la base de données
     */
    private float coordX;
    /**
     * La coordonnée Y (longitude) du compteur
     * On utilise un float car c'est un FLOAT dans la base de données
     */
    private float coordY;
    /**
     * Le quartier du compteur
     * On utilise une reference vers un objet Quartier car c'est un FOREIGN KEY dans la base de données
     */
    private Quartier leQuartier;
    /**
     * Les comptages associes
     * On utilise une liste de Comptage en plus de la reference vers le compteur pour simplifier l'acces aux donnees
     */
    private ArrayList<Comptage> lesComptages;

    // ---------------- Constructors ---------------- //

    /**
     * Default constructor
     * @param idCompteur the id of the Compteur
     * @param nomCompteur the name of the Compteur
     * @param sens the direction of the Compteur
     * @param coordX the X coordinate of the Compteur
     * @param coordY the Y coordinate of the Compteur
     * @param leQuartier the Quartier of the Compteur
     * @throws IllegalArgumentException if one of the parameters is null
     */
    public Compteur(int idCompteur, String nomCompteur, String sens, float coordX, float coordY, Quartier leQuartier) throws IllegalArgumentException {
        if(nomCompteur == null){
            throw new IllegalArgumentException("nomCompteur must not be null");
        }
        if(sens == null){
            throw new IllegalArgumentException("sens must not be null");
        }
        if(leQuartier == null){
            throw new IllegalArgumentException("leQuartier must not be null");
        }

        this.idCompteur = idCompteur;
        this.nomCompteur = nomCompteur;
        this.sens = sens;
        this.coordX = coordX;
        this.coordY = coordY;
        this.leQuartier = leQuartier;
        this.lesComptages = new ArrayList<Comptage>();
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the id of the Compteur
     * @return the id of the Compteur
     */
    public int getIdCompteur() {
        return idCompteur;
    }

    /**
     * Set the id of the Compteur
     * @param idCompteur the id of the Compteur
     */
    public void setIdCompteur(int idCompteur) {
        this.idCompteur = idCompteur;
    }

    /**
     * Get the name of the Compteur
     * @return the name of the Compteur
     */
    public String getNomCompteur() {
        return nomCompteur;
    }

    /**
     * Set the name of the Compteur
     * @param nomCompteur the name of the Compteur
     * @throws IllegalArgumentException if nomCompteur is null
     */
    public void setNomCompteur(String nomCompteur) throws IllegalArgumentException{
        if(nomCompteur == null){
            throw new IllegalArgumentException("nomCompteur is null");
        }

        this.nomCompteur = nomCompteur;
    }

    /**
     * Get the direction of the Compteur
     * @return the direction of the Compteur
     */
    public String getSens() {
        return sens;
    }

    /**
     * Set the direction of the Compteur
     * @param sens the direction of the Compteur
     * @throws IllegalArgumentException if sens is null
     */
    public void setSens(String sens) throws IllegalArgumentException{
        if(sens == null){
            throw new IllegalArgumentException("sens is null");
        }

        this.sens = sens;
    }

    /**
     * Get the X coordinate of the Compteur
     * @return the X coordinate of the Compteur
     */
    public float getCoordX() {
        return coordX;
    }

    /**
     * Set the X coordinate of the Compteur
     * @param coordX the X coordinate of the Compteur
     */
    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    /**
     * Get the Y coordinate of the Compteur
     * @return the Y coordinate of the Compteur
     */
    public float getCoordY() {
        return coordY;
    }

    /**
     * Set the Y coordinate of the Compteur
     * @param coordY the Y coordinate of the Compteur
     */
    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    /**
     * Get the Quartier of the Compteur
     * @return the Quartier of the Compteur
     */
    public Quartier getLeQuartier() {
        return leQuartier;
    }

    /**
     * Set the Quartier of the Compteur
     * @param leQuartier the Quartier of the Compteur
     * @throws IllegalArgumentException if leQuartier is null
     */
    public void setLeQuartier(Quartier leQuartier) throws IllegalArgumentException{
        if(leQuartier == null){
            throw new IllegalArgumentException("leQuartier is null");
        }

        this.leQuartier = leQuartier;
    }

    /**
     * Get the Comptage of the Compteur by date
     * @param laDate the date of the Comptage
     * @return the Comptage of the Compteur by date
     * @throws IllegalArgumentException if laDate is null
     */
    public Comptage getComptageByDate(DateInfo laDate) throws IllegalArgumentException{
        if(laDate == null){
            throw new IllegalArgumentException("laDate is null");
        }

        for(Comptage c : lesComptages){
            if(c.getLaDate().equals(laDate)){
                return c;
            }
        }
        return null;
    }

    /**
     * Get the Comptages of the Compteur
     * @return the Comptages of the Compteur
     */
    public ArrayList<Comptage> getLesComptages() {
        return lesComptages;
    }

    /**
     * Set the Comptages of the Compteur
     * @param lesComptages the Comptages of the Compteur
     * @throws IllegalArgumentException if lesComptages is null
     */
    public void setLesComptages(ArrayList<Comptage> lesComptages) throws IllegalArgumentException{
        if(lesComptages == null){
            throw new IllegalArgumentException("lesComptages is null");
        }

        this.lesComptages = lesComptages;
    }

    // ---------------- Add & Remove ---------------- //

    /**
     * Add a Comptage to the Compteur
     * @param comptage the Comptage to add
     * @throws IllegalArgumentException if comptage is null
     */
    public void addComptage(Comptage comptage) throws IllegalArgumentException{
        if(comptage == null){
            throw new IllegalArgumentException("comptage is null");
        }
        this.lesComptages.add(comptage);
    }

    /**
     * Remove a Comptage to the Compteur
     * @param comptage the Comptage to remove
     * @throws IllegalArgumentException if comptage is null
     */
    public void removeComptage(Comptage comptage) throws IllegalArgumentException{
        if(comptage == null){
            throw new IllegalArgumentException("comptage is null");
        }
        this.lesComptages.remove(comptage);
    }

    // ---------------- Methods ---------------- //

    /**
     * To String method
     * Use a StringBuilder to concatenate String
     * @return the String of the Compteur
     */
    public String toString() {
        StringBuilder ret = new StringBuilder("Compteur(");
        ret.append("idCompteur : ").append(this.idCompteur).append(", ");
        ret.append("nomCompteur : ").append(this.nomCompteur).append(", ");
        ret.append("sens : ").append(this.sens).append(", ");
        ret.append("coordX : ").append(this.coordX).append(", ");
        ret.append("coordY : ").append(this.coordY).append(", ");
        ret.append("leQuartier : ").append(this.leQuartier.getIdQuartier()).append(")");
        return ret.toString();
    }


    /**
     * Compute the total passage of the Compteur
     * @param dateDebut the start date to compute
     * @param dateFin the end date to compute
     * @return the total passage of the Compteur
     * @throws IllegalArgumentException if dateDebut or dateFin is null
     */
    public int totalPassages(DateInfo dateDebut, DateInfo dateFin) throws IllegalArgumentException {
        if(dateDebut == null){
            throw new IllegalArgumentException("dateDebut is null");
        }
        if(dateFin == null){
            throw new IllegalArgumentException("dateFin is null");
        }

        int total = 0;
        Date debut = dateDebut.getDate();
        Date fin = dateFin.getDate();
        for(Comptage c : this.lesComptages){
            DateInfo laDate = c.getLaDate();
            Date date = laDate.getDate();
            if((date.after(debut) && date.before(fin))){
                total += c.totalPassages();
            }
        }
        return total;
    }

    /**
     * Compute the average passage of the Compteur
     * @param dateDebut the start date to compute
     * @param dateFin the end date to compute
     * @return the average passage of the Compteur
     * @throws IllegalArgumentException if either dateDebut or dateFin is null
     */
    public float averagePassages(DateInfo dateDebut, DateInfo dateFin) throws IllegalArgumentException{
        if(dateDebut == null){
            throw new IllegalArgumentException("dateDebut is null");
        }
        if(dateFin == null){
            throw new IllegalArgumentException("dateFin is null");
        }

        float total = 0;
        int nbComptages = 0;
        Date debut = dateDebut.getDate();
        Date fin = dateFin.getDate();
        for(Comptage c : this.lesComptages){
            Date date = c.getLaDate().getDate();
            if(date.after(debut) && date.before(fin)){
                nbComptages++;
                total += c.averagePassages();
            }
        }
        if(nbComptages != 0){
            total /= nbComptages;
        }
        return total;
    }


    /**
     * Compute the total passage per hour of the Compteur
     * @param dateDebut the start date to compute
     * @param dateFin the end date to compute
     * @return the total passage per hour of the Compteur
     * @throws IllegalArgumentException if either dateDebut or dateFin is null
     */
    public int[] totalPassagesPerHour(DateInfo dateDebut, DateInfo dateFin) throws IllegalArgumentException {
        if(dateDebut == null){
            throw new IllegalArgumentException("dateDebut is null");
        }
        if(dateFin == null){
            throw new IllegalArgumentException("dateFin is null");
        }

        int[] total = new int[24];
        Date debut = dateDebut.getDate();
        Date fin = dateFin.getDate();
        for(Comptage c : this.lesComptages){
            Date date = c.getLaDate().getDate();
            if(date.after(debut) && date.before(fin)){
                int[] passages = c.getPassages();
                for(int i = 0; i < 24; i++){
                    total[i] += passages[i];
                }
            }
        }
        return total;
    }

    /**
     * Compute the average passage per hour of the Compteur
     * @param dateDebut the start date to compute
     * @param dateFin the end date to compute
     * @return the average passage per hour of the Compteur
     * @throws IllegalArgumentException if either dateDebut or dateFin is null
     */
    public float[] averagePassagesPerHour(DateInfo dateDebut, DateInfo dateFin) throws IllegalArgumentException {
        if(dateDebut == null){
            throw new IllegalArgumentException("dateDebut is null");
        }
        if(dateFin == null){
            throw new IllegalArgumentException("dateFin is null");
        }

        float[] total = new float[24];
        int nbComptages = 0;
        Date debut = dateDebut.getDate();
        Date fin = dateFin.getDate();
        for(Comptage c : this.lesComptages){
            Date date = c.getLaDate().getDate();
            if(date.after(debut) && date.before(fin)){
                nbComptages++;
                int[] passages = c.getPassages();
                for(int i = 0; i < 24; i++){
                    total[i] += passages[i];
                }
            }
        }
        if(nbComptages != 0){
            for(int i = 0; i < 24; i++){
                total[i] /= nbComptages;
            }
        }
        return total;
    }


    /**
     * Compute the total passage per day of the Compteur
     * @param dateDebut the start date to compute
     * @param dateFin the end date to compute
     * @return the total passage per day of the Compteur
     * @throws IllegalArgumentException if either dateDebut or dateFin is null
     */
    public int[] totalPassagesPerDay(DateInfo dateDebut, DateInfo dateFin) throws IllegalArgumentException {
        if(dateDebut == null){
            throw new IllegalArgumentException("dateDebut is null");
        }
        if(dateFin == null){
            throw new IllegalArgumentException("dateFin is null");
        }

        int[] total = new int[7];
        Date debut = dateDebut.getDate();
        Date fin = dateFin.getDate();
        for(Comptage c : this.lesComptages){
            Date date = c.getLaDate().getDate();
            if(date.after(debut) && date.before(fin)){
                int[] passages = c.getPassages();
                Jour jour = c.getLaDate().getJour();
                for(int i = 0; i < 24; i++){
                    total[jour.ordinal()] += passages[i];
                }
            }
        }
        return total;
    }


    /**
     * Compute the average passage per day of the Compteur
     * @param dateDebut the start date to compute
     * @param dateFin the end date to compute
     * @return the average passage per day of the Compteur
     * @throws IllegalArgumentException if either dateDebut or dateFin is null
     */
    public float[] averagePassagesPerDay(DateInfo dateDebut, DateInfo dateFin) throws IllegalArgumentException {
        if(dateDebut == null){
            throw new IllegalArgumentException("dateDebut is null");
        }
        if(dateFin == null){
            throw new IllegalArgumentException("dateFin is null");
        }

        float[] total = new float[7];
        int nbComptages = 0;
        Date debut = dateDebut.getDate();
        Date fin = dateFin.getDate();
        for(Comptage c : this.lesComptages){
            Date date = c.getLaDate().getDate();
            if(date.after(debut) && date.before(fin)){
                nbComptages++;
                int[] passages = c.getPassages();
                Jour jour = c.getLaDate().getJour();
                for(int i = 0; i < 24; i++){
                    total[jour.ordinal()] += passages[i];
                }
            }
        }
        if(nbComptages != 0){
            for(int i = 0; i < 7; i++){
                total[i] /= nbComptages;
            }
        }
        return total;
    }
}