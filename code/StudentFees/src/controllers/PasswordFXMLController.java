
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import prefrences.Password;

public class PasswordFXMLController implements Initializable {

    @FXML
    private Label message;
    @FXML
    private JFXPasswordField oldPassword;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField reenterNewPassword;
    @FXML
    private JFXButton saveButton;
    @FXML
    private Label error;
    
    private MenuItem setPassword;
    private MenuItem removePassword;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void showAsChangePassword(){
        message.setText("Change Password");
        saveButton.setText("Update Password");
        oldPassword.setVisible(true);
        oldPassword.setManaged(true);
        newPassword.requestFocus();
    }
    
    public void showAsSetPassword(){
        message.setText("Set Password");
        saveButton.setText("Set Password");
        oldPassword.setManaged(false);
        newPassword.requestFocus();
    }
    
    public void showAsRemovePassword(){
        message.setText("Remove Password");
        saveButton.setText("Remove Password");
        oldPassword.setManaged(false);
        reenterNewPassword.setManaged(false);
        newPassword.requestFocus();
        newPassword.setPromptText("Enter Password");
                
    }

    public void setRemovePassword(MenuItem removePassword) {
        this.removePassword = removePassword;
    }
    
    

    public void setSetPassword(MenuItem setPassword) {
        this.setPassword = setPassword;
    }
    
    

    @FXML
    private void setPassword(ActionEvent event) {
        if(message.getText().equalsIgnoreCase("Change Password")){
            changePassword(event);
        }else if(message.getText().equalsIgnoreCase("Remove Password")){
            removePassword(event);
        }else{
            String o = oldPassword.getText();
            String np = newPassword.getText();
            String rnp = reenterNewPassword.getText();
            if(np== null && rnp == null){
                error.setText("All feilds are required !!");
            }else{
                error.setText("");
                
                np = np.trim();
                rnp = rnp.trim();
                
                if(np.length()<6 || rnp.length()<6 ){
                    error.setText("password must be in more then 5 characters");
                }else{
                    Password p  = new Password();
                    p.setPassword(np);
                    oldPassword.clear();
                    newPassword.clear();
                    reenterNewPassword.clear();
                    setPassword.setText("Change Password");
                    
                    removePassword.setVisible(true);
                    
                    ((Stage)(error.getScene().getWindow())).close();
                    
                }
            }
        }
    }
    
    private void removePassword(ActionEvent event){
            String np = newPassword.getText();
            if(np== null){
                error.setText("All feilds are required !!");
            }else{
                error.setText("");
                np = np.trim();
                
                if(np.length()<6 ){
                    error.setText("password must be in more then 5 characters");
                }else{
                    Password p  = new Password();
                    if(p.login(np)){
                        p.removePassword();
                        
                        oldPassword.clear();
                        newPassword.clear();
                        reenterNewPassword.clear();
                        ((Stage)(error.getScene().getWindow())).close();
                        removePassword.setVisible(false);
                        setPassword.setText("Set Password");
                    }else{
                        error.setText("passsword is wrong");
                    }
                    
                }
            }
    }
    
    private void changePassword(ActionEvent event){
            String o = oldPassword.getText();
            String np = newPassword.getText();
            String rnp = reenterNewPassword.getText();
            if(np== null && rnp == null && o==null){
                error.setText("All feilds are required !!");
            }else{
                error.setText("");
                o = o.trim();
                np = np.trim();
                rnp = rnp.trim();
                
                if(np.length()<6 || rnp.length()<6 || o.length()<6 ){
                    error.setText("password must be in more then 5 characters");
                }else if(!np.equalsIgnoreCase(rnp)){
                    error.setText("password not matched");
                }else{
                    Password p  = new Password();
                    if(p.login(o)){
                        p.setPassword(np);
                        oldPassword.clear();
                        newPassword.clear();
                        reenterNewPassword.clear();
                        ((Stage)(error.getScene().getWindow())).close();
                    }else{
                        error.setText("old passsword is wrong");
                    }
                    
                }
            }
    }

    @FXML
    private void setPasswordOnKeyPress(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            setPassword(null);
        }
    }

    
}
