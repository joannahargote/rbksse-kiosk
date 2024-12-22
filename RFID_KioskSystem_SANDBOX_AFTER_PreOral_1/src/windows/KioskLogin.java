package windows;


import global.DatabaseConnection;
import global.StudentData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//for RFID listener
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//import java.security.Timestamp;




import java.sql.Timestamp;
import javax.swing.Timer;


public class KioskLogin extends javax.swing.JFrame {

    String TransactionRemark = "";
    private KeyAdapter rfidKeyListener; // Declare KeyListener as a field
    
    static boolean rfidExist=false;
    private JFrame frame;
    private Timer blinkTimer;
    private String baseMessage = "Connected to server";
    private int dotCount = 0;


    
    public KioskLogin() {
        initComponents();
        
        StudentData.setInLogin(true);
        
//        if(!StudentData.getBlinking()){
            startBlinking();
//            StudentData.setBlinking(true);
//            System.out.println("Starts Blinking");
//        }else{
//            System.out.println("Already Blinking");
//        }
        
//        ADMIN_BTN_tempAdminLog.setVisible(false);
        
        StudentData.setfetchedGradeFromDb(false);
        
        rfidInputField.requestFocusInWindow();
        
        // Initialize the KeyListener
        rfidKeyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String rfidData = rfidInputField.getText();
                getStudentByRFID(rfidData);
                rfidInputField.setText("");
                
                if(rfidExist){
                    rfidLogTransaction(rfidData,true);
                    
                    Pin pinWindow = new Pin();
                    pinWindow.setVisible(true);  
                     
                    // Hide and dispose of this window
                    setVisible(false);
                    dispose();

                    // Remove KeyListener after the scan
                    rfidInputField.removeKeyListener(rfidKeyListener);
                }
                else{
                    rfidLogTransaction(rfidData,false);
                    
                    String popupTitle = "Logging Out"; 
                    String popupMessage = "RFID NOT RECOGNIZED.";
                    int click = showCustomDialog(popupTitle, popupMessage, "None", "OK");
                }
            }
        }
    };
    
    // Add the KeyListener to the input field
    rfidInputField.addKeyListener(rfidKeyListener);
}

//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888

     // Method to start blinking lbl_connectionStatus and check connection
    private void startBlinking() {
        lbl_connectionStatus.setText(baseMessage);
        blinkTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkConnection()) {
                    dotCount = (dotCount + 1) % 4; // Increment and reset after 3
                    StringBuilder message = new StringBuilder(baseMessage);
                    for (int i = 0; i < dotCount; i++) {
                        message.append(" .");
                    }
                    lbl_connectionStatus.setText(message.toString());
                } else {
                    lbl_connectionStatus.setText("Not connected to server");
                }
            }
        });
