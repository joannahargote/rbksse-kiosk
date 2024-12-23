package windows;

import global.DatabaseConnection;
import global.StudentData;
import javax.swing.JFrame;
//import javax.swing.JOptionPane;
import java.awt.Color;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
//import javax.swing.JDialog;
import javax.swing.JLabel;
//import java.awt.*;
//import javax.swing.BorderFactory;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;

public class KioskViewGrade extends javax.swing.JFrame {
  
    String clickedYearBtn;
    String clickedSemesterBtn="1st Semester"; 
    //Color clickedBtnColor = new Color(102, 102, 102); // gray color 
    Color clickedBtnColor = new Color(0,51,102); // blue color 
    Color clickedTextColor = Color.WHITE; 
    Color defaultBtnColor = new Color(255,204,0); //yellow 
    Color defaultTextColor = Color.BLACK; 
    Color subjectCannotBeTakenYet = new Color(102, 102, 102);
    Color requestButtonDefault = new Color(0,153,51); //green
    int current_GradeIDs[] = new int[13]; //for requestReview - do not remove
    int Days_Allowed_For_Grade_Adjustment= 21; //3 weeks
    String viewableSemCode="11"; 
    
    public KioskViewGrade() {
        initComponents();
        
        StudentData.setidleCounter(0);
        
        checkViewable();
                
        StudentData.setInLogin(false);
        
        clickedYearBtn=StudentData.btnCarryOver;
        
        switch (clickedYearBtn) {
            case "1st YEAR":
                StudentData.setidleCounter(0);
                jbtnYear1ActionPerformed(null);
                break;
            case "2nd YEAR":
                StudentData.setidleCounter(0);
                jbtnYear2ActionPerformed(null);
                break;
            case "3rd YEAR":
                StudentData.setidleCounter(0);
                jbtnYear3ActionPerformed(null);
                break;
            case "4th YEAR":
                StudentData.setidleCounter(0);
                jbtnYear4ActionPerformed(null);
                break;
            default:
                throw new AssertionError();
        }


     }

//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
//                                                                            changed here
    
    
    //made for the PREREQUISITES BUTTONS
    public String[] preRequisiteStillNeeded = new String[13];
    public String[] currentSubjects = new String[13];
    
  
    
