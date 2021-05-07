
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import emailsender.DataSender;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import prefrences.Profile;

public class SendEmailBoxController implements Initializable {

    @FXML
    private JFXProgressBar progess;
    @FXML
    private JFXTextField email ;
    @FXML
    private Label error;
    @FXML
    private JFXButton send;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         error.setVisible(false);
                error.setManaged(false);
               
        email.setText( Profile.getEmail());
    }    

    @FXML
    private void send(ActionEvent event) {
        String email = this.email.getText();
        Stage stage = (Stage)(send.getScene().getWindow());
        
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println(".call()");
                if(email.trim().length()!=0){
                    new DataSender().sendTo(email);
                }
                return null;
            }
        };
        
        task.setOnScheduled(new EventHandler() {
            @Override
            public void handle(Event event) {
                send.setText("Sending..");
                progess.setVisible(true);
            }
        });
        
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                error.setText("Sent Successfuly");
                System.out.println(".handle()");
                onFailAndSuccess();
            }
        });
        
        task.setOnFailed((ev) -> {
            error.setText("Invalid email or check internet connection !");
            onFailAndSuccess();
        });
        new Thread(task).start();
            
    }
    
    public void onFailAndSuccess(){
        error.setVisible(true);
        send.setText("Send");
        progess.setVisible(false);
        error.setManaged(true);
    }

    @FXML
    private void closeStage(MouseEvent event) {
        Stage stage = (Stage)(email.getScene().getWindow());
        stage.close();
    }
    
}
