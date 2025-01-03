/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package windows;

import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author USER
 */
public class _Notifications extends javax.swing.JFrame {

    private JLabel[] notificationLabels;
    private JLabel[] dateLabels;
    
    public _Notifications() {
        initComponents();
        
        // Initialize the instance variables directly
        notificationLabels = new JLabel[] {
            jlblNotification1, jlblNotification2, jlblNotification3, jlblNotification4, jlblNotification5,
            jlblNotification6, jlblNotification7, jlblNotification8, jlblNotification9, jlblNotification10,
            jlblNotification11, jlblNotification12, jlblNotification13, jlblNotification14, jlblNotification15
        };

        dateLabels = new JLabel[] {
            jlblDate1, jlblDate2, jlblDate3, jlblDate4, jlblDate5,
            jlblDate6, jlblDate7, jlblDate8, jlblDate9, jlblDate10,
            jlblDate11, jlblDate12, jlblDate13, jlblDate14, jlblDate15
        };
    }
    
    // Getter methods to access the labels from other classes
    public JLabel[] getNotificationLabels() {
        return notificationLabels;
    }

    public JLabel[] getDateLabels() {
        return dateLabels;
    }
    
    public void hideBoxes(int count){
        for (int a = 0; a <= 15; a++) {
            if(a>count){
                switch (a) {
                    case 1:
                        jPanel1.setVisible(false);
                        break;
                    case 2:
                        jPanel2.setVisible(false);
                        break;
                    case 3:
                        jPanel3.setVisible(false);
                        break;
                    case 4:
                        jPanel4.setVisible(false);
                        break;
                    case 5:
                        jPanel5.setVisible(false);
                        break;
                    case 6:
                        jPanel6.setVisible(false);
                        break;
                    case 7:
                        jPanel7.setVisible(false);
                        break;
                    case 8:
                        jPanel8.setVisible(false);
                        break;
                    case 9:
                        jPanel9.setVisible(false);
                        break;
                    case 10:
                        jPanel10.setVisible(false);
                        break;
                    case 11:
                        jPanel11.setVisible(false);
                        break;
                    case 12:
                        jPanel12.setVisible(false);
                        break;
                    case 13:
                        jPanel13.setVisible(false);
                        break;
                     case 14:
                        jPanel14.setVisible(false);
                        break;
                    case 15:
                        jPanel15.setVisible(false);
                        break;
                    default:
                        throw new AssertionError();
                }
            
            }
        }
    }
    
//    public void jPanelResize(int numberofNotif){
//        jPanelMain.setSize(495, numberofNotif*100);
//    }
    
