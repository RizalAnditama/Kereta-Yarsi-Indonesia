/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Station {

    private int stationID;
    private String stationCode;
    private String name;
    private String city;

    public Station() {
        this.stationID = 0;
        this.stationCode = null;
        this.name = null;
        this.city = null;
    }

    public Station(int stationID, String stationCode, String name, String city) {
        this.stationID = stationID;
        this.stationCode = stationCode;
        this.name = name;
        this.city = city;
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
     * @return the stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * @param stationCode the stationCode to set
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

}
