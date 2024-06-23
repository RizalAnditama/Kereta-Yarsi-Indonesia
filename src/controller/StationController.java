/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.DatabaseConnector;
import model.Station;
import model.StationDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class StationController extends Controller implements StationDAO {

    private static StationController instance;

    private StationController(DatabaseConnector databaseConnector) {
        super(databaseConnector);
    }

    public static synchronized StationController getInstance() {
        if (instance == null) {
            instance = new StationController(DatabaseConnector.getInstance());
        }
        return instance;
    }

    @Override
    public ResultSet getStationByName(String stationName) {
        rs = findWhere("station", "name", stationName);
        try {
            if (rs.next()) {
                return rs;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
