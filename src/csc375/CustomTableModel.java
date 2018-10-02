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
    private Point[][] table;
    
    CustomTableModel(Point[][] table){
        this.table=table;
    }

    public void setTable(Point[][] table) {
        this.table = table;
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public void setValueAt(Point value, int row, int col) {
        table[col][row] = value;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return table[0].length-1;
    }

    @Override
    public int getColumnCount() {
        return table.length-1;
    }

    @Override
    public Point getValueAt(int y, int x) {
        return table[x][y];
    }
}
