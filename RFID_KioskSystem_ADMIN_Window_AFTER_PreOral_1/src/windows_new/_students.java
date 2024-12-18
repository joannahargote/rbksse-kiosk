/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package windows_new;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.xdevapi.Statement;
import com.sun.jdi.connect.spi.Connection;
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
public class _students extends javax.swing.JPanel {

    int count = 0;
        String fName = "", mName = "", lName = "", suffix = "", currCode = "", 
               program = "", block = "", acadStatus = "", studentId = "", rfidCode = "", kioskPin = "";
    int yrLvl = 0;
    boolean NO_rfidAvailable=false;
    private DefaultTableModel tableModel;
    private List<Object[]> results = new ArrayList<>();
    String ActiveTask = "Add New Student"; //Can be "Edit Student Record", OR "Delete Student Record"
    String TableTask = "View All Rows"; // Can be "Filtered Rows"
    int _recordId=0;
    String searchKey="";
    
    
    
    public _students() {
        initComponents();
        pan_infoHolder.setBorder(null);
        setEditableTextboxes();
        loadCurriculumCodes();
        loadPrograms();
        
        tableModel = new DefaultTableModel(
                new Object[]{"Student ID", "Last Name", "First Name", "Suffix", "Middle Name", "Program", "Year", "Block", "RFID Code"}, 0) { 
                    @Override public boolean isCellEditable(int row, int column) 
                    { return false; } 
                };
        
        String[] columnHeaders = {"Student ID", "Last Name", "First Name", "Suffix", "Middle Name", "Program", "Year", "Block", "RFID Code"};
        tableModel = new DefaultTableModel(columnHeaders, 0){
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
        
        refreshTable_UpdateList();
        
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

                // Update JComboBoxes and JTextFields with the selected data
                txt_newStudentID.setText(studentId);
                txt_newFirstN.setText(firstName);
                txt_newMiddleN.setText(middleName);
                txt_newLastN.setText(lastName);
                txt_newRFID.setText(rfid);
                txt_newPin.setText(pin);
                combo_program.setSelectedItem(programCode);
                combo_curiculum.setSelectedItem(curriculumCode);
                combo_yearLevel.setSelectedItem(currentYear);
                combo_block.setSelectedItem(currentBlock);
                combo_suffix.setSelectedItem(suffix != null ? suffix : "n/a");
                combo_acadStatus.setSelectedItem(status);
            }
        }
    });

    }

    private void setEditableTextboxes(){
        txt_newFirstN.setEditable(true);
        txt_newLastN.setEditable(true);
        txt_newMiddleN.setEditable(true);
        txt_newPin.setEditable(true);
        txt_newRFID.setEditable(true);
        txt_search.setEditable(true);
        txt_newStudentID.setEditable(true);
    }
    
    private void loadCurriculumCodes() {
        // Remove the border from the panel
        pan_infoHolder.setBorder(null);

        // SQL query to fetch unique curriculum codes
        String query = "SELECT DISTINCT curriculum_code FROM programs";

          try (java.sql.Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Clear the JComboBox before adding items (optional)
            combo_curiculum.removeAllItems();
            combo_curiculum.addItem("Select");

            // Populate the JComboBox with curriculum codes
            while (rs.next()) {
                String curriculumCode = rs.getString("curriculum_code");
                combo_curiculum.addItem(curriculumCode);
            }

        } catch (SQLException e) {
            // Log and display the error
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error fetching curriculum codes: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadPrograms() {
        // Remove the border from the panel
        pan_infoHolder.setBorder(null);

        // SQL query to fetch unique curriculum codes
        String query = "SELECT DISTINCT program_code FROM programs";

          try (java.sql.Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Clear the JComboBox before adding items (optional)
            combo_program.removeAllItems();
            combo_program.addItem("Select");

            // Populate the JComboBox with curriculum codes
            while (rs.next()) {
                String ProgramCode = rs.getString("program_code");
                combo_program.addItem(ProgramCode);
            }

        } catch (SQLException e) {
            // Log and display the error
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error fetching curriculum codes: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void readData(){
        fName = txt_newFirstN.getText().toString();
        mName = txt_newMiddleN.getText().toString();
        lName = txt_newLastN.getText().toString();
        suffix = combo_suffix.getSelectedItem().toString();
        currCode = combo_curiculum.getSelectedItem().toString();
        program = combo_program.getSelectedItem().toString();
        block = combo_block.getSelectedItem().toString();
        acadStatus = combo_acadStatus.getSelectedItem().toString();
        studentId = txt_newStudentID.getText().toString();
        rfidCode = txt_newRFID.getText().toString();
        kioskPin = txt_newPin.getText().toString();
        NO_rfidAvailable = check_noRFID.isSelected();
        
//        Check for "Select" before parsing
//        yrLvl;
        if (combo_yearLevel.getSelectedItem().toString().equals("Select")) {
            yrLvl = 0; // or any default value you prefer
        } else {
            yrLvl = Integer.parseInt(combo_yearLevel.getSelectedItem().toString());
        }
    }
    
    
    
    
    
    
    
    private void refreshTable_UpdateList() {
        tableModel.setRowCount(0); // Clear the table model
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
                    String status = rs.getString("status");

                    // Add row to the table model
                    Object[] tableRow = {studentId, lastName, firstName, suffix, middleName, programCode, currentYear, currentBlock, rfid};
                    tableModel.addRow(tableRow);

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
                        status             // Status
                    };
                    results.add(fullRow);
                }

                // Set the table model to jTable1
                jTable1.setModel(tableModel);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error: " + e.getMessage());
            }
        } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error: " + e.getMessage());
            }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pan_infoHolder = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_newFirstN = new javax.swing.JTextField();
        combo_suffix = new javax.swing.JComboBox<>();
        txt_newStudentID = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_newMiddleN = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_newLastN = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        combo_program = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        combo_yearLevel = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        combo_block = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        combo_acadStatus = new javax.swing.JComboBox<>();
        txt_newRFID = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        combo_curiculum = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txt_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        btn_Clear = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        btn_Save = new javax.swing.JButton();
        btn_defaultTable = new javax.swing.JButton();
        txt_newPin = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        check_noRFID = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jlblCourse1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1100, 630));
        setMinimumSize(new java.awt.Dimension(1100, 630));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan_infoHolder.setBackground(new java.awt.Color(255, 255, 255));
        pan_infoHolder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pan_infoHolder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("First Name ");
        pan_infoHolder.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 20));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Student ID");
        pan_infoHolder.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 70, 20));

        txt_newFirstN.setEditable(false);
        txt_newFirstN.setBackground(new java.awt.Color(255, 255, 255));
        txt_newFirstN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newFirstN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newFirstNActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newFirstN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 220, 40));

        combo_suffix.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "n/a", "Jr", "III", "IV", "V", "Sr" }));
        pan_infoHolder.add(combo_suffix, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 80, 40));

        txt_newStudentID.setEditable(false);
        txt_newStudentID.setBackground(new java.awt.Color(255, 255, 255));
        txt_newStudentID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newStudentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newStudentIDActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newStudentID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 220, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Middle Name");
        pan_infoHolder.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 90, 20));

        txt_newMiddleN.setEditable(false);
        txt_newMiddleN.setBackground(new java.awt.Color(255, 255, 255));
        txt_newMiddleN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newMiddleN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newMiddleNActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newMiddleN, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 220, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Last Name");
        pan_infoHolder.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 90, 20));

        txt_newLastN.setEditable(false);
        txt_newLastN.setBackground(new java.awt.Color(255, 255, 255));
        txt_newLastN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newLastN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newLastNActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newLastN, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 220, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Suffix");
        pan_infoHolder.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 90, 20));

        pan_infoHolder.add(combo_program, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 220, 40));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Year Level");
        pan_infoHolder.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 90, 20));

        combo_yearLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4" }));
        pan_infoHolder.add(combo_yearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 80, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Block");
        pan_infoHolder.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 90, 20));

        combo_block.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "C", "D", "E", "F", "G" }));
        pan_infoHolder.add(combo_block, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 80, 40));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Academic Status");
        pan_infoHolder.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 150, 20));

        combo_acadStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Regular", "Irregular" }));
        pan_infoHolder.add(combo_acadStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 170, 40));

        txt_newRFID.setEditable(false);
        txt_newRFID.setBackground(new java.awt.Color(255, 255, 255));
        txt_newRFID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newRFID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newRFIDActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newRFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 220, 40));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 0, 51));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText(" 0001234-24 (Format)");
        pan_infoHolder.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 160, 150, 20));

        pan_infoHolder.add(combo_curiculum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 220, 40));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Curriculum Code");
        pan_infoHolder.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 120, 20));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Program");
        pan_infoHolder.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 90, 20));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_search.setEditable(false);
        txt_search.setBackground(new java.awt.Color(255, 255, 255));
        txt_search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        jPanel4.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 40));

        btn_search.setBackground(new java.awt.Color(0, 51, 102));
        btn_search.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sSMALLearch-icon-png-21.png"))); // NOI18N
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel4.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 40, 40));

        btn_Clear.setBackground(new java.awt.Color(0, 51, 102));
        btn_Clear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_Clear.setForeground(new java.awt.Color(255, 255, 255));
        btn_Clear.setText("CLEAR");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });
        jPanel4.add(btn_Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 150, 40));

        btn_Delete.setBackground(new java.awt.Color(0, 51, 102));
        btn_Delete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_Delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_Delete.setText("DELETE");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });
        jPanel4.add(btn_Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 150, 40));

        btn_Save.setBackground(new java.awt.Color(0, 51, 102));
        btn_Save.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_Save.setForeground(new java.awt.Color(255, 255, 255));
        btn_Save.setText("SAVE");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });
        jPanel4.add(btn_Save, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 150, 40));

        btn_defaultTable.setBackground(new java.awt.Color(0, 51, 102));
        btn_defaultTable.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_defaultTable.setForeground(new java.awt.Color(255, 255, 255));
        btn_defaultTable.setText("All");
        btn_defaultTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_defaultTableActionPerformed(evt);
            }
        });
        jPanel4.add(btn_defaultTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 60, 40));

        pan_infoHolder.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 1040, 60));

        txt_newPin.setEditable(false);
        txt_newPin.setBackground(new java.awt.Color(255, 255, 255));
        txt_newPin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newPinActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newPin, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 220, 40));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Kiosk Pin Code");
        pan_infoHolder.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 90, 20));

        check_noRFID.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        check_noRFID.setText("No available RFID card");
        pan_infoHolder.add(check_noRFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 190, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("RFID Code");
        pan_infoHolder.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 70, 20));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 0, 51));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText(" (Scan RFID Card)");
        pan_infoHolder.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 150, 20));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(204, 0, 51));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText(" (6-Digit Numeric)");
        pan_infoHolder.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 160, 120, 20));

        add(pan_infoHolder, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 1040, 310));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("STUDENT RECORDS");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 310, 50));

        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Last Name", "First Name", "Suffix", "Middle Name", "Program", "Year", "Block", "RFID Code"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 1040, 240));

        jlblCourse1.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jlblCourse1.setForeground(new java.awt.Color(153, 153, 153));
        jlblCourse1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblCourse1.setText("RFID-Based Kiosk System for Student Evaluation _ ACI Capstone for MIT 2024 _ JCA");
        jlblCourse1.setToolTipText("");
        add(jlblCourse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 600, 380, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_newFirstNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newFirstNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newFirstNActionPerformed

    private void txt_newStudentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newStudentIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newStudentIDActionPerformed

    private void txt_newMiddleNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newMiddleNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newMiddleNActionPerformed

    private void txt_newLastNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newLastNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newLastNActionPerformed

    private void txt_newRFIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newRFIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newRFIDActionPerformed

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        if (txt_search.getText().length()==0) {
            System.out.println("SEARCHING NONE");
        }else{
            TableTask = "Filtered Rows"; // Can be "Filtered Rows" or "View All Rows"
            searchKey=txt_search.getText();
            refreshTable_UpdateList(); // Refresh the table data after insert/update
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        readData();

        if (fName.length() > 0 || mName.length() > 0 || lName.length() > 0 || !suffix.equals("n/a") || !currCode.equals("Select") || 
            !program.equals("Select") || !block.equals("Select") || !acadStatus.equals("Select") || studentId.length() > 0 || 
            rfidCode.length() > 0 || kioskPin.length() > 0 || yrLvl > 0 || NO_rfidAvailable) {
            int userResponse = JOptionPane.showConfirmDialog(
                null,
                "Clear all typed and selected data?",
                "Select an Option",
                JOptionPane.YES_NO_OPTION);

            // Check the user's response
            if (userResponse == JOptionPane.YES_OPTION) {
                txt_newFirstN.setText(null);
                txt_newLastN.setText(null);
                txt_newMiddleN.setText(null);
                txt_newPin.setText(null);
                txt_newRFID.setText(null);
                txt_newStudentID.setText(null);
                combo_acadStatus.setSelectedIndex(0);
                combo_block.setSelectedIndex(0);
                combo_curiculum.setSelectedIndex(0);
                combo_program.setSelectedIndex(0);
                combo_yearLevel.setSelectedIndex(0);
                combo_suffix.setSelectedIndex(0);
                check_noRFID.setSelected(false);
            } else if (userResponse == JOptionPane.NO_OPTION) {
                System.out.println("User chose No.");
            }
        }
        
        readData();
        refreshTable_UpdateList(); // Refresh the table data after insert/update
        ActiveTask = "Add New Student";
    }//GEN-LAST:event_btn_ClearActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        // Get the selected row index
        int row = jTable1.getSelectedRow();

        // Check if a row is selected
        if (row == -1) {
            JOptionPane.showMessageDialog(null,
                    "There is no selected row in the table to delete.",
                    "Null Action",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Retrieve the record ID from the selected row
        int recordId = Integer.parseInt(results.get(row)[0].toString()); // Ensure recordId is treated as an Integer

        // Show confirmation popup
        int userResponse = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete this record?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION);

        // If user confirms deletion
        if (userResponse == JOptionPane.YES_OPTION) {
            String deleteQuery = "DELETE FROM students WHERE record_id = ?";

            try (java.sql.Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

                // Set the recordId parameter
                pstmt.setInt(1, recordId);

                // Execute the delete query
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Show report popup
                    JOptionPane.showMessageDialog(null,
                            "Record deleted successfully!",
                            "Deletion Report",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the table data after deletion
                    refreshTable_UpdateList(); // Refresh the table data after insert/update
                    
                    txt_newFirstN.setText(null);
                    txt_newLastN.setText(null);
                    txt_newMiddleN.setText(null);
                    txt_newPin.setText(null);
                    txt_newRFID.setText(null);
                    txt_newStudentID.setText(null);
                    combo_acadStatus.setSelectedIndex(0);
                    combo_block.setSelectedIndex(0);
                    combo_curiculum.setSelectedIndex(0);
                    combo_program.setSelectedIndex(0);
                    combo_yearLevel.setSelectedIndex(0);
                    combo_suffix.setSelectedIndex(0);
                    check_noRFID.setSelected(false);
                } else {
                    // Show error message if no rows were affected
                    JOptionPane.showMessageDialog(null,
                            "Error: Record not found or could not be deleted.",
                            "Deletion Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Database error: " + e.getMessage(),
                        "Deletion Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("User chose not to delete the record.");
        }
    }//GEN-LAST:event_btn_DeleteActionPerformed

    
    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
            readData();
    
            String inputError = "<html><b>UNABLE TO SAVE DUE TO MISSING DATA:</b><br><br>";
        System.out.println("checking here");
            boolean NO_rfidAvailable = check_noRFID.isSelected();
            int count = 0;

            if (txt_newFirstN.getText().toString().length() == 0) { 
                count++;
                inputError += "First Name <br>"; 
            } 
            if (txt_newLastN.getText().toString().length() == 0) { 
                count++;
                inputError += "Last Name <br>"; 
            }

            if (combo_curiculum.getSelectedItem().equals("Select")) { 
                count++;
                inputError += "Curriculum Code <br>"; 
            } 

            if (combo_program.getSelectedItem().equals("Select")) { 
                count++;
                inputError += "Program <br>"; 
            }

            if (combo_yearLevel.getSelectedItem().toString().equals("Select")) { 
                count++;
                inputError += "Year Level <br>"; 
            }

            if (combo_block.getSelectedItem().toString().equals("Select")) { 
                count++;
                inputError += "Block <br>"; 
            }

            if (combo_acadStatus.getSelectedItem().toString().equals("Select")) { 
                count++;
                inputError += "Academic Status <br>"; 
            }

            if (txt_newStudentID.getText().toString().length() == 0) { 
                count++;
                inputError += "Student ID <br>"; 
            }

            if (txt_newRFID.getText().toString().length() == 0) { 
                if (!NO_rfidAvailable) {
                    count++;
                    inputError += "RFID Code <br>"; 
                }         
            } else { 
                if (NO_rfidAvailable){
                    count++;
                    inputError += "RFID Code - <b>'No available RFID card' is checked.</b> <br>";
                }            
            }
                String kioskPin = txt_newPin.getText().trim();
            if (kioskPin.length() == 0) {
                count++;
                inputError += "Kiosk Pin <br>";
            } else {
            // Check if the kioskPin is a 6-digit numeric value
                if (!kioskPin.matches("\\d{6}")) {
                    count++;
                    inputError += "Kiosk Pin must be a 6-digit number <br>";
                }
            }
                       
            inputError += "</html>";
            if (count > 0) {
            JOptionPane.showMessageDialog(null,
                inputError,
                "Invalid Input.",
                JOptionPane.WARNING_MESSAGE);
            return;
        } else {   
            boolean duplicateWarning = false;

            // Check for duplicate records if adding a new student
            if (ActiveTask.equals("Add New Student")) {
                String checkQuery = suffix.equals("n/a") ? 
                    "SELECT COUNT(*) FROM students WHERE first_name = ? AND middle_name = ? AND last_name = ? AND suffix IS NULL" : 
                    "SELECT COUNT(*) FROM students WHERE first_name = ? AND middle_name = ? AND last_name = ? AND suffix = ?";

                try (java.sql.Connection conn1 = DatabaseConnection.connect();
                    PreparedStatement pstmtCheck1 = conn1.prepareStatement(checkQuery)) {

                    pstmtCheck1.setString(1, fName);
                    pstmtCheck1.setString(2, mName);
                    pstmtCheck1.setString(3, lName);

                    if (!suffix.equals("n/a")) {
                        pstmtCheck1.setString(4, suffix);
                    }

                    ResultSet rsCheck = pstmtCheck1.executeQuery();
                    if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                        duplicateWarning = true;
                        System.out.println("I AM IN ADD NEW STUDENT");
                        JOptionPane.showMessageDialog(null,
                                "A student with the same name already exists.",
                                "Duplicate Record",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database error: " + e.getMessage());
                }

                // Check if student_id already exists
                String checkStudentIdQuery = "SELECT COUNT(*) FROM students WHERE student_id = ?";

                try (java.sql.Connection conn2 = DatabaseConnection.connect();
                    PreparedStatement pstmtCheckStudentId = conn2.prepareStatement(checkStudentIdQuery)) {
                    pstmtCheckStudentId.setString(1, studentId);
                    try (ResultSet rsCheckStudentId = pstmtCheckStudentId.executeQuery()) {
                        if (rsCheckStudentId.next() && rsCheckStudentId.getInt(1) > 0) {
                            duplicateWarning = true;
                            JOptionPane.showMessageDialog(null,
                                    "The student ID already exists.",
                                    "Duplicate Record",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database error: " + e.getMessage());
                }

                // Check if RFID already exists
                String checkRfidQuery = "SELECT COUNT(*) FROM students WHERE rfid = ?";
               try (java.sql.Connection conn3 = DatabaseConnection.connect();
                    PreparedStatement pstmtCheckRfid = conn3.prepareStatement(checkRfidQuery)) {
                    pstmtCheckRfid.setString(1, rfidCode);
                    try (ResultSet rsCheckRfid = pstmtCheckRfid.executeQuery()) {
                        if (rsCheckRfid.next() && rsCheckRfid.getInt(1) > 0) {
                            duplicateWarning = true;
                            JOptionPane.showMessageDialog(null,
                                    "The RFID already exists.",
                                    "Duplicate Record",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database error: " + e.getMessage());
                }
               
               
               
            //ActiveTask="Edit Student Record";  -------------------------------------------- 
            } else {
                duplicateWarning = false;
                // For editing, check for duplicates only in other records
                String checkQuery1 = suffix.equals("n/a") ? 
                    "SELECT COUNT(*) FROM students WHERE first_name = ? AND middle_name = ? AND last_name = ? AND suffix IS NULL AND record_id != ?" : 
                    "SELECT COUNT(*) FROM students WHERE first_name = ? AND middle_name = ? AND last_name = ? AND suffix = ? AND record_id != ?";

                try (java.sql.Connection conn4 = DatabaseConnection.connect();
                    PreparedStatement pstmtCheck1 = conn4.prepareStatement(checkQuery1)) {

                    pstmtCheck1.setString(1, fName);
                    pstmtCheck1.setString(2, mName);
                    pstmtCheck1.setString(3, lName);
                   
                    if (suffix.equals("n/a")) {
                        pstmtCheck1.setInt(4, _recordId);
                    } else {
                        pstmtCheck1.setString(4, suffix);
                        pstmtCheck1.setInt(5, _recordId);
                    }

                    ResultSet rsCheck = pstmtCheck1.executeQuery();
                    if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                        System.out.println("I AM IN EDIT STUDENT RECORD.");
                        System.out.println("Record_ID: "+_recordId);
                        duplicateWarning = true;
                        JOptionPane.showMessageDialog(null,
                                "A student with the same name already exists.",
                                "Duplicate Record",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database error: " + e.getMessage());
                }
                
                
                // Check for duplicate student_id in other records
                String checkStudentIdQuery = "SELECT COUNT(*) FROM students WHERE student_id = ? AND record_id != ?";
               try (java.sql.Connection conn5 = DatabaseConnection.connect();
                    PreparedStatement pstmtCheckStudentId = conn5.prepareStatement(checkStudentIdQuery)) {
                    pstmtCheckStudentId.setString(1, studentId);
                    pstmtCheckStudentId.setInt(2, _recordId);
                    try (ResultSet rsCheckStudentId = pstmtCheckStudentId.executeQuery()) {
                        if (rsCheckStudentId.next() && rsCheckStudentId.getInt(1) > 0) {
                            duplicateWarning = true;
                            JOptionPane.showMessageDialog(null,
                                    "The student ID already exists.",
                                    "Duplicate Record",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database error: " + e.getMessage());
                }

                // Check for duplicate RFID in other records
                String checkRfidQuery = "SELECT COUNT(*) FROM students WHERE rfid = ? AND record_id != ?";
                try (java.sql.Connection conn6 = DatabaseConnection.connect();
                     PreparedStatement pstmtCheckRfid = conn6.prepareStatement(checkRfidQuery)) {
                    pstmtCheckRfid.setString(1, rfidCode);
                    pstmtCheckRfid.setInt(2, _recordId);
                    try (ResultSet rsCheckRfid = pstmtCheckRfid.executeQuery()) {
                        if (rsCheckRfid.next() && rsCheckRfid.getInt(1) > 0) {
                            duplicateWarning = true;
                            JOptionPane.showMessageDialog(null,
                                    "The RFID already exists.",
                                    "Duplicate Record",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database error: " + e.getMessage());
                }
            }//end of duplicateWarning = false;----------------------------------

            
            
            if (!duplicateWarning) {
                int userResponse = JOptionPane.showConfirmDialog(
                   null, 
                   "<html><b>PLEASE REVIEW STUDENT DATA:</b><br><br>" +
                        "<b>First Name: </b>" + fName + "<br>" +
                        "<b>Middle Name: </b>" + mName + "<br>" +
                        "<b>Last Name: </b>" + lName + "<br>" +
                        "<b>Suffix: </b>" + suffix + "<br>" +
                        "<b>Curriculum: </b>" + currCode + "<br>" +
                        "<b>Program: </b>" + program + "<br>" +
                        "<b>Year Level: </b>" + yrLvl + "<br>" +
                        "<b>Block: </b>" + block + "<br>" +
                        "<b>Academic Status: </b>" + acadStatus + "<br>" +
                        "<b>Student ID: </b>" + studentId + "<br>" +
                        "<b>RFID Code: </b>" + rfidCode + "<br>" +
                        "<b>Kiosk Pin Code: </b>" + kioskPin + "</html>", 
                   "Student Record", 
                   JOptionPane.OK_CANCEL_OPTION);

                if (userResponse == JOptionPane.OK_OPTION) { 
                    System.out.println("User chose Yes."); 

                    String insertQuery;
                    if (ActiveTask.equals("Add New Student")) {
                        insertQuery = "INSERT INTO students ("
                            + "student_id, first_name, middle_name, last_name, suffix, rfid, pin, program_code, "
                            + "curriculum_code, current_year, current_block, current_tuition, status) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    } else {
                        insertQuery = "UPDATE students SET "
                            + "first_name = ?, middle_name = ?, last_name = ?, suffix = ?, rfid = ?, pin = ?, program_code = ?, "
                            + "curriculum_code = ?, current_year = ?, current_block = ?, current_tuition = ?, status = ? "
                            + "WHERE record_id = ?";
                    }

                    try (java.sql.Connection conn7 = DatabaseConnection.connect();
                         PreparedStatement pstmt = conn7.prepareStatement(insertQuery)) {
                        if (ActiveTask.equals("Add New Student")) {
                            pstmt.setString(1, studentId);
                            pstmt.setString(2, fName);
                            pstmt.setString(3, mName);
                            pstmt.setString(4, lName);
                            if (suffix.equals("n/a")) {
                                pstmt.setNull(5, java.sql.Types.VARCHAR);
                            } else {
                                pstmt.setString(5, suffix);
                            }
                            pstmt.setString(6, rfidCode);
                            pstmt.setString(7, kioskPin);
                            pstmt.setString(8, program);
                            pstmt.setString(9, currCode);
                            pstmt.setInt(10, yrLvl);
                            pstmt.setString(11, block);
                            pstmt.setDouble(12, 0); // Adjust as needed
                            pstmt.setString(13, acadStatus);
                        } else {
                            pstmt.setString(1, fName);
                            pstmt.setString(2, mName);
                            pstmt.setString(3, lName);
                            if (suffix.equals("n/a")) {
                                pstmt.setNull(4, java.sql.Types.VARCHAR);
                            } else {
                                pstmt.setString(4, suffix);
                            }
                            pstmt.setString(5, rfidCode);
                            pstmt.setString(6, kioskPin);
                            pstmt.setString(7, program);
                            pstmt.setString(8, currCode);
                            pstmt.setInt(9, yrLvl);
                            pstmt.setString(10, block);
                            pstmt.setDouble(11, 0); // Adjust as needed
                            pstmt.setString(12, acadStatus);
                            pstmt.setInt(13, _recordId);
                        }

                        // Execute the query
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            if (ActiveTask.equals("Add New Student")) {
                                System.out.println("A new student was inserted successfully!");
                                JOptionPane.showMessageDialog(null,
                                    "Student added successfully!",
                                    "Message",
                                    JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                System.out.println("Student record updated successfully!");
                                JOptionPane.showMessageDialog(null,
                                    "Student record updated successfully!",
                                    "Message",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }
                            txt_newFirstN.setText(null);
                            txt_newLastN.setText(null);
                            txt_newMiddleN.setText(null);
                            txt_newPin.setText(null);
                            txt_newRFID.setText(null);
                            txt_newStudentID.setText(null);
                            combo_acadStatus.setSelectedIndex(0);
                            combo_block.setSelectedIndex(0);
                            combo_curiculum.setSelectedIndex(0);
                            combo_program.setSelectedIndex(0);
                            combo_yearLevel.setSelectedIndex(0);
                            combo_suffix.setSelectedIndex(0);
                            check_noRFID.setSelected(false);

//                            TableTask = "Filtered Rows"; // Can be "Filtered Rows" or "View All Rows"   
                            refreshTable_UpdateList(); // Refresh the table data after insert/update
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Database error: " + e.getMessage());
                    }
                } 
            }
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void txt_newPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newPinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newPinActionPerformed

    private void btn_defaultTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_defaultTableActionPerformed
        TableTask = "View All Rows"; // Can be "Filtered Rows"
        refreshTable_UpdateList();
        txt_search.setText("");
        
    }//GEN-LAST:event_btn_defaultTableActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Clear;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_defaultTable;
    private javax.swing.JButton btn_search;
    private javax.swing.JCheckBox check_noRFID;
    private javax.swing.JComboBox<String> combo_acadStatus;
    private javax.swing.JComboBox<String> combo_block;
    private javax.swing.JComboBox<String> combo_curiculum;
    private javax.swing.JComboBox<String> combo_program;
    private javax.swing.JComboBox<String> combo_suffix;
    private javax.swing.JComboBox<String> combo_yearLevel;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel jlblCourse1;
    private javax.swing.JPanel pan_infoHolder;
    private javax.swing.JTextField txt_newFirstN;
    private javax.swing.JTextField txt_newLastN;
    private javax.swing.JTextField txt_newMiddleN;
    private javax.swing.JTextField txt_newPin;
    private javax.swing.JTextField txt_newRFID;
    private javax.swing.JTextField txt_newStudentID;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables

    

}
