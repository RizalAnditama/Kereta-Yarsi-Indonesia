/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 * The {@code StationDAO} interface provides methods for accessing and
 * manipulating
 * station data in the database.
 * <p>
 * Implementing classes should provide concrete implementations for these
 * methods
 * to interact with the underlying database and retrieve station details based
 * on
 * various criteria.
 * </p>
 * 
 * @author
 *         Muhammad Rizal Anditama Nugraha
 */
public interface StationDAO {

    /**
     * Retrieves a {@code ResultSet} containing station details that match the
     * specified
     * station name.
     * 
     * @param stationName the name of the station to search for
     * @return a {@code ResultSet} containing the station details that match the
     *         specified
     *         name, or {@code null} if no matching station is found
     */
    public java.sql.ResultSet getStationByName(String stationName);
}
