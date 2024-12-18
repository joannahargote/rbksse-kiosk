/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package windows_new;

import global.DatabaseConnection;
import global.SystemData;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.sax.SAXSource;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;


/**
 *
 * @author USER
 */
public class _grades_page2 extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private ArrayList<Object[]> studentList;
    private String studentID_;
    String acad_year=null;
    
    public _grades_page2() {
         initComponents();
         
         // Initialize table model
        tableModel = new DefaultTableModel(
                new Object[]{"Last Name", "First Name", "Middle Name", "Rating", "Remarks"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing only for the "Rating" (column 3) and "Remarks" (column 4)
                return column == 3 || column == 4;
            }
        };

        txt_oldRecordAcadYear.setVisible(false);
        lbl_oldRecordAcadYear.setVisible(false);
        btn_cancel.setEnabled(false);
        btn_clear.setEnabled(false);
        btn_save.setEnabled(false);
        txt_newGrade.setEnabled(false);
        combo_remark.setEnabled(false);
        check_oldRecord.setEnabled(false);
        // Set table model
        jTable1.setModel(tableModel);
        jTable1.setBackground(Color.WHITE);
        jTable1.setGridColor(Color.BLACK); // Set grid line color
        jTable1.getTableHeader().setBackground(Color.LIGHT_GRAY); // Header background color
        jTable1.setRowHeight(30);

        // Initialize studentList
        studentList = new ArrayList<>();

        // Add MouseListener to jTable1
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    // Get the corresponding data from studentList
                    Object[] selectedStudent = studentList.get(row);

                    // Extract relevant details
                    String lastName = (String) selectedStudent[0];
                    String firstName = (String) selectedStudent[1];
                    String middleName = (String) selectedStudent[2];
                    String status = (String) selectedStudent[6];
                    String program = SystemData.get_grade_program();
                    String yearBlock = SystemData.get_grade_yearLvl() + "-" + combo_block.getSelectedItem().toString();
                    studentID_=(String) selectedStudent[5]; //kaipuhan sa updating grades

                    // Update labels
                    lbl_seeStudentName.setText(
                            lastName + ", " + firstName + " " + (middleName != null && !middleName.isEmpty() ? middleName.charAt(0) + "." : ""));
                    lbl_seeProgYrBlk.setText(program + " " + yearBlock);
                    lbl_seeStatus.setText(status);
//                    lbl_seeSubject.setText(subjectCode);
                    btn_cancel.setEnabled(true);
                    btn_clear.setEnabled(true);
                    btn_save.setEnabled(true);
                    txt_newGrade.setEnabled(true);
                    combo_remark.setEnabled(true);
                    check_oldRecord.setEnabled(true);
                }
            }
        });

        // Other setup code
        pan_infoHolder.setBorder(null);
        String display = SystemData.get_grade_subCode() + " - " + SystemData.get_grade_subDescrip();

        String query = "SELECT current_acadYear FROM system_status";

        try (java.sql.Connection conn = DatabaseConnection.connect();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                acad_year = rs.getString("current_acadYear");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        loadBatches();
    }
         
 
    


    private void loadBatches(){
        String program = SystemData.get_grade_program(); 
        String curriculum = SystemData.get_grade_curriculumCode();
        combo_batches.addItem("Select Student Batch");
        combo_block.addItem("Select Block"); 
        
        String query = "SELECT student_id, current_year, current_block FROM students WHERE program_code = ? AND curriculum_code = ?"; 
        Map<String, Integer> batchYears = new HashMap<>();
        Set<String> blocks = new HashSet<>(); // To store unique current_block values

        try (java.sql.Connection conn = DatabaseConnection.connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameters for the query
            pstmt.setString(1, program);
            pstmt.setString(2, curriculum);

            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String studentId = rs.getString("student_id");
                    int currentYear = rs.getInt("current_year");
                    String currentBlock = rs.getString("current_block");

                    // Extract the last two digits of the student ID
                    if (studentId.length() >= 2) {
                        String batchYear = studentId.substring(studentId.length() - 2);
                        batchYears.put(batchYear, currentYear);
                    }

                    // Add current_block to the set (ensures uniqueness)
                    if (currentBlock != null && !currentBlock.isEmpty()) {
                        blocks.add(currentBlock);
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        // Create a list from the map's keys (for batches) and sort it
        List<String> sortedBatches = new ArrayList<>(batchYears.keySet());
        Collections.sort(sortedBatches);

        // Populate the combo_batches JComboBox with the sorted batch entries
        for (String batch : sortedBatches) {
            int year = batchYears.get(batch);
            String yearLabel = year + getYearSuffix(year);
            combo_batches.addItem(program + " (Batch 20" + batch + ") - Currently " + yearLabel);
        }

        // Populate the combo_block JComboBox with the unique current_block entries
        for (String block : blocks) {
            combo_block.addItem(block);
        }
    }
    
    
// Helper method to get the suffix for the year
    private String getYearSuffix(int year) {
    return switch (year) {
            case 1 -> "st Yr";
            case 2 -> "nd Yr";
            case 3 -> "rd Yr";
            default -> "th Yr";
        };
    }
        
    
    String programCode = SystemData.get_grade_program();
    String curriculumCode = SystemData.get_grade_curriculumCode();
//    int currentYear = Integer.valueOf(SystemData.get_grade_yearLvl());
  
    private void loadTable() {
    // Clear existing rows from the table and the student list
    tableModel.setRowCount(0);
    studentList.clear();

    String programCode = SystemData.get_grade_program();
    String curriculumCode = SystemData.get_grade_curriculumCode();
    String subjectCode = SystemData.get_grade_subCode(); // Get the exclusive subject code
    String yearNumber = "";

    // Regular expression to extract just the number (e.g., "3", "4", "1")
    String regex = "Currently\\s(\\d+)\\w{2}\\sYr";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(combo_batches.getSelectedItem().toString());
    if (matcher.find()) {
        yearNumber = matcher.group(1); // Extract the matched number
        System.out.println("Extracted Number: " + yearNumber);
    } else {
        System.out.println("No number found in: " + combo_batches.getSelectedItem().toString());
    }

    int currentYear = Integer.valueOf(yearNumber);
    String block = combo_block.getSelectedItem().toString(); // Ensure you use the selected block

    String query = "SELECT student_id, first_name, middle_name, last_name, suffix, status " +
                   "FROM students " +
                   "WHERE program_code = ? " +
                   "AND curriculum_code = ? " +
                   "AND current_year = ? " +
                   "AND current_block = ? " +
                   "ORDER BY last_name ASC, first_name ASC, middle_name ASC";

    try (java.sql.Connection conn = DatabaseConnection.connect();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {

        // Set query parameters
        pstmt.setString(1, programCode);
        pstmt.setString(2, curriculumCode);
        pstmt.setInt(3, currentYear);
        pstmt.setString(4, block);

        try (java.sql.ResultSet rs = pstmt.executeQuery()) {
            // Populate the table model and the student list
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String suffix = rs.getString("suffix") == null ? "" : rs.getString("suffix");
                String status = rs.getString("status");

                // Query for the grades of this student for the specific subject code
                String gradesQuery = "SELECT subject_code, rating, remark " +
                                     "FROM grades " +
                                     "WHERE student_id = ? " +
                                     "AND subject_code = ?";
                try (java.sql.PreparedStatement gradesPstmt = conn.prepareStatement(gradesQuery)) {
                    gradesPstmt.setString(1, studentId);
                    gradesPstmt.setString(2, subjectCode);
                    try (java.sql.ResultSet gradesRs = gradesPstmt.executeQuery()) {
                        // Initialize default values for rating and remark
                        String rating = "";
                        String remark = "";

                        if (gradesRs.next()) {
                            // Fetch rating and remark, handle null values
                            rating = gradesRs.getString("rating") != null ? gradesRs.getString("rating") : "";
                            remark = gradesRs.getString("remark") != null ? gradesRs.getString("remark") : "";
                        }

                        // Prepare row data for the table and the student list
                        Object[] rowTable = {
                            lastName,
                            firstName,
                            middleName,
                            rating,
                            remark
                        };
                        tableModel.addRow(rowTable);
                        Object[] row = {
                            lastName,
                            firstName,
                            middleName,
                            subjectCode,
                            rating,
                            studentId,
                            status,
                            remark
                        };
                        studentList.add(row); // Keep full row in studentList for mouse click listener
                    }
                }
            }
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Check if the table is empty and show a warning if needed
    if (tableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null,
                "No data found for the selected group.",
                "No Data",
                JOptionPane.WARNING_MESSAGE);
    }
}

    
//    private void loadTable() {
//    // Clear existing rows from the table and the student list
//    tableModel.setRowCount(0);
//    studentList.clear();
//
//    String programCode = SystemData.get_grade_program();
//    String curriculumCode = SystemData.get_grade_curriculumCode();
//    String subjectCode = SystemData.get_grade_subCode(); // Get the exclusive subject code
//    String yearNumber = "";
//
//    // Regular expression to extract just the number (e.g., "3", "4", "1")
//    String regex = "Currently\\s(\\d+)\\w{2}\\sYr";
//    Pattern pattern = Pattern.compile(regex);
//    Matcher matcher = pattern.matcher(combo_batches.getSelectedItem().toString());
//    if (matcher.find()) {
//        yearNumber = matcher.group(1); // Extract the matched number
//        System.out.println("Extracted Number: " + yearNumber);
//    } else {
//        System.out.println("No number found in: " + combo_batches.getSelectedItem().toString());
//    }
//
//    int currentYear = Integer.valueOf(yearNumber);
//    String block = combo_block.getSelectedItem().toString(); // Ensure you use the selected block
//
//    String query = "SELECT student_id, first_name, middle_name, last_name, suffix, status " +
//                   "FROM students " +
//                   "WHERE program_code = ? " +
//                   "AND curriculum_code = ? " +
//                   "AND current_year = ? " +
//                   "AND current_block = ? " +
//                   "ORDER BY last_name ASC, first_name ASC, middle_name ASC";
//
//    try (java.sql.Connection conn = DatabaseConnection.connect();
//         java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//        // Set query parameters
//        pstmt.setString(1, programCode);
//        pstmt.setString(2, curriculumCode);
//        pstmt.setInt(3, currentYear);
//        pstmt.setString(4, block);
//
//        try (java.sql.ResultSet rs = pstmt.executeQuery()) {
//            // Populate the table model and the student list
//            while (rs.next()) {
//                String studentId = rs.getString("student_id");
//                String firstName = rs.getString("first_name");
//                String middleName = rs.getString("middle_name");
//                String lastName = rs.getString("last_name");
//                String suffix = rs.getString("suffix") == null ? "" : rs.getString("suffix");
//                String status = rs.getString("status");
//
//                // Query for the grades of this student for the specific subject code
//                String gradesQuery = "SELECT subject_code, rating, remark " +
//                                     "FROM grades " +
//                                     "WHERE student_id = ? " +
//                                     "AND subject_code = ?";
//                try (java.sql.PreparedStatement gradesPstmt = conn.prepareStatement(gradesQuery)) {
//                    gradesPstmt.setString(1, studentId);
//                    gradesPstmt.setString(2, subjectCode);
//                    try (java.sql.ResultSet gradesRs = gradesPstmt.executeQuery()) {
//                        while (gradesRs.next()) {
//                            String rating = gradesRs.getString("rating");
//                            String remark = gradesRs.getString("remark");
//
//                            // Prepare row data for the table and the student list
//                            Object[] row = {
//                                lastName,
//                                firstName,
//                                middleName,
//                                subjectCode,
//                                rating,
//                                studentId,
//                                status,
//                                remark
//                            };
//                            tableModel.addRow(row);
//                            studentList.add(row); // Keep full row in studentList for mouse click listener
//                        }
//                    }
//                }
//            }
//        }
//
//    } catch (java.sql.SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }
//
//    // Check if the table is empty and show a warning if needed
//    if (tableModel.getRowCount() == 0) {
//        JOptionPane.showMessageDialog(null,
//                "No data found for the selected group.",
//                "No Data",
//                JOptionPane.WARNING_MESSAGE);
//    }
//}

    
    
//    private void loadTable() {
//    // Clear existing rows from the table and the student list
//    tableModel.setRowCount(0);
//    studentList.clear();
//
//    String programCode = SystemData.get_grade_program();
//    String curriculumCode = SystemData.get_grade_curriculumCode();
//
//    String yearNumber = "";
//
//    // Regular expression to extract just the number (e.g., "3", "4", "1")
//    String regex = "Currently\\s(\\d+)\\w{2}\\sYr";
//    Pattern pattern = Pattern.compile(regex);
//    Matcher matcher = pattern.matcher(combo_batches.getSelectedItem().toString());
//    if (matcher.find()) {
//        yearNumber = matcher.group(1); // Extract the matched number
//        System.out.println("Extracted Number: " + yearNumber);
//    } else {
//        System.out.println("No number found in: " + combo_batches.getSelectedItem().toString());
//    }
//
//    int currentYear = Integer.valueOf(yearNumber);
//    String block = combo_block.getSelectedItem().toString(); // Ensure you use the selected block
//
//    String query = "SELECT student_id, first_name, middle_name, last_name, suffix, status " +
//                   "FROM students " +
//                   "WHERE program_code = ? " +
//                   "AND curriculum_code = ? " +
//                   "AND current_year = ? " +
//                   "AND current_block = ? " +
//                   "ORDER BY last_name ASC, first_name ASC, middle_name ASC";
//
//    try (java.sql.Connection conn = DatabaseConnection.connect();
//         java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//        // Set query parameters
//        pstmt.setString(1, programCode);
//        pstmt.setString(2, curriculumCode);
//        pstmt.setInt(3, currentYear);
//        pstmt.setString(4, block);
//
//        try (java.sql.ResultSet rs = pstmt.executeQuery()) {
//            // Populate the table model and the student list
//            while (rs.next()) {
//                String studentId = rs.getString("student_id");
//                String firstName = rs.getString("first_name");
//                String middleName = rs.getString("middle_name");
//                String lastName = rs.getString("last_name");
//                String suffix = rs.getString("suffix") == null ? "" : rs.getString("suffix");
//                String status = rs.getString("status");
//
//                // Query for the grades of this student
//                String gradesQuery = "SELECT subject_code, rating, remark " +
//                                     "FROM grades " +
//                                     "WHERE student_id = ?";
//                try (java.sql.PreparedStatement gradesPstmt = conn.prepareStatement(gradesQuery)) {
//                    gradesPstmt.setString(1, studentId);
//                    try (java.sql.ResultSet gradesRs = gradesPstmt.executeQuery()) {
//                        while (gradesRs.next()) {
//                            String subjectCode = gradesRs.getString("subject_code");
//                            String rating = gradesRs.getString("rating");
//                            String remark = gradesRs.getString("remark");
//
//                            // Prepare row data for the table and the student list
//                            Object[] row = {
//                                lastName,
//                                firstName,
//                                middleName,
//                                subjectCode,
//                                rating,
//                                studentId,
//                                status,
//                                remark
//                            };
//                            tableModel.addRow(row);
//                            studentList.add(row); // Keep full row in studentList for mouse click listener
//                        }
//                    }
//                }
//            }
//        }
//
//    } catch (java.sql.SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }
//
//    // Check if the table is empty and show a warning if needed
//    if (tableModel.getRowCount() == 0) {
//        JOptionPane.showMessageDialog(null,
//                "No data found for the selected group.",
//                "No Data",
//                JOptionPane.WARNING_MESSAGE);
//    }
//}

    
    
//    private void loadTable(){
//            
//    // Clear existing rows from the table and the student list
//    tableModel.setRowCount(0);
//    studentList.clear();
//
//    String programCode = SystemData.get_grade_program();
//    String curriculumCode = SystemData.get_grade_curriculumCode();
//    
//    String yearNumber="";
////    int currentYear = Integer.parseInt(SystemData.get_grade_yearLvl());//THISSSSSSSSSSSSSSSSS
//    
//    // Regular expression to extract just the number (e.g., "3", "4", "1")
//        String regex = "Currently\\s(\\d+)\\w{2}\\sYr";
//        Pattern pattern = Pattern.compile(regex);
//        
//        Matcher matcher = pattern.matcher(combo_batches.getSelectedItem().toString());
//        if (matcher.find()) {
//            yearNumber = matcher.group(1); // Extract the matched number
//            System.out.println("Extracted Number: " + yearNumber);
//        } else {
//            System.out.println("No number found in: " + combo_batches.getSelectedItem().toString());
//        }
//        
//    
//    int currentYear = Integer.valueOf(yearNumber);
//    
//    
//    String block = combo_block.getSelectedItem().toString(); // Ensure you use the selected block
//
//    String query = "SELECT student_id, first_name, middle_name, last_name, suffix, status " +
//                   "FROM students " +
//                   "WHERE program_code = ? " +
//                   "AND curriculum_code = ? " +
//                   "AND current_year = ? " +
//                   "AND current_block = ? " +
//                   "ORDER BY last_name ASC, first_name ASC, middle_name ASC";
//
//    try (java.sql.Connection conn = DatabaseConnection.connect();
//         java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//        // Set query parameters
//        pstmt.setString(1, programCode);
//        pstmt.setString(2, curriculumCode);
//        pstmt.setInt(3, currentYear);
//        pstmt.setString(4, block);
//
//        System.out.println("Program Code: " + programCode);
//        System.out.println("Curriculum Code: " + curriculumCode);
//        System.out.println("Current Year: " + currentYear);
//        System.out.println("Block: " + block);
//        
//        try (java.sql.ResultSet rs = pstmt.executeQuery()) {
//            // Populate the table model and the student list
//            while (rs.next()) {
//                String studentId = rs.getString("student_id");
//                String firstName = rs.getString("first_name");
//                String middleName = rs.getString("middle_name");
//                String lastName = rs.getString("last_name");
//                String suffix = rs.getString("suffix") == null ? "" : rs.getString("suffix");
//                String status = rs.getString("status");
//
//                // Prepare row data for the table and the student list
//                Object[] row = {lastName, firstName, middleName, "", "", studentId, status};
//                tableModel.addRow(row);
//                studentList.add(row); // Keep full row in studentList for mouse click listener
//            }
//        }
//
//    } catch (java.sql.SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }
//
//    // Check if the table is empty and show a warning if needed
//    if (tableModel.getRowCount() == 0) {
//        JOptionPane.showMessageDialog(null,
//                "No data found for the selected group.",
//                "No Data",
//                JOptionPane.WARNING_MESSAGE);
//        }
//    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pan_infoHolder = new javax.swing.JPanel();
        lbl_seeStudentName = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_newGrade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        combo_remark = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_seeProgYrBlk = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_seeStatus = new javax.swing.JLabel();
        check_oldRecord = new javax.swing.JCheckBox();
        txt_oldRecordAcadYear = new javax.swing.JTextField();
        lbl_oldRecordAcadYear = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        combo_block = new javax.swing.JComboBox<>();
        combo_batches = new javax.swing.JComboBox<>();
        btn_OKfilterStudentGroup = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1060, 530));
        setMinimumSize(new java.awt.Dimension(1060, 530));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Last Name", "First Name", "Middle Name", "Rating", "Remark"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        jTable1.setShowGrid(false);
        jTable1.setShowHorizontalLines(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 640, 510));

        pan_infoHolder.setBackground(new java.awt.Color(255, 255, 255));
        pan_infoHolder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pan_infoHolder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_seeStudentName.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbl_seeStudentName.setText("-");
        lbl_seeStudentName.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pan_infoHolder.add(lbl_seeStudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 370, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 0));
        jLabel13.setText("Grade/Rating");
        pan_infoHolder.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 110, 20));

        txt_newGrade.setBackground(new java.awt.Color(255, 255, 255));
        txt_newGrade.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_newGradeActionPerformed(evt);
            }
        });
        pan_infoHolder.add(txt_newGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 170, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 0));
        jLabel10.setText("Remark");
        pan_infoHolder.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 110, 20));

        combo_remark.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Passed", "Incomplete", "Failed", "Dropped" }));
        pan_infoHolder.add(combo_remark, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 180, 40));

        jLabel11.setText("Student");
        pan_infoHolder.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 20));

        jLabel18.setText("Program/Year/Block");
        pan_infoHolder.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 130, 20));

        lbl_seeProgYrBlk.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbl_seeProgYrBlk.setText("-");
        lbl_seeProgYrBlk.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pan_infoHolder.add(lbl_seeProgYrBlk, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 120, 30));

        jLabel20.setText("Status");
        pan_infoHolder.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 130, 20));

        lbl_seeStatus.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbl_seeStatus.setText("-");
        lbl_seeStatus.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pan_infoHolder.add(lbl_seeStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 120, 30));

        check_oldRecord.setText("Old Record");
        check_oldRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_oldRecordActionPerformed(evt);
            }
        });
        pan_infoHolder.add(check_oldRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 30));

        txt_oldRecordAcadYear.setText("2024-2025");
        pan_infoHolder.add(txt_oldRecordAcadYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 150, 30));

        lbl_oldRecordAcadYear.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbl_oldRecordAcadYear.setForeground(new java.awt.Color(204, 0, 51));
        lbl_oldRecordAcadYear.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_oldRecordAcadYear.setText("AY Format: 2024-2025");
        pan_infoHolder.add(lbl_oldRecordAcadYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 190, 20));

        btn_clear.setBackground(new java.awt.Color(0, 51, 102));
        btn_clear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear.setText("CLEAR");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        pan_infoHolder.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 110, 40));

        btn_save.setBackground(new java.awt.Color(0, 51, 102));
        btn_save.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("SAVE");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        pan_infoHolder.add(btn_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 110, 40));

        btn_cancel.setBackground(new java.awt.Color(0, 51, 102));
        btn_cancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setText("CANCEL");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });
        pan_infoHolder.add(btn_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 110, 40));

        add(pan_infoHolder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 175, 410, 320));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.add(combo_block, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 110, 30));

        jPanel1.add(combo_batches, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 30));

        btn_OKfilterStudentGroup.setBackground(new java.awt.Color(0, 51, 102));
        btn_OKfilterStudentGroup.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_OKfilterStudentGroup.setForeground(new java.awt.Color(255, 255, 255));
        btn_OKfilterStudentGroup.setText("OK");
        btn_OKfilterStudentGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKfilterStudentGroupActionPerformed(evt);
            }
        });
        jPanel1.add(btn_OKfilterStudentGroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 90, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("FILTER STUDENT GROUP:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 170, 40));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 120));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("STUDENT DATA:");
        jPanel2.add(jLabel8);

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 410, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_newGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newGradeActionPerformed
        
    }//GEN-LAST:event_txt_newGradeActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        txt_newGrade.setText("");
        combo_remark.setSelectedIndex(0);
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_OKfilterStudentGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKfilterStudentGroupActionPerformed

        String selectedItem = combo_batches.getSelectedItem().toString();
        String block = combo_block.getSelectedItem().toString();
        int count = 0;

        StringBuilder inputError = new StringBuilder("<html><b>TASK DISABLED DUE TO MISSING DATA:</b><br><br>");
        if (selectedItem.equals("Select Student Batch")) {
            count++;
            inputError.append("Student batch<br>");
        }
        if (block.equals("Select Block")) {
            count++;
            inputError.append("Block <br>");
        }
        inputError.append("</html>");

        if (count > 0) {
            JOptionPane.showMessageDialog(null,
                    inputError.toString(),
                    "Invalid Input.",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            // Fetch and populate student data
            loadTable();
        }

    }//GEN-LAST:event_btn_OKfilterStudentGroupActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        btn_OKfilterStudentGroupActionPerformed(null);
        combo_batches.setSelectedIndex(0);
        combo_block.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        lbl_seeProgYrBlk.setText("-");
        lbl_seeStatus.setText("-");
        lbl_seeStudentName.setText("-");
        btn_cancel.setEnabled(false);
        btn_clear.setEnabled(false);
        btn_save.setEnabled(false);
        txt_newGrade.setEnabled(false);
        combo_remark.setEnabled(false);
        check_oldRecord.setEnabled(false);
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

       // Add to database
        System.out.println(studentID_);
        System.out.println(acad_year); // if old record, set here
        System.out.println(SystemData.get_grade_subCode());

        // INGREDIENTS
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        System.out.println(formattedNow);

        String newGrade = txt_newGrade.getText();
        String newRemark = (String) combo_remark.getSelectedItem();

        double grade = 0.0;
        try {
            grade = Double.parseDouble(newGrade);

            if (grade > 100) {
                JOptionPane.showMessageDialog(null,
                    "Number exceeds limit.",
                    "Invalid Input.",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                if (newRemark.equals("Select")) {
                    JOptionPane.showMessageDialog(null,
                        "No selected remark.",
                        "Invalid Input.",
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    if (newRemark.equals("Incomplete") || newRemark.equals("Failed")) {
                        newRemark = newRemark.toUpperCase();
                    }

                    // Check if the grade already exists in the database for this student and subject
                    String checkQuery = "SELECT record_id FROM grades WHERE student_id = ? AND subject_code = ? AND acad_year = ?";
                    int existingRecordId = -1;
                    try (Connection conn = DatabaseConnection.connect();
                         PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {

                        pstmt.setString(1, studentID_);
                        pstmt.setString(2, SystemData.get_grade_subCode());
                        pstmt.setString(3, acad_year);

                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                existingRecordId = rs.getInt("record_id");
                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null,
                            "Database error while checking existing grade: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // If the grade exists, ask the user if they want to overwrite
                    if (existingRecordId != -1) {
                        int choice = JOptionPane.showConfirmDialog(
                            null,
                            "This student already has a grade for this subject. Do you want to overwrite it?",
                            "Overwrite Grade",
                            JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.NO_OPTION) {
                            return; // Abort the operation if the user does not want to overwrite
                        } else {
                            // Create a password field
                            JPasswordField passwordField = new JPasswordField(10);
                            JPanel panel = new JPanel();
                            panel.add(new JLabel("Enter registrar password:"));
                            panel.add(passwordField);

                            int option = JOptionPane.showConfirmDialog(
                                null,
                                panel,
                                "Password Authentication",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);

                            if (option == JOptionPane.OK_OPTION) {
                                char[] passwordArray = passwordField.getPassword();
                                String password = new String(passwordArray);

                                // Password validation
                                if (password == null || !password.equals(SystemData.get_login_password())) {
                                    JOptionPane.showMessageDialog(null, "Incorrect password. Grade not updated.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return; // Exit if password is incorrect
                                }

                                // Update the existing grade record after successful password validation
                                String updateQuery = "UPDATE grades SET rating = ?, remark = ?, uploaded_when = ? WHERE record_id = ?";

                                try (Connection conn = DatabaseConnection.connect();
                                     PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                                    pstmt.setDouble(1, grade);
                                    pstmt.setString(2, newRemark);
                                    pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(formattedNow));
                                    pstmt.setInt(4, existingRecordId);

                                    int rowsUpdated = pstmt.executeUpdate();

                                    if (rowsUpdated > 0) {
                                        JOptionPane.showMessageDialog(null,
                                            "Grade updated successfully.",
                                            "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                            "Failed to update the grade.",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (SQLException e) {
                                    JOptionPane.showMessageDialog(null,
                                        "Database error while updating grade: " + e.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                }
                                loadTable(); // Refresh the table to show updated data
                                return; // Exit after updating the grade
                            }
                        }
                    } else {
                        // No existing grade, insert the new grade
                        String insertQuery = "INSERT INTO grades (student_id, acad_year, subject_code, rating, remark, uploaded_when) VALUES (?, ?, ?, ?, ?, ?)";

                        try (Connection conn = DatabaseConnection.connect();
                             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

                            pstmt.setString(1, studentID_);
                            pstmt.setString(2, acad_year);
                            pstmt.setString(3, SystemData.get_grade_subCode());
                            pstmt.setDouble(4, grade);
                            pstmt.setString(5, newRemark);
                            pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(formattedNow));

                            int rowsInserted = pstmt.executeUpdate();

                            if (rowsInserted > 0) {
                                JOptionPane.showMessageDialog(null,
                                    "Grade added successfully.",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null,
                                    "Failed to add the grade.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null,
                                "Database error while adding grade: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                        loadTable(); // Refresh the table to show new data
                    }
                }
            }
        } catch (NumberFormatException e) {
            if (lbl_seeStudentName.getText().equals("-")) {
                JOptionPane.showMessageDialog(null,
                    "No selected kiosk alert.",
                    "Invalid Input.",
                    JOptionPane.WARNING_MESSAGE);
            } else if (newGrade.length() == 0) {
                JOptionPane.showMessageDialog(null,
                    "Updated Grade is blank.",
                    "Invalid Input.",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Updated Grade is not a valid number.",
                    "Invalid Input.",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
        
        
        
        jTable1.clearSelection();

    }//GEN-LAST:event_btn_saveActionPerformed

    private void check_oldRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_oldRecordActionPerformed
        if (check_oldRecord.isSelected()) {
            txt_oldRecordAcadYear.setVisible(true);
            lbl_oldRecordAcadYear.setVisible(true);
        }else{
            txt_oldRecordAcadYear.setVisible(false);
            lbl_oldRecordAcadYear.setVisible(false);
        }
    }//GEN-LAST:event_check_oldRecordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_OKfilterStudentGroup;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_save;
    private javax.swing.JCheckBox check_oldRecord;
    private javax.swing.JComboBox<String> combo_batches;
    private javax.swing.JComboBox<String> combo_block;
    private javax.swing.JComboBox<String> combo_remark;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_oldRecordAcadYear;
    private javax.swing.JLabel lbl_seeProgYrBlk;
    private javax.swing.JLabel lbl_seeStatus;
    private javax.swing.JLabel lbl_seeStudentName;
    private javax.swing.JPanel pan_infoHolder;
    private javax.swing.JTextField txt_newGrade;
    private javax.swing.JTextField txt_oldRecordAcadYear;
    // End of variables declaration//GEN-END:variables
}