    public int showCustomDialog(String title, String message, String btn1, String btn2){
//        String popupTitle = "Pre-requisite Subjects"; 
//        String popupMessage = 
//                "<b>To enroll in " + currentSubjects[0] +
//                " you need to successfully pass the following subjects:</b><br><br>" + 
//                preRequisiteStillNeeded[0]
//                ;
        _popUp popup = new _popUp(this, true);    
        popup.showPopup(title, message, btn1,btn2); 
                
        // Handle the clicked button 
        int clickedButton = popup.getClickedButton(); 
//        if (clickedButton == 2) { 
//            System.out.println("Button 1 was clicked");
//        }  else if (clickedButton == 1) { 
//            System.out.println("Button 2 was clicked");
//        }
       return clickedButton;
    }
    
    
    private void showGrades(String sID, String yearLevel, String semesterNum) {
    showPanels(); 
    int counter = 0; //for arrays
    int _subjectID;
    int _instructorID=0;
    String _acadYear;
    
    //adding design elements here
   
    JLabel[] codeLabel = { jlblCode1, jlblCode2, jlblCode3, jlblCode4, jlblCode5,jlblCode6,
        jlblCode7, jlblCode8, jlblCode9, jlblCode10, jlblCode11, jlblCode12, jlblCode13 };
    
    JLabel[] descripLabel = { jlblDescrip1, jlblDescrip2, jlblDescrip3, jlblDescrip4, jlblDescrip5,jlblDescrip6,
        jlblDescrip7, jlblDescrip8, jlblDescrip9, jlblDescrip10, jlblDescrip11, jlblDescrip12, jlblDescrip13 };
    
    JLabel[] ratingLabel = { jlblRating1, jlblRating2, jlblRating3, jlblRating4, jlblRating5,jlblRating6,
        jlblRating7, jlblRating8, jlblRating9, jlblRating10, jlblRating11, jlblRating12, jlblRating13 };
    
//    JLabel[] instructorLabel = { jlblInstructor1, jlblInstructor2, jlblInstructor3, jlblInstructor4, jlblInstructor5,jlblInstructor6,
//        jlblInstructor7, jlblInstructor8, jlblInstructor9, jlblInstructor10, jlblInstructor11, jlblInstructor12, jlblInstructor13 };
    
    JButton[] requestButtons = { jbtnRequest1, jbtnRequest2, jbtnRequest3, jbtnRequest4, jbtnRequest5,jbtnRequest6,
        jbtnRequest7, jbtnRequest8, jbtnRequest9, jbtnRequest10, jbtnRequest11, jbtnRequest12, jbtnRequest13 };
    
    JLabel[] deadlineLabel = { jlblDeadline1, jlblDeadline2, jlblDeadline3, jlblDeadline4, jlblDeadline5,jlblDeadline6,
        jlblDeadline7, jlblDeadline8, jlblDeadline9, jlblDeadline10, jlblDeadline11, jlblDeadline12, jlblDeadline13 };
    
    JButton[] seePrerequisitesButtons = { jbtnSeePrerequisites1, jbtnSeePrerequisites2, jbtnSeePrerequisites3, jbtnSeePrerequisites4, jbtnSeePrerequisites5,jbtnSeePrerequisites6,
        jbtnSeePrerequisites7, jbtnSeePrerequisites8, jbtnSeePrerequisites9, jbtnSeePrerequisites10, jbtnSeePrerequisites11, jbtnSeePrerequisites12, jbtnSeePrerequisites13 };
    
    //GETS THE SUBJECTS BASED ON STUDENT CURRICULUM 
    String query = "SELECT * FROM subjects WHERE curriculum = ? AND yearlvl = ? AND semester = ? AND program = ?";
    
    try (Connection conn = DatabaseConnection.connect(this);
        PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, StudentData.getCurriculumCode());
        pstmt.setString(2, yearLevel);
        pstmt.setString(3, semesterNum);
        pstmt.setString(4, StudentData.getProgram());

        ResultSet rs = pstmt.executeQuery();
        counter=0; //counts WHILE iteration
        while (rs.next()) {
                        
            //default of every element: FOREGROUND OF LABELS
            codeLabel[counter].setForeground(Color.BLACK);
            descripLabel[counter].setForeground(Color.BLACK); 
            ratingLabel[counter].setForeground(Color.BLACK);
//            instructorLabel[counter].setForeground(Color.BLACK);
            deadlineLabel[counter].setForeground(Color.BLACK);
            
            //default of seePrerequisite button: HIDDEN 
            seePrerequisitesButtons[counter].setVisible(false);
            
            double rating=0;
            String remark=null;
            String instructor="";
            int requested=0, reviewed=0;
            LocalDate dateGradeUploaded, dateReviewed;
            LocalDate deadline=LocalDate.of(1995, Month.JULY, 15);
            
//            _subjectID = rs.getInt("subject_id");
            String subjectCode = rs.getString("code");  
            currentSubjects[counter] = subjectCode;
//            System.out.println("retrieved: "+subjectCode);
            String subjectDescription = rs.getString("description");
            String preRequisite = rs.getString("prerequisites"); //---------HAS TO BE TRIMMED BY COMMA-SPACE
            preRequisiteStillNeeded[counter]=preRequisite;
            
            
            //GETS THE GRADES AND OTHER INFO BASED ON SUBJECT_CODE FROM ABOVE + STUDENT_ID           
            String queryDescrip = "SELECT * FROM grades WHERE subject_code = ? AND student_id = ?";          
                        
            try (PreparedStatement pstmt1 = conn.prepareStatement(queryDescrip)) {
                pstmt1.setString(1, subjectCode);
                pstmt1.setString(2, StudentData.getStudentID());
                
                ResultSet rs1 = pstmt1.executeQuery();
                if (rs1.next()) {
                    
                    current_GradeIDs[counter] = rs1.getInt("record_id");
                    
                    _acadYear = rs1.getString("acad_year");
                    rating = rs1.getDouble("rating");
                    remark = rs1.getString("remark");
                    _instructorID = rs1.getInt("instructor_id");
                   
                                      
                    String queryPrerequisiteOf = "SELECT * FROM instructors WHERE instructor_id = ?";
                    
                    try (PreparedStatement pstmt3 = conn.prepareStatement(queryPrerequisiteOf)) {

                        pstmt3.setString(1, Integer.toString(_instructorID));

                        ResultSet rs3 = pstmt3.executeQuery();
                        while (rs3.next()) {
                            String ct = rs3.getString("courtesy_title");
                            String fn = rs3.getString("first_name");
                            String ln = rs3.getString("last_name");
                            String suff = rs3.getString("suffix");
                            instructor = ct+". "+fn+" "+ln+" "+suff; 
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
                    
                    
                    requested  = rs1.getInt("review_request");
                    reviewed = rs1.getInt("reviewed");
                    
                    java.sql.Date sqlDateUp = rs1.getDate("uploaded_when");
                    java.sql.Date sqlDateRev = rs1.getDate("reviewed_when");
                                        
                    if (sqlDateUp != null) {
                        dateGradeUploaded = sqlDateUp.toLocalDate();
                        deadline = dateGradeUploaded.plusDays(Days_Allowed_For_Grade_Adjustment);
                    } else {
                        // Handle the case where the Date_Grade_Upload is null if needed
                        dateGradeUploaded = null;
                        deadline = null; // or some default value
                    }                    
                    if (sqlDateRev != null) {
                        dateReviewed = sqlDateRev.toLocalDate();
                    } else {
                        // Handle the case where the Date_Grade_Upload is null if needed
                        dateReviewed = null;
                    }
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            

            //PREREQUISITE CHECK
//            String queryPrerequisiteOf = "SELECT * FROM grades WHERE student_id = ? AND subject_code = ?";
            String preRequisiteRemark="None";
            double preRequisiteRating=0;
            String preRequisiteSubject="";
            boolean passedPreRequisiteCheck=true;
            
            
            
//            NEW PREREQUISITE CHECK
            if (!"None".equals(preRequisite)) {
                String[] preRequList = preRequisite.split(", ");
                
                for (String sub : preRequList){
                    String queryPrerequisiteOf = "SELECT * FROM grades WHERE student_id = ? AND subject_code = ?";
                    
                    try (PreparedStatement pstmt2 = conn.prepareStatement(queryPrerequisiteOf)) {

//                        if (passedPreRequisiteCheck) {
                            pstmt2.setString(1, StudentData.getStudentID());
                            pstmt2.setString(2, sub);

                            ResultSet rs2 = pstmt2.executeQuery();
                            
                            if (!rs2.isBeforeFirst()){
                                passedPreRequisiteCheck=false;
                            }else{
                                while (rs2.next()) {
                                preRequisiteRating = rs2.getDouble("rating");
                                preRequisiteRemark = rs2.getString("remark");
                                preRequisiteSubject = rs2.getString("subject_code");

                                if (preRequisiteRating==0.0 || preRequisiteRating<75 || preRequisiteRemark.contains("INC") || preRequisiteRemark.contains("FAILED")) {
                                    
                                    //secures a false once detected
                                    if (passedPreRequisiteCheck) {
                                        passedPreRequisiteCheck=false;
                                    }
//                                    // Initialize if null 
//                                    if (preRequisiteStillNeeded[counter] == null) { 
//                                        preRequisiteStillNeeded[counter] = ""; 
//                                    }
//                                    
//                                    preRequisiteStillNeeded[counter]+=preRequisiteSubject+"\n";
                                }
                            }
                            }
//                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("------------"+preRequisiteStillNeeded[counter]+"----------------");
                    
                }
            }
            
//            System.out.println(passedPreRequisiteCheck);
            

            
            
            //IF PAST DEADLINE
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, ''yy");
            String deadlineText = (deadline != null) ? deadline.format(formatter) : " ";
            
            //DISPLAY TEXTS
            codeLabel[counter].setText(subjectCode);
            descripLabel[counter].setText(subjectDescription);
            if (rating>0) { ratingLabel[counter].setText(Double.toString(rating));
            }else{ratingLabel[counter].setText("");}
//            instructorLabel[counter].setText(instructor);
            deadlineLabel[counter].setText(deadlineText);
                       
            ratingLabel[counter].setVisible(true); 
            deadlineLabel[counter].setVisible(true);
                    
            //BUTTON CONFIG DEFAULT
            requestButtons[counter].setForeground(Color.WHITE);
            requestButtons[counter].setBackground(requestButtonDefault);
            requestButtons[counter].setEnabled(true);
            requestButtons[counter].setVisible(true);
            requestButtons[counter].setText("Recheck");
            requestButtons[counter].setFont(requestButtons[counter].getFont().deriveFont(18f));
            
            //IF RATING IS 0.0, HIDE REQBUTTON AND DEADLINE
            if (rating==0.0) {
                requestButtons[counter].setVisible(false);
                deadlineLabel[counter].setVisible(false); 
            }
            
            ///IF INCOMPLETE OR FAILED, RED RATING
            if ((remark != null && (remark.contains("INC") || remark.contains("FAILED")))) {
                ratingLabel[counter].setForeground(Color.RED);
                ratingLabel[counter].setText(remark);
                deadlineLabel[counter].setVisible(true);
                requestButtons[counter].setVisible(true);
            }
            
            
            
            
            ///IF PROBLEM WITH PREREQUISITE, GRAY CODE AND DESCRIP
            if (passedPreRequisiteCheck==false) {  
                seePrerequisitesButtons[counter].setVisible(true);
                codeLabel[counter].setForeground(subjectCannotBeTakenYet);
                descripLabel[counter].setForeground(subjectCannotBeTakenYet); 
                ratingLabel[counter].setText("");
                deadlineLabel[counter].setText("");
                requestButtons[counter].setVisible(false); 
                ratingLabel[counter].setVisible(false); 
                deadlineLabel[counter].setVisible(false); 
            }
            
            
            //IF REVIEWED
            if(reviewed==1){
                requestButtons[counter].setText("Resolved");
                requestButtons[counter].setForeground(Color.BLACK);
                requestButtons[counter].setBackground(defaultBtnColor);
            }
            //IF REQUESTED RECHECK
            else if(requested==1){
                requestButtons[counter].setText("Sent");
                requestButtons[counter].setForeground(Color.BLACK);
                requestButtons[counter].setBackground(defaultBtnColor);
            }
                        
            // IF CURRENT DATE IS AFTER THE DEADLINE, DISABLE BUTTON, TURN DATE TO BLUE
            else if (deadline != null && LocalDate.now().isAfter(deadline)) {
                requestButtons[counter].setText("Unavailable");
                requestButtons[counter].setFont(requestButtons[counter].getFont().deriveFont(15f));
                requestButtons[counter].setEnabled(false);
                deadlineLabel[counter].setForeground(Color.GRAY);
            }
//            System.out.println(subjectCode+" -- "+rating+" -- "+instructor+" -- "+deadlineText);
            counter++;
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        hidePanels(counter);
    }        
          
    
    private void hidePanels(int counter) {
        for (int i = 0; i <= 13; i++) {
            if(i>counter){
                switch (i) {
                    case 1:
                        jpanelGrades1.setVisible(false);
                        break;
                    case 2:
                        jpanelGrades2.setVisible(false);
                        break;
                    case 3:
                        jpanelGrades3.setVisible(false);
                        break;
                    case 4:
                        jpanelGrades4.setVisible(false);
                        break;
                    case 5:
                        jpanelGrades5.setVisible(false);
                        break;
                    case 6:
                        jpanelGrades6.setVisible(false);
                        break;
                    case 7:
                        jpanelGrades7.setVisible(false);
                        break;
                    case 8:
                        jpanelGrades8.setVisible(false);
                        break;
                    case 9:
                        jpanelGrades9.setVisible(false);
                        break;
                    case 10:
                        jpanelGrades10.setVisible(false);
                        break;
                    case 11:
                        jpanelGrades11.setVisible(false);
                        break;
                    case 12:
                        jpanelGrades12.setVisible(false);
                        break;
                    case 13:
                        jpanelGrades13.setVisible(false);
                        break;
                    
                    default:
                        throw new AssertionError();
                }
            
            }
        }
    }
        
    
    private void showPanels(){
        jpanelGrades1.setVisible(true);
        jpanelGrades2.setVisible(true);
        jpanelGrades3.setVisible(true);
        jpanelGrades4.setVisible(true);
        jpanelGrades5.setVisible(true);
        jpanelGrades6.setVisible(true);
        jpanelGrades7.setVisible(true);
        jpanelGrades8.setVisible(true);
        jpanelGrades9.setVisible(true);
        jpanelGrades10.setVisible(true);
        jpanelGrades11.setVisible(true);
        jpanelGrades12.setVisible(true);
        jpanelGrades13.setVisible(true);
        
    }
    
   
    
    
    //ASKS THE CONFIRMATION AND SETS CHANGES TO DB
    private void requestReview(int btnReviewNumber){
        resetIdleTimer();
        btnReviewNumber-=1;
        
        JButton[] requestButtons = { jbtnRequest1, jbtnRequest2, jbtnRequest3, jbtnRequest4, jbtnRequest5,jbtnRequest6,
            jbtnRequest7, jbtnRequest8, jbtnRequest9, jbtnRequest10, jbtnRequest11, jbtnRequest12, jbtnRequest13 };
        
        JLabel[] codeLabel = { jlblCode1, jlblCode2, jlblCode3, jlblCode4, jlblCode5,jlblCode6,
        jlblCode7, jlblCode8, jlblCode9, jlblCode10, jlblCode11, jlblCode12, jlblCode13 };
        
        if(requestButtons[btnReviewNumber].getText().equals("Recheck")){
            
            String popupTitle = "Confirm Request"; 
            String popupMessage = 
                    "Are you sure you want to send the request to the registrar?<br><br>This cannot be undone.";
            int response = showCustomDialog(popupTitle, popupMessage, "Yes", "Cancel");
            
//            int response = JOptionPane.showConfirmDialog(
//                null,
//                "Are you sure you want to send the request to the registrar? \nThis cannot be undone.",
//                "Confirm Request",
//                JOptionPane.OK_CANCEL_OPTION,
//                JOptionPane.QUESTION_MESSAGE
//            );
//
//            if (response == JOptionPane.OK_OPTION) {
                if (response == 1) {
                // Code to handle sending the request
                requestButtons[btnReviewNumber].setText("Sent");
                requestButtons[btnReviewNumber].setBackground(new Color(255,204,0));
                requestButtons[btnReviewNumber].setForeground(Color.BLACK);
                
                
                //SET VALUES TO GRADES
                System.out.println("Requested panel # "+btnReviewNumber);
        
                String updateQuery = "UPDATE grades SET review_request = ?, review_request_when = ? WHERE record_id = ?";
                LocalDate currentDate = LocalDate.now();

                try (Connection conn = DatabaseConnection.connect(this);
                     PreparedStatement pr = conn.prepareStatement(updateQuery)) {

                    pr.setInt(1, 1); 
                    pr.setDate(2, java.sql.Date.valueOf(currentDate)); // Set the current date
                    pr.setInt(3, current_GradeIDs[btnReviewNumber]); // Set the subject code or any other identifier

                    int rowsAffected = pr.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Request review updated for panel # " + btnReviewNumber);
                    } else {
                        System.out.println("No record updated for Grade_ID: " + current_GradeIDs[btnReviewNumber]);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } else {
                // Code to handle if the user cancels
                System.out.println("Request canceled by the user.");
            }
        }
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
        jPanel3 = new javax.swing.JPanel();
        jbtnProfile = new javax.swing.JButton();
        jbtnYear2 = new javax.swing.JButton();
        jbtnYear3 = new javax.swing.JButton();
        jbtnYear4 = new javax.swing.JButton();
        jbtnLogout = new javax.swing.JButton();
        jbtnYear1 = new javax.swing.JButton();
        jimgLogoHere150x150 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jlblNameHeader14 = new javax.swing.JLabel();
        jlblNameHeader16 = new javax.swing.JLabel();
        jlblNameHeader17 = new javax.swing.JLabel();
        jlblNameHeader18 = new javax.swing.JLabel();
        jlblNameHeader20 = new javax.swing.JLabel();
        jlblNameHeader21 = new javax.swing.JLabel();
        jlblNameHeader22 = new javax.swing.JLabel();
        jpanelGrades1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jlblCode1 = new javax.swing.JLabel();
        jlblDescrip1 = new javax.swing.JLabel();
        jlblRating1 = new javax.swing.JLabel();
        jlblDeadline1 = new javax.swing.JLabel();
        jbtnRequest1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jbtnSeePrerequisites1 = new javax.swing.JButton();
        jpanelGrades2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jlblCode2 = new javax.swing.JLabel();
        jlblDescrip2 = new javax.swing.JLabel();
        jlblRating2 = new javax.swing.JLabel();
        jbtnRequest2 = new javax.swing.JButton();
        jlblDeadline2 = new javax.swing.JLabel();
        jbtnSeePrerequisites2 = new javax.swing.JButton();
        jpanelGrades4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jlblCode4 = new javax.swing.JLabel();
        jlblDescrip4 = new javax.swing.JLabel();
        jlblRating4 = new javax.swing.JLabel();
        jbtnRequest4 = new javax.swing.JButton();
        jlblDeadline4 = new javax.swing.JLabel();
        jbtnSeePrerequisites4 = new javax.swing.JButton();
        jpanelGrades3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jlblCode3 = new javax.swing.JLabel();
        jlblDescrip3 = new javax.swing.JLabel();
        jlblRating3 = new javax.swing.JLabel();
        jbtnRequest3 = new javax.swing.JButton();
        jlblDeadline3 = new javax.swing.JLabel();
        jbtnSeePrerequisites3 = new javax.swing.JButton();
        jpanelGrades6 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jlblCode6 = new javax.swing.JLabel();
        jlblDescrip6 = new javax.swing.JLabel();
        jlblRating6 = new javax.swing.JLabel();
        jbtnRequest6 = new javax.swing.JButton();
        jlblDeadline6 = new javax.swing.JLabel();
        jbtnSeePrerequisites6 = new javax.swing.JButton();
        jpanelGrades8 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jlblCode8 = new javax.swing.JLabel();
        jlblDescrip8 = new javax.swing.JLabel();
        jlblRating8 = new javax.swing.JLabel();
        jbtnRequest8 = new javax.swing.JButton();
        jlblDeadline8 = new javax.swing.JLabel();
        jbtnSeePrerequisites8 = new javax.swing.JButton();
        jpanelGrades5 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jlblCode5 = new javax.swing.JLabel();
        jlblDescrip5 = new javax.swing.JLabel();
        jlblRating5 = new javax.swing.JLabel();
        jbtnRequest5 = new javax.swing.JButton();
        jlblDeadline5 = new javax.swing.JLabel();
        jbtnSeePrerequisites5 = new javax.swing.JButton();
        jpanelGrades7 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jlblCode7 = new javax.swing.JLabel();
        jlblDescrip7 = new javax.swing.JLabel();
        jlblRating7 = new javax.swing.JLabel();
        jbtnRequest7 = new javax.swing.JButton();
        jlblDeadline7 = new javax.swing.JLabel();
        jbtnSeePrerequisites7 = new javax.swing.JButton();
        jpanelGrades11 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jlblCode11 = new javax.swing.JLabel();
        jlblDescrip11 = new javax.swing.JLabel();
        jlblRating11 = new javax.swing.JLabel();
        jbtnRequest11 = new javax.swing.JButton();
        jlblDeadline11 = new javax.swing.JLabel();
        jbtnSeePrerequisites11 = new javax.swing.JButton();
        jpanelGrades10 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jlblCode10 = new javax.swing.JLabel();
        jlblDescrip10 = new javax.swing.JLabel();
        jlblRating10 = new javax.swing.JLabel();
        jbtnRequest10 = new javax.swing.JButton();
        jlblDeadline10 = new javax.swing.JLabel();
        jbtnSeePrerequisites10 = new javax.swing.JButton();
        jpanelGrades12 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jlblCode12 = new javax.swing.JLabel();
        jlblDescrip12 = new javax.swing.JLabel();
        jlblRating12 = new javax.swing.JLabel();
        jbtnRequest12 = new javax.swing.JButton();
        jlblDeadline12 = new javax.swing.JLabel();
        jbtnSeePrerequisites12 = new javax.swing.JButton();
        jpanelGrades9 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jlblCode9 = new javax.swing.JLabel();
        jlblDescrip9 = new javax.swing.JLabel();
        jlblRating9 = new javax.swing.JLabel();
        jbtnRequest9 = new javax.swing.JButton();
        jlblDeadline9 = new javax.swing.JLabel();
        jbtnSeePrerequisites9 = new javax.swing.JButton();
        jpanelGrades13 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jlblCode13 = new javax.swing.JLabel();
        jlblDescrip13 = new javax.swing.JLabel();
        jlblRating13 = new javax.swing.JLabel();
        jbtnRequest13 = new javax.swing.JButton();
        jlblDeadline13 = new javax.swing.JLabel();
        jbtnSeePrerequisites13 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jlblNameHeader68 = new javax.swing.JLabel();
        jbtnFirstSem = new javax.swing.JButton();
        jbtnSecondSem = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();

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

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtnProfile.setBackground(new java.awt.Color(255, 204, 0));
        jbtnProfile.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnProfile.setForeground(new java.awt.Color(0, 0, 0));
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
        jbtnLogout.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnLogout.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLogout.setText("LOG OUT");
        jbtnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLogoutActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 800, 230, 110));

        jbtnYear1.setBackground(new java.awt.Color(0, 51, 102));
        jbtnYear1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jbtnYear1.setForeground(new java.awt.Color(255, 255, 255));
        jbtnYear1.setText("1ST YEAR");
        jbtnYear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnYear1ActionPerformed(evt);
            }
        });
        jPanel3.add(jbtnYear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 230, 110));

        jimgLogoHere150x150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SLTCFPI_logo_150x150.png"))); // NOI18N
        jPanel3.add(jimgLogoHere150x150, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 150, 150));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 1080));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 380, -1));

        jPanel9.setBackground(new java.awt.Color(0, 51, 102));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblNameHeader14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblNameHeader14.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader14.setText("DESCRIPTION");
        jlblNameHeader14.setToolTipText("");
        jPanel9.add(jlblNameHeader14, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 70));

        jlblNameHeader16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblNameHeader16.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader16.setText("CODE");
        jlblNameHeader16.setToolTipText("");
        jPanel9.add(jlblNameHeader16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 70));

        jlblNameHeader17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblNameHeader17.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader17.setText("RATING");
        jlblNameHeader17.setToolTipText("");
        jPanel9.add(jlblNameHeader17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 70));

        jlblNameHeader18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblNameHeader18.setForeground(new java.awt.Color(255, 204, 0));
        jlblNameHeader18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader18.setText("UNTIL");
        jlblNameHeader18.setToolTipText("");
        jPanel9.add(jlblNameHeader18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 30, 180, 35));

        jlblNameHeader20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblNameHeader20.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader20.setText("RECHECK");
        jlblNameHeader20.setToolTipText("");
        jPanel9.add(jlblNameHeader20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 30, 180, 35));

        jlblNameHeader21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblNameHeader21.setForeground(new java.awt.Color(255, 204, 0));
        jlblNameHeader21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader21.setText("ONLY");
        jlblNameHeader21.setToolTipText("");
        jPanel9.add(jlblNameHeader21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 5, 180, 35));

        jlblNameHeader22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblNameHeader22.setForeground(new java.awt.Color(255, 255, 255));
        jlblNameHeader22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblNameHeader22.setText("REQUEST GRADE");
        jlblNameHeader22.setToolTipText("");
        jPanel9.add(jlblNameHeader22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 5, 180, 35));

        jPanel4.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1630, 70));

        jpanelGrades1.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode1.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode1.setText("CODE");
        jlblCode1.setToolTipText("");
        jpanelGrades1.add(jlblCode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip1.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip1.setText("DESCRIPTION");
        jlblDescrip1.setToolTipText("");
        jpanelGrades1.add(jlblDescrip1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating1.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating1.setText("RATING");
        jlblRating1.setToolTipText("");
        jpanelGrades1.add(jlblRating1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jlblDeadline1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline1.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline1.setToolTipText("");
        jpanelGrades1.add(jlblDeadline1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnRequest1.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest1.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest1.setText("Recheck");
        jbtnRequest1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest1ActionPerformed(evt);
            }
        });
        jpanelGrades1.add(jbtnRequest1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));
        jpanelGrades1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, -10, -1, -1));

        jbtnSeePrerequisites1.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites1.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites1.setText("?");
        jbtnSeePrerequisites1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites1ActionPerformed(evt);
            }
        });
        jpanelGrades1.add(jbtnSeePrerequisites1, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 78, 1630, 55));

        jpanelGrades2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode2.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode2.setText("CODE");
        jlblCode2.setToolTipText("");
        jpanelGrades2.add(jlblCode2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip2.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip2.setText("DESCRIPTION");
        jlblDescrip2.setToolTipText("");
        jpanelGrades2.add(jlblDescrip2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating2.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating2.setText("RATING");
        jlblRating2.setToolTipText("");
        jpanelGrades2.add(jlblRating2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest2.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest2.setText("Recheck");
        jbtnRequest2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest2ActionPerformed(evt);
            }
        });
        jpanelGrades2.add(jbtnRequest2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline2.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline2.setToolTipText("");
        jpanelGrades2.add(jlblDeadline2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites2.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites2.setText("?");
        jbtnSeePrerequisites2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites2ActionPerformed(evt);
            }
        });
        jpanelGrades2.add(jbtnSeePrerequisites2, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 134, 1630, 55));

        jpanelGrades4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades4.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode4.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode4.setText("CODE");
        jlblCode4.setToolTipText("");
        jpanelGrades4.add(jlblCode4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip4.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip4.setText("DESCRIPTION");
        jlblDescrip4.setToolTipText("");
        jpanelGrades4.add(jlblDescrip4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating4.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating4.setText("RATING");
        jlblRating4.setToolTipText("");
        jpanelGrades4.add(jlblRating4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest4.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest4.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest4.setText("Recheck");
        jbtnRequest4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest4ActionPerformed(evt);
            }
        });
        jpanelGrades4.add(jbtnRequest4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline4.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline4.setToolTipText("");
        jpanelGrades4.add(jlblDeadline4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites4.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites4.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites4.setText("?");
        jbtnSeePrerequisites4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites4ActionPerformed(evt);
            }
        });
        jpanelGrades4.add(jbtnSeePrerequisites4, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 246, 1630, 55));

        jpanelGrades3.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades3.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode3.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode3.setText("CODE");
        jlblCode3.setToolTipText("");
        jpanelGrades3.add(jlblCode3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip3.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip3.setText("DESCRIPTION");
        jlblDescrip3.setToolTipText("");
        jpanelGrades3.add(jlblDescrip3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating3.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating3.setText("RATING");
        jlblRating3.setToolTipText("");
        jpanelGrades3.add(jlblRating3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest3.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest3.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest3.setText("Recheck");
        jbtnRequest3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest3ActionPerformed(evt);
            }
        });
        jpanelGrades3.add(jbtnRequest3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline3.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline3.setToolTipText("");
        jpanelGrades3.add(jlblDeadline3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites3.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites3.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites3.setText("?");
        jbtnSeePrerequisites3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites3ActionPerformed(evt);
            }
        });
        jpanelGrades3.add(jbtnSeePrerequisites3, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 1630, 55));

        jpanelGrades6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades6.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode6.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode6.setText("CODE");
        jlblCode6.setToolTipText("");
        jpanelGrades6.add(jlblCode6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip6.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip6.setText("DESCRIPTION");
        jlblDescrip6.setToolTipText("");
        jpanelGrades6.add(jlblDescrip6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating6.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating6.setText("RATING");
        jlblRating6.setToolTipText("");
        jpanelGrades6.add(jlblRating6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest6.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest6.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest6.setText("Recheck");
        jbtnRequest6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest6ActionPerformed(evt);
            }
        });
        jpanelGrades6.add(jbtnRequest6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline6.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline6.setToolTipText("");
        jpanelGrades6.add(jlblDeadline6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites6.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites6.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites6.setText("?");
        jbtnSeePrerequisites6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites6ActionPerformed(evt);
            }
        });
        jpanelGrades6.add(jbtnSeePrerequisites6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 358, 1630, 55));

        jpanelGrades8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades8.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode8.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode8.setText("CODE");
        jlblCode8.setToolTipText("");
        jpanelGrades8.add(jlblCode8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip8.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip8.setText("DESCRIPTION");
        jlblDescrip8.setToolTipText("");
        jpanelGrades8.add(jlblDescrip8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating8.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating8.setText("RATING");
        jlblRating8.setToolTipText("");
        jpanelGrades8.add(jlblRating8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest8.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest8.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest8.setText("Recheck");
        jbtnRequest8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest8ActionPerformed(evt);
            }
        });
        jpanelGrades8.add(jbtnRequest8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline8.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline8.setToolTipText("");
        jpanelGrades8.add(jlblDeadline8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites8.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites8.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites8.setText("?");
        jbtnSeePrerequisites8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites8ActionPerformed(evt);
            }
        });
        jpanelGrades8.add(jbtnSeePrerequisites8, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 1630, 55));

        jpanelGrades5.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades5.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode5.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode5.setText("CODE");
        jlblCode5.setToolTipText("");
        jpanelGrades5.add(jlblCode5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip5.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip5.setText("DESCRIPTION");
        jlblDescrip5.setToolTipText("");
        jpanelGrades5.add(jlblDescrip5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating5.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating5.setText("RATING");
        jlblRating5.setToolTipText("");
        jpanelGrades5.add(jlblRating5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest5.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest5.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest5.setText("Recheck");
        jbtnRequest5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest5ActionPerformed(evt);
            }
        });
        jpanelGrades5.add(jbtnRequest5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline5.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline5.setToolTipText("");
        jpanelGrades5.add(jlblDeadline5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites5.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites5.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites5.setText("?");
        jbtnSeePrerequisites5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites5ActionPerformed(evt);
            }
        });
        jpanelGrades5.add(jbtnSeePrerequisites5, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 302, 1630, 55));

        jpanelGrades7.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades7.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode7.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode7.setText("CODE");
        jlblCode7.setToolTipText("");
        jpanelGrades7.add(jlblCode7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip7.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip7.setText("DESCRIPTION");
        jlblDescrip7.setToolTipText("");
        jpanelGrades7.add(jlblDescrip7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating7.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating7.setText("RATING");
        jlblRating7.setToolTipText("");
        jpanelGrades7.add(jlblRating7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest7.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest7.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest7.setText("Recheck");
        jbtnRequest7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest7ActionPerformed(evt);
            }
        });
        jpanelGrades7.add(jbtnRequest7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline7.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline7.setToolTipText("");
        jpanelGrades7.add(jlblDeadline7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites7.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites7.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites7.setText("?");
        jbtnSeePrerequisites7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites7ActionPerformed(evt);
            }
        });
        jpanelGrades7.add(jbtnSeePrerequisites7, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 414, 1630, 55));

        jpanelGrades11.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades11.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode11.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode11.setText("CODE");
        jlblCode11.setToolTipText("");
        jpanelGrades11.add(jlblCode11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip11.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip11.setText("DESCRIPTION");
        jlblDescrip11.setToolTipText("");
        jpanelGrades11.add(jlblDescrip11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating11.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating11.setText("RATING");
        jlblRating11.setToolTipText("");
        jpanelGrades11.add(jlblRating11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest11.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest11.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest11.setText("Recheck");
        jbtnRequest11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest11ActionPerformed(evt);
            }
        });
        jpanelGrades11.add(jbtnRequest11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline11.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline11.setToolTipText("");
        jpanelGrades11.add(jlblDeadline11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites11.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites11.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites11.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites11.setText("?");
        jbtnSeePrerequisites11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites11ActionPerformed(evt);
            }
        });
        jpanelGrades11.add(jbtnSeePrerequisites11, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 638, 1630, 55));

        jpanelGrades10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades10.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode10.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode10.setText("CODE");
        jlblCode10.setToolTipText("");
        jpanelGrades10.add(jlblCode10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip10.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip10.setText("DESCRIPTION");
        jlblDescrip10.setToolTipText("");
        jpanelGrades10.add(jlblDescrip10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating10.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating10.setText("RATING");
        jlblRating10.setToolTipText("");
        jpanelGrades10.add(jlblRating10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest10.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest10.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest10.setText("Recheck");
        jbtnRequest10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest10ActionPerformed(evt);
            }
        });
        jpanelGrades10.add(jbtnRequest10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline10.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline10.setToolTipText("");
        jpanelGrades10.add(jlblDeadline10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites10.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites10.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites10.setText("?");
        jbtnSeePrerequisites10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites10ActionPerformed(evt);
            }
        });
        jpanelGrades10.add(jbtnSeePrerequisites10, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 582, 1630, 55));

        jpanelGrades12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades12.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode12.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode12.setText("CODE");
        jlblCode12.setToolTipText("");
        jpanelGrades12.add(jlblCode12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip12.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip12.setText("DESCRIPTION");
        jlblDescrip12.setToolTipText("");
        jpanelGrades12.add(jlblDescrip12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating12.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating12.setText("RATING");
        jlblRating12.setToolTipText("");
        jpanelGrades12.add(jlblRating12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest12.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest12.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest12.setText("Recheck");
        jbtnRequest12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest12ActionPerformed(evt);
            }
        });
        jpanelGrades12.add(jbtnRequest12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline12.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline12.setToolTipText("");
        jpanelGrades12.add(jlblDeadline12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites12.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites12.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites12.setText("?");
        jbtnSeePrerequisites12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites12ActionPerformed(evt);
            }
        });
        jpanelGrades12.add(jbtnSeePrerequisites12, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 694, 1630, 55));

        jpanelGrades9.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades9.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode9.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode9.setText("CODE");
        jlblCode9.setToolTipText("");
        jpanelGrades9.add(jlblCode9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip9.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip9.setText("DESCRIPTION");
        jlblDescrip9.setToolTipText("");
        jpanelGrades9.add(jlblDescrip9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating9.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating9.setText("RATING");
        jlblRating9.setToolTipText("");
        jpanelGrades9.add(jlblRating9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest9.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest9.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest9.setText("Recheck");
        jbtnRequest9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest9ActionPerformed(evt);
            }
        });
        jpanelGrades9.add(jbtnRequest9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline9.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline9.setToolTipText("");
        jpanelGrades9.add(jlblDeadline9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites9.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites9.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites9.setText("?");
        jbtnSeePrerequisites9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites9ActionPerformed(evt);
            }
        });
        jpanelGrades9.add(jbtnSeePrerequisites9, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 526, 1630, 55));

        jpanelGrades13.setBackground(new java.awt.Color(255, 255, 255));
        jpanelGrades13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true));
        jpanelGrades13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelGradesALLMouseClicked(evt);
            }
        });
        jpanelGrades13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpanelGrades13.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 930, 50));

        jlblCode13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblCode13.setForeground(new java.awt.Color(0, 0, 0));
        jlblCode13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCode13.setText("CODE");
        jlblCode13.setToolTipText("");
        jpanelGrades13.add(jlblCode13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 55));

        jlblDescrip13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblDescrip13.setForeground(new java.awt.Color(0, 0, 0));
        jlblDescrip13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblDescrip13.setText("DESCRIPTION");
        jlblDescrip13.setToolTipText("");
        jpanelGrades13.add(jlblDescrip13, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 760, 55));

        jlblRating13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlblRating13.setForeground(new java.awt.Color(0, 0, 0));
        jlblRating13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblRating13.setText("RATING");
        jlblRating13.setToolTipText("");
        jpanelGrades13.add(jlblRating13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 160, 55));

        jbtnRequest13.setBackground(new java.awt.Color(0, 153, 51));
        jbtnRequest13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbtnRequest13.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRequest13.setText("Recheck");
        jbtnRequest13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRequest13ActionPerformed(evt);
            }
        });
        jpanelGrades13.add(jbtnRequest13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 180, 55));

        jlblDeadline13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDeadline13.setForeground(new java.awt.Color(0, 0, 0));
        jlblDeadline13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblDeadline13.setToolTipText("");
        jpanelGrades13.add(jlblDeadline13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 180, 55));

        jbtnSeePrerequisites13.setBackground(new java.awt.Color(204, 0, 0));
        jbtnSeePrerequisites13.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSeePrerequisites13.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSeePrerequisites13.setText("?");
        jbtnSeePrerequisites13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeePrerequisites13ActionPerformed(evt);
            }
        });
        jpanelGrades13.add(jbtnSeePrerequisites13, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 50, 55));

        jPanel4.add(jpanelGrades13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, 1630, 55));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("GRAY CODE & DESCRIPTIONS: Missing required subjects (not passed or not taken).");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 810, 940, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 1630, 840));

        jlblNameHeader68.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jlblNameHeader68.setForeground(new java.awt.Color(0, 0, 0));
        jlblNameHeader68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblNameHeader68.setText("#xx YEAR");
        jlblNameHeader68.setToolTipText("");
        jPanel1.add(jlblNameHeader68, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 230, 60));

        jbtnFirstSem.setBackground(new java.awt.Color(0, 51, 102));
        jbtnFirstSem.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnFirstSem.setForeground(new java.awt.Color(255, 255, 255));
        jbtnFirstSem.setText("1st Semester");
        jbtnFirstSem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFirstSemActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnFirstSem, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 360, 80));

        jbtnSecondSem.setBackground(new java.awt.Color(255, 204, 0));
        jbtnSecondSem.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jbtnSecondSem.setText("2nd Semester");
        jbtnSecondSem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSecondSemActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnSecondSem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 90, 360, 80));

        jPanel11.setBackground(new java.awt.Color(255, 204, 0));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 1670, 10));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1670, 50));

        jPanel31.setBackground(new java.awt.Color(255, 204, 0));
        jPanel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel31MouseClicked(evt);
            }
        });
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 1020, 1680, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private JFrame frame;
    private void jbtnSecondSemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSecondSemActionPerformed
