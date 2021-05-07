
package controllers;

import com.jfoenix.controls.JFXButton;
import controllers.Details;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import javax.management.Notification;
import models.Batch;
import models.FeesMode;
import models.Student;
import org.controlsfx.control.Notifications;
import prefrences.Password;
import prefrences.Profile;

public class ReceiptController implements Initializable {
    
    
    private Student student;
    
    boolean printed = false;
    private final int rnumber = new Password().getReceiptNumber();
    private ObservableList<TableData> tableData = FXCollections.observableArrayList();
    private ObservableList<Details> details = FXCollections.observableArrayList();

    @FXML
    private VBox reciptBox;
    @FXML
    private Label heading;
    @FXML
    private Label instituteName ;
    @FXML
    private JFXButton print;
    @FXML
    private Label studentId;
    @FXML
    private Label studentName;
    @FXML
    private Label fathersName;
    @FXML
    private Label batch;
    @FXML
    private Label dateToday;
    
    @FXML
    private TableView<TableData> table;
    @FXML
    private TableColumn<TableData, Integer> snoColumn;
    @FXML
    private TableColumn<TableData, String> paricularsColumn;
    @FXML
    private TableColumn<TableData, Integer> amountColumn;
    @FXML
    private TableColumn<TableData, String> dateOfSubmission;
    @FXML
    private Label total;
    @FXML
    private Label address;
    @FXML
    private Label totalLabel;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableSetup();
        instituteName.setText(Profile.getInstituteName());
        address.setText(Profile.getAddress() + "( Mobile - "+Profile.getMobile()+" )" );
        table.setEditable(true);
        paricularsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    } 
    
    
    
    private void tableSetup(){
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setItems(tableData);
        snoColumn.setCellValueFactory(new PropertyValueFactory<>("sno") );
        paricularsColumn.setCellValueFactory(new PropertyValueFactory<>("particulars") );
        dateOfSubmission.setCellValueFactory(new PropertyValueFactory<>("date") );
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount") );
    }

    @FXML
    private void print(ActionEvent event) {
        Printer p = Printer.getDefaultPrinter();
        PrinterJob pj = PrinterJob.createPrinterJob(p);
        
        PageLayout pl = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0 ,0,0,0);
        if(pj.showPrintDialog(reciptBox.getScene().getWindow())){
            System.err.println(pj.jobStatusProperty().asString());
            if(pj.printPage(pl , reciptBox)){
                Batch b = student.getBatch();
                File file = new File("Receipts");
                if (!file.exists()) {
                    file.mkdir();
                }
                File file2 = new File(file, b.getBatchName());
                if (!file2.exists()) {
                    file2.mkdir();
                }

                File name = new File(file2, student.getStudentId() + "_"
                        + student.getStudentName() + "_"
                        + rnumber + ".png");

                WritableImage image = reciptBox.snapshot(new SnapshotParameters(), null);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", name);
                    Notifications.create().title("Receipt Saved..").text("Receipt Saved in location "
                            + file.getAbsolutePath()).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               
                if(!printed){
                    printed = true;
                    new Password().increaseReceiptNumber();;
                }
            }
            pj.endJob();
        }
        
        
    }
    
    public void setStudent(Student student , ObservableList<Details> details){
        this.details = details;
        this.student = student;
        setText();
        totalLabel.setText("Rs. ");
    }
    
    
    private void setText(){
          studentId.setText(student.getStudentId()+"");
          studentName.setText(student.getStudentName());
          fathersName.setText(student.getFatherName());
          batch.setText(student.getBatch().toString());
          
          heading.setText("ReceiptNo. "+rnumber);
         
          SimpleDateFormat s = new SimpleDateFormat(" dd/MM/YYYY");
          dateToday.setText(s.format(new Date()));
          
         Iterator<Details> itr = details.iterator();
         int i = 1;
         int total = 0;
         if(student.getBatch().getFeesMode()==FeesMode.instalment){
            while(itr.hasNext()){
                Details dd = itr.next();
                TableData data = new TableData();
                data.setParticulars("Tution fee");
                data.setAmount(dd.getAmount());
                total = total + dd.getAmount();
                data.setDate(dd.getDate());
                data.setSno(i++);
                this.total.setText(total+" only");
                tableData.add(data);
            }
         }else{
             int fee = student.getBatch().getBatchFee();
             while(itr.hasNext()){
                Details dd = itr.next();
                TableData data = new TableData();
                data.setParticulars("Tution fee of "+dd.getMonth());
                data.setAmount(fee);
                total = total + fee;
                data.setDate(dd.getDate());
                data.setSno(i++);
                this.total.setText(total+" only");
                tableData.add(data);
                
            }
             
         }
         
         
          
          
    }
    

    @FXML
    private void changeParticular(TableColumn.CellEditEvent event) {
        table.getSelectionModel().getSelectedItem().particulars = (String)event.getNewValue();
    }

    
    public class TableData{
        private String particulars;
        private Integer amount;
        private Integer sno;
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
        
        

        public String getParticulars() {
            return particulars;
        }

        public Integer getAmount() {
            return amount;
        }

        public Integer getSno() {
            return sno;
        }

        public void setParticulars(String particulars) {
            this.particulars = particulars;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public void setSno(Integer sno) {
            this.sno = sno;
        }
        
        
    }
    
    
    
}
