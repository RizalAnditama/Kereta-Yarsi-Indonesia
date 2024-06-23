package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * The {@code Passenger} class represents a passenger in the KYI Integrated
 * system.
 * It includes details such as the passenger's ID, type of ID, number of ID,
 * full name,
 * age type, and seat number.
 * <p>
 * This class provides constructors to create a {@code Passenger} object and
 * getter and
 * setter methods to access and modify the passenger's attributes.
 * </p>
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Passenger {

    private int passengerID;
    private String tipeID;
    private String noID;
    private String namaLengkap;
    private String tipeUmur;
    private String kursi;

    /**
     * Constructs a {@code Passenger} object with specified passenger ID, full name,
     * type of ID, number of ID, and seat number.
     * 
     * @param passengerID the ID of the passenger
     * @param namaLengkap the full name of the passenger
     * @param tipeID      the type of ID of the passenger
     * @param noID        the number of the ID of the passenger
     * @param kursi       the seat number of the passenger
     */
    public Passenger(int passengerID, String namaLengkap, String tipeID, String noID, String kursi) {
        this.passengerID = passengerID;
        this.tipeID = tipeID;
        this.noID = noID;
        this.namaLengkap = namaLengkap;
        this.kursi = kursi;
        if (tipeID == null && noID == null) {
            this.tipeUmur = "Anak-anak";
        } else {
            this.tipeUmur = "Dewasa";
        }
    }

    /**
     * Constructs a {@code Passenger} object with specified full name, type of ID,
     * number of ID, and seat number. The passenger ID is set to -1.
     * 
     * @param namaLengkap the full name of the passenger
     * @param tipeID      the type of ID of the passenger
     * @param noID        the number of the ID of the passenger
     * @param kursi       the seat number of the passenger
     */
    public Passenger(String namaLengkap, String tipeID, String noID, String kursi) {
        this(-1, namaLengkap, tipeID, noID, kursi);
    }

    /**
     * Returns the type of ID of the passenger.
     * 
     * @return the type of ID of the passenger
     */
    public String getTipeID() {
        return tipeID;
    }

    /**
     * Sets the type of ID of the passenger.
     * 
     * @param tipeID the type of ID to set
     */
    public void setTipeID(String tipeID) {
        this.tipeID = tipeID;
    }

    /**
     * Returns the number of the ID of the passenger.
     * 
     * @return the number of the ID of the passenger
     */
    public String getNoID() {
        return noID;
    }

    /**
     * Sets the number of the ID of the passenger.
     * 
     * @param noID the number of the ID to set
     */
    public void setNoID(String noID) {
        this.noID = noID;
    }

    /**
     * Returns the full name of the passenger.
     * 
     * @return the full name of the passenger
     */
    public String getNamaLengkap() {
        return namaLengkap;
    }

    /**
     * Sets the full name of the passenger.
     * 
     * @param namaLengkap the full name to set
     */
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    /**
     * Returns the age type of the passenger.
     * 
     * @return the age type of the passenger
     */
    public String getTipeUmur() {
        return tipeUmur;
    }

    /**
     * Sets the age type of the passenger.
     * 
     * @param tipeUmur the age type to set
     */
    public void setTipeUmur(String tipeUmur) {
        this.tipeUmur = tipeUmur;
    }

    /**
     * Returns the seat number of the passenger.
     * 
     * @return the seat number of the passenger
     */
    public String getKursi() {
        return kursi;
    }

    /**
     * Sets the seat number of the passenger.
     * 
     * @param kursi the seat number to set
     */
    public void setKursi(String kursi) {
        this.kursi = kursi;
    }

    /**
     * Returns the ID of the passenger.
     * 
     * @return the ID of the passenger
     */
    public int getPassengerID() {
        return passengerID;
    }

    /**
     * Sets the ID of the passenger.
     * 
     * @param passengerID the ID to set
     */
    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

}
