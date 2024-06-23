package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 * Interface for User Data Access Object (DAO).
 * <p>
 * This interface defines methods to access and manipulate user data in the
 * database.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-23
 * 
 * @see User
 * @see controller.UserController
 * @see DatabaseConnector
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface UserDAO {

    /**
     * Creates a new user in the database.
     *
     * @param user the user object to be created
     * @return true if the user is successfully created, false otherwise
     */
    boolean createUser(User user);

    /**
     * Retrieves a user from the database based on the user ID.
     *
     * @param userID the ID of the user to retrieve
     * @return the User object if found, null otherwise
     */
    User getUser(int userID);

    /**
     * Retrieves a user from the database based on the username.
     *
     * @param username the username of the user to retrieve
     * @return the User object if found, null otherwise
     */
    User getUserByUsername(String username);

    /**
     * Updates an existing user in the database.
     *
     * @param user the user object to be updated
     * @return true if the user is successfully updated, false otherwise
     */
    boolean updateUser(User user);

    /**
     * Deletes a user from the database based on the user ID.
     *
     * @param userID the ID of the user to delete
     * @return true if the user is successfully deleted, false otherwise
     */
    boolean deleteUser(int userID);

    /**
     * Registers a new user in the system.
     *
     * @param user the user object to be registered
     * @return true if the user is successfully registered, false otherwise
     */
    boolean register(User user);

    /**
     * Authenticates a user based on the username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the login is successful, false otherwise
     */
    boolean login(String username, String password);
}
