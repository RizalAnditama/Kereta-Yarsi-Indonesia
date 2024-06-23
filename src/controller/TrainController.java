/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import static controller.Controller.rs;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatabaseConnector;
import model.Train;
import model.TrainDAO;

/**
 * Controller class for handling operations related to trains. Implements the
 * TrainDAO interface to provide data access methods for trains.
 *
 * This class uses the singleton pattern to ensure that only one instance of the
 * controller exists.
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * TrainController controller = TrainController.getInstance();
 * Train train = controller.getTrainByTrainName("Argo Bromo");
 * </pre>
 *
 * @see model.TrainDAO
 * @see model.Train
 * @see model.DatabaseConnector
 *
 * @version 1.0
 * @author Muhammad Rizal Anditama Nugraha
 */
public class TrainController extends Controller implements TrainDAO {

    private static TrainController instance;

    /**
     * Private constructor to prevent direct instantiation. Initializes the
     * controller with the given DatabaseConnector.
     *
     * @param databaseConnector the database connector to use
     */
    private TrainController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    /**
     * Returns the single instance of TrainController. If no instance exists, it
     * creates one using the provided DatabaseConnector.
     *
     * @return the singleton instance of TrainController
     */
    public static synchronized TrainController getInstance() {
        if (instance == null) {
            instance = new TrainController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Retrieves a train from the database by its name.
     *
     * @param trainName the name of the train to retrieve
     * @return a Train object if found, otherwise null
     */
    @Override
    public Train getTrainByTrainName(String trainName) {
        try {
            rs = findWhere("train", "name", trainName);
            if (rs.next()) {
                return new Train(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5),
                        rs.getTimestamp(6), rs.getInt(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
