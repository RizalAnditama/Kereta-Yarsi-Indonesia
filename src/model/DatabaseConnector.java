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
 * @version 1.0
 * @since 2024-06-23
 * 
 * @see Passenger
 * @see User
 * @see DefaultTableModel
 * @see SecretKeyFactory
 * @see PreparedStatement
 * @see ResultSet
 * @see SQLException
 * @see SecureRandom
 * @see PBEKeySpec
 * @see NoSuchAlgorithmException
 * @see InvalidKeySpecException
 * @see KeySpec
 * @see Logger
 * @see Level
 * @see DriverManager
 * @see Connection
 * @see Statement
 * @see ResultSetMetaData
 * @see Pattern
 * @see Matcher
 * @see JOptionPane
 * @see Charset
 * @see HeadlessException
 * @see Date
 * @see Timestamp
 * @see Vector
 * @see Base64
 * @see Base64.Encoder
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

    private static boolean response;

    protected static final String DELIMITER = ":";
    protected static final int ITERATION_COUNT = 65536;
    protected static final int KEY_LENGTH = 256;

    private User user;

    private String stasiunAsal;
    private String stasiunAkhir;
    private Date tanggalPergi;
    private Date tanggalPulang;
    private int penumpangDewasa = 1;
    private int penumpangAnakAnak;
    private int penumpangTotal = 1;

    private String namaKereta;
    private java.sql.Timestamp timestampDepart;
    private java.sql.Timestamp timestampArrive;
    private int hargaKereta;

    private List<Passenger> passengerData;

    private String metodePembayaran;
    private double potonganAsuransi = 10;
    private double potonganBiayaPenanganan = 2;
    private double subtotalHarga;
    private double totalHarga;

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

    /**
     * Generates a salt of specified length using a secure random number generator.
     *
     * @param saltIteration the length of the salt to generate
     * @return a byte array representing the generated salt
     */
    public static byte[] generateSalt(int saltIteration) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltIteration];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Encrypts the given password using PBKDF2 algorithm with HMAC SHA-256.
     *
     * @param password the password to encrypt
     * @param salt     the salt used in encryption
     * @return a string containing the base64 encoded salt and hash separated by a
     *         delimiter
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
     * Verifies the given password against the stored password hash using PBKDF2
     * algorithm with HMAC SHA-256.
     *
     * @param inputPassword  the password to verify
     * @param storedPassword the stored password hash and salt
     * @param salt           the salt used in the stored password hash
     * @return true if the input password matches the stored password, false
     *         otherwise
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
     * Counts the number of trains available between the specified stations and
     * date.
     *
     * @param stasiunAwal  the starting station
     * @param stasiunAkhir the ending station
     * @param tanggal      the departure date
     * @return the number of trains available
     */
    public int countFoundTrain(String stasiunAwal, String stasiunAkhir, Date tanggal) {
        try {
            ps = con.prepareStatement(
                    "SELECT COUNT(*) as count FROM train WHERE starting_station = ? AND ending_station = ? AND departing_time > ?");
            ps.setString(1, stasiunAwal);
            ps.setString(2, stasiunAkhir);
            ps.setTimestamp(3, new Timestamp(tanggal.getTime()));
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Retrieves all trains between the specified stations and date.
     *
     * @param stasiunAwal  the starting station
     * @param stasiunAkhir the ending station
     * @param tanggal      the departure date
     * @return a ResultSet containing all matching train records
     */
    public ResultSet findSpecificTrain(String stasiunAwal, String stasiunAkhir, Date tanggal) {
        try {
            ps = con.prepareStatement(
                    "SELECT * FROM train WHERE starting_station = ? AND ending_station = ? AND departing_time > ? ORDER BY departing_time ASC");
            ps.setString(1, stasiunAwal);
            ps.setString(2, stasiunAkhir);
            ps.setTimestamp(3, new Timestamp(tanggal.getTime()));
            return ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Constructs a DefaultTableModel based on the provided ResultSet.
     *
     * @param rs the ResultSet containing the data
     * @return a DefaultTableModel representing the ResultSet data
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
     * Executes a query to retrieve all records from a specified table.
     * 
     * @param table The name of the table to query.
     * @return A ResultSet object containing the results of the query.
     */
    public ResultSet findAll(String table) {
        return executeQuery("SELECT * FROM " + table);
    }

    /**
     * Executes a query to retrieve records from a specified table where a specific
     * column matches a keyword.
     * 
     * @param table      The name of the table to query.
     * @param columnName The name of the column to match.
     * @param keyword    The keyword to match against the column.
     * @return A ResultSet object containing the results of the query.
     */
    public ResultSet findWhere(String table, String columnName, String keyword) {
        return executeQuery("SELECT * FROM " + table + " WHERE " + columnName + " = '" + keyword + "'");
    }

    /**
     * Executes a SQL query and returns the result as a ResultSet.
     * 
     * @param execute The SQL query to execute.
     * @return A ResultSet object containing the results of the query.
     */
    public ResultSet executeQuery(String execute) {
        try {
            setSt(con.createStatement());
            setRs(getSt().executeQuery(execute));
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getRs();
    }

    /**
     * Inserts a new record into a specified table.
     * 
     * @param table  The name of the table to insert into.
     * @param withID Indicates whether to include an ID column in the insert.
     * @param value  The values to insert into the table.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean insert(String table, boolean withID, String... value) {
        String[] columnName = getColumnName(table);
        if (columnName.length < 1) {
            return false;
        }

        // Building the query
        StringBuilder query = new StringBuilder("INSERT INTO " + table + " (");
        int a = 1;
        for (String row : columnName) {
            query.append(row);
            a++;
            if (a - 1 < getColumnCount(table)) {
                query.append(", ");
            }
        }

        byte minusId = 0;
        if (withID) {
            query.append(") VALUES (null, ");
            minusId = 1;
        } else {
            query.append(") VALUES (");
        }

        for (int i = 0; i < getColumnCount(table) - minusId; i++) {
            query.append("?");
            if (i + 1 < getColumnCount(table) - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try {
            setPs(con.prepareStatement(query.toString()));
            for (int i = 0; i < value.length; i++) {
                getPs().setString(i + 1, value[i]);
            }
            response = getPs().executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(query.toString());
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    /**
     * Displays an error message dialog.
     * 
     * @param title   The title of the error dialog.
     * @param message The error message to display.
     * @throws HeadlessException If the environment does not support graphical
     *                           displays.
     */
    public void errorMessage(String title, String message) throws HeadlessException {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a warning message dialog.
     * 
     * @param message The warning message to display.
     * @throws HeadlessException If the environment does not support graphical
     *                           displays.
     */
    public void warningMessage(String message) throws HeadlessException {
        JOptionPane.showMessageDialog(null, message, "Warning Message", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays an information message dialog.
     * 
     * @param message The information message to display.
     * @throws HeadlessException If the environment does not support graphical
     *                           displays.
     */
    public void infoMessage(String message) throws HeadlessException {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Retrieves the column names of a specified table.
     * 
     * @param table The name of the table.
     * @return An array of column names in the specified table.
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
     * Retrieves the number of columns in a specified table.
     * 
     * @param table The name of the table.
     * @return The number of columns in the specified table.
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
     * Retrieves the number of rows in a specified table.
     * 
     * @param table The name of the table.
     * @return The number of rows in the specified table.
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
     * Checks if a database with the specified name exists.
     * 
     * @param database The name of the database to check.
     * @return true if the database exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean checkForDatabase(String database) throws SQLException {
        setRs(executeQuery("SELECT SCHEMA_NAME AS rowCount FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
                + database + "'"));

        return getRs().next();
    }

    /**
     * Retrieves the number of rows in a specified table where a column matches a
     * keyword.
     * 
     * @param table      The name of the table.
     * @param columnName The name of the column to match.
     * @param keyword    The keyword to match against the column.
     * @return The number of rows matching the criteria.
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
     * @param str The string to check.
     * @return true if the string contains whitespace, false otherwise.
     */
    public boolean hasWhitespace(String str) {
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Checks if a string contains any special characters (characters other than
     * alphabets and digits).
     * 
     * @param str The string to check.
     * @return true if the string contains special characters, false otherwise.
     */
    public boolean hasSpecialChar(String str) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Checks if a string contains any uppercase letters.
     * 
     * @param str The string to check.
     * @return true if the string contains uppercase letters, false otherwise.
     */
    public boolean hasUpperCase(String str) {
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * Generates a random byte array of specified length.
     * 
     * @param keyLength The length of the byte array to generate.
     * @return A byte array containing random bytes.
     */
    public byte[] generateKey(int keyLength) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[keyLength];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Hashes a password using PBKDF2 with a random salt and returns the hashed
     * password as a string.
     * 
     * @param str The password to hash.
     * @return A string containing the Base64-encoded salt followed by the
     *         Base64-encoded hashed password.
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

    // boolean checkPassword(String input, String username) {
    // ResultSet user = executeQuery("SELECT * FROM user_login WHERE username LIKE
    // '" + username + "'");
    // try {
    // return hashPassword(userDB).equals(user.getString("password"));
    // } catch (SQLException ex) {
    // Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null,
    // ex);
    // }
    // return false;
    // }
    /**
     * @return the st
     */
    public static Statement getSt() {
        return st;
    }

    /**
     * @param aSt the st to set
     */
    public static void setSt(Statement aSt) {
        st = aSt;
    }

    /**
     * @return the rs
     */
    public static ResultSet getRs() {
        return rs;
    }

    /**
     * @param aRs the rs to set
     */
    public static void setRs(ResultSet aRs) {
        rs = aRs;
    }

    /**
     * @return the ps
     */
    public static PreparedStatement getPs() {
        return ps;
    }

    /**
     * @param aPs the ps to set
     */
    public static void setPs(PreparedStatement aPs) {
        ps = aPs;
    }

    /**
     * @return the penumpangDewasa
     */
    public int getPenumpangDewasa() {
        return penumpangDewasa;
    }

    /**
     * @param penumpangDewasa the penumpangDewasa to set
     */
    public void setPenumpangDewasa(int penumpangDewasa) {
        this.penumpangDewasa = penumpangDewasa;
    }

    /**
     * @return the penumpangAnakAnak
     */
    public int getPenumpangAnakAnak() {
        return penumpangAnakAnak;
    }

    /**
     * @param penumpangAnakAnak the penumpangAnakAnak to set
     */
    public void setPenumpangAnakAnak(int penumpangAnakAnak) {
        this.penumpangAnakAnak = penumpangAnakAnak;
    }

    /**
     * @return the stasiunAsal
     */
    public String getStasiunAsal() {
        return stasiunAsal;
    }

    /**
     * @param stasiunAsal the stasiunAsal to set
     */
    public void setStasiunAsal(String stasiunAsal) {
        this.stasiunAsal = stasiunAsal;
    }

    /**
     * @return the stasiunAkhir
     */
    public String getStasiunAkhir() {
        return stasiunAkhir;
    }

    /**
     * @param stasiunAkhir the stasiunAkhir to set
     */
    public void setStasiunAkhir(String stasiunAkhir) {
        this.stasiunAkhir = stasiunAkhir;
    }

    /**
     * @return the tanggalPergi
     */
    public Date getTanggalPergi() {
        return tanggalPergi;
    }

    /**
     * @param tanggalPergi the tanggalPergi to set
     */
    public void setTanggalPergi(Date tanggalPergi) {
        this.tanggalPergi = tanggalPergi;
    }

    /**
     * @return the tanggalPulang
     */
    public Date getTanggalPulang() {
        return tanggalPulang;
    }

    /**
     * @param tanggalPulang the tanggalPulang to set
     */
    public void setTanggalPulang(Date tanggalPulang) {
        this.tanggalPulang = tanggalPulang;
    }

    /**
     * @return the penumpangTotal
     */
    public int getPenumpangTotal() {
        return penumpangTotal;
    }

    /**
     * @param penumpangTotal the penumpangTotal to set
     */
    public void setPenumpangTotal(int penumpangTotal) {
        this.penumpangTotal = penumpangTotal;
    }

    /**
     * @return the timestampDepart
     */
    public java.sql.Timestamp getTimestampDepart() {
        return timestampDepart;
    }

    /**
     * @param timestampDepart the timestampDepart to set
     */
    public void setTimestampDepart(java.sql.Timestamp timestampDepart) {
        this.timestampDepart = timestampDepart;
    }

    /**
     * @return the timestampArrive
     */
    public java.sql.Timestamp getTimestampArrive() {
        return timestampArrive;
    }

    /**
     * @param timestampArrive the timestampArrive to set
     */
    public void setTimestampArrive(java.sql.Timestamp timestampArrive) {
        this.timestampArrive = timestampArrive;
    }

    /**
     * @return the namaKereta
     */
    public String getNamaKereta() {
        return namaKereta;
    }

    /**
     * @param namaKereta the namaKereta to set
     */
    public void setNamaKereta(String namaKereta) {
        this.namaKereta = namaKereta;
    }

    /**
     * @return the hargaKereta
     */
    public int getHargaKereta() {
        return hargaKereta;
    }

    /**
     * @param hargaKereta the hargaKereta to set
     */
    public void setHargaKereta(int hargaKereta) {
        this.hargaKereta = hargaKereta;
    }

    /**
     * @return the passengerData
     */
    public List<Passenger> getPassengerData() {
        return passengerData;
    }

    /**
     * @param passengerData the passengerData to set
     */
    public void setPassengerData(List<Passenger> passengerData) {
        this.passengerData = passengerData;
    }

    /**
     * @return the metodePembayaran
     */
    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    /**
     * @param metodePembayaran the metodePembayaran to set
     */
    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    /**
     * @return the subtotalHarga
     */
    public double getSubtotalHarga() {
        return subtotalHarga;
    }

    /**
     * @param subtotalHarga the subtotalHarga to set
     */
    public void setSubtotalHarga(double subtotalHarga) {
        this.subtotalHarga = subtotalHarga;
    }

    /**
     * @return the totalHarga
     */
    public double getTotalHarga() {
        return totalHarga;
    }

    /**
     * @param totalHarga the totalHarga to set
     */
    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    /**
     * @return the potonganAsuransi
     */
    public double getPotonganAsuransi() {
        return potonganAsuransi;
    }

    /**
     * @param potonganAsuransi the potonganAsuransi to set
     */
    public void setPotonganAsuransi(double potonganAsuransi) {
        this.potonganAsuransi = potonganAsuransi;
    }

    /**
     * @return the potonganBiayaPenanganan
     */
    public double getPotonganBiayaPenanganan() {
        return potonganBiayaPenanganan;
    }

    /**
     * @param potonganBiayaPenanganan the potonganBiayaPenanganan to set
     */
    public void setPotonganBiayaPenanganan(double potonganBiayaPenanganan) {
        this.potonganBiayaPenanganan = potonganBiayaPenanganan;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
