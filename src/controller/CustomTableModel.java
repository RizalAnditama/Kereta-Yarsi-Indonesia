package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */

public class CustomTableModel extends DefaultTableModel {

    public CustomTableModel(TableModel model) {
        // Copy the data from the provided model to the new DefaultTableModel
        int columnCount = model.getColumnCount();
        int rowCount = model.getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            addColumn(model.getColumnName(columnIndex));
        }
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Object[] rowData = new Object[columnCount];
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                rowData[columnIndex] = model.getValueAt(rowIndex, columnIndex);
            }
            addRow(rowData);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // All cells are uneditable
        return false;
    }
}
