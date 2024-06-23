/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
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

    public Ticket(String ticketID, int userID, int passengerID, int trainID, int stationID, Timestamp currentTimestamp) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.passengerID = passengerID;
        this.trainID = trainID;
        this.stationID = stationID;
        this.currentTimestamp = currentTimestamp;
    }

    public Ticket(String ticketID, int userID, int passengerID, int trainID, int stationID) {
        this(ticketID, userID, passengerID, trainID, stationID, null);
    }

    public Ticket() {
        this(null, -1, -1, -1, -1);
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the passengerID
     */
    public int getPassengerID() {
        return passengerID;
    }

    /**
     * @param passengerID the passengerID to set
     */
    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    /**
     * @return the trainID
     */
    public int getTrainID() {
        return trainID;
    }

    /**
     * @param trainID the trainID to set
     */
    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    /**
     * @return the stationID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     * @param stationID the stationID to set
     */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    /**
     * @return the currentTimestamp
     */
    public Timestamp getCurrentTimestamp() {
        return currentTimestamp;
    }

    /**
     * @param currentTimestamp the currentTimestamp to set
     */
    public void setCurrentTimestamp(Timestamp currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

}