////        connect();
//        for (int i = 0; i < currentCourseCodes.length; i++) {
//            currentCourseCodes[i] = null;  // or 0 for int arrays, false for boolean arrays, etc.
//        }
        jbtnSecondSem.setBackground(clickedBtnColor);
        jbtnSecondSem.setForeground(clickedTextColor);
        clickedSemesterBtn="2nd Semester";
        jbtnFirstSem.setBackground(defaultBtnColor);
        jbtnFirstSem.setForeground(defaultTextColor);
//        enableButtons();
        showGrades(StudentData.getStudentID(),clickedYearBtn, clickedSemesterBtn);
//        requestButtonInitialization(StudentData.getStudentID());
    }//GEN-LAST:event_jbtnSecondSemActionPerformed

    private void jbtnFirstSemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFirstSemActionPerformed
                    
        jbtnFirstSem.setBackground(clickedBtnColor);
        jbtnFirstSem.setForeground(clickedTextColor);
        clickedSemesterBtn="1st Semester";
        jbtnSecondSem.setBackground(defaultBtnColor);
        jbtnSecondSem.setForeground(defaultTextColor);
//        showPanels(); //x
//        enableButtons(); //x
        showGrades(StudentData.getStudentID(),clickedYearBtn, clickedSemesterBtn);
