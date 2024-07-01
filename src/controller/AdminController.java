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
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class AdminController extends Controller {

    private static AdminController instance;
    private JTable lastTable;

    private AdminController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    public static synchronized AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    public void display(JTable table) {
        lastTable = table;
        rs = executeQuery("select * from user");
        TableModel model = DbUtils.resultSetToTableModel(rs);
        TableModel customModel = new CustomTableModel(model);
        table.setModel(customModel);
    }

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

    public boolean setSelectedUserRole(int userId, String role) {
        return executeUpdate("update user set role = ? where user_id = ?", role, userId);
    }

    public boolean invertSelectedUserRole(int userId) {
        User userSession = getSession().getUser();
        int id = userSession.getUserID();
        String username = userSession.getUsername();
        String password = userSession.getPassword();
        byte[] salt = userSession.getSalt();

        if (id == userId) {
            return false;
        } else {
            String role = getSelectedUserRole(userId);

            String newRole = null;
            if (role.equalsIgnoreCase("admin")) {
                newRole = "member";
            } else {
                newRole = "admin";
            }
            setSelectedUserRole(userId, newRole);

//            getSession().setUser(new User(userId, username, password, salt, newRole));
            return true;
        }
    }

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

    public JTable getLastTable() {
        return lastTable;
    }
}
