package modele.entities;

import java.util.Arrays;

/**
 * The Comptage class which represents the Comptage table in the database
 * @author Groupe 4B2
 */
public class Comptage{

    // ---------------- Attributes ---------------- //

    /**
     * Le nombre de passages par heures
     * Comme on aura toujours 24 heures, on peut utiliser un tableau de taille 24 au lieu d'une liste
     */
    private int[] passages;
    /**
     * La presence d'anomalie dans les donnees
     * Vue que l'on a que 3 valeurs possibles (Forte, Faible, Nulle), on peut utiliser un enum
     */
    private PresenceAnomalie anomalie;
    /**
     * Le compteur associe
     * On utilise une reference vers un objet Compteur car c'est un FOREIGN KEY dans la base de données
     */
    private Compteur leCompteur;
    /**
     * La date associee
     * On utilise une reference vers la date associee
     * On reproduit ainsi la foreign key de la table Comptage
     */
    private DateInfo laDate;

    // ---------------- Constructors ---------------- //

    /**
     * Default constructor
     * @param passages the number of passages
     * @param anomalie the anomaly
     * @param leCompteur the Compteur
     * @param laDate the DateInfo
     * @throws IllegalArgumentException if passages, anomalie, leCompteur or laDate is null
     */
    public Comptage(int[] passages, PresenceAnomalie anomalie, Compteur leCompteur, DateInfo laDate) throws IllegalArgumentException {
        if(passages == null){
            throw new IllegalArgumentException("passages must not be null");
        }
        if(anomalie == null){
            throw new IllegalArgumentException("anomalie must not be null");
        }
        if(leCompteur == null){
            throw new IllegalArgumentException("leCompteur must not be null");
        }
        if(laDate == null){
            throw new IllegalArgumentException("laDate must not be null");
        }

        this.passages = passages;
        this.anomalie = anomalie;
        this.leCompteur = leCompteur;
        this.laDate = laDate;
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the number of passages
     * @param heure the hour
     * @return the number of passages
     * @throws IllegalArgumentException if heure is not between 0 and 23
     */
    public int getPassage(int heure) throws IllegalArgumentException {
        if(heure < 0 || heure > 23){
            throw new IllegalArgumentException("heure must be between 0 and 23");
        }

        return passages[heure];
    }

    /**
     * Set the number of passages
     * @param heure the hour
     * @param passages the number of passages
     * @throws IllegalArgumentException if heure is not between 0 and 23 or if passages is negative
     */
    public void setPassage(int heure, int passages) throws IllegalArgumentException {
        if(heure < 0 || heure > 23){
            throw new IllegalArgumentException("heure must be between 0 and 23");
        }
        if(passages < 0){
            throw new IllegalArgumentException("passages must be positive");
        }

        this.passages[heure] = passages;
    }

    /**
     * Get the number of passages for every hour
     * @return the number of passages for every hour
     */
    public int[] getPassages() {
        return passages;
    }

    /**
     * Set the number of passages for every hour
     * @param passages the number of passages for every hour
     * @throws IllegalArgumentException if passages is null
     */
    public void setPassages(int[] passages) throws IllegalArgumentException {
        if(passages == null){
            throw new IllegalArgumentException("passages must not be null");
        }
        if(passages.length != 24){
            throw new IllegalArgumentException("passages must have a length of 24");
        }

        this.passages = passages;
    }

    /**
     * Get the anomaly
     * @return the anomaly
     */
    public PresenceAnomalie getAnomalie() {
        return anomalie;
    }

    /**
     * Set the anomaly
     * @param anomalie the anomaly
     * @throws IllegalArgumentException if anomalie is null
     */
    public void setAnomalie(PresenceAnomalie anomalie) throws IllegalArgumentException {
        if(anomalie == null){
            throw new IllegalArgumentException("anomalie must not be null");
        }

        this.anomalie = anomalie;
    }

    /**
     * Get the Compteur
     * @return the Compteur
     */
    public Compteur getLeCompteur() {
        return leCompteur;
    }

    /**
     * Set the Compteur
     * @param leCompteur the Compteur
     * @throws IllegalArgumentException if leCompteur is null
     */
    public void setLeCompteur(Compteur leCompteur) throws IllegalArgumentException {
        if(leCompteur == null){
            throw new IllegalArgumentException("leCompteur must not be null");
        }

        this.leCompteur = leCompteur;
    }

    /**
     * Get the DateInfo
     * @return the DateInfo
     */
    public DateInfo getLaDate() {
        return laDate;
    }

    /**
     * Set the DateInfo
     * @param laDate the DateInfo
     * @throws IllegalArgumentException if laDate is null
     */
    public void setLaDate(DateInfo laDate) throws IllegalArgumentException {
        if(laDate == null){
            throw new IllegalArgumentException("laDate must not be null");
        }

        this.laDate = laDate;
    }

    // ---------------- Methods ---------------- //

    /**
     * To String method
     * Use a StringBuilder to concatenate String
     * @return the String
     */
    public String toString() {
        StringBuilder ret = new StringBuilder("Comptage(");
        ret.append("laDate : ").append(this.laDate.getDate()).append(", ");
        ret.append("leCompteur : ").append(this.leCompteur.getIdCompteur()).append(", ");
        ret.append("anomalie : ").append(this.anomalie.name()).append(", ");
        ret.append("passages : ").append(Arrays.toString(this.passages)).append(")");
        return ret.toString();
    }


    /**
     * Compute the total number of passages
     * @return the total number of passages
     */
    public int totalPassages(){
        int total = 0;
        for(int i = 0; i < 24; i++){
            total += this.passages[i];
        }
        return total;
    }

    /**
     * Compute the average number of passages
     * @return the average number of passages
     */
    public float averagePassages(){
        return this.totalPassages() / 24.0f;
    }
}