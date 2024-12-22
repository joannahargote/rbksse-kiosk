/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package windows;

import global.DatabaseConnection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class _AdminViewLogs extends javax.swing.JPanel {

    /**
     * Creates new form _AdminViewLogs
     */
    public _AdminViewLogs() {
        initComponents();
        
        String[] columnNames = {"Date and Time", "RFID", "Last Name", "First Name", "Middle Name", "Report"}; 
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override 
            public boolean isCellEditable(int row, int column) { 
                return false;
            } // All cells are non-editable }
        }; 
        jTable1.setModel(model); 
        
        // Set custom font for the table 
        jTable1.setFont(new Font("Arial", Font.PLAIN, 15)); 
        jTable1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        jTable1.setRowHeight(25);
        
        // Set custom cell renderer to change the font color for unknown entries 
        jTable1.setDefaultRenderer(Object.class, new _AdminViewLogs.CustomTableCellRenderer());
        
        fetchDataAndPopulateTable(model);
        jTable1.repaint();
    }
    
    
    
    private static void fetchDataAndPopulateTable(DefaultTableModel model) { 
        String query = "SELECT kl.timestamp, kl.rfid, IFNULL(s.last_name, 'Unknown') AS last_name, " 
                + "IFNULL(s.first_name, 'Unknown') AS first_name, IFNULL(s.middle_name, 'Unknown') AS middle_name, " +
                "kl.report " + 
                "FROM kiosk_login kl " +
                "LEFT JOIN students s ON kl.rfid = s.rfid " + 
                "ORDER BY kl.log_id DESC";
    
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)){
            
            ResultSet rs = stmt.executeQuery(); 
            
            while (rs.next()) { 
                String timestamp = rs.getString("timestamp");
                String rfid = rs.getString("rfid"); 
                String lastName = rs.getString("last_name"); 
                String firstName = rs.getString("first_name"); 
                String middleName = rs.getString("middle_name"); 
                String report = rs.getString("report"); 
                
                model.addRow(new Object[]{timestamp, rfid, lastName, firstName, middleName, report}); 
                
//                System.out.println("Debug: timestamp = " + rs.getString("timestamp"));
//                System.out.println("Debug: rfid = " + rs.getString("rfid"));
//                System.out.println("Debug: lastName = " + rs.getString("last_name"));
//                System.out.println("Debug: firstName = " + rs.getString("first_name"));
//                System.out.println("Debug: middleName = " + rs.getString("middle_name"));
//                System.out.println("Debug: report = " + rs.getString("report"));

            } 
        } catch (SQLException e) { 
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Error fetching data from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
   
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override 
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String name = (String) table.getModel().getValueAt(row, 3); 
            String report = (String) table.getModel().getValueAt(row, 5); 

        if ("UNKNOWN RFID".equals(report)) { 
            cell.setForeground(Color.RED); 
        } else if("ADMIN".equals(name)){
            cell.setForeground(Color.BLUE); 
        }
        else { 
            cell.setForeground(Color.BLACK); 
        } 

        return cell; 
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jlblNameHeader1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1420, 80));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 51, 102));

        jlblNameHeader1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader1.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader1.setText("KIOSK VISITORS");
        jlblNameHeader1.setToolTipText("");
        jPanel4.add(jlblNameHeader1);

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 1280, 60));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 1280, 600));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel jlblNameHeader1;
    // End of variables declaration//GEN-END:variables
}
