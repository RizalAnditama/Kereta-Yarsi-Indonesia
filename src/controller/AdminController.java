/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import model.DatabaseConnector;
import model.User;
import net.proteanit.sql.DbUtils;

/**
 * The AdminController class is responsible for managing admin-related operations
 * such as displaying users, managing user roles, and deleting users.
 * This class follows the singleton pattern to ensure only one instance is created.
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public class AdminController extends Controller {

    private static AdminController instance;
    private JTable lastTable;

    /**
     * Private constructor to prevent instantiation.
     * 
     * @param databaseConnector The DatabaseConnector instance used for database operations.
     */
    private AdminController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    /**
     * Returns the singleton instance of AdminController.
     * 
     * @return The singleton instance of AdminController.
     */
    public static synchronized AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    /**
     * Displays the user information in the given JTable.
     * 
     * @param table The JTable to display the user information in.
     */
    public void display(JTable table) {
        lastTable = table;
        rs = executeQuery("select * from user");
        TableModel model = DbUtils.resultSetToTableModel(rs);
        TableModel customModel = new CustomTableModel(model);
        table.setModel(customModel);
    }

    /**
     * Retrieves the role of the user with the specified user ID.
     * 
     * @param userId The ID of the user whose role is to be retrieved.
     * @return The role of the user, or null if the user is not found.
     */
    public String getSelectedUserRole(int userId) {
        rs = executeQuery("select * from user where user_id = ?", userId);

        try {
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Sets the role of the user with the specified user ID.
     * 
     * @param userId The ID of the user whose role is to be set.
     * @param role The new role to be assigned to the user.
     * @return True if the role was successfully updated, false otherwise.
     */
    public boolean setSelectedUserRole(int userId, String role) {
        return executeUpdate("update user set role = ? where user_id = ?", role, userId);
    }

    /**
     * Inverts the role of the user with the specified user ID.
     * If the current role is "admin", it will be changed to "member" and vice versa.
     * 
     * @param userId The ID of the user whose role is to be inverted.
     * @return True if the role was successfully inverted, false otherwise.
     */
    public boolean invertSelectedUserRole(int userId) {
        User userSession = getSession().getUser();
        int id = userSession.getUserID();

        if (id == userId) {
            return false;
        } else {
            String role = getSelectedUserRole(userId);

            String newRole = role.equalsIgnoreCase("admin") ? "member" : "admin";
            setSelectedUserRole(userId, newRole);

            return true;
        }
    }

    /**
     * Deletes the user with the specified user ID.
     * 
     * @param userId The ID of the user to be deleted.
     * @return True if the user was successfully deleted, false otherwise.
     */
    public boolean deleteUser(int userId) {
        User userSession = getSession().getUser();
        int adminId = userSession.getUserID();
        if (adminId == userId) {
            infoMessage("Anda adalah admin, Anda tidak bisa menghapus akun anda sendiri!");
            return false;
        } else {
            return executeUpdate("DELETE FROM user WHERE user_id = ?", userId);
        }
    }

    /**
     * Returns the last JTable used for displaying user information.
     * 
     * @return The last JTable used for displaying user information.
     */
    public JTable getLastTable() {
        return lastTable;
    }
}
