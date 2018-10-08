/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc375;

//array[row][col]==>[y][x]
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author alex
 */
public class GUI extends JFrame {//comments after globals are their default/starting value

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
    private static final int INDEX_FOR_BACK_ARROW = 7;
    private static final int INDEX_FOR_VIEWABLE_THREAD = 8;
    private static final int INDEX_FOR_FORWARD_ARROW = 9;

    private static final int COLUMNSIZE = 10;//10
    private static final int ROWSIZE = 10;//10
    private static final int TEXTFIELD_COLUMNSIZE = 10;//10

    private static final int TIME_SET_FOR_WAITING = 10;

    private static final int AMOUNT_OF_THREADS = 40;
    private static volatile int AMOUNT_OF_ITERATIONS;

    //GUI stuff
    private JButton jbutton;
    private JLabel jlabelx;
    private JTextField jtextfieldx;
    private JLabel jlabely;
    private JTextField jtextfieldy;
    private JLabel jlabelAmount;
    private JTextField jtextfieldAmount;
    private JButton jbuttonbackArrow;
    private JButton jbuttonfowardArrow;
    private JLabel jlabelthreadDisplay;

    private JTable board;

    private JPanel masterFrame;
    private JPanel boarderPane;

    private volatile CustomTableModel customModel;

    private volatile int viewable = 0;

    //functionality
    private boolean run = false;

    private volatile Factory factory;
    private final Factory[] outputs = new Factory[AMOUNT_OF_THREADS];
    private volatile double fitness=-1;
    private final Lock l = new ReentrantLock();
    private final Lock l2 = new ReentrantLock();

    private ExecutorService scheduledExecutorService;

    private volatile int len, wid, mac;