//        requestButtonInitialization(StudentData.getStudentID());
    }//GEN-LAST:event_jbtnFirstSemActionPerformed

    private void jbtnRequest2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest2ActionPerformed
        
        requestReview(2);  // Call your method to update the review status        
    }//GEN-LAST:event_jbtnRequest2ActionPerformed

    private void jbtnRequest3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest3ActionPerformed
        
        requestReview(3);  // Call your method to update the review status        
    }//GEN-LAST:event_jbtnRequest3ActionPerformed

    private void jbtnRequest4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest4ActionPerformed
        
        requestReview(4);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest4ActionPerformed

    private void jbtnRequest5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest5ActionPerformed
        
        requestReview(5);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest5ActionPerformed

    private void jbtnRequest6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest6ActionPerformed
        
        requestReview(6);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest6ActionPerformed

    private void jbtnRequest7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest7ActionPerformed
        
        requestReview(7);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest7ActionPerformed

    private void jbtnRequest8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest8ActionPerformed
        
        requestReview(8);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest8ActionPerformed

    private void jbtnRequest9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest9ActionPerformed
        
        requestReview(9);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest9ActionPerformed

    private void jbtnRequest10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest10ActionPerformed
        
        requestReview(10);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest10ActionPerformed

    private void jbtnRequest11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest11ActionPerformed
        
        requestReview(11);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest11ActionPerformed

    private void jbtnRequest12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest12ActionPerformed
        
        requestReview(12);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest12ActionPerformed

    private void jbtnRequest13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest13ActionPerformed
        
        requestReview(13);  // Call your method to update the review status
    }//GEN-LAST:event_jbtnRequest13ActionPerformed

    private void jbtnYear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear1ActionPerformed
        resetIdleTimer();
        System.out.println("[YEAR 1] Viewable Sem Code: "+viewableSemCode);
        
        jbtnYear1.setBackground(clickedBtnColor);
        jbtnYear1.setForeground(clickedTextColor);

        jbtnYear2.setBackground(defaultBtnColor);
        jbtnYear2.setForeground(defaultTextColor);
        jbtnYear3.setBackground(defaultBtnColor);
        jbtnYear3.setForeground(defaultTextColor);
        jbtnYear4.setBackground(defaultBtnColor);
        jbtnYear4.setForeground(defaultTextColor);

        jbtnFirstSem.setEnabled(true);
        jbtnSecondSem.setEnabled(true);
        
        if (viewableSemCode.equals("11")) {
            jbtnSecondSem.setEnabled(false);
            System.out.println("2nd Sem FALSE");
        }
            
        clickedYearBtn="1st YEAR";
        jlblNameHeader68.setText("1st Year");
        jbtnFirstSemActionPerformed(null); //simulates the 1st Semester button being clicked
        
        
        

    }//GEN-LAST:event_jbtnYear1ActionPerformed

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

    private void jbtnYear4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear4ActionPerformed
        resetIdleTimer();
        System.out.println("[YEAR 4] Viewable Sem Code: "+viewableSemCode);

