package windows;


import global.DatabaseConnection;
import java.time.LocalTime;
import global.StudentData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class KioskProfile extends javax.swing.JFrame {

    _Notifications notifications = new _Notifications();
    private JLabel[] notificationLabels;
    JLabel[] dateLabels;
    public String[] messages;
    public Timestamp[] dates;
    public int [] pages;
    boolean balanceVisible=false;
    String viewableSemCode="11"; 
    private static final int IDLE_TIME = 30 * 1000; // 30 sec
    private java.util.Timer idleTimer;
    private TimerTask idleTask;
   
    
    
    public KioskProfile() {
        initComponents();
        StudentData.setidleCounter(0);
        
        DatabaseConnection.connect(this);
                
        StudentData.setInLogin(false);
        
        String query = "SELECT * FROM system_status";
        
        try (Connection conn = DatabaseConnection.connect(this);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int sem = rs.getInt("current_semester");
                StudentData.setCurrentSemester(sem);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
        
        System.out.println("CURENT SEMESTER: "+StudentData.getCurrentSemester());
        
        jlblBalance.setVisible(false);
        jlblBalanceTitle.setVisible(false);
        jProgressBar_payment.setVisible(false);
        jbtnVIEWbalance.setText("VIEW");
        jbtnVIEWbalance.setBackground(new Color(0, 51, 102));
        
                
        jlblGreeting.setText(greetUser());
        if (StudentData.getMiddleName() == null) { StudentData.setMiddleName(""); }
        jlblNameHeader.setText(StudentData.getLastName()+", "+StudentData.getFirstName()+" "+StudentData.getMiddleName());
        
        jlblCourse.setText(StudentData.getProgramLong());
        jlblYearBlk.setText(StudentData.getYearLevel()+"-"+StudentData.getBlock());
        jlblStatus.setText(StudentData.getStatus());
        

        // Access the labels
        notificationLabels = notifications.getNotificationLabels();
        dateLabels = notifications.getDateLabels();
 
        postInitialize(); // Handle further setup outside the constructor
}
    
    
    
    

    private void postInitialize() {
        updateNotifications();
        updateLastLog();
        getAcademicStatus(StudentData.getStudentID());
        getAcademicFees(StudentData.getStudentID());
        
        jbtnYear1.setEnabled(true);
        jbtnYear2.setEnabled(false);
        jbtnYear3.setEnabled(false);
        jbtnYear4.setEnabled(false);
        
        if(Integer.valueOf(StudentData.getYearLevel())>1){
           jbtnYear2.setEnabled(true);
        }
        if(Integer.valueOf(StudentData.getYearLevel())>2){
           jbtnYear3.setEnabled(true);
        }
        if(Integer.valueOf(StudentData.getYearLevel())>3){
           jbtnYear4.setEnabled(true);
        }
        

        String currentSemCode = String.valueOf(StudentData.getYearLevel())+String.valueOf(StudentData.getCurrentSemester());
        System.out.println("CURRENT SEM CODE: "+currentSemCode);

        
        switch (currentSemCode) {
            case "11":
                viewableSemCode="12";
                jbtnYear2.setEnabled(false);
                break;
            case "12":
                viewableSemCode="21";
                jbtnYear2.setEnabled(true);
                break;
            case "21":
                viewableSemCode="22";
                jbtnYear3.setEnabled(false);
                break;
            case "22":
                viewableSemCode="31";
                jbtnYear3.setEnabled(true);
                break;
            case "31":
                viewableSemCode="32";
                jbtnYear4.setEnabled(false);
                break;
            case "32":
                viewableSemCode="41";
                jbtnYear4.setEnabled(true);
                break;
            case "41":
                viewableSemCode="42";
                jbtnYear4.setEnabled(true);
                break;
            case "42":
                viewableSemCode="42";
                jbtnYear4.setEnabled(true);
                break;
            default:
                throw new AssertionError();
        }
    }

//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    
    
    public void getAcademicFees(String sID){

            
        String query = """
        SELECT s.current_tuition, 
               COALESCE((
                   SELECT p.remaining_balance 
                   FROM payment p 
                   WHERE p.student_id = s.student_id 
                   ORDER BY p.date DESC LIMIT 1
               ), 0) AS latest_balance
        FROM students s 
        WHERE s.student_id = ?;
    """;

    double tuition = 0.00, balance = 0.00;

    try (Connection conn = DatabaseConnection.connect(this);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, sID);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                tuition = rs.getDouble("current_tuition");
                balance = rs.getDouble("latest_balance");
            }
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving data: " + e.getMessage());
    }

    // Calculate progress bar value
    System.out.println("Balance: " + balance);
    System.out.println("Tuition: " + tuition);

    double bar = tuition > 0 ? 100 * ((tuition - balance) / tuition) : 0;

    // Update UI
    jlblBalance.setText("Php " + String.format("%.2f", balance));
    jProgressBar_payment.setValue((int) Math.round(bar));
    
        
//        String query = """
//            SELECT s.current_tuition, p.remaining_balance 
//            FROM students s 
//            LEFT JOIN payment p 
//            ON s.student_id = p.student_id 
//            WHERE s.student_id = ?;
//        """;
//
//        double tuition = 0.00, balance = 0.00;
//
//        try (Connection conn = DatabaseConnection.connect(this);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, sID);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                tuition = rs.getDouble("current_tuition");
//                balance = rs.getDouble("remaining_balance");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving data: " + e.getMessage());
//        }
//
//        // Calculate progress bar value
//        System.out.println("Balance: " + balance);
//        System.out.println("Tuition: " + tuition);
//
//        double bar = 100 * ((tuition - balance) / tuition);
//
//        // Update UI
//        jlblBalance.setText("Php " + String.format("%.2f", balance));
//        jProgressBar_payment.setValue((int) Math.round(bar));

    }
    
    public void getAcademicStatus(String sID) {
        String query = """
            SELECT 
                g.remark,
                g.subject_code,
                s.units,
                (SELECT SUM(units) 
                 FROM subjects 
                 WHERE curriculum = ? AND program = ?) AS total_units
            FROM grades g
            LEFT JOIN subjects s 
            ON g.subject_code = s.code
            WHERE g.student_id = ? AND s.curriculum = ? AND s.program = ?;
        """;

        double progress = 0;
        int pass = 0, inc = 0, fail = 0, drop = 0, countAll = 0, earnedUnits = 0, totalUnits = 0;
        ArrayList<String> passedSubs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect(this);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, StudentData.getCurriculumCode());
            pstmt.setString(2, StudentData.getProgram());
            pstmt.setString(3, sID);
            pstmt.setString(4, StudentData.getCurriculumCode());
            pstmt.setString(5, StudentData.getProgram());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String remark = rs.getString("remark");
                int units = rs.getInt("units");
                totalUnits = rs.getInt("total_units");

                switch (remark) {
                    case "Passed" -> {
                        pass++;
                        earnedUnits += units;
                        passedSubs.add(rs.getString("subject_code"));
                    }
                    case "INCOMPLETE" -> inc++;
                    case "FAILED" -> fail++;
                    case "DROPPED" -> drop++;
                }
                countAll++;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving grades and subjects: " + e.getMessage());
        }

        // Calculate progress percentage
        progress = ((double) earnedUnits / totalUnits) * 100;

        // Update progress bar and labels
        jProgressBar_AcadProgress.setValue((int) Math.round(progress));
        jlblAcadProgress.setVisible(false);
        jlblPassed.setText(pass + " subject/s");
        jlblIncomplete.setText(inc + " subject/s");
        jlblFail.setText(fail + " subject/s");
        jlblDrop.setText(drop + " subject/s");
        jlblRemaining.setText((countAll - pass) + " subject/s");

    }
    
    
    
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
    
    public void updateNotifications() {

    // Create a list to hold notification messages with associated dates
    List<Map.Entry<LocalDateTime, String>> notificationsWithDates = new ArrayList<>();

    String query = "SELECT * FROM grades WHERE student_id = ?";

    try (Connection conn = DatabaseConnection.connect(this);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, StudentData.getStudentID());
        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
            String remark = rs.getString("remark");
            int reviewRequest = rs.getInt("review_request");
            String subjectCode = rs.getString("subject_code");
            int reviewed = rs.getInt("reviewed");

            String notificationMessage = "";
            LocalDateTime date = null;

            // Define the formatter to include both date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

            // Check for rechecked grade
            if (reviewed == 1) {
                date = rs.getTimestamp("reviewed_when") != null ? rs.getTimestamp("reviewed_when").toLocalDateTime() : null;
                if (date != null) {
                    notificationMessage = "<html><b>" + subjectCode + "</b> has been RECHECKED.</html>";
                }
            }
            // Check for requested recheck
            else if (reviewRequest == 1) {
                date = rs.getTimestamp("review_request_when") != null ? rs.getTimestamp("review_request_when").toLocalDateTime() : null;
                if (date != null) {
                    notificationMessage = "<html>You have requested a <b>RECHECK</b> for <b>" + subjectCode + "</b>.</html>";
                }
            }
            // Check for incomplete or failed grade
            else if (remark.contains("INC") || remark.contains("FAIL")) {
                date = rs.getTimestamp("uploaded_when") != null ? rs.getTimestamp("uploaded_when").toLocalDateTime() : null;
                if (date != null) {
                    if (remark.contains("INC")) {
                        notificationMessage = "<html>Your grade in <b>" + subjectCode + "</b> is <b>INCOMPLETE</b>.</html>";
                    } else if (remark.contains("FAIL")) {
                        notificationMessage = "<html>You received a <b>FAILING GRADE</b> in <b>" + subjectCode + "</b>.</html>";
                    }
                }
            }

            // Only add the notification if there is a valid message and date
            if (notificationMessage != null && !notificationMessage.isEmpty() && date != null) {
                notificationsWithDates.add(new AbstractMap.SimpleEntry<>(date, notificationMessage));
            }
        }//end of while

    } catch (SQLException e) {
        System.out.println("Error retrieving data " + e.getMessage());
    }

    // Sort notifications by date in descending order
    notificationsWithDates.sort((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()));

    // Initialize arrays for messages and dates
    messages = new String[notificationsWithDates.size()];
    dates = new Timestamp[notificationsWithDates.size()];  // Change to Timestamp[]

    int i = 0;
    // Separate sorted notifications into messages[] and dates[] arrays
    for (i = 0; i < notificationsWithDates.size(); i++) {
        Map.Entry<LocalDateTime, String> entry = notificationsWithDates.get(i);
        messages[i] = entry.getValue();
        dates[i] = java.sql.Timestamp.valueOf(entry.getKey()); // Convert LocalDateTime to Timestamp

        // Format the date and time to "MMM dd, yyyy h:mm a"
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        String formattedDate = sdf.format(dates[i]);

        // Set the formatted date and time on the label
        dateLabels[i].setText(formattedDate);
    }

    // SETS THE PAGES OF THE NOTIF
    int notifAmt = dates.length, x = 0;
    pages = new int[(notifAmt / 7) + 1];

    while (notifAmt > 7) {
        pages[x] = 7;
        notifAmt -= 7;
        x++;
    }
    pages[x] = notifAmt;

    // DEFAULT DISPLAY FOR NOTIFICATIONS - page1
    totalNotifPage = 1 + (dates.length / 7);
    jlblCurrentPage.setText(notificationPage + " / " + totalNotifPage);

    jbtnPREVnotif.setEnabled(false);
    if (notificationPage == totalNotifPage) {
        jbtnNEXTnotif.setEnabled(false);
    }

    if (notifAmt > 0) {
        showNotifPanels();
        showNotifications(notificationPage); // Show notif for page number...
    } else {
        jlblCurrentPage.setText("0 / 0");
    }
    hideNotifPanels(pages[notificationPage - 1]);

}
   
     
    private void showNotifPanels(){ //shows all
        notifPanel1.setVisible(true);
        notifPanel2.setVisible(true);
        notifPanel3.setVisible(true);
        notifPanel4.setVisible(true);
        notifPanel5.setVisible(true);
        notifPanel6.setVisible(true);
        notifPanel7.setVisible(true);
        
    }
    
    private void showNotifications(int page){//show notif for page number...
       
        JLabel[] notifLabel = { jlblNotif_1, jlblNotif_2, jlblNotif_3, 
            jlblNotif_4, jlblNotif_5,jlblNotif_6, jlblNotif_7 };
    
        JLabel[] notifDateLabel = { jlblNotifDate_1, jlblNotifDate_2, jlblNotifDate_3, 
            jlblNotifDate_4, jlblNotifDate_5,jlblNotifDate_6, jlblNotifDate_7 };
        
        int forStart=(page-1)*7;
        int forEnd=page*7;
        System.out.println(forStart+" - "+forEnd);

        for (int i = forStart, j = 0; i < forEnd && j < notifLabel.length; i++, j++) { 
            if (i < dates.length && i < messages.length) { 
                notifLabel[j].setText(messages[i]);
                notifDateLabel[j].setText(dates[i].toString()); 
            } else { 
                notifLabel[j].setText(""); // Clear label if there's no notification 
                notifDateLabel[j].setText(""); 
            } 
        }

    }
    
    private void hideNotifPanels(int notifInThisPage){ //hides blanks
        for (int i = 0; i <= 7; i++) {
            if(i>notifInThisPage){
                switch (i) {
                    case 1:
                        notifPanel1.setVisible(false);
                        break;
                    case 2:
                        notifPanel2.setVisible(false);
                        break;
                    case 3:
                        notifPanel3.setVisible(false);
                        break;
                    case 4:
                        notifPanel4.setVisible(false);
                        break;
                    case 5:
                        notifPanel5.setVisible(false);
                        break;
                    case 6:
                        notifPanel6.setVisible(false);
                        break;
                    case 7:
                        notifPanel7.setVisible(false);
                        break;
                    default:
                        throw new AssertionError();    
                }
            }
        }
    }
    
    
    private void updateLastLog() {
        String sql = "SELECT timestamp FROM kiosk_login WHERE rfid = ?";
        
        try(Connection conn = DatabaseConnection.connect(this);
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, StudentData.getCurrentRFID()); 
            ResultSet rs = stmt.executeQuery(); 
            
            while (rs.next()) {
                if (rs.next()){
                    // Get the timestamp from the result set 
                    Timestamp timestamp = rs.getTimestamp("timestamp"); 

                    // Create a SimpleDateFormat instance with the desired format 
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - hh:mm:ss a"); 

                    // Format the timestamp 
                    String formattedDate = sdf.format(timestamp); 

                    // Set the formatted date string to the label 
                    jlblLastLog.setText(formattedDate); 
                }
                else{
                    jlblLastLog.setText("");
            }
                }
        } catch (SQLException e) { 
            System.out.println("Error retrieving last log time: " + e.getMessage()); 
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
        jPanel2 = new javax.swing.JPanel();
        jlblCourse2 = new javax.swing.JLabel();
        jlblCourse1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jbtnYear1 = new javax.swing.JButton();
        jbtnProfile = new javax.swing.JButton();
        jbtnYear2 = new javax.swing.JButton();
        jbtnYear3 = new javax.swing.JButton();
        jbtnYear4 = new javax.swing.JButton();
        jbtnLogout = new javax.swing.JButton();
        jimgLogoHere150x150 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanelNOTIFICATIONS = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jlblNameHeader14 = new javax.swing.JLabel();
        notifPanel2 = new javax.swing.JPanel();
        jlblNotifDate_2 = new javax.swing.JLabel();
        jlblNotif_2 = new javax.swing.JLabel();
        notifPanel1 = new javax.swing.JPanel();
        jlblNotifDate_1 = new javax.swing.JLabel();
        jlblNotif_1 = new javax.swing.JLabel();
        notifPanel4 = new javax.swing.JPanel();
        jlblNotifDate_4 = new javax.swing.JLabel();
        jlblNotif_4 = new javax.swing.JLabel();
        notifPanel3 = new javax.swing.JPanel();
        jlblNotifDate_3 = new javax.swing.JLabel();
        jlblNotif_3 = new javax.swing.JLabel();
        notifPanel7 = new javax.swing.JPanel();
        jlblNotifDate_7 = new javax.swing.JLabel();
        jlblNotif_7 = new javax.swing.JLabel();
        notifPanel5 = new javax.swing.JPanel();
        jlblNotifDate_5 = new javax.swing.JLabel();
        jlblNotif_5 = new javax.swing.JLabel();
        notifPanel6 = new javax.swing.JPanel();
        jlblNotifDate_6 = new javax.swing.JLabel();
        jlblNotif_6 = new javax.swing.JLabel();
        jlblCurrentPage = new javax.swing.JLabel();
        jbtnNEXTnotif = new javax.swing.JButton();
        jbtnPREVnotif = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jlblNameHeader18 = new javax.swing.JLabel();
        jPanel_AcadFee = new javax.swing.JPanel();
        jProgressBar_payment = new javax.swing.JProgressBar();
        jlblBalance = new javax.swing.JLabel();
        jlblBalanceTitle = new javax.swing.JLabel();
        jbtnPREVnotif1 = new javax.swing.JButton();
        jbtnPREVnotif2 = new javax.swing.JButton();
        jbtnVIEWbalance = new javax.swing.JButton();
        jlblNameHeader6 = new javax.swing.JLabel();
        jlblNameHeader7 = new javax.swing.JLabel();
        jlblNameHeader5 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jlblNameHeader19 = new javax.swing.JLabel();
        jlblNameHeader8 = new javax.swing.JLabel();
        jlblCourse = new javax.swing.JLabel();
        jlblNameHeader9 = new javax.swing.JLabel();
        jlblYearBlk = new javax.swing.JLabel();
        jlblNameHeader20 = new javax.swing.JLabel();
        jlblStatus = new javax.swing.JLabel();
        jlblNameHeader22 = new javax.swing.JLabel();
        jlblLastLog = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jlblNameHeader21 = new javax.swing.JLabel();
        jlblAcadProgress = new javax.swing.JLabel();
        jlblNameHeader10 = new javax.swing.JLabel();
        jlblPassed = new javax.swing.JLabel();
        jlblNameHeader11 = new javax.swing.JLabel();
        jlblIncomplete = new javax.swing.JLabel();
        jlblNameHeader16 = new javax.swing.JLabel();
        jlblFail = new javax.swing.JLabel();
        jlblNameHeader17 = new javax.swing.JLabel();
        jlblDrop = new javax.swing.JLabel();
        jlblNameHeader24 = new javax.swing.JLabel();
        jlblRemaining = new javax.swing.JLabel();
        jProgressBar_AcadProgress = new javax.swing.JProgressBar();
        jlblNameHeader = new javax.swing.JLabel();
        jlblGreeting = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jbtnRequest1 = new javax.swing.JButton();

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
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblCourse2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCourse2.setForeground(new java.awt.Color(255, 204, 0));
        jlblCourse2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCourse2.setText("SOUTHERN LUZON TECHNOLOGICAL COLLEGE FOUNDATION PILAR, INC.");
        jlblCourse2.setToolTipText("");
        jPanel2.add(jlblCourse2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1550, 50));

        jlblCourse1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblCourse1.setForeground(new java.awt.Color(255, 204, 0));
        jlblCourse1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCourse1.setText("MARIFOSQUE, PILAR, SORSOGON");
        jlblCourse1.setToolTipText("");
        jPanel2.add(jlblCourse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1550, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1670, 130));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtnYear1.setBackground(new java.awt.Color(255, 204, 0));
        jbtnYear1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnYear1.setForeground(new java.awt.Color(0, 0, 0));
        jbtnYear1.setText("1ST YEAR");
        jbtnYear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnYear1ActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnYear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 230, 110));

        jbtnProfile.setBackground(new java.awt.Color(0, 51, 102));
        jbtnProfile.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnProfile.setForeground(new java.awt.Color(255, 255, 255));
        jbtnProfile.setText("PROFILE");
        jbtnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnProfileActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 230, 110));

        jbtnYear2.setBackground(new java.awt.Color(255, 204, 0));
        jbtnYear2.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnYear2.setForeground(new java.awt.Color(0, 0, 0));
        jbtnYear2.setText("2ND YEAR");
        jbtnYear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnYear2ActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnYear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 230, 110));

        jbtnYear3.setBackground(new java.awt.Color(255, 204, 0));
        jbtnYear3.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnYear3.setForeground(new java.awt.Color(0, 0, 0));
        jbtnYear3.setText("3RD YEAR");
        jbtnYear3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnYear3ActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnYear3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 230, 110));

        jbtnYear4.setBackground(new java.awt.Color(255, 204, 0));
        jbtnYear4.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnYear4.setForeground(new java.awt.Color(0, 0, 0));
        jbtnYear4.setText("4TH YEAR");
        jbtnYear4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnYear4ActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnYear4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 230, 110));

        jbtnLogout.setBackground(new java.awt.Color(204, 0, 51));
        jbtnLogout.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnLogout.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLogout.setText("LOG OUT");
        jbtnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLogoutActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 800, 230, 110));

        jimgLogoHere150x150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SLTCFPI_logo_150x150.png"))); // NOI18N
        jimgLogoHere150x150.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jimgLogoHere150x150MouseClicked(evt);
            }
        });
        jPanel3.add(jimgLogoHere150x150, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 150, 150));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 1080));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 380, -1));

        jPanelNOTIFICATIONS.setBackground(new java.awt.Color(255, 204, 0));
        jPanelNOTIFICATIONS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelNOTIFICATIONSMouseClicked(evt);
            }
        });
        jPanelNOTIFICATIONS.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(0, 51, 102));
        jPanel6.setToolTipText("");
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNameHeader14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader14.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader14.setText("NOTIFICATIONS");
        jlblNameHeader14.setToolTipText("");
        jPanel6.add(jlblNameHeader14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 590, -1));

        jPanelNOTIFICATIONS.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 70));

        notifPanel2.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_2.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_2.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_2.setToolTipText("");
        jlblNotifDate_2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel2.add(jlblNotifDate_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_2.setText("Notification<br>fff");
        jlblNotif_2.setToolTipText("");
        notifPanel2.add(jlblNotif_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 550, 80));

        notifPanel1.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_1.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_1.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_1.setToolTipText("");
        jlblNotifDate_1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel1.add(jlblNotifDate_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_1.setText("Notification<br>fff");
        jlblNotif_1.setToolTipText("");
        notifPanel1.add(jlblNotif_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 550, 80));

        notifPanel4.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_4.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_4.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_4.setToolTipText("");
        jlblNotifDate_4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel4.add(jlblNotifDate_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_4.setText("Notification<br>fff");
        jlblNotif_4.setToolTipText("");
        notifPanel4.add(jlblNotif_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 550, 80));

        notifPanel3.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_3.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_3.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_3.setToolTipText("");
        jlblNotifDate_3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel3.add(jlblNotifDate_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_3.setText("Notification<br>fff");
        jlblNotif_3.setToolTipText("");
        notifPanel3.add(jlblNotif_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 550, 80));

        notifPanel7.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_7.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_7.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_7.setToolTipText("");
        jlblNotifDate_7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel7.add(jlblNotifDate_7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_7.setText("Notification<br>fff");
        jlblNotif_7.setToolTipText("");
        notifPanel7.add(jlblNotif_7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 550, 80));

        notifPanel5.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_5.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_5.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_5.setToolTipText("");
        jlblNotifDate_5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel5.add(jlblNotifDate_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_5.setText("Notification<br>fff");
        jlblNotif_5.setToolTipText("");
        notifPanel5.add(jlblNotif_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 550, 80));

        notifPanel6.setBackground(new java.awt.Color(255, 255, 255));
        notifPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNotifDate_6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlblNotifDate_6.setForeground(new java.awt.Color(102, 102, 102));
        jlblNotifDate_6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNotifDate_6.setText("mmm yy, yyyy hh:mm a");
        jlblNotifDate_6.setToolTipText("");
        jlblNotifDate_6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        notifPanel6.add(jlblNotifDate_6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 510, 20));

        jlblNotif_6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jlblNotif_6.setText("Notification<br>fff");
        jlblNotif_6.setToolTipText("");
        notifPanel6.add(jlblNotif_6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 50));

        jPanelNOTIFICATIONS.add(notifPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 550, 80));

        jlblCurrentPage.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCurrentPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCurrentPage.setText("1 / 4");
        jlblCurrentPage.setToolTipText("");
        jPanelNOTIFICATIONS.add(jlblCurrentPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 720, 90, 50));

        jbtnNEXTnotif.setBackground(new java.awt.Color(0, 51, 102));
        jbtnNEXTnotif.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnNEXTnotif.setForeground(new java.awt.Color(255, 255, 255));
        jbtnNEXTnotif.setText("NEXT");
        jbtnNEXTnotif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNEXTnotifActionPerformed(evt);
            }
        });
        jPanelNOTIFICATIONS.add(jbtnNEXTnotif, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 720, 210, 50));

        jbtnPREVnotif.setBackground(new java.awt.Color(0, 51, 102));
        jbtnPREVnotif.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnPREVnotif.setForeground(new java.awt.Color(255, 255, 255));
        jbtnPREVnotif.setText("PREV");
        jbtnPREVnotif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPREVnotifActionPerformed(evt);
            }
        });
        jPanelNOTIFICATIONS.add(jbtnPREVnotif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 720, 210, 50));

        jPanel4.add(jPanelNOTIFICATIONS, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 590, 780));

        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(0, 51, 102));
        jPanel13.setToolTipText("");
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNameHeader18.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader18.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader18.setText("ACADEMIC FEES");
        jlblNameHeader18.setToolTipText("");
        jPanel13.add(jlblNameHeader18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 430, -1));

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 70));

        jPanel_AcadFee.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_AcadFee.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jProgressBar_payment.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar_payment.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jProgressBar_payment.setForeground(new java.awt.Color(0, 0, 0));
        jProgressBar_payment.setStringPainted(true);
        jPanel_AcadFee.add(jProgressBar_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 370, 40));

        jlblBalance.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblBalance.setText("jlblBalance");
        jlblBalance.setToolTipText("");
        jPanel_AcadFee.add(jlblBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 240, 50));

        jlblBalanceTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblBalanceTitle.setText("BALANCE:");
        jlblBalanceTitle.setToolTipText("");
        jPanel_AcadFee.add(jlblBalanceTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 140, 50));

        jPanel12.add(jPanel_AcadFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 390, 120));

        jbtnPREVnotif1.setBackground(new java.awt.Color(0, 51, 102));
        jbtnPREVnotif1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnPREVnotif1.setForeground(new java.awt.Color(255, 255, 255));
        jbtnPREVnotif1.setText("PREV");
        jbtnPREVnotif1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPREVnotif1ActionPerformed(evt);
            }
        });
        jPanel12.add(jbtnPREVnotif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 720, 210, 50));

        jbtnPREVnotif2.setBackground(new java.awt.Color(0, 51, 102));
        jbtnPREVnotif2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnPREVnotif2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnPREVnotif2.setText("PREV");
        jbtnPREVnotif2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPREVnotif2ActionPerformed(evt);
            }
        });
        jPanel12.add(jbtnPREVnotif2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 720, 210, 50));

        jbtnVIEWbalance.setBackground(new java.awt.Color(0, 51, 102));
        jbtnVIEWbalance.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnVIEWbalance.setForeground(new java.awt.Color(255, 255, 255));
        jbtnVIEWbalance.setText("VIEW");
        jbtnVIEWbalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVIEWbalanceActionPerformed(evt);
            }
        });
        jPanel12.add(jbtnVIEWbalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 290, 50));

        jlblNameHeader6.setFont(new java.awt.Font("Segoe UI", 2, 20)); // NOI18N
        jlblNameHeader6.setText("Note:");
        jlblNameHeader6.setToolTipText("");
        jPanel12.add(jlblNameHeader6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 370, 40));

        jlblNameHeader7.setFont(new java.awt.Font("Segoe UI", 2, 20)); // NOI18N
        jlblNameHeader7.setText("See cashier for additional details. ");
        jlblNameHeader7.setToolTipText("");
        jPanel12.add(jlblNameHeader7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 370, 40));

        jlblNameHeader5.setFont(new java.awt.Font("Segoe UI", 2, 20)); // NOI18N
        jlblNameHeader5.setText("Mon-Fri, 8:30 AM - 4:30 PM");
        jlblNameHeader5.setToolTipText("");
        jPanel12.add(jlblNameHeader5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 370, 40));

        jPanel4.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, -1, 420));

        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(0, 51, 102));
        jPanel15.setToolTipText("");
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNameHeader19.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader19.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader19.setText("STUDENT INFO");
        jlblNameHeader19.setToolTipText("");
        jPanel15.add(jlblNameHeader19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 960, -1));

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 70));

        jlblNameHeader8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader8.setText("PROGRAM:");
        jlblNameHeader8.setToolTipText("");
        jPanel14.add(jlblNameHeader8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 210, 50));

        jlblCourse.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCourse.setText("jlblCourse");
        jlblCourse.setToolTipText("");
        jPanel14.add(jlblCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 700, 50));

        jlblNameHeader9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader9.setText("YEAR/BLOCK:");
        jlblNameHeader9.setToolTipText("");
        jPanel14.add(jlblNameHeader9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 210, 50));

        jlblYearBlk.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblYearBlk.setText("jlblYearBlk ");
        jlblYearBlk.setToolTipText("");
        jPanel14.add(jlblYearBlk, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 520, 50));

        jlblNameHeader20.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader20.setText("STATUS:");
        jlblNameHeader20.setToolTipText("");
        jPanel14.add(jlblNameHeader20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 210, 50));

        jlblStatus.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblStatus.setText("jlblStatus");
        jlblStatus.setToolTipText("");
        jPanel14.add(jlblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 700, 50));

        jlblNameHeader22.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader22.setText("LAST LOGGED IN:");
        jlblNameHeader22.setToolTipText("");
        jPanel14.add(jlblNameHeader22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 210, 50));

        jlblLastLog.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblLastLog.setText("jlblLastLog");
        jlblLastLog.setToolTipText("");
        jPanel14.add(jlblLastLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 700, 50));

        jPanel4.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 960, 330));

        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(0, 51, 102));
        jPanel17.setToolTipText("");
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNameHeader21.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader21.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader21.setText("ACADEMIC STATUS");
        jlblNameHeader21.setToolTipText("");
        jPanel17.add(jlblNameHeader21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 500, -1));

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 70));

        jlblAcadProgress.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblAcadProgress.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblAcadProgress.setText("100%");
        jlblAcadProgress.setToolTipText("");
        jPanel16.add(jlblAcadProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 150, 40));

        jlblNameHeader10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader10.setText("PASSED:");
        jlblNameHeader10.setToolTipText("");
        jPanel16.add(jlblNameHeader10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 135, 170, 50));

        jlblPassed.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblPassed.setText("jlblPassed");
        jlblPassed.setToolTipText("");
        jPanel16.add(jlblPassed, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 135, 260, 50));

        jlblNameHeader11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader11.setText("INCOMPLETE:");
        jlblNameHeader11.setToolTipText("");
        jPanel16.add(jlblNameHeader11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 185, 170, 50));

        jlblIncomplete.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblIncomplete.setText("jlblIncomplete");
        jlblIncomplete.setToolTipText("");
        jPanel16.add(jlblIncomplete, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 185, 260, 50));

        jlblNameHeader16.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader16.setText("FAIL:");
        jlblNameHeader16.setToolTipText("");
        jPanel16.add(jlblNameHeader16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 235, 170, 50));

        jlblFail.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblFail.setText("jlblFail");
        jlblFail.setToolTipText("");
        jPanel16.add(jlblFail, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 235, 260, 50));

        jlblNameHeader17.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader17.setText("DROP:");
        jlblNameHeader17.setToolTipText("");
        jPanel16.add(jlblNameHeader17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 285, 170, 50));

        jlblDrop.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDrop.setText("jlblDrop");
        jlblDrop.setToolTipText("");
        jPanel16.add(jlblDrop, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 285, 260, 50));

        jlblNameHeader24.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlblNameHeader24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblNameHeader24.setText("REMAINING:");
        jlblNameHeader24.setToolTipText("");
        jPanel16.add(jlblNameHeader24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 335, 170, 50));

        jlblRemaining.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRemaining.setText("jlblRemaining");
        jlblRemaining.setToolTipText("");
        jPanel16.add(jlblRemaining, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 335, 260, 50));

        jProgressBar_AcadProgress.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar_AcadProgress.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jProgressBar_AcadProgress.setForeground(new java.awt.Color(0, 0, 0));
        jProgressBar_AcadProgress.setStringPainted(true);
        jPanel16.add(jProgressBar_AcadProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 420, 40));

        jPanel4.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 505, 420));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 1610, 860));

        jlblNameHeader.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblNameHeader.setText("STUDENT NAME");
        jlblNameHeader.setToolTipText("");
        jPanel1.add(jlblNameHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 1040, 60));

        jlblGreeting.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jlblGreeting.setText("Good morning,");
        jlblGreeting.setToolTipText("");
        jPanel1.add(jlblGreeting, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 650, -1));

        jPanel9.setBackground(new java.awt.Color(255, 204, 0));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 1670, 10));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private JFrame frame;
    private void jbtnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLogoutActionPerformed
        String popupTitle = "Logging Out"; 
        String popupMessage = "You are about to log out.";
        int click = showCustomDialog(popupTitle, popupMessage, "OK", "Cancel");
        if (click == 1) { 
            FrameManager.closeAllFrames();
            System.out.println("Button 1 was clicked");
            // Perform the logout actions 
            KioskLogin login = new KioskLogin(); 
            login.setVisible(true); // Hide and dispose of this window 
            setVisible(false); 
            dispose();
            FrameManager.addFrame(login);
            StudentData.setidleCounter(0);
        }  else if (click == 2) { 
        System.out.println("Button 2 was clicked");
        }
    }//GEN-LAST:event_jbtnLogoutActionPerformed

    private void jbtnYear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear1ActionPerformed
        StudentData.btnCarryOver="1st YEAR";
        
        frame = new JFrame("1st Year");
        KioskViewGrade viewGrade = new KioskViewGrade();
        viewGrade.setVisible(true);
        FrameManager.closeAllFrames();        
        // Hide and dispose of this window
        setVisible(false);
        dispose();
        FrameManager.addFrame(viewGrade);
        StudentData.setidleCounter(0);
    }//GEN-LAST:event_jbtnYear1ActionPerformed

    private void jbtnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnProfileActionPerformed

    private void jbtnYear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear2ActionPerformed
        StudentData.btnCarryOver="2nd YEAR";
        FrameManager.closeAllFrames();
        frame = new JFrame("2nd Year");
        KioskViewGrade viewGrade = new KioskViewGrade();
        viewGrade.setVisible(true);
                
        // Hide and dispose of this window
        setVisible(false);
        dispose();
        FrameManager.addFrame(viewGrade);
        StudentData.setidleCounter(0);
    }//GEN-LAST:event_jbtnYear2ActionPerformed

    private void jbtnYear3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear3ActionPerformed
        StudentData.btnCarryOver="3rd YEAR";
        FrameManager.closeAllFrames();
        frame = new JFrame("3rd Year");
        KioskViewGrade viewGrade = new KioskViewGrade();
        viewGrade.setVisible(true);
                
        // Hide and dispose of this window
        setVisible(false);
        dispose();
        FrameManager.addFrame(viewGrade);
        StudentData.setidleCounter(0);
    }//GEN-LAST:event_jbtnYear3ActionPerformed

    private void jbtnYear4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear4ActionPerformed
        StudentData.btnCarryOver="4th YEAR";
        FrameManager.closeAllFrames();
        frame = new JFrame("4th Year");
        KioskViewGrade viewGrade = new KioskViewGrade();
        viewGrade.setVisible(true);
                
        // Hide and dispose of this window
        setVisible(false);
        dispose();
        FrameManager.addFrame(viewGrade);
        StudentData.setidleCounter(0);
    }//GEN-LAST:event_jbtnYear4ActionPerformed

    private void jbtnRequest1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest1ActionPerformed
        resetIdleTimer();
    }//GEN-LAST:event_jbtnRequest1ActionPerformed

    private void resetIdleTimer() {
        StudentData.setidleCounter(0);
    }
    
    int notificationPage = 1, totalNotifPage;
    
    private void jbtnNEXTnotifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTnotifActionPerformed
        resetIdleTimer();
        jbtnNEXTnotif.setEnabled(true);
        jbtnPREVnotif.setEnabled(true);
        notificationPage++;
        jlblCurrentPage.setText(notificationPage+" / "+totalNotifPage);
        
        if (notificationPage==totalNotifPage) {
            jbtnNEXTnotif.setEnabled(false);
        }
        
