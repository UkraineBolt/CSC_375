/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;


import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author alex
 */
public class GUI extends JFrame{
    private static final int WIDTH_OF_FRAME = 1500;
    private static final int LENGTH_OF_FRAME = 800;
    
    private static final int INDEX_FOR_BOARDERPANE = 0;
    private static final int INDEX_FOR_BOARD = 1;
    
    private static final int INDEX_FOR_WIDTH_LABEL = 0;
    private static final int INDEX_FOR_LENGTH_LABEL = 2;
    private static final int INDEX_FOR_WIDTH_TEXTFIELD = 1;
    private static final int INDEX_FOR_LENGTH_TEXTFIELD = 3;
    private static final int INDEX_FOR_LENGTH_BUTTON = 4;
    
    private static final int INDEX_FOR_JTABLE = 0;
    
    private static final int[] INDEXES_FOR_PATH_TO_BOARD = new int[]{INDEX_FOR_BOARD,INDEX_FOR_JTABLE};
    
    private static final int COLUMNSIZE = 10;
    private static final int ROWSIZE = 10;
    
    private JButton jbutton;
    private JLabel jlabelx;
    private JTextField jtextfieldx;
    private JLabel jlabely;
    private JTextField jtextfieldy;
    
    private boolean changeTableSize(int row, int column){
        try{
            JTable table = (JTable) this.getBuriedComponent(INDEXES_FOR_PATH_TO_BOARD.length, INDEXES_FOR_PATH_TO_BOARD);
            this.setUpCellSize(table,row,column);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private Component getBuriedComponent(int layer, int[] path){
        Component traveler = this.getContentPane().getComponent(0);        
        for(int depth=0;depth<layer;depth++){
            traveler = this.getComponent(path[depth]);
        }
        
        return traveler;
    }
    
    private boolean setUp(){
        try{
            jlabelx = this.setUpJLabel("Width of Board"); 
            jtextfieldx = this.setUpJTextField();
            jlabely = this.setUpJLabel("Length of Board"); 
            jtextfieldy = this.setUpJTextField(); 
            jbutton=this.setUpJButton("Begin Program");
            jbutton.addActionListener(action -> {
                try{
                    String stringx = jtextfieldx.getText();
                    String stringy = jtextfieldy.getText();
                }catch(Exception e){
                    
                }
                
            });
            
            
            JPanel panel = this.setStaticPanel(jlabelx,jtextfieldx,jlabely,jtextfieldy,jbutton);
            
            JTable board = new JTable(35,35);
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
    private JPanel setStaticPanel(JLabel xLabel, JTextField xTextField, JLabel yLabel, JTextField yTextField, JButton button){
        JPanel jpanel = new JPanel();
        jpanel.add(xLabel,INDEX_FOR_WIDTH_LABEL);
        jpanel.add(xTextField,INDEX_FOR_WIDTH_TEXTFIELD);
        jpanel.add(yLabel,INDEX_FOR_LENGTH_LABEL);
        jpanel.add(yTextField,INDEX_FOR_LENGTH_TEXTFIELD);
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
        textField.setColumns(10);
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