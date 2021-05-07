package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import prefrences.Profile;

public class Student implements Serializable{
    
    private String studentName;
    private Integer studentId;
    private String  fatherName;
    private String mobilNumber;
    private String addharNumber;
    private Date dob;
    private Gender gender;
    private String address;
    private String school;
    private Batch batch;
    private Month joiningMonth;
    private StudentType studentType = StudentType.ADMITTED; 
    private Integer paid = 0;
    private Integer pending =0;
    private Date admissionDate;
    
    public static final Integer numberOfRowToShow = 100;
    public static Integer rowShown = 0;
    public static Integer rowShownEnquiry = 0;

    
    
    
    
    public static void refresh(){
        rowShown = 0;
        rowShownEnquiry = 0;
    }

    public Student() {
        gender = Gender.notSpecified;
        admissionDate = new Date(new java.util.Date().getTime());
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public Integer getPaid() {
        return paid;
    }

    public Integer getPending() {
        return pending;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("models.Student.finalize()"+ this);;
    }
    
    
    
    
    
    

    public Student(String studentName, String fatherName, String mobilNumber, String addharNumber, Date dob, Gender gender, String address, String school, Batch batch) {
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.mobilNumber = mobilNumber;
        this.addharNumber = addharNumber;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.school = school;
        this.batch = batch;
    }

    public Month getJoiningMonth() {
        return joiningMonth;
    }

    public void setJoiningMonth(Month joiningMonth) {
        this.joiningMonth = joiningMonth;
    }

        
    public String getMobilNumber() {
        return mobilNumber;
    }

    public String getAddharNumber() {
        return addharNumber;
    }

    public Date getDob() {
        return dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setMobilNumber(String mobilNumber) {
        this.mobilNumber = mobilNumber;
    }

    public void setAddharNumber(String addharNumber) {
        this.addharNumber = addharNumber;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    

    public String getStudentName() {
        return studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getAddress() {
        return address;
    }

    public String getSchool() {
        return school;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
    
    @Override
    public String toString() {
        return  studentName ;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student){
            Student s =(Student)obj;
               if(s.studentId == this.studentId){
                   return true;
               }
        }else{
            return false;
        }
        return false;
    }
    
    
    
    public static Connection getConnection() throws Exception{
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        return connection;
    }
    
    public static void createStudentTable(){
        Connection con;
        try {
            con = getConnection();

            String sql ="CREATE TABLE [students](" +
                        "  [student_name] TEXT NOT NULL, " +
                        "  [student_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "  [dob] DATE, " +
                        "  [gender] INTEGER, " +
                        "  [aadhar_number] TEXT, " +
                        "  [fathers_name] TEXT, " +
                        "  [batch_id] INTEGER NOT NULL REFERENCES [batches]([batch_id]) ON DELETE CASCADE, " +
                        "  [address] TEXT, " +
                        "  [college] TEXT, " +
                        "  [joining_month_id] INTEGER, " +
                        "  [mobil] TEXT, " +
                        "  [student_type] INTEGER NOT NULL DEFAULT 1"
                        + " ,[admission_date] DATE);"; 
            Statement ps = con.createStatement();
            ps.executeUpdate(sql);
            ps.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("models.Student.createStudentTable()");
            System.out.println(ex.getMessage());
        }
   }
    
    public Integer saveStudent() throws Exception{
        int id  = -1;
        Connection con = getConnection();
        String sql = "INSERT INTO students (student_name , "
                    + "fathers_name ,"
                    + " address , "
                    + "aadhar_number ,"
                    + " college , "
                    + "dob , "
                    + "gender ,"
                    + " mobil , "
                    + " joining_month_id , "
                    + "batch_id , student_type , admission_date) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,? , ?, ? );";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1 , studentName);
        ps.setString(2 , fatherName);
        ps.setString(3 , address);
        ps.setString(4 , addharNumber);
        ps.setString(5 , school);
        ps.setDate(6, dob);
        ps.setInt(7, gender.getId());
        ps.setString(8, mobilNumber);
        if(getJoiningMonth()==null){
            ps.setInt(9, -1);
        }else{
             ps.setInt(9, getJoiningMonth().getIndex());
        }
        
        ps.setInt(10, batch.getBatchId());
        ps.setInt(11, getStudentType().getId());
        ps.setDate(12, admissionDate);
        
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            id = rs.getInt(1);
        }
        con.close();
       return id;
    }

    public void updateStudent() throws Exception{
        Connection con = getConnection();
        String sql = "UPDATE students SET "
                + "student_name = ? ,"
                + " fathers_name = ? ,"
                + " aadhar_number=? ,"
                + " address = ? ,"
                + " batch_id = ? ,"
                + " college = ? ,"
                + " dob  = ? ,"
                + " gender = ? ,"
                + " mobil = ? , "
                + " joining_month_id = ?"
                + " where student_id = ?";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1 , studentName);
        ps.setString(2 , fatherName);
        ps.setString(3 , addharNumber);
        ps.setString(4 , address);
        ps.setInt(5, batch.getBatchId());
        ps.setString(6 , school);
        ps.setDate(7, dob);
        ps.setInt(8, gender.getId());
        ps.setString(9, mobilNumber);
        if(getJoiningMonth()==null){
            ps.setInt(10, -1);
        }else{
             ps.setInt(10, getJoiningMonth().getIndex());
        }
        ps.setInt(11, studentId);
        
        ps.executeUpdate();
        ps.close();;
        con.close();
    }
    
    public static ObservableList<Student> getAdmittedStudents(Integer from , Integer to) throws Exception{
        
        Connection con = getConnection();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name ,"
                + " batch_fee ,"
                + " student_id ,"
                + " joining_month_id , "
                 + " start_month_id ,"
                + " fees_mode_id , student_type"
                + " from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id and student_type = ? limit ? , ?";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, StudentType.getADMITTED().getId());
        ps.setInt(2, from);
        ps.setInt(3, to);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Student s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.setStudentType(StudentType.getStudentType(rs.getInt(16)));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
            list.add(s);
        }
        
