/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.sql.ResultSet;

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface PassengerDAO {

    public int createPassenger(Passenger passenger);

    public int getPassengerIDByIdNumber(int idNumber);

    public Passenger getPassengerByPassengerId(int passengerId);
}