//        enableButtons();
//        
        jbtnYear4.setBackground(clickedBtnColor);
        jbtnYear4.setForeground(clickedTextColor);

        jbtnYear2.setBackground(defaultBtnColor);
        jbtnYear2.setForeground(defaultTextColor);
        jbtnYear1.setBackground(defaultBtnColor);
        jbtnYear1.setForeground(defaultTextColor);
        jbtnYear3.setBackground(defaultBtnColor);
        jbtnYear3.setForeground(defaultTextColor);

        checkViewable();

        jbtnFirstSem.setEnabled(true);
        jbtnSecondSem.setEnabled(true);
        
        if (viewableSemCode.equals("41")) {
            jbtnSecondSem.setEnabled(false);
            System.out.println("2nd Sem FALSE");

        }
        
        clickedYearBtn="4th YEAR";
        jlblNameHeader68.setText("4th Year");
        jbtnFirstSemActionPerformed(null); //simulates the 1st Semester button being clicked
        
        
    }//GEN-LAST:event_jbtnYear4ActionPerformed

    private void jbtnYear3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear3ActionPerformed
        resetIdleTimer();
        System.out.println("[YEAR 3] Viewable Sem Code: "+viewableSemCode);

//        
//        enableButtons();
//        
        jbtnYear3.setBackground(clickedBtnColor);
        jbtnYear3.setForeground(clickedTextColor);

        jbtnYear2.setBackground(defaultBtnColor);
        jbtnYear2.setForeground(defaultTextColor);
        jbtnYear1.setBackground(defaultBtnColor);
        jbtnYear1.setForeground(defaultTextColor);
        jbtnYear4.setBackground(defaultBtnColor);
        jbtnYear4.setForeground(defaultTextColor);

        jbtnFirstSem.setEnabled(true);
        jbtnSecondSem.setEnabled(true);
        
        if (viewableSemCode.equals("31")) {
            jbtnSecondSem.setEnabled(false);
            System.out.println("2nd Sem FALSE");

        }

        clickedYearBtn="3rd YEAR";
        jlblNameHeader68.setText("3rd Year");
        jbtnFirstSemActionPerformed(null); //simulates the 1st Semester button being clicked
