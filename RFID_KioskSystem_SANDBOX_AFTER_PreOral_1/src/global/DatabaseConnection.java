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
import windows.KioskLogin;


public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://7.0.0.15:3306/rbksse_db"; 
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rbksse_db"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    
public static Connection connect(JFrame parentFrame) { // Accept a parent JFrame as a parameter
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
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
        }
        return conn;
    } 
    
    //regular connect
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
}
