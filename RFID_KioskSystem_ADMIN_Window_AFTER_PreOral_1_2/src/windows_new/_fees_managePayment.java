/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package windows_new;

import global.DatabaseConnection;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class _fees_managePayment extends javax.swing.JPanel {

    int count = 0;
        String fName = "", mName = "", lName = "", suffix = "", currCode = "", 
               program = "", block = "", acadStatus = "", studentId = "", rfidCode = "", kioskPin = "";
    int yrLvl = 0;
    boolean NO_rfidAvailable=false;
    private DefaultTableModel tableModel_1, tableModel_2;
    private List<Object[]> results = new ArrayList<>();
    String ActiveTask = "Add New Student"; //Can be "Edit Student Record", OR "Delete Student Record"
    String TableTask = "View All Rows"; // Can be "Filtered Rows"
    int _recordId=0;
    String searchKey="";
    String SELECTED_STUDENT_ID="";
    Double SELECTED_STUDENT_TUITION=0.0;
    
    
    public _fees_managePayment() {
        initComponents();
        
        txt_payment.setEditable(true);
        txt_search.setEditable(true);
        
        
        
//        --------------------------------------------------------------------------
        tableModel_1 = new DefaultTableModel(
                new Object[]{"Student ID", "Last Name", "First Name", "Suffix", "Middle Name", "Program", "Year", "Block"}, 0) { 
                    @Override public boolean isCellEditable(int row, int column) 
                    { return false; } 
                };
        
        String[] columnHeaders = {"Student ID", "Last Name", "First Name", "Suffix", "Middle Name", "Program", "Year", "Block"};
        tableModel_1 = new DefaultTableModel(columnHeaders, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //no cell editable
            }
        };
        
        // tab;e appearance
        jTable1.setBackground(Color.WHITE);
        jTable1.setGridColor(Color.BLACK);  // Set grid line color
        jTable1.getTableHeader().setBackground(Color.LIGHT_GRAY);  // Header background color
        jTable1.setRowHeight(30);
        
        refreshTable_UpdateList_1();
        
        jTable1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            ActiveTask="Edit Student Record";
            // Get the selected row index
            int row = jTable1.getSelectedRow();

            // Check if the row is valid
            if (row != -1) {
                // Retrieve the full row data from the results list
                Object[] selectedRowData = results.get(row);

                // Extract the necessary data from selectedRowData
                String recordID = (String) selectedRowData[0];
                _recordId = Integer.valueOf(recordID);
                System.out.println("RECORD ID: "+_recordId);
                String studentId = (String) selectedRowData[1];
                SELECTED_STUDENT_ID=studentId;
                String firstName = (String) selectedRowData[2];
                String middleName = (String) selectedRowData[3];
                String lastName = (String) selectedRowData[4];
                String suffix = (String) selectedRowData[5];
                String rfid = (String) selectedRowData[6];
                String pin = (String) selectedRowData[7];
                String programCode = (String) selectedRowData[8];
                String curriculumCode = (String) selectedRowData[9];
                String currentYear = (String) selectedRowData[10];
                String currentBlock = (String) selectedRowData[11];
                String status = (String) selectedRowData[12];
                Double tuition = (Double) selectedRowData[13];
                SELECTED_STUDENT_TUITION = tuition;
                
                // Log all extracted data
            System.out.println("\n\nExtracted Data:");
            System.out.println("First Name: " + firstName);
            System.out.println("Middle Name: " + middleName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Suffix: " + suffix);
            System.out.println("ID: " + studentId);
            System.out.println("RFID: " + rfid);
            System.out.println("PIN: " + pin);
            System.out.println("Program Code: " + programCode);
            System.out.println("Curriculum Code: " + curriculumCode);
            System.out.println("Current Year: " + currentYear);
            System.out.println("Current Block: " + currentBlock);
            System.out.println("Status: " + status);
            System.out.println("SELECTED_STUDENT_ID: " + SELECTED_STUDENT_ID+"\n\n\n");    
                
                // Update JComboBoxes and JTextFields with the selected data\
                String name=lastName + ", " + firstName 
                    + (suffix != null ? " " + suffix : "") 
                    + (middleName != null ? " " + middleName : "");
                lbl_seeName.setText(name.toUpperCase());
                lbl_seeProgYrBlk.setText(programCode+" "+currentYear+" - "+currentBlock);
                lbl_seeTuition.setText("Php "+SELECTED_STUDENT_TUITION.toString()+"0");
                
                refreshTable_UpdateList_2();
                
            }
        }
    });
        
