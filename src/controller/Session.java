/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import model.Admin;
import model.Passenger;
import model.Ticket;
import model.User;
import model.Station;
import model.Train;
import view.MainMenu_KYI;
import view.SignIn_KYI;

/**
 * Singleton class to manage session information.
 * This class holds the state of the current session including user, ticket,
 * passenger, station, and train details.
 * It also handles user authentication and access checks.
 * 
 * @see model.User
 * @see model.Ticket
 * @see model.Passenger
 * @see model.Station
 * @see model.Train
 * @see view.MainMenu_KYI
 * @see view.SignIn_KYI
 * 
 * @version 1.0
 * @author Muhammad Rizal Anditama Nugraha
 * 
 */
public class Session {

    private static Session instance;
    private boolean auth;
    private int penumpangDewasa = 1;
    private int penumpangAnak;
    private int penumpangTotal;

    private String metodePembayaran;
    private double potonganAsuransi = 10;
    private double potonganBiayaPenanganan = 2;
    private double subtotalHarga;
    private double totalHarga;

    private User user;
    private Ticket ticket;
    private List<Passenger> passenger;
    private Station station;
    private Train train;

    /**
     * No-argument constructor initializing default values.
     * Initializes user, ticket, passenger, station, and train with default or empty
     * values.
     */
    private Session() {
        this.auth = false;
        this.user = new User(); // Or set to null if no default user
        this.ticket = new Ticket(); // Or set to null if no default ticket
        this.passenger = new ArrayList<>(); // Assuming you want an empty list by default
        this.station = new Station(); // Or set to null if no default station
        this.train = new Train(); // Initialize an empty Train object
    }

    /**
     * Parameterized constructor for full initialization.
     * 
     * @param auth             the authentication status
     * @param currentUser      the current user
     * @param currentTicket    the current ticket
     * @param currentPassenger the current list of passengers
     * @param currentStation   the current station
     * @param currentTrain     the current train
     */
    public Session(boolean auth, User currentUser, Ticket currentTicket, List<Passenger> currentPassenger,
            Station currentStation, Train currentTrain) {
        this.auth = auth;
        this.user = currentUser;
        this.ticket = currentTicket;
        this.passenger = currentPassenger;
        this.station = currentStation;
        this.train = currentTrain;
    }

    /**
     * Returns the single instance of Session.
     * If no instance exists, it creates one using the default constructor.
     * 
     * @return the singleton instance of Session
     */
    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Clears the current session by setting user, ticket, passenger, station, and
     * train to null.
     */
    public void clearSession() {
        setUser(null);
        setTicket(null);
        setPassenger(null);
        setStation(null);
        setTrain(null);
    }

    /**
     * Checks if the user is authenticated.
     * If not authenticated, displays a message and redirects to the SignIn_KYI
     * view.
     * 
     * @param jFrame the current JFrame
     * @return true if the user is authenticated, false otherwise
     */
    public boolean checkUserAuth(JFrame jFrame) {
        UserController userController = UserController.getInstance();

        if (!isAuth()) {
            userController.infoMessage("You are not allowed to access this.");
            new SignIn_KYI(userController).setVisible(true);
            jFrame.dispose();
            return false;
        }
        return true;
    }

    /**
     * Checks if the user has admin access.
     * If not an admin, displays a message and redirects to the MainMenu_KYI view.
     * 
     * @param jFrame the current JFrame
     * @return true if the user has admin access, false otherwise
     */
    public boolean checkUserAccess(JFrame jFrame) {
        UserController userController = UserController.getInstance();
        User user = Session.getInstance().getUser();

        if (!(user instanceof Admin)) {
            userController.infoMessage("You are not allowed to access this.");
            new MainMenu_KYI().setVisible(true);
            jFrame.dispose();
            return false;
        }
        return true;
    }

