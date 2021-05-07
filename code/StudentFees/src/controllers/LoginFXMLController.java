
package controllers;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Batch;
import models.InstalmentFee;
import models.MonthlyFee;
import models.Student;
import prefrences.Password;
import prefrences.Profile;
import prefrences.Theme;

public class LoginFXMLController implements Initializable {
    
    Parent root;
    FXMLLoader loader ;
    Stage stage = new Stage();

    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            loader = new FXMLLoader(getClass().getResource("/FXML/MainFrame.fxml"));
            root = loader.load();
            stage.initStyle(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            
            try{
                 stage.getIcons().add(Profile.getLogo());
            }catch(Exception ex){
                ex.printStackTrace();
            }
            System.err.println("Theme code: " + Theme.getThemeCode());
            System.err.println(Theme.getMainFrameCss());
            
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getMainFrameCss()).toExternalForm());
            
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getDynamicCss()).toExternalForm());
            
            
            readData();;
            
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }   
    
    public void show(){
        stage.show();
    }

    @FXML
    private void login(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            login();
        }
    }

    @FXML
    private void login(ActionEvent event) {
        login();
    }
    
    
    private void login(){
        
        String pass = password.getText();
        if(pass!=null){
            if(new Password().login(pass)){
                ((Stage)(password.getScene().getWindow())).close();
                stage.show();
            }else{
                Alert alert = new  Alert(Alert.AlertType.WARNING);
                alert.setContentText("You Entered Wrong Password !!");
                alert.show();
            }
        }
        
    }
    
    // Reading  data
    
    public void readData(){
        stage.setTitle(Profile.getInstituteName());
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    System.out.println(".call()" +" initialixation data");
                    Batch.createBatchTable();
                    Student.createStudentTable();
                    MonthlyFee.createMonthlyFeeTable();
                    InstalmentFee.createInstalmentFeeTable();

                    ObservableList batches = Batch.getBatches();
                    ObservableList students = Student.getAdmittedStudents();
                    ObservableList enquiruStudents = Student.getEnquiryStudents();
                    MainFrameController con = (MainFrameController) (loader.getController());

                    setPasswordMenuSetup(con);

                    con.getBatchList().addAll(batches);

                    con.getStudentList().addAll(students);
                    con.getEnquiryStudentList().addAll(enquiruStudents);
                    con.pichartInitilization();
                    con.dashboardSetup();

                    con.initializeMonthIncomeChart();

                    System.err.println("Password is : " + new Password().getPassword());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
    
    private void setPasswordMenuSetup(MainFrameController con ){
        Password  p = new Password();
        if(p.isPasswordSetted()){
            con.getSetPasswordMenuItem().setText("Change Password");
            con.getRemovePasswordMenuItem().setVisible(true);
        }else{
            con.getSetPasswordMenuItem().setText("Set Password");
            con.getRemovePasswordMenuItem().setVisible(false);
        }
    }
    
    
}