//        if (dates.length>0) { 
            showNotifPanels();
            showNotifications(notificationPage); //show notif for page number...
            hideNotifPanels(pages[notificationPage-1]);
//        }
    }//GEN-LAST:event_jbtnNEXTnotifActionPerformed

    private void jbtnPREVnotifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPREVnotifActionPerformed
        resetIdleTimer();
        jbtnNEXTnotif.setEnabled(true);
        jbtnPREVnotif.setEnabled(true);
        notificationPage--;
        jlblCurrentPage.setText(notificationPage+" / "+totalNotifPage);
        
        if (notificationPage==1) {
            jbtnPREVnotif.setEnabled(false);
        }
        
//        if (dates.length>0) { 
            showNotifPanels();
            showNotifications(notificationPage); //show notif for page number...
            hideNotifPanels(pages[notificationPage-1]);
//        }
    }//GEN-LAST:event_jbtnPREVnotifActionPerformed

    private void jbtnPREVnotif1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPREVnotif1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnPREVnotif1ActionPerformed

    private void jbtnPREVnotif2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPREVnotif2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnPREVnotif2ActionPerformed

    private void jbtnVIEWbalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVIEWbalanceActionPerformed
        resetIdleTimer();
        if(!balanceVisible){ //if it's hidden
            jlblBalance.setVisible(true);
            jlblBalanceTitle.setVisible(true);
            jProgressBar_payment.setVisible(true);
            balanceVisible=true;
            jbtnVIEWbalance.setText("HIDE");
            jbtnVIEWbalance.setBackground(new Color(204,0,0));
        }else{
            jlblBalance.setVisible(false);
            jlblBalanceTitle.setVisible(false);
            jProgressBar_payment.setVisible(false);
            balanceVisible=false;
            jbtnVIEWbalance.setText("VIEW");
            jbtnVIEWbalance.setBackground(new Color(0, 51, 102));
        }
    }//GEN-LAST:event_jbtnVIEWbalanceActionPerformed

    private void jimgLogoHere150x150MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jimgLogoHere150x150MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jimgLogoHere150x150MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanelNOTIFICATIONSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelNOTIFICATIONSMouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanelNOTIFICATIONSMouseClicked
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
            java.util.logging.Logger.getLogger(KioskProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KioskProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KioskProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KioskProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new KioskProfile().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelNOTIFICATIONS;
    private javax.swing.JPanel jPanel_AcadFee;
    private javax.swing.JProgressBar jProgressBar_AcadProgress;
    private javax.swing.JProgressBar jProgressBar_payment;
    private javax.swing.JButton jbtnLogout;
    private javax.swing.JButton jbtnNEXTnotif;
    private javax.swing.JButton jbtnPREVnotif;
    private javax.swing.JButton jbtnPREVnotif1;
    private javax.swing.JButton jbtnPREVnotif2;
    private javax.swing.JButton jbtnProfile;
    private javax.swing.JButton jbtnRequest1;
    private javax.swing.JButton jbtnVIEWbalance;
    private javax.swing.JButton jbtnYear1;
    private javax.swing.JButton jbtnYear2;
    private javax.swing.JButton jbtnYear3;
    private javax.swing.JButton jbtnYear4;
    private javax.swing.JLabel jimgLogoHere150x150;
    private javax.swing.JLabel jlblAcadProgress;
    private javax.swing.JLabel jlblBalance;
    private javax.swing.JLabel jlblBalanceTitle;
    private javax.swing.JLabel jlblCourse;
    private javax.swing.JLabel jlblCourse1;
    private javax.swing.JLabel jlblCourse2;
    private javax.swing.JLabel jlblCurrentPage;
    private javax.swing.JLabel jlblDrop;
    private javax.swing.JLabel jlblFail;
    private javax.swing.JLabel jlblGreeting;
    private javax.swing.JLabel jlblIncomplete;
    private javax.swing.JLabel jlblLastLog;
    private javax.swing.JLabel jlblNameHeader;
    private javax.swing.JLabel jlblNameHeader10;
    private javax.swing.JLabel jlblNameHeader11;
    private javax.swing.JLabel jlblNameHeader14;
    private javax.swing.JLabel jlblNameHeader16;
    private javax.swing.JLabel jlblNameHeader17;
    private javax.swing.JLabel jlblNameHeader18;
    private javax.swing.JLabel jlblNameHeader19;
    private javax.swing.JLabel jlblNameHeader20;
    private javax.swing.JLabel jlblNameHeader21;
    private javax.swing.JLabel jlblNameHeader22;
    private javax.swing.JLabel jlblNameHeader24;
    private javax.swing.JLabel jlblNameHeader5;
    private javax.swing.JLabel jlblNameHeader6;
    private javax.swing.JLabel jlblNameHeader7;
    private javax.swing.JLabel jlblNameHeader8;
    private javax.swing.JLabel jlblNameHeader9;
    private javax.swing.JLabel jlblNotifDate_1;
    private javax.swing.JLabel jlblNotifDate_2;
    private javax.swing.JLabel jlblNotifDate_3;
    private javax.swing.JLabel jlblNotifDate_4;
    private javax.swing.JLabel jlblNotifDate_5;
    private javax.swing.JLabel jlblNotifDate_6;
    private javax.swing.JLabel jlblNotifDate_7;
    private javax.swing.JLabel jlblNotif_1;
    private javax.swing.JLabel jlblNotif_2;
    private javax.swing.JLabel jlblNotif_3;
    private javax.swing.JLabel jlblNotif_4;
    private javax.swing.JLabel jlblNotif_5;
    private javax.swing.JLabel jlblNotif_6;
    private javax.swing.JLabel jlblNotif_7;
    private javax.swing.JLabel jlblPassed;
    private javax.swing.JLabel jlblRemaining;
    private javax.swing.JLabel jlblStatus;
    private javax.swing.JLabel jlblYearBlk;
    private javax.swing.JPanel notifPanel1;
    private javax.swing.JPanel notifPanel2;
    private javax.swing.JPanel notifPanel3;
    private javax.swing.JPanel notifPanel4;
    private javax.swing.JPanel notifPanel5;
    private javax.swing.JPanel notifPanel6;
    private javax.swing.JPanel notifPanel7;
    // End of variables declaration//GEN-END:variables


    

}
