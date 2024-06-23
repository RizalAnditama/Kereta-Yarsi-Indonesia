/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Represents an admin user in the system.
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Admin extends User {

    /**
     * Constructs a new Admin.
     *
     * @param userID the user ID
     * @param username the username
     * @param password the password
     * @param salt the salt used for password hashing
     * @param role the role of the user
     */
    public Admin(int userID, String username, String password, byte[] salt, String role) {
        super(userID, username, password, salt, role);
    }

    /**
     * Constructs a new Admin with role "admin".
     *
     * @param userID the user ID
     * @param username the username
     * @param password the password
     * @param salt the salt used for password hashing
     */
    public Admin(int userID, String username, String password, byte[] salt) {
        super(userID, username, password, salt, "admin");
    }

    // Additional methods specific to admin
    public void manageUsers() {
        System.out.println("Managing users...");
    }
}
