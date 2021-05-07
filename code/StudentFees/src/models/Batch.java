/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static models.Student.getConnection;

public class Batch implements Serializable{
    private String batchName;
    private Integer batchId;
    private Integer batchFee;
    private FeesMode feesMode;
    private Month startMonth;

    public Batch() {
    }

    public Batch(String batchName, Integer batchId , Integer batchFee ) {
        this.batchName = batchName;
        this.batchId = batchId;
        this.batchFee = batchFee;
    }

    public Month getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Month startMonth) {
        this.startMonth = startMonth;
    }
    
    

    public Batch(String batchName, Integer batchFee) {
        this.batchName = batchName;
        this.batchFee = batchFee;
    }

    public void setFeesMode(FeesMode feesMode) {
        this.feesMode = feesMode;
    }

    public FeesMode getFeesMode() {
        return feesMode;
    }
    
    
    
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public void setBatchFee(Integer batchFee) {
        this.batchFee = batchFee;
    }
    
    

    public Integer getBatchId() {
        return batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public Integer getBatchFee() {
        return batchFee;
    }

    @Override
    public String toString() {
        return batchName; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static Connection getConnection() throws Exception{
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        return connection;
    }
    
    public static void createBatchTable(){
        try{
            Connection con = getConnection();
            String sql = "CREATE TABLE [batches](\n" +
                        "  [batch_name] TEXT NOT NULL, \n" +
                        "  [batch_id] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                        "  [batch_fee] INTEGER NOT NULL, \n" +
                        "  [fees_mode_id] INTEGER NOT NULL, \n" +
                        "  [start_month_id] INTEGER);";
            Statement ps = con.createStatement();
            ps.executeUpdate(sql);
            ps.close();
            con.close();
        }catch(Exception ex){
            System.out.println("models.Batch.createBatchTable()");
            System.out.println(ex.getMessage());
        }
    }
    
    public Integer saveBatch() throws Exception{
        Connection con = getConnection();
        String sql = "insert into batches (batch_name ,batch_fee, fees_mode_id , start_month_id)"+
                " values(?,? ,? , ?)";
        
        PreparedStatement ps = con.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getBatchName());
        ps.setInt(2, getBatchFee());
        ps.setInt(3, getFeesMode().getIndex());
        if(getStartMonth()!=null){
            ps.setInt(4, getStartMonth().getIndex());
        }else{
            ps.setInt(4, -1);
        }
        
        int i  = ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            i = rs.getInt(1);
        }
        System.out.println(i + " is key  updated");
        ps.close();
        con.close();
        return i;
    }
    
    public static ObservableList<Batch> getBatches() throws Exception{
        Connection con = getConnection();
        String sql = "select batch_name ,batch_id ,batch_fee , fees_mode_id , start_month_id from batches ";
        ObservableList<Batch> list = FXCollections.observableArrayList();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs =   ps.executeQuery();
        while(rs.next()){
            Batch batch = new Batch();
            batch.setBatchId(rs.getInt(2));
            batch.setBatchName(rs.getString(1).toUpperCase());
            batch.setBatchFee(rs.getInt(3));
            batch.setFeesMode(FeesMode.getFeesMode(rs.getInt(4)));
            int m = rs.getInt(5);
            if(m!=-1){
                batch.setStartMonth(Month.getMonth(m));
            }else{
                batch.setStartMonth(null);
            }
            list.add(batch);
            System.out.println(batch + " row getted");
        }
        
        ps.close();
        con.close();
        return list;
    }
    
    
    public static void main(String[] args) throws Exception{
        System.out.println(getConnection());
        createBatchTable();
        System.out.println(getBatches());
    }
    
    public static void delete(ObservableList<Batch> list) throws Exception{
        
        Connection con = getConnection();
        String sql = "delete from batches WHERE batch_id = ?";
        Iterator<Batch> itr = list.iterator();
        while(itr.hasNext()){
            Batch batch = itr.next();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,batch.getBatchId());
            int i = ps.executeUpdate();
            System.err.println(i + "batch recorders deleted" );
        }
        con.close();
    }
    
    public void upadte() throws Exception{
        String sql = "UPDATE batches SET"
                + " batch_name=? ,"
                + " batch_fee = ?"
                + " where batch_id = ?";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1 ,  batchName);
        ps.setInt(2, batchFee);
        ps.setInt(3, batchId);
        ps.executeUpdate();
        con.close();
    }
    
    public Integer getStundentCount(){
        try{
            Connection con = getConnection();
            String sql = "SELECT COUNT(student_id) FROM students WHERE batch_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1 ,this.batchId);
            ResultSet rs  = ps.executeQuery();
            int size = 0;
            if(rs.next()){
                size = rs.getInt(1);
            }
            con.close();
            
            return size;
        }catch(Exception ex){
         
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("models.Batch.finalize() " + this );
    }
    
    
    

   
    
    
}
