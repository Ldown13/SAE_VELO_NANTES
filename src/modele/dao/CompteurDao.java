package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.database.Database;
import modele.entities.*;

/**
 * Class CompteurDao to implement the DAO pattern for Compteur
 * @author Groupe 4B2
 */
public class CompteurDao implements IDao<Compteur> {

    // ---------------- Attributes ---------------- //

    private Database database;
    private QuartierDao quartierDao;
    private ArrayList<Compteur> lesCompteurs;

    // ---------------- Constructors ---------------- //

    /**
     * Constructor of CompteurDao
     * @param db the database
     * @param quartierDao the QuartierDao to use
     * @throws IllegalArgumentException if db or quartierDao is null
     */
    public CompteurDao(Database db, QuartierDao quartierDao) throws IllegalArgumentException{
        if(db == null){
            throw new IllegalArgumentException("Database cannot be null");
        }
        if(quartierDao == null){
            throw new IllegalArgumentException("QuartierDao cannot be null");
        }

        this.database = db;
        this.quartierDao = quartierDao;
        this.lesCompteurs = new ArrayList<Compteur>();
    }

    // ---------------- Getters and Setters ---------------- //

    /**
     * Get a Compteur by its id
     * @param leCompteur the id of the Compteur
     * @return the Compteur
     */
    public Compteur get(int leCompteur) {
        Compteur compteur = null;
        boolean found = false;
        int i = 0;
        while(!found && i < lesCompteurs.size()) {
            if(lesCompteurs.get(i).getIdCompteur() == leCompteur) {
                compteur = lesCompteurs.get(i);
                found = true;
            }
            i++;
        }
        return compteur;
    }

    /**
     * Get all the Compteur
     * @return an ArrayList of Compteur
     */
    public ArrayList<Compteur> getAll() {
        return lesCompteurs;
    }

    // ---------------- Methods ---------------- //

    /**
     * Read all the Compteur from the database
     * @throws SQLException if an error occurs
     */
    public void readAll() throws SQLException {
        PreparedStatement ps = database.preparedReadStatment("SELECT * FROM COMPTEUR");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int idCompteur = rs.getInt("idCompteur");
            String nomCompteur = rs.getString("nomCompteur");
            String sens = rs.getString("sens");
            float coordX = rs.getFloat("coord_x");
            float coordY = rs.getFloat("coord_y");
            Quartier leQuartier = quartierDao.get(rs.getInt("leQuartier"));
            Compteur compteur = new Compteur(idCompteur, nomCompteur, sens, coordX, coordY, leQuartier);

            this.lesCompteurs.add(compteur);
            leQuartier.addCompteur(compteur);
        }
        rs.close();
        ps.close();
    }

    /**
     * Add a Compteur to the database
     * @param compteur the Compteur to add
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if compteur is null
     */
    public void add(Compteur compteur) throws SQLException, IllegalArgumentException {
        if(compteur == null) {
            throw new IllegalArgumentException("Compteur cannot be null");
        }

        if(this.get(compteur.getIdCompteur()) == null) {
            this.lesCompteurs.add(compteur);
        }
        String query = "INSERT INTO COMPTEUR VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setInt(1, compteur.getIdCompteur());
        ps.setString(2, compteur.getNomCompteur());
        ps.setString(3, compteur.getSens());
        ps.setFloat(4, compteur.getCoordX());
        ps.setFloat(5, compteur.getCoordY());
        ps.setInt(6, compteur.getLeQuartier().getIdQuartier());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Remove a Compteur from the database
     * @param compteur the Compteur to remove
     */
    public void remove(Compteur compteur) throws SQLException, IllegalArgumentException {
        if(compteur == null) {
            throw new IllegalArgumentException("Compteur cannot be null");
        }

        String query = "DELETE FROM COMPTEUR WHERE idCompteur = ?";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setInt(1, compteur.getIdCompteur());
        ps.executeUpdate();
        ps.close();
        this.lesCompteurs.remove(compteur);
    }

    /**
     * Update a Compteur from the database
     * @param compteur the Compteur to update
     * @throws SQLException if an error occurs
     */
    public void update(Compteur compteur) throws SQLException, IllegalArgumentException {
        if(compteur == null) {
            throw new IllegalArgumentException("Compteur cannot be null");
        }

        String query = "UPDATE COMPTEUR SET nomCompteur = ?, sens = ?, coord_x = ?, coord_y = ?, leQuartier = ? WHERE idCompteur = ?";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setString(1, compteur.getNomCompteur());
        ps.setString(2, compteur.getSens());
        ps.setFloat(3, compteur.getCoordX());
        ps.setFloat(4, compteur.getCoordY());
        if(compteur.getLeQuartier().getIdQuartier() == 0){
            ps.setNull(5, java.sql.Types.INTEGER);
        } else {
            ps.setInt(5, compteur.getLeQuartier().getIdQuartier());
        }
        ps.setInt(6, compteur.getIdCompteur());
        ps.executeUpdate();
        ps.close();
        this.lesCompteurs.remove(compteur);
        this.lesCompteurs.add(compteur);
    }
}
