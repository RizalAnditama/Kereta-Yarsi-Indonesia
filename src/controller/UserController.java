/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.security.SecureRandom;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;
import model.DatabaseConnector;
import model.User;
import model.UserDAO;

/**
 * Controller class for managing operations related to users. Implements the
 * UserDAO interface to provide methods for user management.
 *
 * This class uses the singleton pattern to ensure that only one instance of the
 * controller exists.
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * UserController controller = UserController.getInstance();
 * boolean isLogin = controller.login("myworld", "theworld213");
 * </pre>
 *
 * @see model.UserDAO
 * @see model.User
 * @see model.Admin
 * @see model.DatabaseConnector
 *
 * @version 1.0
 * @author Muhammad Rizal Anditama Nugraha
 */
public class UserController extends Controller implements UserDAO {

    private static UserController instance;

    /**
     * Private constructor to prevent direct instantiation. Initializes the
     * controller with the given DatabaseConnector.
     *
     * @param databaseConnector the database connector to use
     */
    private UserController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    /**
     * Returns the single instance of UserController. If no instance exists, it
     * creates one using the provided DatabaseConnector.
     *
     * @return the singleton instance of UserController
     */
    public static synchronized UserController getInstance() {
        if (instance == null) {
            instance = new UserController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the User object representing the user to create
     * @return true if user creation was successful, false otherwise
     */
    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO user (user_id, username, password, salt, role) VALUES (null, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DatabaseConnector.getConnect().prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, DatabaseConnector.encryptPBKDF2(user.getPassword(), user.getSalt()));
            ps.setBytes(3, user.getSalt());
            ps.setString(4, user.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Retrieves a user from the database by user ID.
     *
     * @param userID the ID of the user to retrieve
     * @return a User object if found, otherwise null
     */
    @Override
    public User getUser(int userID) {
        try {
            String query = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement ps = DatabaseConnector.getConnect().prepareStatement(query);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if ("admin".equals(role)) {
                    return new Admin(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                            rs.getBytes("salt"), rs.getString("role"));
                } else {
                    return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                            rs.getBytes("salt"), rs.getString("role"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves a user from the database by username.
     *
     * @param username the username of the user to retrieve
     * @return a User object if found, otherwise null
     */
    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement ps = DatabaseConnector.getConnect().prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if ("admin".equals(role)) {
                    return new Admin(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                            rs.getBytes("salt"), rs.getString("role"));
                } else {
                    return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                            rs.getBytes("salt"), rs.getString("role"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the User object representing the updated user information
     * @return true if user update was successful, false otherwise
     */
    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE user SET username = ?, password = ?, salt = ?, role = ? WHERE user_id = ?";
        try {
            PreparedStatement ps = DatabaseConnector.getConnect().prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, DatabaseConnector.encryptPBKDF2(user.getPassword(), user.getSalt()));
            ps.setBytes(3, user.getSalt());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getUserID());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Deletes a user from the database by user ID.
     *
     * @param userID the ID of the user to delete
     * @return true if user deletion was successful, false otherwise
     */
    @Override
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try {
            PreparedStatement ps = DatabaseConnector.getConnect().prepareStatement(query);
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param user the User object representing the user to register
     * @return true if user registration was successful, false otherwise
     */
    @Override
    public boolean register(User user) {
        return createUser(user);
    }

    /**
     * Authenticates a user login based on username and password.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return true if login is successful, false otherwise
     */
    @Override
    public boolean login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            databaseConnector.infoMessage("Username not found!");
            return false;
        } else if (!DatabaseConnector.verifyPassword(password, user.getPassword(), user.getSalt())) {
            databaseConnector.infoMessage("Wrong password!");
            return false;
        }
        session.setUser(user);
        session.setAuth(true);
        return true;
    }

    /**
     * Performs user login and retrieves the User object if successful.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return the User object if login is successful, otherwise null
     */
    public User loginAndGetUser(String username, String password) {
        if (login(username, password)) {
            User user = getUserByUsername(username);
            if ("admin".equalsIgnoreCase(user.getRole())) {
                return new Admin(user.getUserID(), user.getUsername(), user.getPassword(), user.getSalt(),
                        user.getRole());
            }
            return user;
        }
        return null;
    }

    /**
     * Registers a new user in the database with the provided username and
     * password. Automatically generates a salt for password encryption.
     *
     * @param username the username of the user to register
     * @param password the password of the user to register
     * @return true if user registration was successful, false otherwise
     */
    public boolean register(String username, String password) {
        SecureRandom random = new SecureRandom();
        int saltValue = random.nextInt(100);
        byte[] salt = DatabaseConnector.generateSalt(saltValue);
        User newUser = new User(-1, username, password, salt); // Assuming -1 means the ID is auto-generated
        return register(newUser);
    }

    /**
     * Manages users based on the role of the provided User object.
     *
     * @param user the User object whose role determines the actions allowed
     */
    public void manageUsers(User user) {
        if (user instanceof Admin) {
            ((Admin) user).manageUsers();
        } else {
            System.out.println("User is not an admin.");
        }
    }
}
