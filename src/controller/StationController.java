/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.DatabaseConnector;
import model.StationDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code StationController} class is responsible for handling operations
 * related to stations, including fetching station details from the database. It
 * implements the {@link StationDAO} interface.
 *
 * This class uses the singleton pattern to ensure that only one instance of the
 * controller exists.
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * StationController controller = StationController.getInstance();
 * ResultSet rs = controller.getStationByName("StationName");
 * </pre>
 *
 * @see model.StationDAO
 * @see model.DatabaseConnector
 * @see java.sql.ResultSet
 * @see java.sql.SQLException
 * @see java.util.logging.Logger
 * @see java.util.logging.Level
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class StationController extends Controller implements StationDAO {

    private static StationController instance;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the {@code StationController} with the given
     * {@code DatabaseConnector}.
     *
     * @param databaseConnector the database connector instance to use for
     *                          database operations.
     */
    private StationController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    /**
     * Returns the singleton instance of the {@code StationController} class. If
     * the instance is {@code null}, it creates a new one using the
     * {@code DatabaseConnector} instance.
     *
     * @return the singleton instance of {@code StationController}.
     */
    public static synchronized StationController getInstance() {
        if (instance == null) {
            instance = new StationController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Retrieves a station by its name from the database.
     *
     * @param stationName the name of the station to retrieve.
     * @return a {@code ResultSet} containing the station details, or
     *         {@code null} if no station with the specified name is found or an
     *         error
     *         occurs.
     */
    @Override
    public ResultSet getStationByName(String stationName) {
        rs = findWhere("station", "name", stationName);
        try {
            if (rs.next()) {
                return rs;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
