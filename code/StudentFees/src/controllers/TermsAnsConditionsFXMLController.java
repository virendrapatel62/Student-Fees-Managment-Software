/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import prefrences.Profile;
import prefrences.Theme;

public class TermsAnsConditionsFXMLController implements Initializable {

    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private JFXButton next;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void next(ActionEvent event) {
        Profile.acceptedTermsAndConditions();
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainFrame.fxml"));
           Parent root = loader.load();
           Stage stage = new Stage();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           scene.getStylesheets().clear();
           scene.getStylesheets().add(Theme.getMainFrameCss());
           stage.setMaximized(true);
           stage.show();
           ((Stage)(checkBox.getScene().getWindow())).close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        ((Stage)(checkBox.getScene().getWindow())).close();
    }

    @FXML
    private void isCheked(ActionEvent event) {
        next.setDisable(!checkBox.isSelected());
    }
    
}
