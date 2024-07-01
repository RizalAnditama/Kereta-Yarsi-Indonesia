/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.HeadlessException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DatabaseConnector;
import model.Train;

/**
 * Controller class handles various operations related to the database and
 * application logic. It includes methods for database connection, encryption,
 * and user session management. This class follows a singleton design pattern to
 * ensure only one instance exists.
 *
 * This class uses the singleton pattern to ensure that only one instance of the
 * controller exists.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * Controller controller = Controller.getInstance();
 * int userCount = controller.getRowCount("user");
 * </pre>
 *
 * @version 1.0
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Controller {

    private static Controller instance;

    protected static DatabaseConnector databaseConnector;
    protected static Connection con;
    protected static String sql;
    public static Statement st;
    public static ResultSet rs;
    public static PreparedStatement ps;

    private static final String DELIMITER = ":";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    public boolean response = false;

    public Session session = Session.getInstance();

    /**
     * Constructor for Controller. It initializes the DatabaseConnector.
     *
     * @param databaseConnector the DatabaseConnector instance to use
     */
    protected Controller(DatabaseConnector databaseConnector) {
        Controller.databaseConnector = databaseConnector;
        con = DatabaseConnector.getConnect();
    }

    /**
     * Returns the singleton instance of the Controller.
     *
     * @return the singleton instance
     */
    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Returns the singleton instance of the Session.
     *
     * @return the singleton session instance
     */
    public static synchronized Session getSession() {
        return Session.getInstance();
    }

    /**
     * Generates a random salt for password hashing.
     *
     * @param saltIteration the length of the salt in bytes
     * @return the generated salt
     */
    public static byte[] generateSalt(int saltIteration) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltIteration];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Encrypts a password using PBKDF2 with HMAC SHA-256.
     *
     * @param password the password to encrypt
     * @param salt the salt to use in the encryption
     * @return the encrypted password
     */
    public static String encryptPBKDF2(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] hash = null;
        try {
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);
        return saltBase64 + DELIMITER + hashBase64;
    }

    /**
     * Verifies if the input password matches the stored password.
     *
     * @param inputPassword the password to verify
     * @param storedPassword the stored hashed password
     * @param salt the salt used in hashing
     * @return true if passwords match, false otherwise
     */
    public static boolean verifyPassword(String inputPassword, String storedPassword, byte[] salt) {
        String newHash = null;
        String storedHash = storedPassword.split(DELIMITER)[1];
        KeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            newHash = Base64.getEncoder().encodeToString(hash);
            return newHash.equals(storedHash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Sets the initial train information for a session.
     *
     * @param stasiunAsal the starting station
     * @param stasiunAkhir the ending station
     * @param tanggalPergi the departure date
     * @param tanggalPulang the return date
     */
    public void setFirstStuff(String stasiunAsal, String stasiunAkhir, Date tanggalPergi, Date tanggalPulang) {
        Train currentTrain = session.getTrain();
        currentTrain.setStartingStation(stasiunAsal);
        currentTrain.setEndingStation(stasiunAkhir);
        currentTrain.setDepartingTime(new Timestamp(tanggalPergi.getTime()));
        if (tanggalPulang != null) {
            currentTrain.setArrivingTime(new Timestamp(tanggalPulang.getTime()));
        }
        session.setPenumpangTotal(session.getPenumpangAnak() + session.getPenumpangDewasa());
    }

    /**
     * Counts the number of trains matching the specified criteria.
     *
     * @param stasiunAwal the starting station
     * @param stasiunAkhir the ending station
     * @param tanggal the date of departure
     * @return the number of matching trains
     */
    public int countFoundTrain(String stasiunAwal, String stasiunAkhir, Date tanggal) {
        try {
            setPs(con.prepareStatement(
                    "SELECT COUNT(*) as count FROM train WHERE starting_station = ? AND ending_station = ? AND departing_time > ?"));
            getPs().setString(1, stasiunAwal);
            getPs().setString(2, stasiunAkhir);
            getPs().setTimestamp(3, new Timestamp(tanggal.getTime()));
            setRs(getPs().executeQuery());
            getRs().next();
            return getRs().getInt("count");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Finds specific trains matching the criteria.
     *
     * @param stasiunAwal the starting station
     * @param stasiunAkhir the ending station
     * @param tanggal the date of departure
     * @return a ResultSet of the matching trains
     */
    public ResultSet findSpecificTrain(String stasiunAwal, String stasiunAkhir, Date tanggal) {
        try {
            setPs(con.prepareStatement(
                    "SELECT * FROM train WHERE starting_station = ? AND ending_station = ? AND departing_time > ? ORDER BY departing_time ASC"));
            getPs().setString(1, stasiunAwal);
            getPs().setString(2, stasiunAkhir);
            getPs().setTimestamp(3, new Timestamp(tanggal.getTime()));
            System.out.println(getPs().toString());
            return getPs().executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Builds a DefaultTableModel from a ResultSet.
     *
     * @param rs the ResultSet to process
     * @return a DefaultTableModel containing the data from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    /**
     * Finds all records from a specified table.
     *
     * @param table the table to query
     * @return a ResultSet containing all records from the table
     */
    public ResultSet findAll(String table) {
        return executeQuery("SELECT * FROM " + table);
    }

    /**
     * Finds records from a table where a specific column matches a keyword.
     *
     * @param table the table to query
     * @param columnName the column to search
     * @param keyword the keyword to match
     * @return a ResultSet containing the matching records
     */
    public ResultSet findWhere(String table, String columnName, Object keyword) {
        return executeQuery("SELECT * FROM " + table + " WHERE " + columnName + " = '" + keyword + "'");
    }

    /**
     * Executes a specified SQL query without parameters.
     *
     * @param sql the SQL query to execute
     * @return a ResultSet containing the results of the query
     */
    public ResultSet executeQuery(String sql) {
        try {
            setSt(con.createStatement());
            setRs(getSt().executeQuery(sql));
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getRs();
    }

    /**
     * Executes a specified SQL query with parameters.
     *
     * @param sql the SQL query to execute
     * @param values the parameters to set in the query
     * @return a ResultSet containing the results of the query
     */
    public ResultSet executeQuery(String sql, Object... values) {
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getRs();
    }

    /**
     * Executes a specified SQL update (INSERT, UPDATE, DELETE) with parameters.
     *
     * @param sql the SQL update to execute
     * @param values the parameters to set in the update
     * @return true if the update affected at least one row, false otherwise
     */
    public boolean executeUpdate(String sql, Object... values) {
        int rowsAffected = 0;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rowsAffected = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowsAffected > 0;
    }

    /**
     * Inserts a new record into a specified table.
     *
     * @param table the table to insert into
     * @param withID whether to include an ID column
     * @param value the values to insert
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insert(String table, boolean withID, Object... value) {
        String[] columnName = getColumnName(table);
        if (columnName.length < 1) {
            return false;
        }

        for (String string : columnName) {
            System.out.print(string + ", ");
        }

        String query = "INSERT INTO " + table + " (";
        int a = 1;
        for (String row : columnName) {
            query += row;
            a++;
            if (a - 1 < getColumnCount(table)) {
                query += ", ";
            }
        }

        byte minusId = 0;

        if (withID) {
            query += ") VALUES (null, ";
            minusId = 1;
        } else {
            query += ") VALUES (";
        }

        for (int i = 0; i < getColumnCount(table) - minusId; i++) {
            query += "?";
            if (i + 1 < getColumnCount(table) - 1) {
                query += ", ";
            }
        }
        query += ")";
        try {
            setPs(con.prepareStatement(query));
            for (int i = 0; i < value.length; i++) {
                if (value[i] == null) {
                    getPs().setNull(i + 1, Integer.MIN_VALUE);
                } else {
                    getPs().setObject(i + 1, value[i]);
                }
            }
            response = getPs().executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(query);
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    /**
     * Displays an error message dialog.
     *
     * @param title the title of the dialog
     * @param message the message to display
     * @throws HeadlessException if an error occurs
     */
    public void errorMessage(String title, String message) throws HeadlessException {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a warning message dialog.
     *
     * @param message the message to display
     * @throws HeadlessException if an error occurs
     */
    public void warningMessage(String message) throws HeadlessException {
        JOptionPane.showMessageDialog(null, message, "Warning Message", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays an informational message dialog.
     *
     * @param message the message to display
     * @throws HeadlessException if an error occurs
     */
    public void infoMessage(String message) throws HeadlessException {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Retrieves the column names of a specified table.
     *
     * @param table the table to query
     * @return an array of column names
     */
    public String[] getColumnName(String table) {
        String[] columnName = new String[0];
        try {
            ResultSetMetaData metaData = findAll(table).getMetaData();
            int count = metaData.getColumnCount();
            columnName = new String[count];
            for (int i = 1; i <= count; i++) {
                columnName[i - 1] = metaData.getColumnName(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnName;
    }

    /**
     * Retrieves the column count of a specified table.
     *
     * @param table the table to query
     * @return the number of columns
     */
    public int getColumnCount(String table) {
        int count = 0;
        ResultSetMetaData metaData;
        try {
            metaData = findAll(table).getMetaData();
            count = metaData.getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    /**
     * Retrieves the row count of a specified table.
     *
     * @param table the table to query
     * @return the number of rows
     */
    public int getRowCount(String table) {
        setRs(executeQuery("SELECT COUNT(*) AS rowCount FROM " + table + ""));
        try {
            getRs().next();
            return getRs().getInt("rowCount");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Checks if a specified database exists.
     *
     * @param database the database to check
     * @return true if the database exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean checkForDatabase(String database) throws SQLException {
        setRs(executeQuery("SELECT SCHEMA_NAME AS rowCount FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
                + database + "'"));

        return getRs().next();
    }

    /**
     * Retrieves the row count of a specified table where a column matches a
     * keyword.
     *
     * @param table the table to query
     * @param columnName the column to search
     * @param keyword the keyword to match
     * @return the number of matching rows
     */
    public int getRowCountWhere(String table, String columnName, String keyword) {
        setRs(executeQuery(
                "SELECT COUNT(*) AS rowCount FROM " + table + " WHERE " + columnName + " = '" + keyword + "'"));
        try {
            getRs().next();
            return getRs().getInt("rowCount");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Checks if a string contains any whitespace characters.
     *
     * @param str the string to check
     * @return true if the string contains whitespace, false otherwise
     */
    public boolean hasWhitespace(String str) {
        Pattern p = Pattern.compile("\\S");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Checks if a string contains any special characters.
     *
     * @param str the string to check
     * @return true if the string contains special characters, false otherwise
     */
    public boolean hasSpecialChar(String str) {
        Pattern p = Pattern.compile("[^a-z0-9]");
        Matcher m = p.matcher(str);
        return !m.find();
    }

    /**
     * Checks if a string contains any uppercase characters.
     *
     * @param str the string to check
     * @return true if the string contains uppercase characters, false otherwise
     */
    public boolean hasUpperCase(String str) {
        Pattern p = Pattern.compile("[^A-Z]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Generates a random key of a specified length.
     *
     * @param keyLength the length of the key in bytes
     * @return the generated key
     */
    public byte[] generateKey(int keyLength) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[keyLength];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Hashes a password using PBKDF2 with HMAC SHA-1.
     *
     * @param str the password to hash
     * @return the hashed password
     */
    public String hashPassword(String str) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] hash = new byte[0];
        try {
            hash = f.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(salt) + enc.encodeToString(hash);
    }

    /**
     * Retrieves the current Statement.
     *
     * @return the current Statement
     */
    public static Statement getSt() {
        return st;
    }

    /**
     * Sets the current Statement.
     *
     * @param aSt the Statement to set
     */
    public static void setSt(Statement aSt) {
        st = aSt;
    }

    /**
     * Retrieves the current ResultSet.
     *
     * @return the current ResultSet
     */
    public static ResultSet getRs() {
        return rs;
    }

    /**
     * Sets the current ResultSet.
     *
     * @param aRs the ResultSet to set
     */
    public static void setRs(ResultSet aRs) {
        rs = aRs;
    }

    /**
     * Retrieves the current PreparedStatement.
     *
     * @return the current PreparedStatement
     */
    public static PreparedStatement getPs() {
        return ps;
    }

    /**
     * Sets the current PreparedStatement.
     *
     * @param aPs the PreparedStatement to set
     */
    public static void setPs(PreparedStatement aPs) {
        ps = aPs;
    }
}
