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
    private static final int START_X = 0;
    private static final int START_Y = 0;
    
    private boolean setUp(){
        try{
            JLabel jlabelx = this.setUpJLabel("Width of Board", ABORT, ABORT); 
            JTextField jtextfieldx = this.setUpJTextField(ABORT, ABORT);
            JLabel jlabely = this.setUpJLabel("Length of Board", ABORT, ABORT); 
            JTextField jtextfieldy = this.setUpJTextField(ABORT, ABORT); 
            JButton jButton=this.setUpJButton("Begin Program", ABORT, ABORT);
            JPanel panel = this.setPanel(jlabelx,jtextfieldx,jlabely,jtextfieldy,jButton);
            
            return true;
        }catch(Exception e){
            return false;
        }
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
    private JLabel setUpJLabel(String text,int startX, int startY){
        JLabel label = new javax.swing.JLabel(text);
        label.setSize(WIDTH, HEIGHT);//need constants
        label.setLocation(startX, startY);//need math
        label.setVisible(true);
        return label;
    }
    private JTextField setUpJTextField(int startX, int startY){
        JTextField textField = new javax.swing.JTextField();
        textField.setSize(WIDTH, HEIGHT);
        textField.setLocation(startX, startY);
        textField.setVisible(true);
        return textField;
    }
    private JButton setUpJButton(String text,int startX, int startY){
        JButton button = new javax.swing.JButton(text);
        button.setSize(WIDTH, HEIGHT);
        button.setLocation(startX, startY);
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
