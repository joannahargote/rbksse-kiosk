/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package windows_new.registrar;

import global.DatabaseConnection;
import global.SystemData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author USER
 */
public class grades extends javax.swing.JPanel {
    
    
    public grades() {
        initComponents();
        btn_newTASK.setEnabled(false);
        lbl_BIGsubject.setText("");
        pan_PARENT_1060x530.setBorder(null);
        pan_PARENT_1060x530.removeAll();
        _grades_page1 s = new _grades_page1(this);
        pan_PARENT_1060x530.add(s);
    }

    public void switchToPanel(){
        String display = SystemData.get_grade_cleanedSubject();
        lbl_BIGsubject.setText(display);
        pan_PARENT_1060x530.removeAll();
        _grades_page2 s = new _grades_page2();
        pan_PARENT_1060x530.add(s);
        pan_PARENT_1060x530.revalidate();
        pan_PARENT_1060x530.repaint();
        btn_newTASK.setEnabled(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jlblCourse1 = new javax.swing.JLabel();
        pan_PARENT_1060x530 = new javax.swing.JPanel();
        btn_newTASK = new javax.swing.JButton();
        lbl_BIGsubject = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1100, 630));
        setMinimumSize(new java.awt.Dimension(1100, 630));
        setPreferredSize(new java.awt.Dimension(1080, 630));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("RECORD GRADES");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 210, 50));

        jlblCourse1.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jlblCourse1.setForeground(new java.awt.Color(153, 153, 153));
        jlblCourse1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblCourse1.setText("RFID-Based Kiosk System for Student Evaluation _ ACI Capstone for MIT 2024 _ JCA");
        jlblCourse1.setToolTipText("");
        add(jlblCourse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 600, 380, 20));

        pan_PARENT_1060x530.setBackground(new java.awt.Color(255, 255, 255));
        pan_PARENT_1060x530.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pan_PARENT_1060x530.setLayout(new java.awt.BorderLayout());
        add(pan_PARENT_1060x530, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1060, 510));

        btn_newTASK.setBackground(new java.awt.Color(0, 51, 102));
        btn_newTASK.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_newTASK.setForeground(new java.awt.Color(255, 255, 255));
        btn_newTASK.setText("NEW TASK");
        btn_newTASK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newTASKActionPerformed(evt);
            }
        });
        add(btn_newTASK, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, 170, 40));

        lbl_BIGsubject.setBackground(new java.awt.Color(255, 255, 255));
        lbl_BIGsubject.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_BIGsubject.setForeground(new java.awt.Color(204, 0, 0));
        lbl_BIGsubject.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_BIGsubject.setText(":  \"...i am helping you\"ttttttttttttttttttttttttttttttqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        add(lbl_BIGsubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 890, 50));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_newTASKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newTASKActionPerformed
        btn_newTASK.setEnabled(false);
        pan_PARENT_1060x530.setBorder(null);
        pan_PARENT_1060x530.removeAll();
        _grades_page1 s = new _grades_page1(this);
        pan_PARENT_1060x530.add(s);
    }//GEN-LAST:event_btn_newTASKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_newTASK;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jlblCourse1;
    private javax.swing.JLabel lbl_BIGsubject;
    private javax.swing.JPanel pan_PARENT_1060x530;
    // End of variables declaration//GEN-END:variables
}
