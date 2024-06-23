/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Train {

    private int trainID;
    private String name;
    private String startingStation;
    private String endingStation;
    private Timestamp departingTime;
    private Timestamp arrivingTime;
    private int price;

    public Train() {
        this.trainID = 0;
        this.name = null;
        this.startingStation = null;
        this.endingStation = null;
        this.departingTime = null;
        this.arrivingTime = null;
        this.price = 0;
    }
    
    public Train(int train_id, String name, String startingStation, String endingStation, Timestamp departingTime, Timestamp arrivingTime, int price) {
        this.trainID = train_id;
        this.name = name;
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.departingTime = departingTime;
        this.arrivingTime = arrivingTime;
        this.price = price;
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
     * @return the startingStation
     */
    public String getStartingStation() {
        return startingStation;
    }

    /**
     * @param startingStation the startingStation to set
     */
    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation.replace("Stasiun ", "");
    }

    /**
     * @return the endingStation
     */
    public String getEndingStation() {
        return endingStation;
    }

    /**
     * @param endingStation the endingStation to set
     */
    public void setEndingStation(String endingStation) {
        this.endingStation = endingStation.replace("Stasiun ", "");
    }

    /**
     * @return the departingTime
     */
    public Timestamp getDepartingTime() {
        return departingTime;
    }

    /**
     * @param departingTime the departingTime to set
     */
    public void setDepartingTime(Timestamp departingTime) {
        this.departingTime = departingTime;
    }

    /**
     * @return the arrivingTime
     */
    public Timestamp getArrivingTime() {
        return arrivingTime;
    }

    /**
     * @param arrivingTime the arrivingTime to set
     */
    public void setArrivingTime(Timestamp arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
}
