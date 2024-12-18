/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author USER
 */
public class StudentData {
    private static String studentID;
    private static String lastName;
    private static String firstName;
    private static String middleName;
    private static String program;
    private static String programLong;
    private static String curriculumCode;
    private static String yearLevel;
    private static String block;
    private static String currentRFID; 
    private static String status;
    private static double tuition;
    private static boolean fetchedGradeFromDb;
    private static int currentSemester;
    private static String pin;
    private static boolean InLogin;
    private static boolean blinking=false;
    
    public static boolean getBlinking() {
        return blinking;
    }
    
    public static void setBlinking(boolean x) {
        blinking = x;
    }
    
    public static boolean getInLogin() {
        return InLogin;
    }
    
    public static void setInLogin(boolean x) {
        InLogin = x;
    }
    
    public static String getPin() {
        return pin;
    }
    
    public static void setPin(String _pin) {
        pin = _pin;
    }
    
    public static int getCurrentSemester() {
        return currentSemester;
    }
    
    public static void setCurrentSemester(int sem) {
        currentSemester = sem;
    }
    
    public static String btnCarryOver; //determines button clicked fromprofile to view    
    
//    public static List<Grade> grades = new ArrayList<>();
    
    //public static ArrayList 
    
    public static double getTuition() {
        return tuition;
    }
    
    public static void setTuition(double tuition1) {
        tuition = tuition1;
    }
    
    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status1) {
        status = status1;
    }
    
    public static String getCurriculumCode() {
        return curriculumCode;
    }

    public static void setCurriculumCode(String curriculumCode1) {
        curriculumCode = curriculumCode1;
    }
    
    public static boolean getFetchedGradeFromDb() {
        return fetchedGradeFromDb;
    }

    public static void setfetchedGradeFromDb(boolean fetched) {
        fetchedGradeFromDb = fetched;
    }

    // Getter and Setter for studentID
    public static String getStudentID() {
        return studentID;
    }

    public static void setStudentID(String id) {
        studentID = id;
    }

    // Getter and Setter for lastName
    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String last) {
        lastName = last;
    }

    // Getter and Setter for firstName
    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String first) {
        firstName = first;
    }

    // Getter and Setter for middleName
    public static String getMiddleName() {
        return middleName;
    }

    public static void setMiddleName(String middle) {
        middleName = middle;
    }

    // Getter and Setter for program
    public static String getProgram() {
        return program;
    }

    public static void setProgram(String prog) {
        program = prog;
    }
    
    // Getter and Setter for programLong
    public static String getProgramLong() {
        return programLong;
    }

    public static void setProgramLong(String progL) {
        programLong = progL;
    }

    // Getter and Setter for yearLevel
    public static String getYearLevel() {
        return yearLevel;
    }

    public static void setYearLevel(String year) {
        yearLevel = year;
    }
    
    // Getter and Setter for block
    public static String getBlock() {
        return block;
    }

    public static void setBlock(String blk) {
        block = blk;
    }

    // Getter and Setter for currentRFID
    public static String getCurrentRFID() {
        return currentRFID;
    }

    public static void setCurrentRFID(String rfid) {
        currentRFID = rfid;
    }
}
