package modele.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.database.Database;
import modele.entities.DateInfo;
import modele.entities.Jour;
import modele.entities.Vacances;

/**
 * Class DateInfoDao to implement the DAO pattern for DateInfo
 * @author Groupe 4B2
 */
public class DateInfoDao implements IDao<DateInfo> {

    // ---------------- Attributes ---------------- //

    private Database database;
    private ArrayList<DateInfo> lesDate;

    // ---------------- Constructors ---------------- //    

    /**
     * Constructor of DateInfoDao
     * @param db the database
     * @throws IllegalArgumentException if db is null
     */
    public DateInfoDao(Database db) throws IllegalArgumentException {
        if(db == null){
            throw new IllegalArgumentException("Database cannot be null");
        }

        this.database = db;
        this.lesDate = new ArrayList<DateInfo>();
    }

    // ---------------- Getters and Setters ---------------- //

    /**
     * Get a DateInfo by its date
     * @param laDate the date of the DateInfo
     * @return the DateInfo
     * @throws IllegalArgumentException if laDate is null
     */
    public DateInfo get(Date laDate) throws IllegalArgumentException{
        if(laDate == null){
            throw new IllegalArgumentException("Date cannot be null");
        }

        DateInfo dateInfo = null;
        boolean found = false;
        int i = 0;
        while(!found && i < lesDate.size()) {
            if(lesDate.get(i).getDate().equals(laDate)) {
                dateInfo = lesDate.get(i);
                found = true;
            }
            i++;
        }
        return dateInfo;
    }

    /**
     * Get all the DateInfo
     * @return an ArrayList of DateInfo
     */
    public ArrayList<DateInfo> getAll() {
        return lesDate;
    }

    // ---------------- Methods ---------------- //

    /**
     * Read all the DateInfo from the database
     * @throws SQLException if an error occurs
     */
    public void readAll() throws SQLException {
        PreparedStatement ps = database.preparedReadStatment("SELECT * FROM DATEINFO");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Date laDate = rs.getDate("laDate");
            float tempMoy = rs.getFloat("tempMoy");
            Jour jour = Jour.valueOf(rs.getString("jour"));
            Vacances vacances;
            try {
                vacances = Vacances.valueOf(rs.getString("vacances"));
            } catch (Exception e) {
                vacances = Vacances.Nulle;
            }
            DateInfo dateInfo = new DateInfo(laDate, tempMoy, jour, vacances);

            this.lesDate.add(dateInfo);
        }
        rs.close();
        ps.close();
    }

    /**
     * Add a DateInfo to the database
     * @param dateInfo the DateInfo to add
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if dateInfo is null
     */
    public void add(DateInfo dateInfo) throws SQLException, IllegalArgumentException {
        if(dateInfo == null){
            throw new IllegalArgumentException("DateInfo cannot be null");
        }

        if(this.get(dateInfo.getDate()) == null){
            this.lesDate.add(dateInfo);
        }
        String query = "INSERT INTO DATEINFO VALUES(?, ?, ?, ?)";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setDate(1, dateInfo.getDate());
        ps.setFloat(2, dateInfo.getTempMoy());
        ps.setString(3, dateInfo.getJour().name());
        ps.setString(4, dateInfo.getVacances().name());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Remove a DateInfo from the database
     * @param dateInfo the DateInfo to remove
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if dateInfo is null
     */
    public void remove(DateInfo dateInfo) throws SQLException, IllegalArgumentException {
        String query = "DELETE FROM DATEINFO WHERE laDate = ?";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setDate(1, dateInfo.getDate());
        ps.executeUpdate();
        ps.close();
        this.lesDate.remove(dateInfo);
    }

    /**
     * Update a DateInfo from the database
     * @param dateInfo the DateInfo to update
     * @throws SQLException if an error occurs
     * @throws IllegalArgumentException if dateInfo is null
     */
    public void update(DateInfo dateInfo) throws SQLException, IllegalArgumentException {
        if(dateInfo == null){
            throw new IllegalArgumentException("DateInfo cannot be null");
        }
        
        String query = "UPDATE DATEINFO SET tempMoy = ?, jour = ?, vacances = ? WHERE laDate = ?";
        PreparedStatement ps = database.preparedWriteStatment(query);
        ps.setFloat(1, dateInfo.getTempMoy());
        ps.setString(2, dateInfo.getJour().name());
        ps.setString(3, dateInfo.getVacances().name());
        ps.setDate(4, dateInfo.getDate());
        ps.executeUpdate();
        ps.close();
        this.lesDate.remove(dateInfo);
        this.lesDate.add(dateInfo);
    }

}
