/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ExportImportProgressController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label heading;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private Label info;
    @FXML
    private ProgressIndicator processer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    
    
    public void endProccesing(){
        processer.setProgress(0.5);
    }

    public ProgressIndicator getProcesser() {
        return processer;
    }

    public JFXProgressBar getProgressBar() {
        return progressBar;
    }
    

    public Label getHeading() {
        return heading;
    }

    public Label getInfo() {
        return info;
    }
   
    
    public void show(){
        Stage stage  = (Stage)(pane.getScene().getWindow());
        stage.show();
    }
    
    public void hide(){
        Stage stage  = (Stage)(pane.getScene().getWindow());
        stage.hide();
    }
    
    public void close(){
        Stage stage  = (Stage)(pane.getScene().getWindow());
        stage.close();
    }
    
}