    volatile CountDownLatch latch1 = new CountDownLatch(AMOUNT_OF_THREADS);

//everything below handles or is involved in multithreading someway or form
    private Runnable runnableSetUp(int z) {
        Runnable r = () -> {
            System.out.println("start");
            final CountDownLatch locallatch = latch1;
            final int i = z;
            Factory loci = new Factory(len, wid, mac);
            int counter = 0;
            int merge = 0;
            for (int threadProducer = 0; threadProducer < AMOUNT_OF_ITERATIONS; threadProducer++) {
                loci.mutation();
                update(i, loci);
                if (counter == 5) {
                    visualUpdate(i);
                    counter = 0;
                }
                try {
                    Thread.sleep(TIME_SET_FOR_WAITING);
                } catch (InterruptedException ex) {
                    System.out.println("interrupted");;
                    break;
                }
                if (merge == 200) {
                    double d = loci.fitness();
                    this.setFitness(d,loci);
                    locallatch.countDown();
                    try {
                        locallatch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    loci=new Factory(getUpdatedFactory());
                    System.out.println("new factory");
                    if(i==0){
                        latch1 = new CountDownLatch(AMOUNT_OF_THREADS);
                        System.out.println("new latch");
                    }
                    merge=0;
                }
                counter++;
                merge++;
                System.out.println("stuff at " + i);

            }
            System.out.println("end");
        };
        return r;
    }
    
    private void setFitness(double fit,Factory fa){
        l.lock();
        try{
            if(fit>fitness){
                fitness=fit;
                factory = fa;
            }
        }finally{
            l.unlock();
        }
    }
    private Factory getUpdatedFactory(){
        l2.lock();
        try{
            return factory;
        }finally{
            l2.unlock();
        }
    }

    private void multiThreading() {
        Runnable[] jobs = new Runnable[AMOUNT_OF_THREADS];
        for (int i = 0; i < AMOUNT_OF_THREADS; i++) {
            Runnable r = runnableSetUp(i);
            jobs[i] = r;
        }
        scheduledExecutorService = Executors.newFixedThreadPool(AMOUNT_OF_THREADS);
        for (int i = 0; i < AMOUNT_OF_THREADS; i++) {
            scheduledExecutorService.execute(jobs[i]);
        }
    }

    private synchronized void update(int i, Factory f) {
        outputs[i] = f;
    }

    //Everything below handles GUI layout and presets
    private void visualUpdate(int i) {//need a lock here and wrap this method
        if (i == viewable) {
            customModel.changeData(outputs[i].getFactory());
        }
    }

    private void changeViewable(int i) {
        viewable = i;
    }

    private boolean changeTableSize(Point[][] board) {
        try {
            customModel.setTable(board);
            setUpCellSize(this.board);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setUp() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Project 1");
        setSize(WIDTH_OF_FRAME, LENGTH_OF_FRAME);
        setResizable(false);

        try {
            jlabelx = this.setUpJLabel("Length of Board");
            jtextfieldx = this.setUpJTextField();
            jlabely = this.setUpJLabel("Width of Board");
            jtextfieldy = this.setUpJTextField();
            jlabelAmount = this.setUpJLabel("Amount of machines");
            jtextfieldAmount = this.setUpJTextField();
            jbutton = this.setUpJButton("Begin Program");
            jbutton.addActionListener((ActionEvent action) -> {
                try {
                    if (run) {
                        scheduledExecutorService.shutdownNow();
                    } else {
                        run = true;
                    }
                    String stringx = jtextfieldx.getText();
                    String stringy = jtextfieldy.getText();
                    String amount = jtextfieldAmount.getText();
                    jtextfieldx.setText("");
                    jtextfieldy.setText("");
                    jtextfieldAmount.setText("");
                    int length = Integer.parseInt(stringx);
                    int width = Integer.parseInt(stringy);
                    int amountOfMachines = Integer.parseInt(amount);
                    AMOUNT_OF_ITERATIONS = 50 * amountOfMachines;
                    if (length >= 32 && width >= 32 && length <= 99 && width <= 73 && amountOfMachines < 5 && amountOfMachines < length * width) {
                        throw new NumberFormatException();
                    }
                    len = length;
                    wid = width;
                    mac = amountOfMachines;
                    factory = new Factory(length, width, amountOfMachines);
                    System.out.println("factory is built");
                    this.changeTableSize(factory.getFactory());
                    this.multiThreading();

                } catch (NumberFormatException e) {
                    System.out.println("running with preset");
                    AMOUNT_OF_ITERATIONS = 50 * DEFAULT_MACHINES;
                    factory = new Factory(DEFAULT_TABLE_SIZE, DEFAULT_TABLE_SIZE, DEFAULT_MACHINES);
                    System.out.println("factory is built");
                    len = DEFAULT_TABLE_SIZE;
                    wid = DEFAULT_TABLE_SIZE;
                    mac = DEFAULT_MACHINES;
                    this.changeTableSize(factory.getFactory());
                    this.multiThreading();

                } catch (Exception e) {
                    System.out.println("something else broke");
                    System.exit(33637);
                }
            });

            jbuttonbackArrow = new javax.swing.JButton("<");
            jbuttonbackArrow.addActionListener((ActionEvent action) -> {
                if (viewable > 0) {
                    changeViewable(--viewable);
                    jlabelthreadDisplay.setText(String.valueOf(viewable + 1));
                }
            });
            jlabelthreadDisplay = new javax.swing.JLabel(String.valueOf(viewable + 1));
            jbuttonfowardArrow = new javax.swing.JButton(">");
            jbuttonfowardArrow.addActionListener((ActionEvent action) -> {
                if (viewable < AMOUNT_OF_THREADS) {
                    changeViewable(++viewable);
                    jlabelthreadDisplay.setText(String.valueOf(viewable + 1));
                }
            });
            
            JPanel panel = this.setStaticPanel(jlabelx, jtextfieldx, jlabely, jtextfieldy, jlabelAmount, jtextfieldAmount, jbutton, jbuttonbackArrow, jlabelthreadDisplay, jbuttonfowardArrow);

            customModel = new CustomTableModel(new Point[DEFAULT_TABLE_SIZE][DEFAULT_TABLE_SIZE]);
            board = new JTable(customModel);

            board.setBorder(BorderFactory.createLineBorder(Color.black));
            board.setEnabled(false);
            board = this.setUpCellSize(board);
            board.setVisible(true);
            boarderPane = new JPanel();
            boarderPane.add(board);

            masterFrame = new JPanel();
            masterFrame.setLayout(new BoxLayout(masterFrame, BoxLayout.Y_AXIS));
            masterFrame.add(panel, INDEX_FOR_BOARDERPANE);
            masterFrame.add(boarderPane, INDEX_FOR_BOARD);

            this.getContentPane().add(masterFrame);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JTable setUpCellSize(JTable table) {
        table.setRowHeight(ROWSIZE);
        TableColumnModel columnModel = table.getColumnModel();
        for (int dex = 0; dex < columnModel.getColumnCount(); dex++) {
            columnModel.getColumn(dex).setPreferredWidth(COLUMNSIZE);
        }
        return table;
    }

    private JPanel setStaticPanel(JLabel xLabel, JTextField xTextField, JLabel yLabel, JTextField yTextField, JLabel amountLabel,
            JTextField amountField, JButton button, JButton arrow1, JLabel thread, JButton arrow2) {

        JPanel jpanel = new JPanel();
        jpanel.add(xLabel, INDEX_FOR_WIDTH_LABEL);
        jpanel.add(xTextField, INDEX_FOR_WIDTH_TEXTFIELD);
        jpanel.add(yLabel, INDEX_FOR_LENGTH_LABEL);
        jpanel.add(yTextField, INDEX_FOR_LENGTH_TEXTFIELD);
        jpanel.add(amountLabel, INDEX_FOR_MACHINE_AMOUNT_LABEL);
        jpanel.add(amountField, INDEX_FOR_MACHINE_AMOUNT_TEXTFIELD);
        jpanel.add(button, INDEX_FOR_LENGTH_BUTTON);
        jpanel.add(arrow1, INDEX_FOR_BACK_ARROW);
        jpanel.add(thread, INDEX_FOR_VIEWABLE_THREAD);
        jpanel.add(arrow2, INDEX_FOR_FORWARD_ARROW);

        return jpanel;
    }

    private JLabel setUpJLabel(String text) {
        JLabel label = new javax.swing.JLabel(text);
        label.setVisible(true);
        return label;
    }

    private JTextField setUpJTextField() {
        JTextField textField = new javax.swing.JTextField();
        textField.setColumns(TEXTFIELD_COLUMNSIZE);
        textField.setVisible(true);
        return textField;
    }

    private JButton setUpJButton(String text) {
        JButton button = new javax.swing.JButton(text);
        button.setVisible(true);
        return button;
    }

    public GUI() {
        boolean token = this.setUp();
        if (!token) {
            System.exit(100);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}
