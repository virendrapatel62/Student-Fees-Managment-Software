
package models;

import com.sun.org.apache.regexp.internal.REUtil;
import controllers.Details;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static models.MonthlyFee.getConnection;

public class InstalmentFee {
    private Student student;
    private Batch batch;
    private Integer id;
    private Date date;
    private Integer amount;
    
     public static void main(String[] args){
        System.err.println(getCurrentMonthIncomeBatchVise());
    }

    public InstalmentFee(Student student, Batch batch, Integer id, Date date, Integer amount) {
        this.student = student;
        this.batch = batch;
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public InstalmentFee(Student student, Batch batch,  Date date, Integer amount) {
        this.student = student;
        this.batch = batch;
        this.date = date;
        this.amount = amount;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public Student getStudent() {
        return student;
    }

    public Batch getBatch() {
        return batch;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }
    
    public static Connection getConnection() throws Exception{
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        return connection;
    }
     
    public static void createInstalmentFeeTable(){
        try{
            Connection con  = getConnection();
            String sql = "CREATE TABLE [InstalmentFee](" +
                        "  [id] INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  [student_id] INTEGER NOT NULL REFERENCES [students]([student_id])," +
                        "  [batch_id] INTEGER NOT NULL REFERENCES [batches]([batch_id])," +
                        "  [date] DATE NOT NULL," +
                        "  [amount] INTEGER NOT NULL)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.close();
            System.err.println("Intallment Fee Table Created");
            
        }catch(Exception ex){
            System.out.println("Intallment table Table alredy exists");
        }
    }
    
     public void save()throws Exception{
        Connection con = getConnection();
        String sql = "insert INTO InstalmentFee "
                + "(student_id , batch_id , date , amount)"
                + " values (? , ? , ? , ?);";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(2, this.batch.getBatchId());
        ps.setInt(1, this.student.getStudentId());
        ps.setDate(3, date);
        ps.setInt(4, amount);
        ps.executeUpdate();
        con.close();
    }
     
    public static ArrayList<InstalmentFee> getDetails(Student student , Batch batch){
        ArrayList<InstalmentFee> list = new ArrayList();
        try{
            Connection con = getConnection();
            String sql = "select amount , "
                    + "date "
                    + "from InstalmentFee "
                    + "WHERE student_id = ? "
                    + "and batch_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, batch.getBatchId());
            ps.setInt(1, student.getStudentId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                InstalmentFee is = new InstalmentFee(student , batch , rs.getDate(2) , rs.getInt(1));
                list.add(is);
                System.err.println(is.date);
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public static ObservableList<Details> getFeesStatement(Student student , Batch batch){
        ObservableList<Details> list = FXCollections.observableArrayList();
        try{
            Connection con = getConnection();
            String sql = "SELECT amount ,"
                    + " date , id from "
                    + "InstalmentFee "
                    + "WHERE student_id = ?"
                    + " and batch_id= ? ORDER BY date asc ";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, batch.getBatchId());
            ps.setInt(1, student.getStudentId());
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat smf = new SimpleDateFormat("dd MMM YYYY");
            SimpleDateFormat smf2 = new SimpleDateFormat("hh:mm:ss a");
            int i = 1;
            int total =0;
            while(rs.next()){
                Details d = new Details();
                d.setSno(i++);
                int fees = batch.getBatchFee();
                int paid = rs.getInt(1);
                total = total+ paid;
                d.setAmount(paid);
                d.setRemaining(fees-total);
                d.setDate(smf.format(rs.getDate(2)));
                d.setTime(smf2.format(rs.getDate(2)));
                d.setId(rs.getInt(3));
                list.add(d);
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public static Integer getTotalPaid(Student student){
       int paid = 0;
        try{
            Connection con = getConnection();
            String sql = "SELECT sum(amount) "
                    + " from "
                    + "InstalmentFee "
                    + "WHERE student_id = ?";
                    

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, student.getStudentId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                paid = rs.getInt(1);
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return paid;
    }
    
    public  static ObservableList<HashMap> getIncomeBatchVise(Date from , Date to){
        ObservableList<HashMap> list = FXCollections.observableArrayList();
        Integer amount  = 0;
        try {
            String sql = "select  sum(amount) , "
                    + "batch_name , batch_fee ,"
                    + " b.batch_id from "
                    + "InstalmentFee as i INNER JOIN batches"
                    + " as b on "
                    + "b.batch_id = i.batch_id "
                    + " where date >= ? and date <= ? group by i.batch_id  ";
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, from);
            ps.setDate(2, to);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HashMap map = new HashMap();
                map.put("batch", new Batch(rs.getString(2).toUpperCase(), rs.getInt(3), rs.getInt(3)));
                map.put("income", rs.getInt(1));
                list.add(map);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println("models.InstalmentFee.getIncomeBatchVise()"+ list);;
        return list;
        
    }
    
    public static ObservableList<HashMap> getCurrentMonthIncomeBatchVise(){
        
            Calendar from = Calendar.getInstance();
            System.err.println("from.get(Calendar.DAY_OF_MONTH)" + from.get(Calendar.DAY_OF_MONTH));
            from.add(Calendar.DAY_OF_MONTH, -from.get(Calendar.DAY_OF_MONTH));
            
            Calendar to = Calendar.getInstance();
            to.set(Calendar.DAY_OF_MONTH, to.getActualMaximum(Calendar.DAY_OF_MONTH));
            return getIncomeBatchVise(new Date(from.getTimeInMillis()) , new Date(to.getTimeInMillis()));
    }
    
    public static  boolean undoPay(int id){
        try{
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("delete from InstalmentFee where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("models.InstalmentFee.finalize()"+ this);
    }
    
    
    
    
    
}
