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


public class AdminLog_pan extends javax.swing.JFrame {

    _Notifications notifications = new _Notifications();
    private JLabel[] notificationLabels;
    JLabel[] dateLabels;
    public String[] messages;
    public Date[] dates;
    public int [] pages;
   
    public AdminLog_pan() {
        initComponents();
//        connect();
        jbtn_4ActionPerformed(null);
        
        StudentData.setInLogin(false);
        
        jlblGreeting.setText(greetUser());
}


//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    

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
        jbtn_3 = new javax.swing.JButton();
        changeablePanel = new javax.swing.JPanel();
        jbtn_4 = new javax.swing.JButton();

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
        jPanel1.add(jbtn_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 680, 350, 80));

        jbtn_2.setBackground(new java.awt.Color(0, 51, 102));
        jbtn_2.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtn_2.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_2.setText("LOG OUT");
        jbtn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_2ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 580, 350, 80));
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

        jbtn_3.setBackground(new java.awt.Color(0, 51, 102));
        jbtn_3.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtn_3.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_3.setText("LOGIN AS...");
        jbtn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_3ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 350, 80));

        changeablePanel.setBackground(new java.awt.Color(255, 255, 255));
        changeablePanel.setLayout(new java.awt.BorderLayout());
        jPanel1.add(changeablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, 1420, 800));

        jbtn_4.setBackground(new java.awt.Color(0, 51, 102));
        jbtn_4.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtn_4.setForeground(new java.awt.Color(255, 255, 255));
        jbtn_4.setText("KIOSK LOGS");
        jbtn_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_4ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtn_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 350, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private JFrame frame;
    private void jbtnRequest1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnRequest1ActionPerformed

    private void jbtn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_1ActionPerformed
        enableButtons();
        jbtn_1.setEnabled(false);
        String popupTitle = "Exiting"; 
        String popupMessage = "You are about to close the kiosk application.";
        int click = showCustomDialog(popupTitle, popupMessage, "OK", "Cancel");
        if (click == 1) { 
            System.exit(0);
        }  else if (click == 2) { 
        System.out.println("Button 2 was clicked");
        }
        enableButtons();
    }//GEN-LAST:event_jbtn_1ActionPerformed

    private void jbtn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_2ActionPerformed
        enableButtons();
        jbtn_2.setEnabled(false);
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
        enableButtons();
    }//GEN-LAST:event_jbtn_2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_3ActionPerformed
        enableButtons();
        jbtn_3.setEnabled(false);
        changeablePanel.removeAll();
        _AdminLogAs _logAs = new _AdminLogAs(this);
        changeablePanel.add(_logAs);
        changeablePanel.revalidate(); changeablePanel.repaint();
    }//GEN-LAST:event_jbtn_3ActionPerformed

    private void jbtn_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_4ActionPerformed
        enableButtons();
        jbtn_4.setEnabled(false);
        changeablePanel.removeAll();
        _AdminViewLogs _logs = new _AdminViewLogs();
        changeablePanel.add(_logs);
        changeablePanel.revalidate(); changeablePanel.repaint();
    }//GEN-LAST:event_jbtn_4ActionPerformed

    private void enableButtons(){
        jbtn_1.setEnabled(true);
        jbtn_2.setEnabled(true);
        jbtn_3.setEnabled(true);
        jbtn_4.setEnabled(true);
    }
    
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
            java.util.logging.Logger.getLogger(AdminLog_pan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminLog_pan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminLog_pan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminLog_pan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            new AdminLog_pan().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel changeablePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbtnRequest1;
    private javax.swing.JButton jbtn_1;
    private javax.swing.JButton jbtn_2;
    private javax.swing.JButton jbtn_3;
    private javax.swing.JButton jbtn_4;
    private javax.swing.JLabel jlblCourse1;
    private javax.swing.JLabel jlblCourse2;
    private javax.swing.JLabel jlblGreeting;
    private javax.swing.JLabel jlblNameHeader;
    // End of variables declaration//GEN-END:variables


}
