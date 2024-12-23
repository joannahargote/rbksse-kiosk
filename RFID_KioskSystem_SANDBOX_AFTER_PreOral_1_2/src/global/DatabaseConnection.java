/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import windows.FrameManager;
import windows.KioskLogin;


public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://7.0.0.15:3306/rbksse_db"; 
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rbksse_db"; 
//    private static final String URL = "jdbc:mysql://127.0.0.1:3306/kiosk"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    
public static Connection connect(JFrame parentFrame) { // Accept a parent JFrame as a parameter
        Connection conn = null;
        if (StudentData.getidleCounter()<30) { //30 seconds
            try {
                StudentData.setidleCounter(StudentData.getidleCounter()+1);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected. Idle Counter: "+StudentData.getidleCounter());
            } catch (SQLException e) {
                String popupMessage = "Database connection failed: " + e.getMessage();
                String popupTitle = "ERROR: NOT CONNECTED TO SERVER";
                JOptionPane.showMessageDialog(parentFrame, popupMessage, popupTitle, JOptionPane.ERROR_MESSAGE);

                if (!StudentData.getInLogin()) {
                    // Close the current window (parentFrame)
                    if (parentFrame != null) {
                        parentFrame.setVisible(false);
                        parentFrame.dispose();
                    }

                    // Open the login frame
                    KioskLogin loginFrame = new KioskLogin(); 
                    loginFrame.setVisible(true);
                }
            }//catch      
        }//if <30
        else{ //lampas na 30 seconds
            StudentData.setidleCounter(0);
//            para di maga double yung KioskLogin
            System.out.println("Already in KioskLogin");
            if (!StudentData.getInLogin()){
                // Close the current window (parentFrame)
                if (parentFrame != null) {
                    parentFrame.setVisible(false);
                    parentFrame.dispose();
                }
                    FrameManager.closeAllFrames(); // Close all existing frames before opening a new one
                    // Open the login frame
                    KioskLogin loginFrame = new KioskLogin(); 
                    loginFrame.setVisible(true);
                    FrameManager.addFrame(loginFrame); // Add the new frame to the manager
            }
        }
        return conn;
    } 

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            String popupMessage = "Database connection failed: " + e.getMessage();
            String popupTitle = "ERROR: NOT CONNECTED TO SERVER";
            JOptionPane.showMessageDialog(null, popupMessage);
        }
        return conn;
    }   
    
//    public static Connection connect() { Connection conn = null; 
//        try { 
//            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the database."); 
//        } catch (SQLException e) { 
//            String popupMessage = "Database connection failed: " + e.getMessage();
//            String popupTitle = "ERROR: NOT CONNECTED TO SERVER"; 
//            JOptionPane.showMessageDialog(null, popupMessage, popupTitle, JOptionPane.ERROR_MESSAGE);
//            
//            System.out.println("DO STUFF HERE");
//            //fix this so that when StudentData.getInLogin is false, the current window closes and the program returns to the login frame
//            if (!StudentData.getInLogin()) {
//                // Close the current window
//                JFrame currentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(null);
//                if (currentFrame != null) {
//                    currentFrame.dispose();
//                }
//
//                // Return to the login frame
//                KioskLogin loginFrame = new KioskLogin(); // Assuming you have a LoginFrame class
//                loginFrame.setVisible(true);
//            }
//            
//        } 
//        return conn;
//    }
}
