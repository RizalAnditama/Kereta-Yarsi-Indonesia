/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 * The {@code TrainDAO} interface provides methods for accessing and
 * manipulating
 * train data in the database. Implementations of this interface should provide
 * the actual database operations for train-related data.
 * <p>
 * This interface defines the method for retrieving a train based on its name.
 * </p>
 * 
 * @version 1.0
 * @since 2024-06-23
 * 
 * @see Train
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface TrainDAO {

    /**
     * Retrieves a train from the database based on the specified train name.
     * 
     * @param trainName the name of the train to retrieve
     * @return the {@code Train} object corresponding to the specified train name,
     *         or {@code null} if no such train exists
     */
    public Train getTrainByTrainName(String trainName);

}
