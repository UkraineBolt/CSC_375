/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

//array[row][col]==>[y][x]
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.*;
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
    
    private static final int AMOUNT_OF_THREADS = 1;
    private static final int AMOUNT_OF_ITERATIONS = 1000;
    
    //GUI stuff
    private JButton jbutton;
    private JLabel jlabelx;
    private JTextField jtextfieldx;
    private JLabel jlabely;
    private JTextField jtextfieldy;
    private JLabel jlabelAmount;
    private JTextField jtextfieldAmount;
    
    private JTable board;
    
    private JPanel masterFrame;
    private JPanel boarderPane;
    
    
    
    
    //Other
    private CustomTableModel customModel;
    
    private final int count = 0;
    
    private Factory factory;
    private final Factory[] outputs = new Factory[AMOUNT_OF_THREADS];
    private final boolean[] threadCompletion = new boolean[AMOUNT_OF_THREADS];
    
//everything below handles or is involved in multithreading someway or form
    private Runnable runnableSetUp(int i){
        Runnable r = () -> {
            final Factory loci = new Factory(outputs[i]);
            for(int threadProducer=0;threadProducer<AMOUNT_OF_ITERATIONS;threadProducer++){
                loci.mutation();
                update(i,loci);
            }
            completionUpdate(i);
        };
        return r;
    }
    
    private void multiThreading(){
        ScheduledExecutorService scheduledExecutorService 
                = Executors.newSingleThreadScheduledExecutor();
        Runnable[] jobs = new Runnable[AMOUNT_OF_THREADS];
        for(int i=0;i<AMOUNT_OF_THREADS;i++){
            Runnable r = runnableSetUp(i);
            jobs[i]=r;
        }
        for(int i=0;i<AMOUNT_OF_THREADS;i++){
            scheduledExecutorService.schedule(jobs[i], 250, TimeUnit.NANOSECONDS);
        }
    }
    
    private synchronized void update(int i, Factory f){
        outputs[i]=f;
    }
    
    private synchronized void completionUpdate(int i){
        threadCompletion[i] = true;
    }
    
    
    //Everything below handles GUI layout and presets
    private void visualUpdate(int i){
        refreash(outputs[i].getFactory());
    }
    
    private void arrayOfBaseFactorySetUps(){
        for(int i=0;i<outputs.length;i++){
           outputs[i]=factory; 
           threadCompletion[i]=false;
        }
    }
    
    private boolean changeTableSize(Point[][] board){
        try{
            customModel.setTable(board);
            setUpCellSize(this.board);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private void refreash(Point[][] board){
        customModel.setTable(board);
        setUpCellSize(this.board);
    }
    
    private boolean setUp(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Project 1");
        setSize(WIDTH_OF_FRAME, LENGTH_OF_FRAME);
        setResizable(false);
        
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
                    if (length >= 32 && width >= 32 && length <= 99 && width <= 73 && amountOfMachines < 5 && amountOfMachines < length * width) {
                        throw new NumberFormatException();
                    }
                    factory = new Factory(length,width,amountOfMachines);
                    System.out.println("factory is built");
                    this.changeTableSize(factory.getFactory());
                    this.arrayOfBaseFactorySetUps();
                    //this.multiThreading();
                    
                    
                }catch(NumberFormatException e){
                    System.out.println("running with preset");
                    factory = new Factory(DEFAULT_TABLE_SIZE,DEFAULT_TABLE_SIZE,DEFAULT_MACHINES);
                    System.out.println("factory is built");
                    this.changeTableSize(factory.getFactory());
                    this.arrayOfBaseFactorySetUps();
                    //this.multiThreading();
                    
                    
                }catch(Exception e){
                    System.out.println("something else broke");
                    System.exit(33637);
                }
            });
            
            JPanel panel = this.setStaticPanel(jlabelx,jtextfieldx,jlabely,jtextfieldy,jlabelAmount,jtextfieldAmount,jbutton);
            
            customModel = new CustomTableModel(new Point[DEFAULT_TABLE_SIZE][DEFAULT_TABLE_SIZE]);
            board = new JTable(customModel);
            
            board.setBorder(BorderFactory.createLineBorder(Color.black));
            board.setEnabled(false);
            board = this.setUpCellSize(board);
            board.setVisible(true);
            boarderPane = new JPanel();
            boarderPane.add(board);
            
            masterFrame = new JPanel();
            masterFrame.setLayout(new BoxLayout(masterFrame,BoxLayout.Y_AXIS));
            masterFrame.add(panel,INDEX_FOR_BOARDERPANE);
            masterFrame.add(boarderPane,INDEX_FOR_BOARD);
            
            this.getContentPane().add(masterFrame);
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
        boolean token = this.setUp();
        if(!token){System.exit(100);}
    }   
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}