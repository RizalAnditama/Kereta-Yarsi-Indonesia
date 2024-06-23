package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Represents a user in the system.
 * <p>
 * This class encapsulates the details of a user, including their ID, username,
 * password, salt used for password hashing, and role within the system.
 * </p>
 * 
 * @version 1.0
 * @since 2024-06-23
 * 
 * @see model.UserDAO
 * @see controller.UserController
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public class User {

    private int userID;
    private String username;
    private String password;
    private byte[] salt;
    private String role;

    /**
     * Default constructor for creating an empty user object.
     */
    public User() {
        userID = 0;
        username = null;
        password = null;
        salt = null;
        role = null;
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param userID   the user ID
     * @param username the username
     * @param password the password
     * @param salt     the salt used for password hashing
     * @param role     the role of the user
     */
    public User(int userID, String username, String password, byte[] salt, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    /**
     * Constructs a new User with the specified details and a default role of
     * "member".
     *
     * @param userID   the user ID
     * @param username the username
     * @param password the password
     * @param salt     the salt used for password hashing
     */
    public User(int userID, String username, String password, byte[] salt) {
        this(userID, username, password, salt, "member");
    }

    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID.
     * 
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the salt used for password hashing.
     * 
     * @return the salt
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Sets the salt used for password hashing.
     * 
     * @param salt the salt to set
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * Gets the role of the user.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
