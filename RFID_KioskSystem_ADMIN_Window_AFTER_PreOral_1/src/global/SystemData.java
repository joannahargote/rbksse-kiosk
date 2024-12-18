/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;




/**
 *
 * @author USER
 */
public class SystemData {
//    LOGIN
    private static String login_password;
    
    public static String get_login_password() {
        return login_password;
    }
    public static void set_login_password(String x) {
        login_password = x;
    }
    
//    GRADE-----------------------------------
    private static String grade_curriculumCode, grade_program, grade_yearLvl, 
            grade_block, grade_semester, grade_subCode, grade_subDescrip, 
            grade_oldYear, grade_oldMonth, grade_oldDay, cleanedSubject, grade_subjectId;
    private static boolean grade_oldRecord;
    
    public static String get_grade_curriculumCode() {
        return grade_curriculumCode;
    }
    public static void set_grade_curriculumCode(String id) {
        grade_curriculumCode = id;
    }
    public static String get_grade_program() {
        return grade_program;
    }
    public static void set_grade_program(String id) {
        grade_program = id;
    }
    public static String get_grade_yearLvl() {
        return grade_yearLvl;
    }
    public static void set_grade_yearLvl(String id) {
        grade_yearLvl = id;
    }
    public static String get_grade_block() {
        return grade_block;
    }
    public static void set_grade_block(String id) {
        grade_block = id;
    }
    public static String get_grade_semester() {
        return grade_semester;
    }
    public static void set_grade_semester(String id) {
        grade_semester = id;
    }
    public static String get_grade_subCode() {
        return grade_subCode;
    }
    public static void set_grade_subCode(String id) {
        grade_subCode = id;
    }
    public static String get_grade_subDescrip() {
        return grade_subDescrip;
    }
    public static void set_grade_subDescrip(String id) {
        grade_subDescrip = id;
    }
    public static String get_grade_oldYear() {
        return grade_oldYear;
    }
    public static void set_grade_oldYear(String id) {
        grade_oldYear = id;
    }
    public static String get_grade_oldMonth() {
        return grade_oldMonth;
    }
    public static void set_grade_oldMonth(String id) {
        grade_oldMonth = id;
    }
    public static String get_grade_oldDay() {
        return grade_oldDay;
    }
    public static void set_grade_oldDay(String id) {
        grade_oldDay = id;
    }
    public static boolean get_grade_oldRecord() {
        return grade_oldRecord;
    }
    public static void set_grade_oldRecord(boolean id) {
        grade_oldRecord = id;
    }
    public static String get_grade_cleanedSubject() {
        return cleanedSubject;
    }
    public static void set_grade_cleanedSubject(String id) {
        cleanedSubject = id;
    }
    public static String get_grade_subjectId() {
        return grade_subjectId;
    }
    public static void set_grade_subjectId(String id) {
        grade_subjectId = id;
    }
    
    
//    END OF GRADE-----------------------------------
    
}
