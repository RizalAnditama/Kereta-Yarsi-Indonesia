/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.TicketDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatabaseConnector;
import model.Ticket;

/**
 * Controller class for handling operations related to tickets. Implements the
 * TicketDAO interface to provide data access methods for tickets.
 *
 * @see model.TicketDAO
 * @see model.Ticket
 * @see model.DatabaseConnector
 *
 * @version 1.0
 * @author Muhammad Rizal Anditama Nugraha
 *
 */
public class TicketController extends Controller implements TicketDAO {

    private static TicketController instance;

    /**
     * Private constructor to prevent direct instantiation. Initializes the
     * controller with the given DatabaseConnector.
     *
     * @param databaseConnector the database connector to use
     */
    private TicketController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    /**
     * Returns the single instance of TicketController. If no instance exists,
     * it creates one using the provided DatabaseConnector.
     *
     * @return the singleton instance of TicketController
     */
    public static synchronized TicketController getInstance() {
        if (instance == null) {
            instance = new TicketController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Checks if a record with the given ID exists in the specified table.
     *
     * @param tableName the name of the table to check
     * @param columnName the name of the column to check
     * @param id the ID to check
     * @return true if the record exists, false otherwise
     */
    private boolean recordExists(String tableName, String columnName, int id) {
        try {
            ps = con.prepareStatement("SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Creates a new ticket in the database.
     *
     * @param ticket the ticket to create
     * @return true if the ticket was created successfully, false otherwise
     */
    @Override
    public boolean createTicket(Ticket ticket) {
        try {
            // Check if station ID exists
            if (!recordExists("station", "station_id", ticket.getStationID())) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, "Station ID {0} does not exist", ticket.getStationID());
                return false;
            }

            // Check if user ID exists
            if (!recordExists("user", "user_id", ticket.getUserID())) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, "User ID {0} does not exist", ticket.getUserID());
                return false;
            }

            // Check if passenger ID exists
            if (!recordExists("passenger", "passenger_id", ticket.getPassengerID())) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, "Passenger ID {0} does not exist", ticket.getPassengerID());
                return false;
            }

            // Check if train ID exists
            if (!recordExists("train", "train_id", ticket.getTrainID())) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, "Train ID {0} does not exist", ticket.getTrainID());
                return false;
            }
            ps = con.prepareStatement("INSERT INTO ticket VALUES (?, ?, ?, ?, ?, null)");
            ps.setString(1, ticket.getTicketID());
            ps.setInt(2, ticket.getUserID());
            ps.setInt(3, ticket.getPassengerID());
            ps.setInt(4, ticket.getTrainID());
            ps.setInt(5, ticket.getStationID());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Retrieves a ticket from the database by its ID.
     *
     * @param ticketId the ID of the ticket to retrieve
     * @return a ResultSet containing the ticket data
     */
    @Override
    public ResultSet getTicket(String ticketId) {
        return findWhere("ticket", "ticket_id", ticketId);
    }

    /**
     * Retrieves detailed information about a ticket and its related entities by
     * the ticket ID. Joins multiple tables to get comprehensive ticket details.
     *
     * @param ticketId the ID of the ticket to retrieve
     * @return a ResultSet containing the joined ticket data
     */
    @Override
    public ResultSet getAllJoin(String ticketId) {
        sql = "SELECT t.ticket_id, t.timestamp, "
                + "p.passenger_id, p.name AS passenger_name, p.id_type, p.id_number, p.age_type, p.seatNumber, "
                + "tr.train_id, tr.name AS train_name, tr.starting_station, tr.ending_station, tr.departing_time, tr.arriving_time, tr.price "
                + "FROM ticket t "
                + "JOIN passenger p ON t.passenger_id = p.passenger_id "
                + "JOIN train tr ON t.train_id = tr.train_id "
                + "WHERE t.ticket_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, ticketId);
            System.out.println(ps.toString());
            return ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
