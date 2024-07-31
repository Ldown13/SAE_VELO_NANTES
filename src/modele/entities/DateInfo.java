package modele.entities;

import java.sql.*;
import java.util.ArrayList;

/**
 * The DateInfo class which represents the DateInfo table in the database
 * It's a model who can contain all attributes and methods to access at data
 * @author Groupe 4B2
 */
public class DateInfo {

    // ---------------- Attributes ---------------- //

    /**
     * La date
     * On utilise une date sql car elle est compatible avec JDBC
     */
    private Date date;
    /**
     * La température moyenne
     * On utilise un float car c'est un FLOAT dans la base de données
     */
    private float tempMoy;
    /**
     * Le jour associe
     * On utilise un enum car on a que 7 valeurs possibles
     */
    private Jour jour;
    /**
     * Les vacances associees
     * On utilise un enum car il y a que 6 vacances differentes + 1 pour la valeur "Nulle"
     */
    private Vacances vacances;
    /**
     * Les comptages associes
     * On utilise une liste de Comptage en plus de la reference vers la date pour simplifier l'acces aux donnees
     */
    private ArrayList<Comptage> lesComptages;

    // ---------------- Constructors ---------------- //

    /**
     * Default constructor
     * @param laDate the date
     * @param tempMoy the average temperature
     * @param jour the day
     * @param vacances the holidays
     * @throws IllegalArgumentException if laDate, jour or vacances is null
     */
    public DateInfo(Date laDate, float tempMoy, Jour jour, Vacances vacances) throws IllegalArgumentException {
        if(laDate == null){
            throw new IllegalArgumentException("laDate must not be null");
        }
        if(jour == null){
            throw new IllegalArgumentException("jour must not be null");
        }
        if(vacances == null){
            throw new IllegalArgumentException("vacances must not be null");
        }

        this.date = laDate;
        this.tempMoy = tempMoy;
        this.jour = jour;
        this.vacances = vacances;
        this.lesComptages = new ArrayList<Comptage>();
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the date
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date
     * @param date the date
     * @throws IllegalArgumentException if date is null
     */
    public void setDate(Date date) throws IllegalArgumentException{
        if(date == null){
            throw new IllegalArgumentException("date must not be null");
        }

        this.date = date;
    }

    /**
     * Get the average temperature
     * @return the average temperature
     */
    public float getTempMoy() {
        return tempMoy;
    }

    /**
     * Set the average temperature
     * @param tempMoy the average temperature
     */
    public void setTempMoy(float tempMoy) {
        this.tempMoy = tempMoy;
    }

    /**
     * Get the day
     * @return the day
     */
    public Jour getJour() {
        return jour;
    }

    /**
     * Set the day
     * @param jour the day
     * @throws IllegalArgumentException if jour is null
     */
    public void setJour(Jour jour) throws IllegalArgumentException{
        if(jour == null){
            throw new IllegalArgumentException("jour must not be null");
        }

        this.jour = jour;
    }

    /**
     * Get the holidays
     * @return the holidays
     */
    public Vacances getVacances() {
        return vacances;
    }

    /**
     * Set the holidays
     * @param vacances the holidays
     */
    public void setVacances(Vacances vacances) throws IllegalArgumentException{
        if(vacances == null){
            throw new IllegalArgumentException("vacances must not be null");
        }

        this.vacances = vacances;
    }

    /**
     * Get the Comptages
     * @return the Comptages
     */
    public ArrayList<Comptage> getLesComptages() {
        return lesComptages;
    }

    // ---------------- Add & Remove ---------------- //

    /**
     * Add a Comptage to the DateInfo
     * @param comptage the Comptage
     */
    public void addComptage(Comptage comptage) throws IllegalArgumentException{
        if(comptage == null){
            throw new IllegalArgumentException("comptage must not be null");
        }

        this.lesComptages.add(comptage);
    }

    /**
     * Remove a Comptage to the DateInfo
     * @param comptage the Comptage
     */
    public void removeComptage(Comptage comptage) throws IllegalArgumentException{
        if(comptage == null){
            throw new IllegalArgumentException("comptage must not be null");
        }

        this.lesComptages.remove(comptage);
    }

    // ---------------- Methods ---------------- //

    /**
     * Get the String representation of the DateInfo
     * Use a StringBuilder to concatenate String
     * @return the String representation of the DateInfo
     */
    public String toString() {
        StringBuilder ret = new StringBuilder("DateInfo(");
        ret.append("laDate : ").append(this.date).append(", ");
        ret.append("tempMoy : ").append(this.tempMoy).append(", ");
        ret.append("jour : ").append(this.jour.name()).append(", ");
        ret.append("vacances : ").append(this.vacances.name()).append(")");
        return ret.toString();
    }

    /**
     * Check if the DateInfo is in holidays
     * @return true if the DateInfo is in holidays, false otherwise
     */
    public boolean isInVacances(){
        boolean ret = true;
        if(this.vacances == Vacances.Nulle){
            ret = false;
        }
        return ret;
    }


    /**
     * Check if the DateInfo is in weekend
     * @return true if the DateInfo is in weekend, false otherwise
     */
    public boolean isInWeekend(){
        boolean ret = false;
        if(this.jour == Jour.Samedi || this.jour == Jour.Dimanche){
            ret = true;
        }
        return ret;
    }

    /**
     * Compute the total passage of the DateInfo
     * @return the total passage of the DateInfo
     */
    public int totalPassages(){
        int total = 0;
        for(Comptage c : this.lesComptages){
            total += c.totalPassages();
        }
        return total;
    }

    /**
     * Compute the average passage of the DateInfo
     * @return the average passage of the DateInfo
     */
    public float averagePassages(){
        float total = 0;
        for(Comptage c : this.lesComptages){
            total += c.averagePassages();
        }
        int nbComptages = this.lesComptages.size();
        if(nbComptages != 0){
            total /= nbComptages;
        }
        return total;
    }
    
    /**
     * Compute the total passage per hour of the DateInfo
     * @return the total passage per hour of the DateInfo
     */
    public int[] totalPassagesPerHour(){
        int[] total = new int[24];
        for(Comptage c : this.lesComptages){
            int[] totalComptage = c.getPassages();
            for(int i = 0; i < 24; i++){
                total[i] += totalComptage[i];
            }
        }
        return total;
    }

    /**
     * Compute the average passage per hour of the DateInfo
     * @return the average passage per hour of the DateInfo
     */
    public float[] averagePassagesPerHour(){
        float[] total = new float[24];
        for(Comptage c : this.lesComptages){
            int[] totalComptage = c.getPassages();
            for(int i = 0; i < 24; i++){
                total[i] += totalComptage[i];
            }
        }
        int nbComptages = this.lesComptages.size();
        if(nbComptages != 0){
            for(int i = 0; i < 24; i++){
                total[i] /= nbComptages;
            }
        }
        return total;
    }
}
