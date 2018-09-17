/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;


import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author alex
 */
public class GUI extends JFrame{//comments after globals are their default/starting value
    private static final int DEFAULT_TABLE_SIZE = 35;//35
    private static final int DEFAULT_MACHINES = 500;//500
    
    private static final int WIDTH_OF_FRAME = 1500;//1500
    private static final int LENGTH_OF_FRAME = 800;//800
    
    private static final int INDEX_FOR_BOARDERPANE = 0;//0
    private static final int INDEX_FOR_BOARD = 1;//1
    
    private static final int INDEX_FOR_WIDTH_LABEL = 0;//0
    private static final int INDEX_FOR_LENGTH_LABEL = 2;//2
    private static final int INDEX_FOR_WIDTH_TEXTFIELD = 1;//1
    private static final int INDEX_FOR_LENGTH_TEXTFIELD = 3;//3
    private static final int INDEX_FOR_LENGTH_BUTTON = 6;//6
    private static final int INDEX_FOR_MACHINE_AMOUNT_LABEL = 4;//4
    private static final int INDEX_FOR_MACHINE_AMOUNT_TEXTFIELD = 5;//5
    
    
    private static final int COLUMNSIZE = 10;//10
    private static final int ROWSIZE = 10;//10
    private static final int TEXTFIELD_COLUMNSIZE = 10;//10
    
    private JButton jbutton;
    private JLabel jlabelx;
    private JTextField jtextfieldx;
    private JLabel jlabely;
    private JTextField jtextfieldy;
    private JLabel jlabelAmount;
    private JTextField jtextfieldAmount;
    
    private Factory factory;
    
    private boolean changeTableSize(Point[][] board){
        try{
            JPanel masterframe = (JPanel)this.getContentPane().getComponent(0);
            JPanel subframe = (JPanel)masterframe.getComponent(1);
            JTable table = (JTable) subframe.getComponent(0);
            table.setModel(new AbstractTableModel() {
                private Point[][] table = board;
                
                public void setValueAt(Point value, int row, int col) {
                    table[row][col] = value;
                    fireTableCellUpdated(row, col);
                }
                
                @Override
                public int getRowCount() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public int getColumnCount() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            this.setUpCellSize(table,ROWSIZE,COLUMNSIZE);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private boolean setUp(){
        try{
            jlabelx = this.setUpJLabel("Length of Board"); 
            jtextfieldx = this.setUpJTextField();
            jlabely = this.setUpJLabel("Width of Board"); 
            jtextfieldy = this.setUpJTextField(); 
            jlabelAmount = this.setUpJLabel("Amount of machines");
            jtextfieldAmount = this.setUpJTextField();
            jbutton=this.setUpJButton("Begin Program");
            jbutton.addActionListener((ActionEvent action) -> {
                try {
                    String stringx = jtextfieldx.getText();
                    String stringy = jtextfieldy.getText();
                    String amount = jtextfieldAmount.getText();
                    jtextfieldx.setText("");
                    jtextfieldy.setText("");
                    jtextfieldAmount.setText("");
                    int length = Integer.parseInt(stringx);
                    int width = Integer.parseInt(stringy);
                    int amountOfMachines = Integer.parseInt(amount);
                    if (length <= 32 || width <= 32 ||length >= 99 || width >= 73 || amountOfMachines < 5 || amountOfMachines > length * width) {
                        throw new NumberFormatException();
                    }
                    factory = new Factory(length,width,amountOfMachines);
                    System.out.println("factory is built");
                    this.changeTableSize(factory.getFactory());
                    factory.multiThreadedSwapping();
                    
                }catch(NumberFormatException e){
                    System.out.println("running with preset");
                    factory = new Factory(DEFAULT_TABLE_SIZE,DEFAULT_TABLE_SIZE,DEFAULT_MACHINES);
                    System.out.println("factory is built");
                    this.changeTableSize(factory.getFactory());
                    factory.multiThreadedSwapping();
                }catch(Exception e){
                    System.out.println("something else broke");
                    System.exit(33637);
                }
            });
            
            JPanel panel = this.setStaticPanel(jlabelx,jtextfieldx,jlabely,jtextfieldy,jlabelAmount,jtextfieldAmount,jbutton);
            
            JTable board = new JTable(DEFAULT_TABLE_SIZE,DEFAULT_TABLE_SIZE);
            board.setModel(new AbstractTableModel() {
                private Point[][] table;
                public void setPointBoard(Point[][] board){
                    table = board;
                    
                }
                public void setValueAt(Point value, int row, int col) {
                    table[row][col] = value;
                    fireTableCellUpdated(row, col);
                }
                
                @Override
                public int getRowCount() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public int getColumnCount() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            board.setBorder(BorderFactory.createLineBorder(Color.black));
            board.setEnabled(false);
            
            JPanel boardpane = new JPanel();
            boardpane.add(this.setUpCellSize(board,ROWSIZE,COLUMNSIZE),0);
            
            JPanel masterPane = new JPanel();
            masterPane.setLayout(new BoxLayout(masterPane,BoxLayout.Y_AXIS));
            masterPane.add(panel,INDEX_FOR_BOARDERPANE);
            masterPane.add(boardpane,INDEX_FOR_BOARD);
            
            this.getContentPane().add(masterPane);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private JTable setUpCellSize(JTable table, int row, int column){
        table.setRowHeight(row);
        TableColumnModel columnModel = table.getColumnModel();
        for(int dex = 0;dex<columnModel.getColumnCount();dex++){
            columnModel.getColumn(dex).setPreferredWidth(column);
        }
        return table;
    }
    private JPanel setStaticPanel(JLabel xLabel, JTextField xTextField, JLabel yLabel, JTextField yTextField, JLabel amountLabel, JTextField amountField,JButton button){
        JPanel jpanel = new JPanel();
        jpanel.add(xLabel,INDEX_FOR_WIDTH_LABEL);
        jpanel.add(xTextField,INDEX_FOR_WIDTH_TEXTFIELD);
        jpanel.add(yLabel,INDEX_FOR_LENGTH_LABEL);
        jpanel.add(yTextField,INDEX_FOR_LENGTH_TEXTFIELD);
        jpanel.add(amountLabel,INDEX_FOR_MACHINE_AMOUNT_LABEL);
        jpanel.add(amountField,INDEX_FOR_MACHINE_AMOUNT_TEXTFIELD);
        jpanel.add(button,INDEX_FOR_LENGTH_BUTTON);
        return jpanel;
    }
    private JLabel setUpJLabel(String text){
        JLabel label = new javax.swing.JLabel(text);
        label.setVisible(true);
        return label;
    }
    private JTextField setUpJTextField(){
        JTextField textField = new javax.swing.JTextField();
        textField.setColumns(TEXTFIELD_COLUMNSIZE);
        textField.setVisible(true);
        return textField;
    }
    private JButton setUpJButton(String text){
        JButton button = new javax.swing.JButton(text);
        button.setVisible(true);
        return button;
    }
    public GUI(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Project 1");
        this.setSize(WIDTH_OF_FRAME, LENGTH_OF_FRAME);
        this.setResizable(false);
        boolean token = this.setUp();
        if(!token){System.exit(00100);}
    }   
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}