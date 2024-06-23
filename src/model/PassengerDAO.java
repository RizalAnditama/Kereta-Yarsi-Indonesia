/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 * The {@code PassengerDAO} interface provides methods for performing database
 * operations
 * related to passengers in the KYI Integrated system.
 * <p>
 * This interface includes methods for creating a passenger, retrieving a
 * passenger ID
 * by their ID number, and retrieving a passenger by their passenger ID.
 * </p>
 * <p>
 * Implementations of this interface should handle the database-specific logic
 * for
 * these operations.
 * </p>
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface PassengerDAO {

    /**
     * Creates a new passenger in the database.
     * 
     * @param passenger the passenger object containing the details of the passenger
     *                  to create
     * @return the ID of the created passenger
     */
    public int createPassenger(Passenger passenger);

    /**
     * Retrieves the ID of a passenger by their ID number.
     * 
     * @param idNumber the ID number of the passenger
     * @return the ID of the passenger
     */
    public int getPassengerIDByIdNumber(int idNumber);

    /**
     * Retrieves a passenger by their passenger ID.
     * 
     * @param passengerId the ID of the passenger
     * @return the passenger object
     */
    public Passenger getPassengerByPassengerId(int passengerId);
}