//
//        if(!StudentData.getBlinking()){
//            blinkTimer.start();
//            StudentData.setBlinking(true);
//            System.out.println("Starts Blinking");
//        }
        
        blinkTimer.start();
    }

    // Method to check the connection to the server
    private boolean checkConnection() {
        try (Connection conn = DatabaseConnection.connect(this)) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void getStudentByRFID(String rfidTag) {
        
        String query = """
            SELECT s.*, p.description 
            FROM students s 
            LEFT JOIN programs p 
            ON s.program_code = p.program_code 
            WHERE s.rfid = ?;
        """;

        try (Connection conn = DatabaseConnection.connect(this);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            rfidTag = rfidTag.trim();
            stmt.setString(1, rfidTag);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rfidExist = true;

                // Using setters to set values in StudentData
                StudentData.setStudentID(rs.getString("student_id"));
                StudentData.setLastName(rs.getString("last_name"));
                StudentData.setFirstName(rs.getString("first_name"));
                StudentData.setMiddleName(rs.getString("middle_name"));
                StudentData.setProgram(rs.getString("program_code"));
                StudentData.setCurriculumCode(rs.getString("curriculum_code"));
                StudentData.setYearLevel(rs.getString("current_year"));
                StudentData.setBlock(rs.getString("current_block"));
                StudentData.setTuition(rs.getDouble("current_tuition"));
                StudentData.setCurrentRFID(rfidTag);
                StudentData.setPin(rs.getString("pin"));
                StudentData.setStatus(rs.getString("status"));

                // Fetch program description and combine it with program code
                String programDescription = rs.getString("description");
                if (programDescription != null) {
                    StudentData.setProgramLong(programDescription + " (" + StudentData.getProgram() + ")");
                }

                System.out.println("Student found: " + StudentData.getFirstName() + " " + StudentData.getLastName());
                TransactionRemark = StudentData.getLastName() + ", " + StudentData.getFirstName() + " - LOGGED IN";

            } else {
                System.out.println("No student found with RFID: " + rfidTag);
                TransactionRemark = "UNKNOWN RFID";
                rfidExist = false;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving student data: " + e.getMessage());
        }
       
    }
    
    
    private void rfidLogTransaction(String rfidData, boolean isValid) {
        String sql = "INSERT INTO kiosk_login (rfid, timestamp, report) VALUES (?, ?, ?)";
        String remark = isValid ? "OK" : "UNKNOWN RFID";

        try (Connection conn = DatabaseConnection.connect(this);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Correctly create a Timestamp 
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            
            stmt.setString(1, rfidData);
            stmt.setTimestamp(2, currentTime);
            stmt.setString(3, remark);

            stmt.executeUpdate();
            System.out.println(currentTime+" : Login recorded successfully.");

        } catch (SQLException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
        }
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
        jLabel1 = new javax.swing.JLabel();
        rfidInputField = new javax.swing.JTextField();
        jimgLogoHere700x700 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ADMIN_BTN_tempAdminLog = new javax.swing.JButton();
        lbl_connectionStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 1024));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 1024));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 1024));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 1024));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 1024));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FOR STUDENT EVALUATION");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 360, 910, 60));

        rfidInputField.setBackground(new java.awt.Color(0, 51, 102));
        rfidInputField.setForeground(new java.awt.Color(0, 102, 204));
        rfidInputField.setBorder(null);
        jPanel1.add(rfidInputField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1060, 20, 20));

        jimgLogoHere700x700.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SLTCFPI_logo_700x700.png"))); // NOI18N
        jPanel1.add(jimgLogoHere700x700, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 710, 720));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 0));
        jLabel2.setText("Please scan your RFID card...");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 910, 160));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("RFID-BASED KIOSK SYSTEM");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 290, 910, 60));

        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/scanImage.png"))); // NOI18N
        jLabel4.setText("scanImage");
        jLabel4.setToolTipText("");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 630, 660, 320));

        ADMIN_BTN_tempAdminLog.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        ADMIN_BTN_tempAdminLog.setText("Admin [temp]");
        ADMIN_BTN_tempAdminLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADMIN_BTN_tempAdminLogActionPerformed(evt);
            }
        });
        jPanel1.add(ADMIN_BTN_tempAdminLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 1050, 100, -1));

        lbl_connectionStatus.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lbl_connectionStatus.setForeground(new java.awt.Color(255, 255, 255));
        lbl_connectionStatus.setText("Connection Status");
        jPanel1.add(lbl_connectionStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 300, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ADMIN_BTN_tempAdminLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ADMIN_BTN_tempAdminLogActionPerformed
//        AdminLog admin = new AdminLog();
        AdminLog_pan admin = new AdminLog_pan();
        admin.setVisible(true);
        setVisible(false);
        dispose();

    }//GEN-LAST:event_ADMIN_BTN_tempAdminLogActionPerformed
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
            java.util.logging.Logger.getLogger(KioskLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KioskLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KioskLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KioskLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new KioskLogin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ADMIN_BTN_tempAdminLog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jimgLogoHere700x700;
    private javax.swing.JLabel lbl_connectionStatus;
    private javax.swing.JTextField rfidInputField;
    // End of variables declaration//GEN-END:variables
}
