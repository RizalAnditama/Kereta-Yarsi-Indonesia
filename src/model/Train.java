/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 * The {@code Train} class represents a train with its details such as train ID,
 * name, starting station, ending station, departing and arriving times, and
 * price.
 * It provides getters and setters for accessing and modifying these details.
 * <p>
 * Instances of this class can be used to store and manipulate information about
 * trains in a railway reservation system.
 * </p>
 * 
 * @version 1.0
 * @since 2024-06-23
 * 
 * @see TrainDAO
 * @see controller.TrainController
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

    /**
     * Constructs a new {@code Train} object with default values.
     */
    public Train() {
        this.trainID = 0;
        this.name = null;
        this.startingStation = null;
        this.endingStation = null;
        this.departingTime = null;
        this.arrivingTime = null;
        this.price = 0;
    }

    /**
     * Constructs a new {@code Train} object with the specified values.
     * 
     * @param trainID         the ID of the train
     * @param name            the name of the train
     * @param startingStation the starting station of the train
     * @param endingStation   the ending station of the train
     * @param departingTime   the departing time of the train
     * @param arrivingTime    the arriving time of the train
     * @param price           the price of the train ticket
     */
    public Train(int trainID, String name, String startingStation, String endingStation, Timestamp departingTime,
            Timestamp arrivingTime, int price) {
        this.trainID = trainID;
        this.name = name;
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.departingTime = departingTime;
        this.arrivingTime = arrivingTime;
        this.price = price;
    }

    /**
     * @return the train ID
     */
    public int getTrainID() {
        return trainID;
    }

    /**
     * @param trainID the train ID to set
     */
    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    /**
     * @return the name of the train
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the train to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the starting station of the train
     */
    public String getStartingStation() {
        return startingStation;
    }

    /**
     * @param startingStation the starting station to set
     */
    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation.replace("Stasiun ", "");
    }

    /**
     * @return the ending station of the train
     */
    public String getEndingStation() {
        return endingStation;
    }

    /**
     * @param endingStation the ending station to set
     */
    public void setEndingStation(String endingStation) {
        this.endingStation = endingStation.replace("Stasiun ", "");
    }

    /**
     * @return the departing time of the train
     */
    public Timestamp getDepartingTime() {
        return departingTime;
    }

    /**
     * @param departingTime the departing time to set
     */
    public void setDepartingTime(Timestamp departingTime) {
        this.departingTime = departingTime;
    }

    /**
     * @return the arriving time of the train
     */
    public Timestamp getArrivingTime() {
        return arrivingTime;
    }

    /**
     * @param arrivingTime the arriving time to set
     */
    public void setArrivingTime(Timestamp arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    /**
     * @return the price of the train ticket
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