//        showGrades(StudentData.getStudentID(),clickedYearBtn, clickedSemesterBtn);
//        requestButtonInitialization(StudentData.getStudentID(),clickedYearBtn, clickedSemesterBtn);

        
    }//GEN-LAST:event_jbtnYear3ActionPerformed

    private void jbtnYear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnYear2ActionPerformed
        resetIdleTimer();
        System.out.println("[YEAR 2] Viewable Sem Code: "+viewableSemCode);

//        enableButtons();
        
        jbtnYear2.setBackground(clickedBtnColor);
        jbtnYear2.setForeground(clickedTextColor);

        jbtnYear1.setBackground(defaultBtnColor);
        jbtnYear1.setForeground(defaultTextColor);
        jbtnYear3.setBackground(defaultBtnColor);
        jbtnYear3.setForeground(defaultTextColor);
        jbtnYear4.setBackground(defaultBtnColor);
        jbtnYear4.setForeground(defaultTextColor);

        jbtnFirstSem.setEnabled(true);
        jbtnSecondSem.setEnabled(true);
        
        if (viewableSemCode.equals("21")) {
            jbtnSecondSem.setEnabled(false);
            System.out.println("2nd Sem FALSE");

        }

        clickedYearBtn="2nd YEAR";
        jlblNameHeader68.setText("2nd Year");
        jbtnFirstSemActionPerformed(null); //simulates the 1st Semester button being clicked
