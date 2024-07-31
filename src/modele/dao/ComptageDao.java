package modele.dao;

import java.sql.*;
import java.util.ArrayList;

import modele.database.Database;
import modele.entities.*;

/**
 * Class ComptageDao to implement the DAO pattern for Comptage
 * @author Groupe 4B2
 */
public class ComptageDao implements IDao<Comptage>{

    // ---------------- Attributes ---------------- //

    private Database database;
    private CompteurDao compteurDao;
    private DateInfoDao dateInfoDao;
    private ArrayList<Comptage> lesComptages;

    // ---------------- Constructors ---------------- //

    /**
     * Constructor of ComptageDao
     * @param db the database
     * @param compteurDao the CompteurDao to use
     * @param dateInfoDao the DateInfoDao to use
     * @throws IllegalArgumentException if db, compteurDao or dateInfoDao is null
     */
    public ComptageDao(Database db, CompteurDao compteurDao, DateInfoDao dateInfoDao) throws IllegalArgumentException{
        if(db == null){
            throw new IllegalArgumentException("Database cannot be null");
        }
        if(compteurDao == null){
            throw new IllegalArgumentException("CompteurDao cannot be null");
        }
        if(dateInfoDao == null){
            throw new IllegalArgumentException("DateInfoDao cannot be null");
        }

        this.database = db;
        this.compteurDao = compteurDao;
        this.dateInfoDao = dateInfoDao;
        this.lesComptages = new ArrayList<Comptage>();
    }

    // ---------------- Getters and Setters ---------------- //

    /**
     * Get a Comptage by it's DateInfo and Compteur
     * @param laDate the DateInfo of the Comptage
     * @param leCompteur the Compteur of the Comptage 
     * @return the Comptage
     * @throws IllegalArgumentException if laDate or leCompteur is null
     */
    public Comptage get(DateInfo laDate, Compteur leCompteur) throws IllegalArgumentException {
        if(laDate == null){
            throw new IllegalArgumentException("DateInfo cannot be null");
        }
        if(leCompteur == null){
            throw new IllegalArgumentException("Compteur cannot be null");
        }

        Comptage comptage = null;
        boolean found = false;
        int i = 0;
        while(!found && i < lesComptages.size()) {
            if(lesComptages.get(i).getLaDate() == laDate && lesComptages.get(i).getLeCompteur() == leCompteur) {
                comptage = lesComptages.get(i);
                found = true;
            }
            i++;
        }
        return comptage;
    }

    /**
     * Get all the Comptage
     * @return an ArrayList of Comptage
     */
    public ArrayList<Comptage> getAll() {
        return lesComptages;
    }

    // ---------------- Methods ---------------- //

    /**
     * Get all the Comptage from the database
     * @throws SQLException if an error occurs
     */
    public void readAll() throws SQLException {
        PreparedStatement ps = database.preparedReadStatment("SELECT * FROM COMPTAGE");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int[] passages = new int[24];
            for(int i = 0; i < 24; i++) {
                passages[i] = rs.getInt("h" + String.format("%02d", i));            
            }
            PresenceAnomalie anomalie;
            if(rs.getString("presenceAnomalie") != null){
                anomalie = PresenceAnomalie.valueOf(rs.getString("presenceAnomalie"));
            } else {
                anomalie = PresenceAnomalie.Nulle;
            }
            Compteur leCompteur = compteurDao.get(rs.getInt("leCompteur"));
            DateInfo laDate = dateInfoDao.get(rs.getDate("dateComptage"));
            Comptage comptage = new Comptage(passages, anomalie, leCompteur, laDate);

            this.lesComptages.add(comptage);
            leCompteur.addComptage(comptage);
            laDate.addComptage(comptage);
        }
        rs.close();
        ps.close();
    }

    /**
     * Add a Comptage to the database
     * @param comptage the Comptage to add
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if comptage is null
     */
    public void add(Comptage comptage) throws SQLException, IllegalArgumentException {
        if(comptage == null){
            throw new IllegalArgumentException("Comptage cannot be null");
        }

        if(this.get(comptage.getLaDate(), comptage.getLeCompteur()) == null){
            this.lesComptages.add(comptage);
            comptage.getLeCompteur().addComptage(comptage);
            comptage.getLaDate().addComptage(comptage);
        }
        String query = "INSERT INTO COMPTAGE VALUES(?, ?, ";
        for(int i = 0; i < 24; i++) {
            query += "?, ";
        }
        query += "?)";
        PreparedStatement ps = database.preparedWriteStatment(query.toString());
        ps.setInt(1, comptage.getLeCompteur().getIdCompteur());
        ps.setDate(2, comptage.getLaDate().getDate());
        for(int i = 0; i < 24; i++) {
            ps.setInt(i + 3, comptage.getPassages()[i]);
        }
        if(comptage.getAnomalie() == PresenceAnomalie.Nulle){
            ps.setString(27, null);
        } else {
            ps.setString(27, comptage.getAnomalie().toString());
        }
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Remove a Comptage from the database
     * @param comptage the Comptage to remove
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if comptage is null
     */
    public void remove(Comptage comptage) throws SQLException, IllegalArgumentException {
        if(comptage == null){
            throw new IllegalArgumentException("Comptage cannot be null");
        }

        String query = "DELETE FROM COMPTAGE WHERE leCompteur = ? AND dateComptage = ?";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setInt(1, comptage.getLeCompteur().getIdCompteur());
        ps.setDate(2, comptage.getLaDate().getDate());
        ps.executeUpdate();
        ps.close();
        this.lesComptages.remove(comptage);
    }

    /**
     * Update a Comptage from the database
     * @param comptage the Comptage to update
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if comptage is null
     */
    public void update(Comptage comptage) throws SQLException, IllegalArgumentException{
        if(comptage == null){
            throw new IllegalArgumentException("Comptage cannot be null");
        }

        String query = "UPDATE COMPTAGE SET ";
        for(int i = 0; i < 24; i++){
            query += "h" + String.format("%02d", i) + " = ?, ";
        }
        query += "presenceAnomalie = ? ";
        query += " WHERE leCompteur = ? AND dateComptage = ?";
        PreparedStatement ps = database.preparedWriteStatment(query);
        for(int i = 0; i < 24; i++){
            ps.setInt(i + 1, comptage.getPassage(i));
        }
        if(comptage.getAnomalie() == PresenceAnomalie.Nulle){
            ps.setNull(25, Types.VARCHAR);
        } else {
            ps.setString(25, comptage.getAnomalie().toString());
        }
        ps.setInt(26, comptage.getLeCompteur().getIdCompteur());
        ps.setDate(27, comptage.getLaDate().getDate());
        ps.executeUpdate();
        ps.close();
        this.lesComptages.remove(comptage);
        this.lesComptages.add(comptage);
    }
}
