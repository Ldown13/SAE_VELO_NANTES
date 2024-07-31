package modele.dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface IDao to implement the DAO pattern
 * @param <T> the type of the DAO
 * @author Groupe 4B2
 */
public interface IDao<T> {

    /**
     * Get all the T from the database
     * @return an ArrayList of T
     */
    public ArrayList<T> getAll();

    /**
     * Read all the T from the database
     * @throws SQLException if an error occurs
     */
    public void readAll() throws SQLException;

    /**
     * Add a T to the database
     * @param t the T to add
     * @throws SQLException if an error occurs
     */
    public void add(T t) throws SQLException, IllegalArgumentException;

    /**
     * Remove a T from the database
     * @param t the T to remove
     * @throws SQLException if an error occurs
     */
    public void remove(T t) throws SQLException, IllegalArgumentException;

    /**
     * Update a T from the database
     * @param t the T to update
     * @throws SQLException if an error occurs
     */
    public void update(T t) throws SQLException, IllegalArgumentException;
}
