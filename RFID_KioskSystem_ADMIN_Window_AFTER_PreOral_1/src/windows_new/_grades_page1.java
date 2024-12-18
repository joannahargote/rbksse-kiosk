/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package windows_new;

import global.DatabaseConnection;
import global.SystemData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class _grades_page1 extends javax.swing.JPanel {

    private _grades parent;
    
    public _grades_page1(_grades parent) {
        this.parent = parent;
        
        initComponents();
        jPanel2.setVisible(false);
        
//        pan_OldGradesYES.setVisible(false);
        
        btn_OKcomboSubject.setBounds(190, 170, 250, 40);

        loadPrograms();
        loadCurriculumCodes();
    }

    private void loadPrograms() {
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
    
    private void loadCurriculumCodes() {
        // Remove the border from the panel

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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        combo_curiculum = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        combo_program = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        combo_yearLevel = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        combo_semester = new javax.swing.JComboBox<>();
        btn_OKcomboPYBS = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        combo_subject = new javax.swing.JComboBox<>();
        btn_OKcomboSubject = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1060, 530));
        setMinimumSize(new java.awt.Dimension(1060, 530));
        setPreferredSize(new java.awt.Dimension(1060, 530));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combo_curiculum.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(combo_curiculum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 280, 40));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Curriculum Code");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, 20));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Program");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 100, 20));

        jPanel1.add(combo_program, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 160, 40));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Year Level");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 80, 20));

        combo_yearLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4" }));
        jPanel1.add(combo_yearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 120, 40));

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Semester");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 80, 20));

        combo_semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2" }));
        jPanel1.add(combo_semester, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 120, 40));

        btn_OKcomboPYBS.setBackground(new java.awt.Color(0, 51, 102));
        btn_OKcomboPYBS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_OKcomboPYBS.setForeground(new java.awt.Color(255, 255, 255));
        btn_OKcomboPYBS.setText("OK");
        btn_OKcomboPYBS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKcomboPYBSActionPerformed(evt);
            }
        });
        jPanel1.add(btn_OKcomboPYBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 200, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("FILTER SUBJECT GROUP:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, 20));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 530));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Select Subject/Course");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 150, 20));

        jPanel2.add(combo_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 530, 40));

        btn_OKcomboSubject.setBackground(new java.awt.Color(0, 51, 102));
        btn_OKcomboSubject.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_OKcomboSubject.setForeground(new java.awt.Color(255, 255, 255));
        btn_OKcomboSubject.setText("OK");
        btn_OKcomboSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKcomboSubjectActionPerformed(evt);
            }
        });
        jPanel2.add(btn_OKcomboSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 250, 40));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 620, 400));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_OKcomboPYBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKcomboPYBSActionPerformed
//        System.out.println(combo_curiculum.getSelectedItem().toString());
        String curriculum = combo_curiculum.getSelectedItem().toString();
        String program = combo_program.getSelectedItem().toString();
//        String block = combo_block.getSelectedItem().toString();
        int yrLvl = 0, count = 0;
        String semester = combo_semester.getSelectedItem().toString();
        List<Integer> subjectIds = new ArrayList<>(); // List to store subject IDs

        String inputError = "<html><b>TASK DISABLED DUE TO MISSING DATA:</b><br><br>";

        if (curriculum.equals("Select")) {
            count++;
            inputError += "Curriculum Code<br>";
        }
        if (program.equals("Select")) {
            count++;
            inputError += "Program <br>";
        }
        if (combo_yearLevel.getSelectedItem().toString().equals("Select")) {
            count++;
            inputError += "Year Level <br>";
            yrLvl = 0; // or default val
        } else {
            yrLvl = Integer.parseInt(combo_yearLevel.getSelectedItem().toString());
        }
//        if (block.equals("Select")) {
//            count++;
//            inputError += "Block <br>";
//        }
        if (semester.equals("Select")) {
            count++;
            inputError += "Semester <br>";
        }
        inputError += "</html>";

        if (count > 0) {
            JOptionPane.showMessageDialog(null,
                inputError,
                "Invalid Input.",
                JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            // Database query to fetch subjects
            String query = "SELECT subject_id, code, description " +
            "FROM subjects " +
            "WHERE program = ? " +
            "AND yearlvl = ? " +
            "AND semester = ?";

            try (java.sql.Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

                // Set the parameters for the query
                pstmt.setString(1, program);
                pstmt.setInt(2, yrLvl);
                pstmt.setString(3, semester);

                try (ResultSet rs = pstmt.executeQuery()) {
                    // Clear the JComboBox before adding items (optional)
                    combo_subject.removeAllItems();
                    combo_subject.addItem("Select");

                    // Populate the JComboBox with subject codes and descriptions
                    while (rs.next()) {
                        int subjectID = rs.getInt("subject_id");
                        subjectIds.add(subjectID);
                        String code = rs.getString("code");
                        String description = rs.getString("description");
                        combo_subject.addItem("<html><b>" + code + "</b> - " + description + "</html>");
                    }
                }

            } catch (SQLException e) {
                // Log and display the error
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "Error fetching subjects: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }

            // Now you can use the subjectIds list as needed
            for (int id : subjectIds) {
                System.out.println("Subject ID: " + id);
            }

            //----------------------------------------------
            
            jPanel2.setVisible(true);
            btn_OKcomboSubject.setBounds(190, 170, 250, 40);
            
            
            System.out.println(subjectIds);
        }
    }//GEN-LAST:event_btn_OKcomboPYBSActionPerformed

    private void btn_OKcomboSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKcomboSubjectActionPerformed
        String selected = combo_subject.getSelectedItem().toString();
        if(selected.contains("Select")){
            JOptionPane.showMessageDialog(null,
                "No subject/course selected.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }else{
            String subject = combo_subject.getSelectedItem().toString();
            // Clean up all HTML tags using a regex
            String cleanedSubject = subject.replaceAll("<[^>]*>", "").trim();
            SystemData.set_grade_cleanedSubject(cleanedSubject); 

            String[] parts = cleanedSubject.split(" - ");
            String subjectCode = parts[0].trim();
            String subjectDescrip = parts[1].trim();


            SystemData.set_grade_program(combo_program.getSelectedItem().toString());
            SystemData.set_grade_yearLvl(combo_yearLevel.getSelectedItem().toString());
    //        SystemData.set_grade_block(combo_block.getSelectedItem().toString());
            SystemData.set_grade_subCode(subjectCode);
            SystemData.set_grade_subDescrip(subjectDescrip);
            SystemData.set_grade_curriculumCode(combo_curiculum.getSelectedItem().toString());

    //        combo_block.setEnabled(false);
            combo_curiculum.setEnabled(false);
            combo_program.setEnabled(false);
            combo_semester.setEnabled(false);
            combo_subject.setEnabled(false);
            combo_yearLevel.setEnabled(false);
            btn_OKcomboPYBS.setEnabled(false);
            btn_OKcomboSubject.setEnabled(false);

            //       Start of conditions----------------------------------------------------
            if (parent != null) {
                parent.switchToPanel();
            } else {
                System.err.println("_grades : Parent reference is null!");
            }
        }
    }//GEN-LAST:event_btn_OKcomboSubjectActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_OKcomboPYBS;
    private javax.swing.JButton btn_OKcomboSubject;
    private javax.swing.JComboBox<String> combo_curiculum;
    private javax.swing.JComboBox<String> combo_program;
    private javax.swing.JComboBox<String> combo_semester;
    private javax.swing.JComboBox<String> combo_subject;
    private javax.swing.JComboBox<String> combo_yearLevel;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
