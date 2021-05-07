package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.*;
import org.controlsfx.control.Notifications;
import prefrences.Theme;

public class FeesDetailsController implements Initializable {

    private ObservableList<Details> details = FXCollections.observableArrayList();
    ObservableList<String> d = FXCollections.observableArrayList();
    private Student student;
        
    @FXML
    private TableColumn<Details , Integer> snoColumn;
    @FXML
    private TableColumn<Details, String> dateColumn;
    @FXML
    private TableColumn<Details, String> timeColumn;
    @FXML
    private TableColumn<Details, Integer> amountColumn;
    @FXML
    private TableColumn<Details , Integer> remainingColumn;
    private Label studentName;
    @FXML
    private TableView<Details> detailTable;
    private Label batch;
    private Label fee;
    private Label paid;
    @FXML
    private ListView<String> detailsList;
    @FXML
    private TableColumn<Details, Month> monthColumn;
    @FXML
    private ImageView profile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableSetup();
    }    
    
    private void tableSetup(){
        detailTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        detailTable.setItems(details);
        snoColumn.setCellValueFactory(new PropertyValueFactory<>("sno"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
    }

    public FeesDetailsController() {
    }
    
    
    public void setStudent(Student s){
        this.student = s;
        detailsList.setItems(d);
        d.add("Name        - "+s.getStudentName());
        d.add("Course       - "+s.getBatch().getBatchName());
        getAllFeesDetails();
        profile.setImage(s.getProfilePic());
    }
    
    private void getAllFeesDetails(){
        
        if(student.getBatch().getFeesMode() == FeesMode.instalment){
            details.addAll(InstalmentFee.getFeesStatement(student, student.getBatch()));
            
            d.add("Total Fees  - "+ "Rs. "+student.getBatch().getBatchFee());
            d.add("Paid        - "+ "Rs. "+InstalmentFee.getTotalPaid(student));
            
            monthColumn.setVisible(false);
            remainingColumn.setVisible(true);
            amountColumn.setVisible(true);
            System.err.println("hide month Column");
        }else{
            details.addAll(MonthlyFee.getFeesStatement(student, student.getBatch()));
            
            d.add("Monthly Fees  - "+ "Rs. "+student.getBatch().getBatchFee());
            d.add("Paid          - "+details.size() +
                    " * "+ student.getBatch().getBatchFee()
                    +" = Rs. " + details.size() * student.getBatch().getBatchFee() );
            
            monthColumn.setVisible(true);
            remainingColumn.setVisible(false);
            amountColumn.setVisible(false);
        }
        detailTable.refresh();;
    }

    @FXML
    private void showReceipt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Receipt.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getReceiptCss()).toExternalForm());
            ReceiptController com = (ReceiptController)(loader.getController());
            ObservableList<Details> ds = detailTable.getSelectionModel().getSelectedItems();
            com.setStudent(student, ds);
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FeesDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void UndoPayFees(ActionEvent event) {
        if(student.getBatch().getFeesMode() == FeesMode.instalment){
            Details d = detailTable.getSelectionModel().getSelectedItem();
            int id = d.getId();
            
            if(!InstalmentFee.undoPay(id)){
                Notifications.create().text("Can not undo.. Sorry !!").title("Undo Failed").showInformation();
            }else{
                detailsList.getItems().clear();
                details.clear();
                getAllFeesDetails();
            }
        }else{
            
        }
    }
    
}
