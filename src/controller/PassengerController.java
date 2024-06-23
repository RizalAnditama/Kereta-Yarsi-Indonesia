/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import static controller.Controller.con;
import static controller.Controller.ps;
import java.beans.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatabaseConnector;
import model.Passenger;
import model.PassengerDAO;

/**
 * Controller class for managing Passenger entities. This class handles the
 * creation and retrieval of Passenger objects in the database. It uses a
 * singleton pattern to ensure only one instance of the controller is created.
 * It extends the Controller class and implements the PassengerDAO interface.
 *
 * This class uses the singleton pattern to ensure that only one instance of the
 * controller exists.
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * PassengerController controller = PassengerController.getInstance();
 * int lastInsertedId = controller.createPassenger(passenger);
 * </pre>
 *
 * @see model.Passenger
 * @see model.PassengerDAO
 * @see model.DatabaseConnector
 * @see controller.Controller
 * @see java.sql.SQLException
 * @see java.sql.ResultSet
 * @see java.util.logging.Level
 * @see java.util.logging.Logger
 *
 * @version 1.0
 * @author Muhammad Rizal Anditama Nugraha
 *
 */
public class PassengerController extends Controller implements PassengerDAO {

    private static PassengerController instance;

    /**
     * Private constructor to prevent instantiation. Initializes the database
     * connector.
     *
     * @param databaseConnector the database connector instance
     */
    private PassengerController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    /**
     * Returns the single instance of PassengerController. If no instance
     * exists, it creates one using the provided DatabaseConnector.
     *
     * @return the singleton instance of PassengerController
     */
    public static synchronized PassengerController getInstance() {
        if (instance == null) {
            instance = new PassengerController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Creates a new passenger in the database and returns the generated
     * passenger ID.
     *
     * @param passenger the Passenger object representing the passenger to
     * create
     * @return the generated passenger ID if the creation was successful, -1
     * otherwise
     */
    @Override
    public int createPassenger(Passenger passenger) {
        String query = "INSERT INTO passenger VALUES (null, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement ps = con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, passenger.getNamaLengkap());

            if (passenger.getNoID() == null && passenger.getTipeID() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setString(2, passenger.getTipeID());
                ps.setInt(3, Integer.parseInt(passenger.getNoID()));
            }

            ps.setString(4, passenger.getTipeUmur());
            ps.setString(5, passenger.getKursi());

            Logger.getLogger(PassengerController.class.getName()).log(Level.INFO, "Executing SQL: {0}", ps.toString());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PassengerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     * Retrieves the passenger ID by the given ID number.
     *
     * @param idNumber the ID number of the passenger
     * @return the passenger ID if found, -1 otherwise
     */
    @Override
    public int getPassengerIDByIdNumber(int idNumber) {
        try {
            rs = findWhere("passenger", "id_number", idNumber);
            if (rs.next()) {
                return rs.getInt("passenger_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PassengerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     * Retrieves a Passenger object by the given passenger ID.
     *
     * @param passengerId the ID of the passenger to retrieve
     * @return the Passenger object if found, null otherwise
     */
    @Override
    public Passenger getPassengerByPassengerId(int passengerId) {
        try {
            rs = findWhere("passenger", "passenger_id", passengerId);
            if (rs.next()) {
                return new Passenger(passengerId, rs.getString("name"), rs.getString("id_type"),
                        rs.getInt("id_number") + "", rs.getString("seatNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PassengerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
