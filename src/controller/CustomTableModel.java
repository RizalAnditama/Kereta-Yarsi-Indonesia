package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

/**
 * CustomTableModel is a custom implementation of DefaultTableModel that copies 
 * data from another TableModel and makes all cells uneditable.
 * 
 * This class is used to provide a read-only table model based on an existing table model.
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public class CustomTableModel extends DefaultTableModel {

    /**
     * Constructs a CustomTableModel by copying the data from the provided TableModel.
     * 
     * @param model The TableModel to copy data from.
     */
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

    /**
     * Overrides the isCellEditable method to make all cells uneditable.
     * 
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return Always returns false to indicate that all cells are uneditable.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        // All cells are uneditable
        return false;
    }
}