//        showGrades(StudentData.getStudentID(),clickedYearBtn, clickedSemesterBtn);
//        requestButtonInitialization(StudentData.getStudentID(),clickedYearBtn, clickedSemesterBtn);

        
    }//GEN-LAST:event_jbtnYear2ActionPerformed

    private void resetIdleTimer() {
        StudentData.setidleCounter(0);
    }
    
    private void jbtnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnProfileActionPerformed
        FrameManager.closeAllFrames();
        KioskProfile profileWindow = new KioskProfile();
        profileWindow.setVisible(true);

        // Hide and dispose of this window
        setVisible(false);
        dispose();
        FrameManager.addFrame(profileWindow);
        StudentData.setidleCounter(0);
    }//GEN-LAST:event_jbtnProfileActionPerformed

    private void jbtnRequest1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRequest1ActionPerformed
        
        requestReview(1);  // Call your method to update the review status
        
    }//GEN-LAST:event_jbtnRequest1ActionPerformed

    private void jbtnSeePrerequisites1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites1ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[0] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[0]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");

    }//GEN-LAST:event_jbtnSeePrerequisites1ActionPerformed

    private void jbtnSeePrerequisites2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites2ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[1] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[1]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites2ActionPerformed

    private void jbtnSeePrerequisites3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites3ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[2] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[2]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
        
    }//GEN-LAST:event_jbtnSeePrerequisites3ActionPerformed

    private void jbtnSeePrerequisites4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites4ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[3] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[3]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites4ActionPerformed

    private void jbtnSeePrerequisites5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites5ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[4] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[4]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites5ActionPerformed

    private void jbtnSeePrerequisites6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites6ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[5] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[5]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites6ActionPerformed

    private void jbtnSeePrerequisites7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites7ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[6] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[6]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites7ActionPerformed

    private void jbtnSeePrerequisites8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites8ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[7] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[7]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites8ActionPerformed

    private void jbtnSeePrerequisites9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites9ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[8] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[8]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites9ActionPerformed

    private void jbtnSeePrerequisites10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites10ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[9] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[9]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites10ActionPerformed

    private void jbtnSeePrerequisites11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites11ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[10] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[10]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites11ActionPerformed

    private void jbtnSeePrerequisites12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites12ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[11] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[11]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites12ActionPerformed

    private void jbtnSeePrerequisites13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeePrerequisites13ActionPerformed
        resetIdleTimer();
        String popupTitle = "Pre-requisite Subjects"; 
        String popupMessage = 
                "<b>To enroll in " + currentSubjects[12] +
                " you need to successfully pass the following subjects:</b><br><br>" + 
                preRequisiteStillNeeded[12]
                ;
        showCustomDialog(popupTitle, popupMessage, "None", "OK");
    }//GEN-LAST:event_jbtnSeePrerequisites13ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
       resetIdleTimer();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel31MouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jPanel31MouseClicked

    private void jpanelGradesALLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpanelGradesALLMouseClicked
        resetIdleTimer();
    }//GEN-LAST:event_jpanelGradesALLMouseClicked
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
            java.util.logging.Logger.getLogger(KioskViewGrade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KioskViewGrade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KioskViewGrade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KioskViewGrade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new KioskViewGrade().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton jbtnFirstSem;
    private javax.swing.JButton jbtnLogout;
    private javax.swing.JButton jbtnProfile;
    private javax.swing.JButton jbtnRequest1;
    private javax.swing.JButton jbtnRequest10;
    private javax.swing.JButton jbtnRequest11;
    private javax.swing.JButton jbtnRequest12;
    private javax.swing.JButton jbtnRequest13;
    private javax.swing.JButton jbtnRequest2;
    private javax.swing.JButton jbtnRequest3;
    private javax.swing.JButton jbtnRequest4;
    private javax.swing.JButton jbtnRequest5;
    private javax.swing.JButton jbtnRequest6;
    private javax.swing.JButton jbtnRequest7;
    private javax.swing.JButton jbtnRequest8;
    private javax.swing.JButton jbtnRequest9;
    private javax.swing.JButton jbtnSecondSem;
    private javax.swing.JButton jbtnSeePrerequisites1;
    private javax.swing.JButton jbtnSeePrerequisites10;
    private javax.swing.JButton jbtnSeePrerequisites11;
    private javax.swing.JButton jbtnSeePrerequisites12;
    private javax.swing.JButton jbtnSeePrerequisites13;
    private javax.swing.JButton jbtnSeePrerequisites2;
    private javax.swing.JButton jbtnSeePrerequisites3;
    private javax.swing.JButton jbtnSeePrerequisites4;
    private javax.swing.JButton jbtnSeePrerequisites5;
    private javax.swing.JButton jbtnSeePrerequisites6;
    private javax.swing.JButton jbtnSeePrerequisites7;
    private javax.swing.JButton jbtnSeePrerequisites8;
    private javax.swing.JButton jbtnSeePrerequisites9;
    private javax.swing.JButton jbtnYear1;
    private javax.swing.JButton jbtnYear2;
    private javax.swing.JButton jbtnYear3;
    private javax.swing.JButton jbtnYear4;
    private javax.swing.JLabel jimgLogoHere150x150;
    private javax.swing.JLabel jlblCode1;
    private javax.swing.JLabel jlblCode10;
    private javax.swing.JLabel jlblCode11;
    private javax.swing.JLabel jlblCode12;
    private javax.swing.JLabel jlblCode13;
    private javax.swing.JLabel jlblCode2;
    private javax.swing.JLabel jlblCode3;
    private javax.swing.JLabel jlblCode4;
    private javax.swing.JLabel jlblCode5;
    private javax.swing.JLabel jlblCode6;
    private javax.swing.JLabel jlblCode7;
    private javax.swing.JLabel jlblCode8;
    private javax.swing.JLabel jlblCode9;
    private javax.swing.JLabel jlblDeadline1;
    private javax.swing.JLabel jlblDeadline10;
    private javax.swing.JLabel jlblDeadline11;
    private javax.swing.JLabel jlblDeadline12;
    private javax.swing.JLabel jlblDeadline13;
    private javax.swing.JLabel jlblDeadline2;
    private javax.swing.JLabel jlblDeadline3;
    private javax.swing.JLabel jlblDeadline4;
    private javax.swing.JLabel jlblDeadline5;
    private javax.swing.JLabel jlblDeadline6;
    private javax.swing.JLabel jlblDeadline7;
    private javax.swing.JLabel jlblDeadline8;
    private javax.swing.JLabel jlblDeadline9;
    private javax.swing.JLabel jlblDescrip1;
    private javax.swing.JLabel jlblDescrip10;
    private javax.swing.JLabel jlblDescrip11;
    private javax.swing.JLabel jlblDescrip12;
    private javax.swing.JLabel jlblDescrip13;
    private javax.swing.JLabel jlblDescrip2;
    private javax.swing.JLabel jlblDescrip3;
    private javax.swing.JLabel jlblDescrip4;
    private javax.swing.JLabel jlblDescrip5;
    private javax.swing.JLabel jlblDescrip6;
    private javax.swing.JLabel jlblDescrip7;
    private javax.swing.JLabel jlblDescrip8;
    private javax.swing.JLabel jlblDescrip9;
    private javax.swing.JLabel jlblNameHeader14;
    private javax.swing.JLabel jlblNameHeader16;
    private javax.swing.JLabel jlblNameHeader17;
    private javax.swing.JLabel jlblNameHeader18;
    private javax.swing.JLabel jlblNameHeader20;
    private javax.swing.JLabel jlblNameHeader21;
    private javax.swing.JLabel jlblNameHeader22;
    private javax.swing.JLabel jlblNameHeader68;
    private javax.swing.JLabel jlblRating1;
    private javax.swing.JLabel jlblRating10;
    private javax.swing.JLabel jlblRating11;
    private javax.swing.JLabel jlblRating12;
    private javax.swing.JLabel jlblRating13;
    private javax.swing.JLabel jlblRating2;
    private javax.swing.JLabel jlblRating3;
    private javax.swing.JLabel jlblRating4;
    private javax.swing.JLabel jlblRating5;
    private javax.swing.JLabel jlblRating6;
    private javax.swing.JLabel jlblRating7;
    private javax.swing.JLabel jlblRating8;
    private javax.swing.JLabel jlblRating9;
    private javax.swing.JPanel jpanelGrades1;
    private javax.swing.JPanel jpanelGrades10;
    private javax.swing.JPanel jpanelGrades11;
    private javax.swing.JPanel jpanelGrades12;
    private javax.swing.JPanel jpanelGrades13;
    private javax.swing.JPanel jpanelGrades2;
    private javax.swing.JPanel jpanelGrades3;
    private javax.swing.JPanel jpanelGrades4;
    private javax.swing.JPanel jpanelGrades5;
    private javax.swing.JPanel jpanelGrades6;
    private javax.swing.JPanel jpanelGrades7;
    private javax.swing.JPanel jpanelGrades8;
    private javax.swing.JPanel jpanelGrades9;
    // End of variables declaration//GEN-END:variables

    private void checkViewable() {
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

        jbtnFirstSem.setEnabled(true);
        jbtnSecondSem.setEnabled(true);
        
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

    



    
}
