/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package windows;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author USER
 */
public class _popUp extends JDialog {

    private String result;
    public int clickedBtn;
    private JFrame parentFrame;
    
    public _popUp(java.awt.Frame parent, boolean modal) {
        super(parent, modal); 
        setUndecorated(true); 
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        parentFrame = (JFrame) parent;
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
        jPanel2 = new javax.swing.JPanel();
        message = new javax.swing.JLabel();
        button2 = new javax.swing.JButton();
        button1 = new javax.swing.JButton();
        title = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 7));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 7));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        message.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        message.setForeground(new java.awt.Color(0, 0, 0));
        message.setText("Message");
        jPanel2.add(message, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 540, 200));

        button2.setBackground(new java.awt.Color(0, 51, 102));
        button2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Button 2");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel2.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 230, 180, 60));

        button1.setBackground(new java.awt.Color(0, 51, 102));
        button1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Button 1");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel2.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 180, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 650, 310));

        title.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Title");
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 570, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        clickedBtn=2;
        dispose();
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        clickedBtn=1;
        dispose();
    }//GEN-LAST:event_button1ActionPerformed

    public String showPopup(String popupTitle, String popupMessage, String btn1, String btn2) { 
        title.setText(popupTitle);
        message.setText("<html><body style='font-size:20px;'>" + popupMessage + "</body></html>"); 
                
        if (!btn1.contentEquals("None")) {
            button1.setText(btn1);
        }else{
            button1.setVisible(false);
        }
        
        if (!btn2.contentEquals("None")) {
            button2.setText(btn2);
        }else{
            button2.setVisible(false);
        }
        
        
        setLocationRelativeTo(null); // Center the dialog on the screen 
        setVisible(true); 
        
        return result;
    }
    
    public int getClickedButton() { return clickedBtn; }
    
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
            java.util.logging.Logger.getLogger(_popUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_popUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_popUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_popUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
////                new _popUp().setVisible(true);
//            }
        java.awt.EventQueue.invokeLater(() -> { 
            _popUp dialog = new _popUp(new JFrame(), true); 
            dialog.showPopup("Title", "Message", "Button 1", "Button 2");
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel message;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}