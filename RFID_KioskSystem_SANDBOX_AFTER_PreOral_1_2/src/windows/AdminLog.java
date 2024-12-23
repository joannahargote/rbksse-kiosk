package windows;

import java.time.LocalTime;
import global.StudentData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class AdminLog extends javax.swing.JFrame {

    _Notifications notifications = new _Notifications();
    private JLabel[] notificationLabels;
    JLabel[] dateLabels;
    public String[] messages;
    public Date[] dates;
    public int [] pages;
   
    public AdminLog() {
        initComponents();
        connect();
        
        StudentData.setInLogin(false);
        
        jlblGreeting.setText(greetUser());
        
        

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
        jTable1.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        
        fetchDataAndPopulateTable(model);
        
        postInitialize(); // Handle further setup outside the constructor
}

    private void postInitialize() {
           }

//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    
//    private static final String URL = "jdbc:mysql://7.0.0.15:3306/rbksse_db";
        private static final String URL = "jdbc:mysql://127.0.0.1:3306/rbksse_db";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            String popupMessage = "Database connection failed: " + e.getMessage(); String popupTitle = "ERROR: NOT CONNECTED TO SERVER"; 
            JOptionPane.showMessageDialog(null, popupMessage, popupTitle, JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
    

    private static void fetchDataAndPopulateTable(DefaultTableModel model) { 
        String query = "SELECT kl.timestamp, kl.rfid, IFNULL(s.last_name, 'Unknown') AS last_name, " 
                + "IFNULL(s.first_name, 'Unknown') AS first_name, IFNULL(s.middle_name, 'Unknown') AS middle_name, " +
                "kl.report " + 
                "FROM kiosk_login kl " +
                "LEFT JOIN students s ON kl.rfid = s.rfid " + 
                "ORDER BY kl.log_id DESC";
    
        try(Connection conn = connect();
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
    
    
    
    public static String greetUser() {
        LocalTime currentTime = LocalTime.now();
        String greeting;
        if (currentTime.isBefore(LocalTime.of(12, 0))) {
            // Before noon
            if (currentTime.isAfter(LocalTime.of(5, 0))) {
               greeting="Good Morning,";
            } else {
                greeting="So Early,"; // If it's before 5 AM
            }
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            // Afternoon
            greeting="Good Afternoon,";
        } else {
            // Evening
            greeting="Good Evening,";
        }
        return greeting;
    }
    

    

    
    public int showCustomDialog(String title, String message, String btn1, String btn2){

        _popUp popup = new _popUp(this, true);    
        popup.showPopup(title, message, btn1,btn2); 
                
        // Handle the clicked button 
        int clickedButton = popup.getClickedButton(); 

       return clickedButton;
    }
    
    
//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jlblCourse2 = new javax.swing.JLabel();
        jlblCourse1 = new javax.swing.JLabel();
        jlblNameHeader = new javax.swing.JLabel();
        jlblGreeting = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jbtnRequest1 = new javax.swing.JButton();
        jbtn_1 = new javax.swing.JButton();
        jbtn_2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jlblNameHeader1 = new javax.swing.JLabel();
        jbtn_3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 1024));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 1024));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 1024));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 1024));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 1024));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblCourse2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCourse2.setForeground(new java.awt.Color(255, 204, 0));
        jlblCourse2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCourse2.setText("RFID-BASED KIOSK SYSTEM FOR STUDENT EVALUATION");
        jlblCourse2.setToolTipText("");
        jPanel2.add(jlblCourse2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1920, 50));

        jlblCourse1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblCourse1.setForeground(new java.awt.Color(255, 204, 0));
        jlblCourse1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCourse1.setText("AEMILIANUM COLLEGE INC.");
        jlblCourse1.setToolTipText("");
        jPanel2.add(jlblCourse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1920, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 130));

        jlblNameHeader.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jlblNameHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader.setText("ADMIN");
        jlblNameHeader.setToolTipText("");
        jPanel1.add(jlblNameHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 1920, 60));

        jlblGreeting.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jlblGreeting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblGreeting.setText("Good morning,");
        jlblGreeting.setToolTipText("");
        jPanel1.add(jlblGreeting, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1920, -1));

        jPanel9.setBackground(new java.awt.Color(255, 204, 0));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1920, 10));

        jbtnRequest1.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnRequest1.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest1.setText("Recheck");
        jbtnRequest1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest1ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnRequest1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 3, 120, 50));

        jbtn_1.setBackground(new java.awt.Color(0, 51, 102));
        jbtn_1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtn_1.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_1.setText("CLOSE KIOSK");
        jbtn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_1ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 690, 350, 120));

        jbtn_2.setBackground(new java.awt.Color(0, 51, 102));
        jbtn_2.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtn_2.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_2.setText("LOG OUT");
        jbtn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_2ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 350, 120));
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1920, 120));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("127.0.0.1");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 840, 250, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Change Server IP:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 890, 180, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Current Server IP: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 840, 170, 40));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.setText("127.0.0.1");
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 940, 340, 40));

        jButton1.setBackground(new java.awt.Color(0, 51, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("COMMIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 990, 150, 40));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 430, 1280, 600));

        jPanel4.setBackground(new java.awt.Color(0, 51, 102));

        jlblNameHeader1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader1.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader1.setText("KIOSK VISITORS");
        jlblNameHeader1.setToolTipText("");
        jPanel4.add(jlblNameHeader1);

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, 1280, 60));

        jbtn_3.setBackground(new java.awt.Color(0, 51, 102));
        jbtn_3.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtn_3.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_3.setText("LOGIN AS...");
        jbtn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_3ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 350, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private JFrame frame;
    private void jbtnRequest1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnRequest1ActionPerformed

    private void jbtn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_1ActionPerformed
        String popupTitle = "Exiting"; 
        String popupMessage = "You are about to close the kiosk application.";
        int click = showCustomDialog(popupTitle, popupMessage, "OK", "Cancel");
        if (click == 1) { 
            System.exit(0);
        }  else if (click == 2) { 
        System.out.println("Button 2 was clicked");
        }
    }//GEN-LAST:event_jbtn_1ActionPerformed

    private void jbtn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_2ActionPerformed
        String popupTitle = "Logging Out"; 
        String popupMessage = "You are about to log out.";
        int click = showCustomDialog(popupTitle, popupMessage, "OK", "Cancel");
        if (click == 1) { 
            KioskLogin login = new KioskLogin();
            login.setVisible(true);  
        
            // Hide and dispose of this window
            setVisible(false);
            dispose();
        }  else if (click == 2) { 
        System.out.println("Button 2 was clicked");
        }
    }//GEN-LAST:event_jbtn_2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_3ActionPerformed
        _loginAs_popup loginAs = new _loginAs_popup();
        loginAs.setVisible(true);
    }//GEN-LAST:event_jbtn_3ActionPerformed

    int notificationPage = 1, totalNotifPage;
        /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AdminLog().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbtnRequest1;
    private javax.swing.JButton jbtn_1;
    private javax.swing.JButton jbtn_2;
    private javax.swing.JButton jbtn_3;
    private javax.swing.JLabel jlblCourse1;
    private javax.swing.JLabel jlblCourse2;
    private javax.swing.JLabel jlblGreeting;
    private javax.swing.JLabel jlblNameHeader;
    private javax.swing.JLabel jlblNameHeader1;
    // End of variables declaration//GEN-END:variables


}
