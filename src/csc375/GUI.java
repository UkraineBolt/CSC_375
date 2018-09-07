/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;


import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author alex
 */
public class GUI extends JFrame{
    private static final int WIDTH_OF_FRAME = 1500;
    private static final int LENGTH_OF_FRAME = 800;
    private static final int COLUMNSIZE = 7;
    private static final int ROWSIZE = 7;
    
    private boolean setUp(){
        try{
            JLabel jlabelx = this.setUpJLabel("Width of Board"); 
            JTextField jtextfieldx = this.setUpJTextField();
            JLabel jlabely = this.setUpJLabel("Length of Board"); 
            JTextField jtextfieldy = this.setUpJTextField(); 
            JButton jButton=this.setUpJButton("Begin Program");
            JPanel panel = this.setPanel(jlabelx,jtextfieldx,jlabely,jtextfieldy,jButton);
            panel.add(jlabelx);
            panel.add(jtextfieldx);
            panel.add(jlabely);
            panel.add(jtextfieldy);
            panel.add(jButton);
            
            JTable board = new JTable(35,35);
            JPanel boardpane = new JPanel();
            boardpane.add(this.setUpCellSize(board));
            
            JPanel masterPane = new JPanel();
            masterPane.setLayout(new BoxLayout(masterPane,BoxLayout.Y_AXIS));
            masterPane.add(panel,0);
            masterPane.add(boardpane,1);
            
            this.getContentPane().add(masterPane);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private JTable setUpCellSize(JTable table){
        table.setRowHeight(ROWSIZE);
        TableColumnModel columnModel = table.getColumnModel();
        for(int dex = 0;dex<columnModel.getColumnCount();dex++){
            columnModel.getColumn(dex).setPreferredWidth(COLUMNSIZE);
        }
        return table;
    }
    private JPanel setPanel(JLabel xL, JTextField xTF, JLabel yL, JTextField yTF, JButton button){
        JPanel jpanel = new JPanel();
        jpanel.add(xL);
        jpanel.add(xTF);
        jpanel.add(yL);
        jpanel.add(yTF);
        jpanel.add(button);
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
    public void run(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Project 1");
        this.setSize(WIDTH_OF_FRAME, LENGTH_OF_FRAME);
        this.setResizable(false);
        boolean token = this.setUp();
        if(!token){System.exit(00100);}
        this.setVisible(true);
    }   
}