        con.close();
        
        return list;
    }
    
    private static Integer getPending(Student s){
        if(s.getBatch().getFeesMode() == FeesMode.instalment){
            return (s.getBatch().getBatchFee() - s.getPaid());
        }else{
            return -1;
        }
    }
    public static ObservableList<Student> getAdmittedStudents() throws Exception{
        
        Connection con = getConnection();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name ,"
                + " batch_fee ,"
                + " student_id ,"
                + " joining_month_id , "
                 + " start_month_id ,"
                + " fees_mode_id , student_type "
                + " from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id and student_type = ? limit "+ numberOfRowToShow;
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, StudentType.ADMITTED.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Student s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.setStudentType(StudentType.getStudentType(rs.getInt(16)));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
            list.add(s);
        }
        
        con.close();
        
        return list;
    }
    
    public static ObservableList<Student> getEnquiryStudents(Integer from , Integer to) throws Exception{
        
        Connection con = getConnection();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name ,"
                + " batch_fee ,"
                + " student_id ,"
                + " joining_month_id , "
                 + " start_month_id ,"
                + " fees_mode_id , student_type , admission_date "
                + " from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id and student_type = ? limit "+from+" , "+to;
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, StudentType.getENQUIRY().getId());
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Student s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.setStudentType(StudentType.getStudentType(rs.getInt(16)));
            s.setAdmissionDate(rs.getDate(17));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
            list.add(s);
        }
        
        con.close();
        
        return list;
    }
    public static ObservableList<Student> getEnquiryStudents() throws Exception{
        
        Connection con = getConnection();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name ,"
                + " batch_fee ,"
                + " student_id ,"
                + " joining_month_id , "
                 + " start_month_id ,"
                + " fees_mode_id , student_type , admission_date "
                + " from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id and student_type = ? limit "+ numberOfRowToShow;
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, StudentType.ENQUIRY.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Student s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.setStudentType(StudentType.getStudentType(rs.getInt(16)));
            s.setAdmissionDate(rs.getDate(17));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
            list.add(s);
        }
        
        con.close();
        
        return list;
    }
    
    public static ObservableList getStudents(Batch batch) throws Exception{
        Connection con = getConnection();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name ,"
                + " batch_fee ,"
                + " student_id ,"
                + " joining_month_id ,"
                + " start_month_id ,"
                + " fees_mode_id from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id where students.batch_id = (?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, batch.getBatchId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Student s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            
            jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
            list.add(s);
        }
        con.close();
        
        return list;
    }
    
    public static Student getStudent(Integer id) throws Exception{
        Connection con = getConnection();
        Student s = null;
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name , "
                + "batch_fee ,"
                + " student_id  ,"
                + " joining_month_id ,"
                + " start_month_id ,"
                + " fees_mode_id from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id where students.student_id = (?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            
             jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
                  
        }
        con.close();
        
        return s;
    }
    
    public static ObservableList<Student> getStudents(String startWith) throws Exception{
        Connection con = getConnection();
        ObservableList<Student> students = FXCollections.observableArrayList();
        Student s = null;
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name , "
                + "batch_fee ,"
                + " student_id  ,"
                + " joining_month_id ,"
                + " start_month_id ,"
                + " fees_mode_id from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id where student_name LIKE ? or student_id LIKE ? ;";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, startWith+"%");
        ps.setString(2, startWith+"%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            s = new Student();
            s.studentName =  rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9) , rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if(jm!=-1){
                s.joiningMonth = Month.getMonth(jm);
            }else{
                s.joiningMonth = null;
            }
            
             jm = rs.getInt(14);
            if(jm!=-1){
                s.batch.setStartMonth(Month.getMonth(jm));
            }else{
                 s.getBatch().setStartMonth(null);
            }
            
            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);
            
            students.add(s);
                  
        }
        con.close();
        
        return students;
    }
    
    public static ObservableList<Student> getEnquiryStudents(String startWith) throws Exception {
        Connection con = getConnection();
        ObservableList<Student> students = FXCollections.observableArrayList();
        Student s = null;
        String sql = "SELECT  student_name ,"
                + " fathers_name ,"
                + " address ,"
                + " aadhar_number ,"
                + " college ,"
                + " dob ,"
                + " gender ,"
                + " mobil , "
                + "students.batch_id ,"
                + " batch_name , "
                + "batch_fee ,"
                + " student_id  ,"
                + " joining_month_id ,"
                + " start_month_id ,"
                + " fees_mode_id from "
                + "students inner join batches on"
                + " students.batch_id = batches.batch_id where (student_name LIKE ? or student_id LIKE ? or address LIKE ? or batch_name LIKE ? or mobil LIKE ? ) and student_type = ? ;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%"+startWith + "%");
        ps.setString(2, startWith + "%");
        ps.setString(3, "%"+startWith + "%");
        ps.setString(4, "%"+startWith + "%");
        ps.setString(5, startWith + "%");
        ps.setInt(6, StudentType.ENQUIRY.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            s = new Student();
            s.studentName = rs.getString(1);
            s.fatherName = rs.getString(2);
            s.address = rs.getString(3);
            s.addharNumber = rs.getString(4);
            s.school = rs.getString(5);
            s.dob = rs.getDate(6);
            s.gender = Gender.getGender(rs.getInt(7));
            s.mobilNumber = rs.getString(8);
            s.batch = new Batch(rs.getString(10), rs.getInt(9), rs.getInt(11));
            s.studentId = rs.getInt(12);
            int jm = rs.getInt(13);
            if (jm != -1) {
                s.joiningMonth = Month.getMonth(jm);
            } else {
                s.joiningMonth = null;
            }

            jm = rs.getInt(14);
            if (jm != -1) {
                s.batch.setStartMonth(Month.getMonth(jm));
            } else {
                s.getBatch().setStartMonth(null);
            }

            s.batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(15)));
            s.paid = getTotalPaidFee(s);
            s.pending = getPending(s);

            students.add(s);

        }
        con.close();

        return students;
    }
    
    public static void delete(ObservableList<Student> list) throws Exception{
        Connection con = getConnection();
        Student s = null;
        
        String sql = "delete from students WHERE student_id = ?";
        Iterator<Student> itr = list.iterator();
        while(itr.hasNext()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, itr.next().getStudentId());
            int i = ps.executeUpdate();
            System.err.println(i + "  recorders deleted");
        }
        
        con.close();
        
    }

    public static void delete(Batch batch) throws Exception{
        Connection con = getConnection();
        
        String sql = "delete from students WHERE batch_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        con.close();
        
    }
    
    public static class SortById implements  Comparator<Student>{
        @Override
        public int compare(Student o1, Student o2) {
            return Integer.compare(o1.studentId, o2.studentId);
        }
        
    }
    
    public static Integer size() throws Exception{
        Connection con = getConnection();
        String sql = "SELECT COUNT(student_id) from students";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs  = ps.executeQuery();
        int size = 0;
        if(rs.next()){
            size = rs.getInt(1);
        }
        con.close();
        return size;
    }
    public static Integer sizeOfAdmitted() throws Exception{
        Connection con = getConnection();
        String sql = "SELECT COUNT(student_id) from students where student_type = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , StudentType.ADMITTED.getId());
        ResultSet rs  = ps.executeQuery();
        int size = 0;
        if(rs.next()){
            size = rs.getInt(1);
        }
        con.close();
        return size;
    }
    public static Integer sizeOfEnquiry() throws Exception{
        Connection con = getConnection();
        String sql = "SELECT COUNT(student_id) from students where student_type = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , StudentType.ENQUIRY.getId());
        ResultSet rs  = ps.executeQuery();
        int size = 0;
        if(rs.next()){
            size = rs.getInt(1);
        }
        con.close();
        return size;
    }
    
    
    
    
    public static ObservableList<Student> loadNextAdmitted() throws Exception{
        System.err.println(rowShown + " , " + numberOfRowToShow );
        ObservableList<Student> students = FXCollections.observableArrayList();
        if(numberOfRowToShow*(rowShown/numberOfRowToShow)>size()){
            return students;
        }else{
            rowShown = rowShown + numberOfRowToShow;
            students = getAdmittedStudents(rowShown, numberOfRowToShow);
            return students;
        }
    }
    public static ObservableList<Student> loadNextEnquiry() throws Exception{
        System.out.println(rowShownEnquiry + " , " + numberOfRowToShow );
        ObservableList<Student> students = FXCollections.observableArrayList();
        if(numberOfRowToShow*(rowShownEnquiry/numberOfRowToShow)>sizeOfEnquiry()){
            return students;
        }else{
            rowShownEnquiry = rowShownEnquiry + numberOfRowToShow;
            students = getEnquiryStudents(rowShownEnquiry, numberOfRowToShow);
            return students;
        }
    }
    public static ObservableList<Student> loadPreviousEnquiry() throws Exception{
        ObservableList<Student> students = FXCollections.observableArrayList();
        if(rowShownEnquiry!=0){
            students =  getEnquiryStudents(rowShownEnquiry-numberOfRowToShow, numberOfRowToShow);
            rowShownEnquiry = rowShownEnquiry-numberOfRowToShow;
            return students;
        }else{
            return students;
        }
        
    }
    
    public static ObservableList<Student> loadPreviousAdmitted() throws Exception{
        ObservableList<Student> students = FXCollections.observableArrayList();
        if(rowShown!=0){
            students =  getAdmittedStudents(rowShown-numberOfRowToShow, numberOfRowToShow);
            rowShown = rowShown-numberOfRowToShow;
            return students;
        }else{
            return students;
        }
        
    }
    
    
    
    public boolean markAdmitted(){
        boolean flag = false;
        try{
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE students SET student_type = ? where student_id = ?");
            ps.setInt(1, studentType.ADMITTED.getId());
            ps.setInt(2, studentId);

            int i = ps.executeUpdate();
            if(i>0){
                flag = true;
            }
            
            java.util.Date date = new java.util.Date();
            int month = date.getMonth()+1;
            
            String sql = "UPDATE students SET joining_month_id = ? where student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, studentId);
            ps.executeUpdate();

            con.close();
        
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return flag ;
        
    }
    public boolean markEnquiry(){
        boolean flag = false;
        try{
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE students SET student_type = ? where student_id = ?");
            ps.setInt(1, studentType.ENQUIRY.getId());
            ps.setInt(2, studentId);

            int i = ps.executeUpdate();
            if(i>0){
                flag = true;
            }

            con.close();
        
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return flag ;
        
    }
    
    
    
    public static Integer getTotalPaidFee(Student s){
        if(s.batch.getFeesMode() == FeesMode.instalment){
            return InstalmentFee.getTotalPaid(s);
        }else{
            return MonthlyFee.getTotalPaid(s);
        }
    }
    
    
    public Image getProfilePic(){
        try{
            File file = new File(Profile.profilePicPath+"/"+studentId);
            String[] pics = file.list();
            if(pics==null){
               return new Image(getClass().getResourceAsStream("/images/avtar.png"));
            }
            int i = pics.length;
            if(i==0){
                return new Image(getClass().getResourceAsStream("/images/avtar.png"));
            }else{
                return new Image(new FileInputStream(new File(file , (i-1)+".jpg")));
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new Image(getClass().getResourceAsStream("/images/avtar.png"));
        }
        
    }
    
    
    
    
}
