package modele.database;

import java.sql.*;

/**
 * Database class which communicate with the mysql/mariadb database
 * It use JDBC to interact with the database
 * It has two connections, one for read access and one for write access if the user has the rights
 * @author Groupe 4B2
 */
public class Database {

    // ---------------- Attributes ---------------- //

    private final String URL;

    private Connection readConnection;
    private Connection writeConnection;

    // ---------------- Constructors ---------------- //

    /**
     * Default constructor
     * @param url the url of the database
     * @throws IllegalArgumentException if url is null
     */
    public Database(String url) throws IllegalArgumentException{
        if(url == null){
            throw new IllegalArgumentException("URL cannot be null");
        }

        this.URL = url;
    }

    // ---------------- Methods ---------------- //

    /**
     * Open a connection to the database with read access
     * @param user the user
     * @param password the password
     * @throws SQLException if there is an error with the connection
     * @throws IllegalArgumentException if user or password is null
     */
    public void openReadConnection(String user, String password) throws SQLException, IllegalArgumentException{
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if(password == null){
            throw new IllegalArgumentException("Password cannot be null");
        }

        if(readConnection != null){
            System.out.println("Read connection already open");
        } else {
            readConnection = DriverManager.getConnection(URL, user, password);
        }
    }

    /**
     * Open a connection to the database with write access
     * @param user the user
     * @param password the password
     * @throws SQLException if there is an error with the connection
     * @throws IllegalArgumentException if user or password is null
     */
    public void openWriteConnection(String user, String password) throws SQLException, IllegalArgumentException{
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if(password == null){
            throw new IllegalArgumentException("Password cannot be null");
        }

        if(writeConnection != null){
            System.out.println("Write connection already open");
        } else {
            writeConnection = DriverManager.getConnection(URL, user, password);
        }
    }

    /**
     * Close the connection to the database with read access
     * @throws SQLException if there is an error with the connection
     */
    public void closeReadConnection() throws SQLException{
        if(readConnection != null){
            readConnection.close();
            readConnection = null;
        }
    }

    /**
     * Close the connection to the database with write access
     * @throws SQLException if there is an error with the connection
     */
    public void closeWriteConnection() throws SQLException{
        if(writeConnection != null){
            writeConnection.close();
            writeConnection = null;
        }
    }

    /**
     * Return a PreparedStatement for the read connection
     * @param query the query
     * @return the PreparedStatement
     * @throws SQLException if the read connection is not open
     * @throws IllegalArgumentException if query is null
     */
    public PreparedStatement preparedReadStatment(String query) throws SQLException, IllegalArgumentException{
        if(query == null){
            throw new IllegalArgumentException("Query cannot be null");
        }

        if(readConnection == null){
            throw new SQLException("Read connection not open");
        }
        return readConnection.prepareStatement(query);
    }

    /**
     * Return a PreparedStatement for the write connection
     * @param query the query
     * @return the PreparedStatement
     * @throws SQLException if the write connection is not open
     * @throws IllegalArgumentException if query is null
     */
    public PreparedStatement preparedWriteStatment(String query) throws SQLException, IllegalArgumentException{
        if(query == null){
            throw new IllegalArgumentException("Query cannot be null");
        }

        if(writeConnection == null){
            throw new SQLException("Write connection not open");
        }
        return writeConnection.prepareStatement(query);
    }
}