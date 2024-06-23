/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * The {@code Station} class represents a railway station in the KYI Integrated
 * system.
 * <p>
 * It includes details such as the station's ID, code, name, and city.
 * This class provides getter and setter methods for accessing and modifying
 * these details.
 * </p>
 * <p>
 * Instances of this class are used to represent and manipulate station data
 * within the application.
 * </p>
 * 
 * @author
 *         Muhammad Rizal Anditama Nugraha
 */
public class Station {

    private int stationID;
    private String stationCode;
    private String name;
    private String city;

    /**
     * Default constructor. Initializes a new instance of the {@code Station} class
     * with default values.
     */
    public Station() {
        this.stationID = 0;
        this.stationCode = null;
        this.name = null;
        this.city = null;
    }

    /**
     * Initializes a new instance of the {@code Station} class with the specified
     * values.
     * 
     * @param stationID   the ID of the station
     * @param stationCode the code of the station
     * @param name        the name of the station
     * @param city        the city where the station is located
     */
    public Station(int stationID, String stationCode, String name, String city) {
        this.stationID = stationID;
        this.stationCode = stationCode;
        this.name = name;
        this.city = city;
    }

    /**
     * Gets the ID of the station.
     * 
     * @return the stationID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     * Sets the ID of the station.
     * 
     * @param stationID the stationID to set
     */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    /**
     * Gets the code of the station.
     * 
     * @return the stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Sets the code of the station.
     * 
     * @param stationCode the stationCode to set
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * Gets the name of the station.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the station.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the city where the station is located.
     * 
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the station is located.
     * 
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

}
