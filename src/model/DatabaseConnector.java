package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import model.Passenger;
import java.awt.HeadlessException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.sql.Connection;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Manages the connection and operations with the MySQL database.
 * <p>
 * This class provides methods to interact with the database, such as executing
 * queries, inserting data,
 * hashing passwords, and managing database connections.
 * </p>
 *
 * @version 2.0
 */
public class DatabaseConnector {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/database_kyi";
    private static final String USERDB = "root";
    private static final String PASSWORD = "";

    public static Connection con;
    public static DatabaseConnector instance;
    public static Statement st;
    public static ResultSet rs;
    public static PreparedStatement ps;

    protected static final String DELIMITER = ":";
    protected static final int ITERATION_COUNT = 65536;
    protected static final int KEY_LENGTH = 256;


    /**
     * Constructs a DatabaseConnector object and initializes the database
     * connection.
     * <p>
     * It also registers the MySQL JDBC driver and attempts to establish a
     * connection to the specified database.
     * </p>
     */
    public DatabaseConnector() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con = DriverManager.getConnection(URL, USERDB, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the current database connection instance.
     *
     * @return the Connection object representing the database connection
     */
    public static Connection getConnect() {
        return con;
    }

    /**
     * Returns the singleton instance of the DatabaseConnector class.
     *
     * @return the singleton instance of DatabaseConnector
     */
    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }
}
