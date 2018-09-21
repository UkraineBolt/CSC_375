/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author alex
 */
public class CustomTableModel extends AbstractTableModel {

    private final String[] empty;
    private Point[][] table;
    
    CustomTableModel(Point[][] table, String[] header){
        this.table=table;empty = new String[table[0].length];
    }

    public void setTable(Point[][] table) {
        this.table = table;
        fireTableDataChanged();
    }

    public void setValueAt(Point value, int row, int col) {
        table[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    @Override
    public int getRowCount() {
        return table.length;
    }

    @Override
    public int getColumnCount() {
        return empty.length;
    }

    @Override
    public Point getValueAt(int row, int col) {
        return table[row][col];
    }
}
