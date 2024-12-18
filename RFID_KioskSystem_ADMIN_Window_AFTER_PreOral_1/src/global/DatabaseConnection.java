/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import javax.swing.JOptionPane;


public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://127.0.0.1:3306/kiosk"; 
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rbksse_db"; 
//    private static final String URL = "jdbc:mysql://7.0.0.15:3306/rbksse_db"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    
    public static Connection connect() { Connection conn = null; 
        try { 
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database."); 
        } catch (SQLException e) { 
            String popupMessage = "Database connection failed: " + e.getMessage();
            String popupTitle = "ERROR: NOT CONNECTED TO SERVER"; 
            JOptionPane.showMessageDialog(null, popupMessage, popupTitle, JOptionPane.ERROR_MESSAGE);
        } 
        return conn;
    }
}