    public void addNotif(String notif, Date date, int index){
    jlblNotification1.setText(notif);
    jlblDate1.setText(date.toString());
        
//    JLabel[] notificationLabels = {
//        jlblNotification1, jlblNotification2, jlblNotification3, jlblNotification4, jlblNotification5,
//        jlblNotification6, jlblNotification7, jlblNotification8, jlblNotification9, jlblNotification10,
//        jlblNotification11, jlblNotification12, jlblNotification13, jlblNotification14, jlblNotification15
//    };
//
//    JLabel[] dateLabels = {
//        jlblDate1, jlblDate2, jlblDate3, jlblDate4, jlblDate5,
//        jlblDate6, jlblDate7, jlblDate8, jlblDate9, jlblDate10,
//        jlblDate11, jlblDate12, jlblDate13, jlblDate14, jlblDate15
//    };
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jlblNotification1 = new javax.swing.JLabel();
        jlblDate1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlblNotification2 = new javax.swing.JLabel();
        jlblDate2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jlblNotification3 = new javax.swing.JLabel();
        jlblDate3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jlblNotification4 = new javax.swing.JLabel();
        jlblDate4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jlblNotification5 = new javax.swing.JLabel();
        jlblDate5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jlblNotification6 = new javax.swing.JLabel();
        jlblDate6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jlblNotification7 = new javax.swing.JLabel();
        jlblDate7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jlblNotification8 = new javax.swing.JLabel();
        jlblDate8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jlblNotification9 = new javax.swing.JLabel();
        jlblDate9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jlblNotification10 = new javax.swing.JLabel();
        jlblDate10 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jlblNotification11 = new javax.swing.JLabel();
        jlblDate11 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jlblNotification12 = new javax.swing.JLabel();
        jlblDate12 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jlblNotification13 = new javax.swing.JLabel();
        jlblDate13 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jlblNotification14 = new javax.swing.JLabel();
        jlblDate14 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jlblNotification15 = new javax.swing.JLabel();
        jlblDate15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMain.setBackground(new java.awt.Color(255, 204, 0));
        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification1.setText("Notification");
        jlblNotification1.setToolTipText("");
        jPanel1.add(jlblNotification1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate1.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate1.setText("mmm yy, yyyy hh:mm a");
        jlblDate1.setToolTipText("");
        jlblDate1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jlblDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 10, 470, 90));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification2.setText("Notification");
        jlblNotification2.setToolTipText("");
        jPanel2.add(jlblNotification2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate2.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate2.setText("mmm yy, yyyy hh:mm a");
        jlblDate2.setToolTipText("");
        jlblDate2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(jlblDate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 110, 470, 90));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification3.setText("Notification");
        jlblNotification3.setToolTipText("");
        jPanel3.add(jlblNotification3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate3.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate3.setText("mmm yy, yyyy hh:mm a");
        jlblDate3.setToolTipText("");
        jlblDate3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel3.add(jlblDate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 210, 470, 90));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification4.setText("Notification");
        jlblNotification4.setToolTipText("");
        jPanel4.add(jlblNotification4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate4.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate4.setText("mmm yy, yyyy hh:mm a");
        jlblDate4.setToolTipText("");
        jlblDate4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel4.add(jlblDate4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 310, 470, 90));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification5.setText("Notification");
        jlblNotification5.setToolTipText("");
        jPanel5.add(jlblNotification5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate5.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate5.setText("mmm yy, yyyy hh:mm a");
        jlblDate5.setToolTipText("");
        jlblDate5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel5.add(jlblDate5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 410, 470, 90));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification6.setText("Notification");
        jlblNotification6.setToolTipText("");
        jPanel6.add(jlblNotification6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate6.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate6.setText("mmm yy, yyyy hh:mm a");
        jlblDate6.setToolTipText("");
        jlblDate6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(jlblDate6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 510, 470, 90));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification7.setText("Notification");
        jlblNotification7.setToolTipText("");
        jPanel7.add(jlblNotification7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate7.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate7.setText("mmm yy, yyyy hh:mm a");
        jlblDate7.setToolTipText("");
        jlblDate7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel7.add(jlblDate7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 610, 470, 90));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification8.setText("Notification");
        jlblNotification8.setToolTipText("");
        jPanel8.add(jlblNotification8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate8.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate8.setText("mmm yy, yyyy hh:mm a");
        jlblDate8.setToolTipText("");
        jlblDate8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel8.add(jlblDate8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 710, 470, 90));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification9.setText("Notification");
        jlblNotification9.setToolTipText("");
        jPanel9.add(jlblNotification9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate9.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate9.setText("mmm yy, yyyy hh:mm a");
        jlblDate9.setToolTipText("");
        jlblDate9.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel9.add(jlblDate9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 810, 470, 90));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification10.setText("Notification");
        jlblNotification10.setToolTipText("");
        jPanel10.add(jlblNotification10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate10.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate10.setText("mmm yy, yyyy hh:mm a");
        jlblDate10.setToolTipText("");
        jlblDate10.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel10.add(jlblDate10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 910, 470, 90));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification11.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification11.setText("Notification");
        jlblNotification11.setToolTipText("");
        jPanel11.add(jlblNotification11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate11.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate11.setText("mmm yy, yyyy hh:mm a");
        jlblDate11.setToolTipText("");
        jlblDate11.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jlblDate11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 1010, 470, 90));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification12.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification12.setText("Notification");
        jlblNotification12.setToolTipText("");
        jPanel12.add(jlblNotification12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate12.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate12.setText("mmm yy, yyyy hh:mm a");
        jlblDate12.setToolTipText("");
        jlblDate12.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel12.add(jlblDate12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 1110, 470, 90));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification13.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification13.setText("Notification");
        jlblNotification13.setToolTipText("");
        jPanel13.add(jlblNotification13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate13.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate13.setText("mmm yy, yyyy hh:mm a");
        jlblDate13.setToolTipText("");
        jlblDate13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel13.add(jlblDate13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 1210, 470, 90));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification14.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification14.setText("Notification");
        jlblNotification14.setToolTipText("");
        jPanel14.add(jlblNotification14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate14.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate14.setText("mmm yy, yyyy hh:mm a");
        jlblDate14.setToolTipText("");
        jlblDate14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel14.add(jlblDate14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 1310, 470, 90));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotification15.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotification15.setText("Notification");
        jlblNotification15.setToolTipText("");
        jPanel15.add(jlblNotification15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 50));

        jlblDate15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblDate15.setForeground(new java.awt.Color(102, 102, 102));
        jlblDate15.setText("mmm yy, yyyy hh:mm a");
        jlblDate15.setToolTipText("");
        jlblDate15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel15.add(jlblDate15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 320, 30));

        jPanelMain.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 1410, 470, 90));

        getContentPane().add(jPanelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 495, 1500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(_Notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_Notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_Notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_Notifications.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new _Notifications().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JLabel jlblDate1;
    private javax.swing.JLabel jlblDate10;
    private javax.swing.JLabel jlblDate11;
    private javax.swing.JLabel jlblDate12;
    private javax.swing.JLabel jlblDate13;
    private javax.swing.JLabel jlblDate14;
    private javax.swing.JLabel jlblDate15;
    private javax.swing.JLabel jlblDate2;
    private javax.swing.JLabel jlblDate3;
    private javax.swing.JLabel jlblDate4;
    private javax.swing.JLabel jlblDate5;
    private javax.swing.JLabel jlblDate6;
    private javax.swing.JLabel jlblDate7;
    private javax.swing.JLabel jlblDate8;
    private javax.swing.JLabel jlblDate9;
    private javax.swing.JLabel jlblNotification1;
    private javax.swing.JLabel jlblNotification10;
    private javax.swing.JLabel jlblNotification11;
    private javax.swing.JLabel jlblNotification12;
    private javax.swing.JLabel jlblNotification13;
    private javax.swing.JLabel jlblNotification14;
    private javax.swing.JLabel jlblNotification15;
    private javax.swing.JLabel jlblNotification2;
    private javax.swing.JLabel jlblNotification3;
    private javax.swing.JLabel jlblNotification4;
    private javax.swing.JLabel jlblNotification5;
    private javax.swing.JLabel jlblNotification6;
    private javax.swing.JLabel jlblNotification7;
    private javax.swing.JLabel jlblNotification8;
    private javax.swing.JLabel jlblNotification9;
    // End of variables declaration//GEN-END:variables
}