    /**
     * Gets the current user.
     * 
     * @return the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the current user.
     * 
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the current ticket.
     * 
     * @return the current ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets the current ticket.
     * 
     * @param ticket the ticket to set
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Gets the list of current passengers.
     * 
     * @return the list of current passengers
     */
    public List<Passenger> getPassenger() {
        return passenger;
    }

    /**
     * Sets the list of current passengers.
     * 
     * @param passenger the list of passengers to set
     */
    public void setPassenger(List<Passenger> passenger) {
        this.passenger = passenger;
    }

    /**
     * Gets the current station.
     * 
     * @return the current station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the current station.
     * 
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Gets the current train.
     * 
     * @return the current train
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Sets the current train.
     * 
     * @param train the train to set
     */
    public void setTrain(Train train) {
        this.train = train;
    }

    /**
     * Checks if the user is authenticated.
     * 
     * @return true if the user is authenticated, false otherwise
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * Sets the authentication status of the user.
     * 
     * @param auth the authentication status to set
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * Gets the number of adult passengers.
     * 
     * @return the number of adult passengers
     */
    public int getPenumpangDewasa() {
        return penumpangDewasa;
    }

    /**
     * Sets the number of adult passengers.
     * 
     * @param penumpangDewasa the number of adult passengers to set
     */
    public void setPenumpangDewasa(int penumpangDewasa) {
        this.penumpangDewasa = penumpangDewasa;
    }

    /**
     * Gets the number of child passengers.
     * 
     * @return the number of child passengers
     */
    public int getPenumpangAnak() {
        return penumpangAnak;
    }

    /**
     * Sets the number of child passengers.
     * 
     * @param penumpangAnak the number of child passengers to set
     */
    public void setPenumpangAnak(int penumpangAnak) {
        this.penumpangAnak = penumpangAnak;
    }

    /**
     * Gets the total number of passengers.
     * 
     * @return the total number of passengers
     */
    public int getPenumpangTotal() {
        return penumpangTotal;
    }

    /**
     * Sets the total number of passengers.
     * 
     * @param penumpangTotal the total number of passengers to set
     */
    public void setPenumpangTotal(int penumpangTotal) {
        this.penumpangTotal = penumpangTotal;
    }

    /**
     * Gets the payment method.
     * 
     * @return the payment method
     */
    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    /**
     * Sets the payment method.
     * 
     * @param metodePembayaran the payment method to set
     */
    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    /**
     * Gets the subtotal price.
     * 
     * @return the subtotal price
     */
    public double getSubtotalHarga() {
        return subtotalHarga;
    }

    /**
     * Sets the subtotal price.
     * 
     * @param subtotalHarga the subtotal price to set
     */
    public void setSubtotalHarga(double subtotalHarga) {
        this.subtotalHarga = subtotalHarga;
    }

    /**
     * Gets the total price.
     * 
     * @return the total price
     */
    public double getTotalHarga() {
        return totalHarga;
    }

    /**
     * Sets the total price.
     * 
     * @param totalHarga the total price to set
     */
    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    /**
     * Gets the insurance discount amount.
     * 
     * @return the insurance discount amount
     */
    public double getPotonganAsuransi() {
        return potonganAsuransi;
    }

    /**
     * Sets the insurance discount amount.
     * 
     * @param potonganAsuransi the insurance discount amount to set
     */
    public void setPotonganAsuransi(double potonganAsuransi) {
        this.potonganAsuransi = potonganAsuransi;
    }

    /**
     * Gets the handling fee discount amount.
     * 
     * @return the handling fee discount amount
     */
    public double getPotonganBiayaPenanganan() {
        return potonganBiayaPenanganan;
    }

    /**
     * Sets the handling fee discount amount.
     * 
     * @param potonganBiayaPenanganan the handling fee discount amount to set
     */
    public void setPotonganBiayaPenanganan(double potonganBiayaPenanganan) {
        this.potonganBiayaPenanganan = potonganBiayaPenanganan;
    }

}
