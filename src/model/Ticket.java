/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * The {@code Ticket} class represents a ticket in the system. It includes
 * details such as
 * the ticket ID, user ID, passenger ID, train ID, station ID, and timestamp of
 * the ticket.
 * <p>
 * This class provides methods to get and set the values of these fields.
 * </p>
 * <p>
 * The class provides constructors for creating a ticket with all fields, with
 * all fields
 * except timestamp, and a default constructor with placeholder values.
 * </p>
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Ticket {

    private String ticketID;
    private int userID;
    private int passengerID;
    private int trainID;
    private int stationID;
    private Timestamp currentTimestamp;

    /**
     * Constructs a {@code Ticket} with the specified details.
     * 
     * @param ticketID         the ID of the ticket
     * @param userID           the ID of the user
     * @param passengerID      the ID of the passenger
     * @param trainID          the ID of the train
     * @param stationID        the ID of the station
     * @param currentTimestamp the timestamp of the ticket
     */
    public Ticket(String ticketID, int userID, int passengerID, int trainID, int stationID,
            Timestamp currentTimestamp) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.passengerID = passengerID;
        this.trainID = trainID;
        this.stationID = stationID;
        this.currentTimestamp = currentTimestamp;
    }

    /**
     * Constructs a {@code Ticket} with the specified details except timestamp.
     * 
     * @param ticketID    the ID of the ticket
     * @param userID      the ID of the user
     * @param passengerID the ID of the passenger
     * @param trainID     the ID of the train
     * @param stationID   the ID of the station
     */
    public Ticket(String ticketID, int userID, int passengerID, int trainID, int stationID) {
        this(ticketID, userID, passengerID, trainID, stationID, null);
    }

    /**
     * Constructs a {@code Ticket} with placeholder values.
     */
    public Ticket() {
        this(null, -1, -1, -1, -1);
    }

    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID.
     * 
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the passenger ID.
     * 
     * @return the passenger ID
     */
    public int getPassengerID() {
        return passengerID;
    }

    /**
     * Sets the passenger ID.
     * 
     * @param passengerID the passenger ID to set
     */
    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    /**
     * Gets the train ID.
     * 
     * @return the train ID
     */
    public int getTrainID() {
        return trainID;
    }

    /**
     * Sets the train ID.
     * 
     * @param trainID the train ID to set
     */
    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    /**
     * Gets the station ID.
     * 
     * @return the station ID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     * Sets the station ID.
     * 
     * @param stationID the station ID to set
     */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    /**
     * Gets the current timestamp.
     * 
     * @return the current timestamp
     */
    public Timestamp getCurrentTimestamp() {
        return currentTimestamp;
    }

    /**
     * Sets the current timestamp.
     * 
     * @param currentTimestamp the current timestamp to set
     */
    public void setCurrentTimestamp(Timestamp currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    /**
     * Gets the ticket ID.
     * 
     * @return the ticket ID
     */
    public String getTicketID() {
        return ticketID;
    }

    /**
     * Sets the ticket ID.
     * 
     * @param ticketID the ticket ID to set
     */
    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

}
