package windows;

import global.DatabaseConnection;
import global.StudentData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
public class _loginAs_popup extends javax.swing.JFrame {

    
    private DefaultTableModel tableModel;
    String selectedProgram="All", selectedYear="All", selectedBlock="All";
    
    public _loginAs_popup() {
//        super(parent, "Login As", true);
        initComponents();
        
        setupTable();
        
        // Initialize table model with column names
        String[] columnNames = {"Surname", "Firstname", "Middlename", "RFID"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Set the table model to jTable1
        jTable1.setModel(tableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton_VIEW = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jCombo_program = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jCombo_year = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jCombo_block = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jCombo_AY = new javax.swing.JComboBox<>();
        jButton_loginSelected = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(660, 340));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_VIEW.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_VIEW.setText("VIEW STUDENTS");
        jButton_VIEW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VIEWActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_VIEW, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 190, 60));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Program");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 60, 20));

        jCombo_program.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCombo_program.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "BEED", "BTVTED" }));
        jCombo_program.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_programActionPerformed(evt);
            }
        });
        jPanel1.add(jCombo_program, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 190, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Year Level");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 60, 20));

        jCombo_year.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCombo_year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "1", "2", "3", "4" }));
        jCombo_year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_yearActionPerformed(evt);
            }
        });
        jPanel1.add(jCombo_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 80, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Block");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 60, 20));

        jCombo_block.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCombo_block.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "A", "B", "C", "D", "E" }));
        jCombo_block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_blockActionPerformed(evt);
            }
        });
        jPanel1.add(jCombo_block, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 80, -1));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Surname", "Firstname", "Middlename", "RFID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 430, 350));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Academic Year");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 100, 20));

        jCombo_AY.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCombo_AY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024-2025" }));
        jCombo_AY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_AYActionPerformed(evt);
            }
        });
        jPanel1.add(jCombo_AY, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 190, -1));

        jButton_loginSelected.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_loginSelected.setText("LOG IN");
        jButton_loginSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loginSelectedActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_loginSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 190, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_VIEWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VIEWActionPerformed
        // Clear existing data
        tableModel.setRowCount(0);

        // SQL query with filters
        String query = "SELECT last_name, first_name, middle_name, rfid " +
                       "FROM students " +
                       "WHERE program_code = ? AND current_year = ? AND current_block = ? " +
                       "ORDER BY last_name, first_name";
        
        if(selectedProgram.equals("All")){
            query = "SELECT last_name, first_name, middle_name, rfid " +
                       "FROM students " +
                       "ORDER BY last_name, first_name";
        }
        

        try (Connection conn = DatabaseConnection.connect(this);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (selectedProgram.equals("All")) {               
            }else{
                // Set parameters for the query
                pstmt.setString(1, selectedProgram);
                pstmt.setString(2, selectedYear);
                pstmt.setString(3, selectedBlock);
            }
            // Execute query
            try (ResultSet rs = pstmt.executeQuery()) {
                // Process result set
                while (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");
                    String middleName = rs.getString("middle_name");
                    String rfid = rs.getString("rfid");

                    // Add row to the table model
                    tableModel.addRow(new Object[]{lastName, firstName, middleName, rfid});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error fetching data: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_VIEWActionPerformed

    private void jCombo_AYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_AYActionPerformed
        
    }//GEN-LAST:event_jCombo_AYActionPerformed

    private void jCombo_programActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_programActionPerformed
        selectedProgram=jCombo_program.getSelectedItem().toString();
        System.out.println("Just in selectedProgram: "+selectedProgram);
    }//GEN-LAST:event_jCombo_programActionPerformed

    private void jCombo_yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_yearActionPerformed
        selectedYear=jCombo_year.getSelectedItem().toString();
        System.out.println("Just in selectedYear: "+selectedYear);

    }//GEN-LAST:event_jCombo_yearActionPerformed

    private void jCombo_blockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_blockActionPerformed
        selectedBlock=jCombo_block.getSelectedItem().toString();
        System.out.println("Just in selectedBlock: "+selectedBlock);

    }//GEN-LAST:event_jCombo_blockActionPerformed

    private void jButton_loginSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loginSelectedActionPerformed
        // Get the selected row index from jTable1
    int selectedRow = jTable1.getSelectedRow();

    if (selectedRow != -1) { // Ensure a row is selected
        // Retrieve data from the selected row
        String lastName = tableModel.getValueAt(selectedRow, 0).toString();
        String firstName = tableModel.getValueAt(selectedRow, 1).toString();
        String middleName = tableModel.getValueAt(selectedRow, 2).toString();
        String rfid = tableModel.getValueAt(selectedRow, 3).toString();

        login(rfid);
        // Add your specific action here
        // For example: Open a new form or perform the login process
        // Example: openStudentDetails(lastName, firstName, middleName);
    } else {
        // Show a message if no row is selected
        JOptionPane.showMessageDialog(
            jTable1,
            "Please select a student from the table first.",
            "No Selection",
            JOptionPane.WARNING_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton_loginSelectedActionPerformed

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
            java.util.logging.Logger.getLogger(_loginAs_popup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_loginAs_popup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_loginAs_popup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_loginAs_popup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new _loginAs_popup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_VIEW;
    private javax.swing.JButton jButton_loginSelected;
    private javax.swing.JComboBox<String> jCombo_AY;
    private javax.swing.JComboBox<String> jCombo_block;
    private javax.swing.JComboBox<String> jCombo_program;
    private javax.swing.JComboBox<String> jCombo_year;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private void setupTable() {
         try (Connection conn = DatabaseConnection.connect(this)) {
            if (conn != null) {
                // Perform your database operations here
                System.out.println("Database connection was successful.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void login(String rfid) {
        StudentData.setInLogin(false);
        
        String query = """
            SELECT s.*, p.description 
            FROM students s 
            LEFT JOIN programs p 
            ON s.program_code = p.program_code 
            WHERE s.rfid = ?;
        """;

        try (Connection conn = DatabaseConnection.connect(this);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, rfid);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
//                rfidExist = true;

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
                StudentData.setCurrentRFID(rfid);
                StudentData.setPin(rs.getString("pin"));
                StudentData.setStatus(rs.getString("status"));

                // Fetch program description and combine it with program code
                String programDescription = rs.getString("description");
                if (programDescription != null) {
                    StudentData.setProgramLong(programDescription + " (" + StudentData.getProgram() + ")");
                }

                System.out.println("Student found: " + StudentData.getFirstName() + " " + StudentData.getLastName());
//                TransactionRemark = StudentData.getLastName() + ", " + StudentData.getFirstName() + " - LOGGED IN";

            } else {
                System.out.println("No student found with RFID: " + rfid);
//                TransactionRemark = "UNKNOWN RFID";
//                rfidExist = false;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving student data: " + e.getMessage());
        }
                        
        Pin pinWindow = new Pin();
        pinWindow.setVisible(true);  

        // Hide and dispose of this window
        setVisible(false);
        dispose();
    }

}
