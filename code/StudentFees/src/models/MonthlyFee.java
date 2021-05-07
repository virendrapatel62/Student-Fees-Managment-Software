
package models;
import controllers.Details;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static models.InstalmentFee.getConnection;

public class MonthlyFee {
    private Student student;
    private Batch batch;
    private Integer id;
    private Date date;
    private Month month;

    public MonthlyFee() {
    }

    public MonthlyFee(Student student, Batch batch, Integer id, Date date) {
        this.student = student;
        this.batch = batch;
        this.id = id;
        this.date = date;
    }

    public Batch getBatch() {
        return batch;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
    
    
    
    public static void createMonthlyFeeTable(){
        try{
            Connection con  = getConnection();
            String sql = "CREATE TABLE [MonthlyFee](" +
                        "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "  [date] DATE NOT NULL," +
                        "  [student_id] INTEGER NOT NULL REFERENCES [students]([student_id])," +
                        "  [batch_id] INTEGER NOT NULL REFERENCES [batches]([batch_id])," +
                        "  [month_id] INTEGER NOT NULL)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.close();
            System.err.println("MonthLy Fee Table Created");
            
        }catch(Exception ex){
            System.out.println("monthly table Table alredy exists");
        }
    }
    
     public static Connection getConnection() throws Exception{
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        return connection;
    }
     
    
    
    public void save()throws Exception{
        Connection con = getConnection();
        String sql = "INSERT INTO MonthlyFee (batch_id , student_id , date , month_id) VALUES (?,?,?,?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, this.batch.getBatchId());
        ps.setInt(2, this.student.getStudentId());
        ps.setDate(3, date);
        ps.setInt(4, month.getIndex());
        ps.executeUpdate();
        con.close();
    }
    
    public static boolean isPaid(Month month , Student student , Batch batch)throws Exception{
        Connection con = getConnection();
        boolean flag = false;
        String sql = "select * from MonthlyFee "
                + "WHERE batch_id = ? "
                + "and student_id = ? "
                + "and month_id = ?";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, batch.getBatchId());
        ps.setInt(2, student.getStudentId());
        ps.setInt(3, month.getIndex());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            flag = true;
        }
        con.close();
        return flag;
    }
    
    public static ObservableList<Details> getFeesStatement(Student student , Batch batch){
        ObservableList<Details> list = FXCollections.observableArrayList();
        try{
            Connection con = getConnection();
            String sql = "SELECT month_id ,"
                    + " date , id "
                    + " from MonthlyFee "
                    + "where student_id = ? "
                    + "and batch_id = ? "
                    + "order BY date asc";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, batch.getBatchId());
            ps.setInt(1, student.getStudentId());
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat smf = new SimpleDateFormat("dd MMM YYYY");
            SimpleDateFormat smf2 = new SimpleDateFormat("hh:mm:ss a");
            int i = 1;
            while(rs.next()){
                Details d = new Details();
                d.setSno(i++);
                d.setDate(smf.format(rs.getDate(2)));
                d.setTime(smf2.format(rs.getDate(2)));
                d.setMonth(Month.getMonth(rs.getInt(1)));
                d.setId(rs.getInt(1));
                list.add(d);
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public  static ObservableList<HashMap> getIncomeBatchVise(Date from , Date to){
        Integer amount = 0;
        ObservableList<HashMap> list = FXCollections.observableArrayList();
        try {
            
            String sql = "select  count(batch_name)*batch_fee as income , "
                    + "batch_name , batch_fee , b.batch_id "
                    + "from MonthlyFee as i INNER JOIN batches as b "
                    + "on b.batch_id = i.batch_id "
                    + " where date >= ? and date <= ? group by i.batch_id  ";
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, from );
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
        return list;
        
    }
    
    
    public static ObservableList<HashMap> getCurrentMonthIncomeBatchVise(){
        
        Calendar from = Calendar.getInstance();
        System.err.println("from.get(Calendar.DAY_OF_MONTH)" + from.get(Calendar.DAY_OF_MONTH));
        from.add(Calendar.DAY_OF_MONTH, -from.get(Calendar.DAY_OF_MONTH));

        Calendar to = Calendar.getInstance();
        to.set(Calendar.DAY_OF_MONTH, to.getActualMaximum(Calendar.DAY_OF_MONTH));
            return getIncomeBatchVise(new Date(from.getTimeInMillis()), new Date(to.getTimeInMillis()));
        
    }
    
    
    
    public static Integer getTotalPaid(Student student){
       int count = 0;
        try{
            Connection con = getConnection();
            String sql = "select count(student_id) from MonthlyFee where student_id = ?";
                    

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, student.getStudentId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return student.getBatch().getBatchFee()*count;
    }
    
    
    
    
}