//        --------------------------------------------------------------------------
        tableModel_2 = new DefaultTableModel(
                new Object[]{"Date", "Amount Paid", "Remaining Balance"}, 0) { 
                    @Override public boolean isCellEditable(int row, int column) 
                    { return false; } 
                };
        
        String[] columnHeaders2 = {"Date", "Amount Paid", "Remaining Balance"};
        tableModel_2 = new DefaultTableModel(columnHeaders2, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //no cell editable
            }
        };
        
        // tab;e appearance
        jTable2.setBackground(Color.WHITE);
        jTable2.setGridColor(Color.BLACK);  // Set grid line color
        jTable2.getTableHeader().setBackground(Color.LIGHT_GRAY);  // Header background color
        jTable2.setRowHeight(30);
        
        
      
    }

     private void refreshTable_UpdateList_2() {
    tableModel_2.setRowCount(0); // Clear the table model

    String query = "SELECT date, amount_paid, remaining_balance FROM payment WHERE student_id = ?";

    try (java.sql.Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        // Set the selected student ID as a parameter
        pstmt.setString(1, SELECTED_STUDENT_ID);

        try (ResultSet rs = pstmt.executeQuery()) {
            // Process the result set and populate the table
            while (rs.next()) {
                String date = rs.getString("date");
                String amountPaid = rs.getString("amount_paid");
                String remainingBalance = rs.getString("remaining_balance");

                // Add row to the table model
                Object[] tableRow = {date, amountPaid, remainingBalance};
                tableModel_2.addRow(tableRow);
            }

            // Set the table model to jTable2
            jTable2.setModel(tableModel_2);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while processing the result set: " + e.getMessage());
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Database error: " + e.getMessage());
    }
}
    
    
    
    private void refreshTable_UpdateList_1() {
        tableModel_1.setRowCount(0); // Clear the table model
        if (results == null) {
            results = new ArrayList<>(); // Initialize the results list if it's null
        } else {
            results.clear(); // Clear the results list
        }

        String query;
        if (TableTask.equals("View All Rows")) {
            query = "SELECT "
                + "record_id, "
                + "student_id, "
                + "first_name, "
                + "middle_name, "
                + "last_name, "
                + "suffix, "
                + "rfid, "
                + "pin, "
                + "program_code, "
                + "curriculum_code, "
                + "current_year, "
                + "current_block, "
                    + "current_tuition, "
                + "status "
                + "FROM students "
                + "WHERE NOT (student_id = 0 AND first_name = 'ADMIN') "
                + "ORDER BY last_name ASC, first_name ASC, middle_name ASC;";
        } else if (TableTask.equals("Filtered Rows")) {
            query = "SELECT "
                + "record_id, "
                + "student_id, "
                + "first_name, "
                + "middle_name, "
                + "last_name, "
                + "suffix, "
                + "rfid, "
                + "pin, "
                + "program_code, "
                + "curriculum_code, "
                + "current_year, "
                + "current_block, "
                    + "current_tuition, "
                + "status "
                + "FROM students "
                + "WHERE "
                + "NOT (student_id = 0 AND first_name = 'ADMIN') AND ("
                + "student_id LIKE ? OR "
                + "first_name LIKE ? OR "
                + "middle_name LIKE ? OR "
                + "last_name LIKE ? OR "
                + "suffix LIKE ? OR "
                + "rfid LIKE ? OR "
                + "pin LIKE ? OR "
                + "program_code LIKE ? OR "
                + "curriculum_code LIKE ? OR "
                + "current_year LIKE ? OR "
                + "current_block LIKE ? OR "
                + "status LIKE ?)"
                + "ORDER BY last_name ASC, first_name ASC, middle_name ASC;";
        } else {
            // Handle unexpected TableTask values
            System.out.println("Invalid TableTask value: " + TableTask);
            return;
        }

        try (java.sql.Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (TableTask.equals("Filtered Rows")) {
                String searchPattern = "%" + searchKey+ "%";
                for (int i = 1; i <= 12; i++) {
                    pstmt.setString(i, searchPattern);
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                // Process the result set and populate the table
                while (rs.next()) {
                    String recordId = rs.getString("record_id");
                    String studentId = rs.getString("student_id");
                    String firstName = rs.getString("first_name");
                    String middleName = rs.getString("middle_name");
                    String lastName = rs.getString("last_name");
                    String suffix = rs.getString("suffix");
                    String rfid = rs.getString("rfid");
                    String pin = rs.getString("pin");
                    String programCode = rs.getString("program_code");
                    String curriculumCode = rs.getString("curriculum_code");
                    String currentYear = rs.getString("current_year");
                    String currentBlock = rs.getString("current_block");
                    double currentTuition = rs.getDouble("current_tuition");
                    String status = rs.getString("status");

                    // Add row to the table model
                    Object[] tableRow = {studentId, lastName, firstName, suffix, middleName, programCode, currentYear, currentBlock, rfid};
                    tableModel_1.addRow(tableRow);

                    // Add full row details to the results list
                    Object[] fullRow = {
                        recordId,          // Record ID
                        studentId,         // Student ID
                        firstName,         // First Name
                        middleName,        // Middle Name
                        lastName,          // Last Name
                        suffix,            // Suffix
                        rfid,              // RFID Code
                        pin,               // PIN
                        programCode,       // Program Code
                        curriculumCode,    // Curriculum Code
                        currentYear,       // Year
                        currentBlock,      // Block
                        status,             // Status
                        currentTuition
                    };
                    results.add(fullRow);
                }

                // Set the table model to jTable1
                jTable1.setModel(tableModel_1);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error: " + e.getMessage());
            }
        } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error: " + e.getMessage());
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

        jLabel6 = new javax.swing.JLabel();
        jlblCourse1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txt_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        btn_defaultTable = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        lbl_seeName = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_seeProgYrBlk = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lbl_seeTuition = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_payment = new javax.swing.JTextField();
        btn_updateRecord = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1100, 630));
        setMinimumSize(new java.awt.Dimension(1100, 630));
        setPreferredSize(new java.awt.Dimension(1080, 630));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("FEES : MANAGE PAYMENT");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 310, 50));

        jlblCourse1.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jlblCourse1.setForeground(new java.awt.Color(153, 153, 153));
        jlblCourse1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblCourse1.setText("RFID-Based Kiosk System for Student Evaluation _ ACI Capstone for MIT 2024 _ JCA");
        jlblCourse1.setToolTipText("");
        add(jlblCourse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 600, 380, 20));

        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Last Name", "First Name", "Suffix", "Middle Name", "Program/Year/Block", "Tuition", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setShowHorizontalLines(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 1040, 170));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_search.setEditable(false);
        txt_search.setBackground(new java.awt.Color(255, 255, 255));
        txt_search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        jPanel4.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 40));

        btn_search.setBackground(new java.awt.Color(0, 51, 102));
        btn_search.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sSMALLearch-icon-png-21.png"))); // NOI18N
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel4.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 40, 40));

        btn_defaultTable.setBackground(new java.awt.Color(0, 51, 102));
        btn_defaultTable.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_defaultTable.setForeground(new java.awt.Color(255, 255, 255));
        btn_defaultTable.setText("All");
        btn_defaultTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_defaultTableActionPerformed(evt);
            }
        });
        jPanel4.add(btn_defaultTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 60, 40));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1040, 60));

        jTable2.setForeground(new java.awt.Color(0, 0, 0));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DateTime", "Year Level", "Semester", "Payment", "Balance", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setToolTipText("");
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setShowHorizontalLines(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable2);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 320, 640, 260));

        jLabel8.setText("Student Name");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 310, 20));

        lbl_seeName.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbl_seeName.setText("-");
        lbl_seeName.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(lbl_seeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 340, 30));

        jLabel21.setText("Program/Year/Block");
        add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 130, 20));

        lbl_seeProgYrBlk.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbl_seeProgYrBlk.setText("-");
        lbl_seeProgYrBlk.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(lbl_seeProgYrBlk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 160, 30));

        jLabel19.setText("Current Tuition");
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 130, 20));

        lbl_seeTuition.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbl_seeTuition.setForeground(new java.awt.Color(204, 0, 0));
        lbl_seeTuition.setText("-");
        lbl_seeTuition.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(lbl_seeTuition, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 160, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 0));
        jLabel13.setText("Payment");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 70, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Php");
        add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 40, 20));

        txt_payment.setEditable(false);
        txt_payment.setBackground(new java.awt.Color(255, 255, 255));
        txt_payment.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_payment.setForeground(new java.awt.Color(0, 102, 0));
        txt_payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_paymentActionPerformed(evt);
            }
        });
        add(txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 140, 40));

        btn_updateRecord.setBackground(new java.awt.Color(0, 51, 102));
        btn_updateRecord.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_updateRecord.setForeground(new java.awt.Color(255, 255, 255));
        btn_updateRecord.setText("UPDATE RECORD");
        btn_updateRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateRecordActionPerformed(evt);
            }
        });
        add(btn_updateRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, -1, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        if (txt_search.getText().length()==0) {
            System.out.println("SEARCHING NONE");
        }else{
            TableTask = "Filtered Rows"; // Can be "Filtered Rows" or "View All Rows"
            searchKey=txt_search.getText();
            refreshTable_UpdateList_1(); // Refresh the table data after insert/update
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_defaultTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_defaultTableActionPerformed
        TableTask = "View All Rows"; // Can be "Filtered Rows"
        refreshTable_UpdateList_1();
        txt_search.setText("");
    }//GEN-LAST:event_btn_defaultTableActionPerformed

    private void txt_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_paymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_paymentActionPerformed

    private void btn_updateRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateRecordActionPerformed
try {
            // Parse payment from the text field
            Double payment = Double.valueOf(txt_payment.getText());
            System.out.println("Payment entered: " + payment);

            // Check if SELECTED_STUDENT_ID is valid
            if (SELECTED_STUDENT_ID == null || SELECTED_STUDENT_ID.isEmpty()) {
                System.out.println("No student selected.");
                JOptionPane.showMessageDialog(this, "Please select a student before updating the record.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Retrieve the most recent remaining balance for the student
            Double mostRecentRemainingBalance = null; // null if no record is found

            String getBalanceQuery = "SELECT remaining_balance FROM payment WHERE student_id = ? ORDER BY payment_id DESC LIMIT 1";
            try (java.sql.Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(getBalanceQuery)) {

                pstmt.setString(1, SELECTED_STUDENT_ID);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        mostRecentRemainingBalance = rs.getDouble("remaining_balance");
                    }
                }
            }

            // Calculate the new remaining balance
            Double newRemainingBalance;
            if (mostRecentRemainingBalance == null) {
                // If there is no existing balance, use SELECTED_STUDENT_TUITION as the starting balance
                newRemainingBalance = SELECTED_STUDENT_TUITION - payment;
                System.out.println("No previous balance, using tuition: " + SELECTED_STUDENT_TUITION);
            } else {
                // Otherwise, subtract the payment from the existing remaining balance
                newRemainingBalance = mostRecentRemainingBalance - payment;
                System.out.println("Most Recent Balance: " + mostRecentRemainingBalance);
            }

            System.out.println("New Remaining Balance: " + newRemainingBalance);

            // Insert the new record into the payment table
            String insertQuery = "INSERT INTO payment (student_id, amount_paid, remaining_balance) VALUES (?, ?, ?)";
            try (java.sql.Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

                pstmt.setString(1, SELECTED_STUDENT_ID);
                pstmt.setDouble(2, payment);
                pstmt.setDouble(3, newRemainingBalance);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("New payment record added successfully.");
                    JOptionPane.showMessageDialog(this, "Payment record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the table
                    refreshTable_UpdateList_2();
                } else {
                    System.out.println("Failed to add the payment record.");
                }
            }

        } catch (NumberFormatException ex) {
            System.out.println("Invalid payment amount entered.");
            JOptionPane.showMessageDialog(this, "Please enter a valid payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred while updating the record. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        //            try {
            //            // Parse payment from the text field
            //            Double payment = Double.valueOf(txt_payment.getText());
            //            System.out.println("Payment entered: " + payment);
            //
            //            // Check if SELECTED_STUDENT_ID is valid
            //            if (SELECTED_STUDENT_ID == null || SELECTED_STUDENT_ID.isEmpty()) {
                //                System.out.println("No student selected.");
                //                JOptionPane.showMessageDialog(this, "Please select a student before updating the record.", "Error", JOptionPane.ERROR_MESSAGE);
                //                return;
                //            }
            //
            //            // Retrieve the most recent remaining balance for the student
            //            Double mostRecentRemainingBalance = 0.0; // Default if no record is found
            //
            //            String getBalanceQuery = "SELECT remaining_balance FROM payment WHERE student_id = ? ORDER BY payment_id DESC LIMIT 1";
            //            try (java.sql.Connection conn = DatabaseConnection.connect();
                //                 PreparedStatement pstmt = conn.prepareStatement(getBalanceQuery)) {
                //
                //                pstmt.setString(1, SELECTED_STUDENT_ID);
                //
                //                try (ResultSet rs = pstmt.executeQuery()) {
                    //                    if (rs.next()) {
                        //                        mostRecentRemainingBalance = rs.getDouble("remaining_balance");
                        //                    }
                    //                }
                //            }
            //
            //            // Calculate the new remaining balance
            //            Double newRemainingBalance = mostRecentRemainingBalance - payment;
            //            System.out.println("Most Recent Balance: " + mostRecentRemainingBalance);
            //            System.out.println("New Remaining Balance: " + newRemainingBalance);
            //
            //            // Insert the new record into the payment table
            //            String insertQuery = "INSERT INTO payment (student_id, amount_paid, remaining_balance) VALUES (?, ?, ?)";
            //            try (java.sql.Connection conn = DatabaseConnection.connect();
                //                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                //
                //                pstmt.setString(1, SELECTED_STUDENT_ID);
                //                pstmt.setDouble(2, payment);
                //                pstmt.setDouble(3, newRemainingBalance);
                //
                //                int rowsInserted = pstmt.executeUpdate();
                //                if (rowsInserted > 0) {
                    //                    System.out.println("New payment record added successfully.");
                    //                    JOptionPane.showMessageDialog(this, "Payment record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    //
                    //                    // Refresh the table
                    //                    refreshTable_UpdateList_2();
                    //                } else {
                    //                    System.out.println("Failed to add the payment record.");
                    //                }
                //            }
            //
            //        } catch (NumberFormatException ex) {
            //            System.out.println("Invalid payment amount entered.");
            //            JOptionPane.showMessageDialog(this, "Please enter a valid payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
            //        } catch (SQLException ex) {
            //            System.out.println("Database error: " + ex.getMessage());
            //            JOptionPane.showMessageDialog(this, "An error occurred while updating the record. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            //            ex.printStackTrace();
            //    }
    }//GEN-LAST:event_btn_updateRecordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_defaultTable;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_updateRecord;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel jlblCourse1;
    private javax.swing.JLabel lbl_seeName;
    private javax.swing.JLabel lbl_seeProgYrBlk;
    private javax.swing.JLabel lbl_seeTuition;
    private javax.swing.JTextField txt_payment;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
