package model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Represents a user in the system.
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class User {

    private int userID;
    private String username;
    private String password;
    private byte[] salt;
    private String role;
    
    public User(){
        userID = 0;
        username = null;
        password = null;
        salt = null;
        role = null;
    }

    /**
     * Constructs a new User.
     *
     * @param userID the user ID
     * @param username the username
     * @param password the password
     * @param salt the salt used for password hashing
     * @param role the role of the user
     */
    public User(int userID, String username, String password, byte[] salt, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    /**
     * Constructs a new User with default role "member".
     *
     * @param userID the user ID
     * @param username the username
     * @param password the password
     * @param salt the salt used for password hashing
     */
    public User(int userID, String username, String password, byte[] salt) {
        this(userID, username, password, salt, "member");
    }

    // Getters and setters for encapsulation
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
