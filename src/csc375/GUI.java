/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author alex
 */
public class GUI extends JFrame{
    private static final int WIDTH_OF_FRAME = 1000;
    private static final int LENGTH_OF_FRAME = 750;
    
    ArrayList<JButton> allButtons = new ArrayList();
    ArrayList<JLabel> allLabels = new ArrayList();
    ArrayList<JTextField> allTextFields = new ArrayList();
    
    private boolean setUp(){
        try{
            
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private JLabel setUpJLabel(String text){
        JLabel label = new javax.swing.JLabel(text);
        label.setSize(WIDTH, HEIGHT);
        label.setVisible(true);
        return label;
    }
    private JTextField setUpJTextField(){
        JTextField textField = new javax.swing.JTextField();
        
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
        this.setUp();
        this.setVisible(true);
    }   
}
