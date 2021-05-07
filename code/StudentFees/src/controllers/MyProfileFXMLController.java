
package controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import prefrences.Profile;

public class MyProfileFXMLController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField instituteName;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField mobile;
    
    SimpleStringProperty nm = new SimpleStringProperty(Profile.getName());
    SimpleStringProperty ins = new SimpleStringProperty(Profile.getInstituteName());
    SimpleStringProperty addr = new SimpleStringProperty(Profile.getAddress());
    SimpleStringProperty em = new SimpleStringProperty(Profile.getEmail());
    SimpleStringProperty mob = new SimpleStringProperty(Profile.getMobile());
    @FXML
    private ImageView logo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        name.textProperty().bindBidirectional(nm);
        email.textProperty().bindBidirectional(em);
        address.textProperty().bindBidirectional(addr);
        mobile.textProperty().bindBidirectional(mob);
        instituteName.textProperty().bindBidirectional(ins);
        setLogo();
        System.gc();
        // TODO
    }    
    
    private void setLogo(){
            try{
                logo.setImage(Profile.getLogo());
            }catch(Exception ex){
                ex.printStackTrace();
            }
            System.gc();
    }

    @FXML
    private void save(ActionEvent event) {
        System.gc();
        Pattern p = Pattern.compile("\\d{10}");
        Matcher m= p.matcher(mob.get());
        Alert a = new Alert(Alert.AlertType.ERROR, "Mobil number is valid", ButtonType.OK);
        a.setTitle("Invalid Mobile");
        if(!m.matches()){
            a.show();
        }else{
            Profile.setAddress(addr.get());
            Profile.setEmail(em.get());
            Profile.setInstituteName(ins.get());
            Profile.setMobile(mob.get());
            Profile.setName(nm.get());
            
            
            ((Stage)(name.getScene().getWindow())).close();
//            a = new Alert(Alert.AlertType.INFORMATION, "Profile Updated Successfully", ButtonType.OK);
//            a.setTitle("Done");
//            a.show();
        }
        
    }

    @FXML
    private void uploadLogo(MouseEvent event) {
        
        try {
            
            System.gc();
            FileChooser chooseImage = new FileChooser();
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png", "*.jpeg", "*.gif"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG images", "*.jpg"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG images", "*.jpeg"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF images", "*.gif"));
            File file = chooseImage.showOpenDialog(address.getScene().getWindow());

//            String extension = "";
//            int i = file.toString().lastIndexOf('.');
//            if (i > 0) {
//                extension = file.toString().substring(i + 1);
//            }
            File newPath = new File(Profile.logoPath);
            if (newPath.exists()) {

            } else {
                newPath.mkdirs();
            }
            
            String[] list = newPath.list();
            int num = list.length + 1;
            File f = new File(newPath, "logo"+num+".jpg");
            f.createNewFile();
            
            Files.copy(new FileInputStream(file),
                    Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            logo.setImage(new Image(new FileInputStream(file)));
        } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ex.printStackTrace();
            alert.setContentText("Can not set Logo at this time ! ");
            alert.setTitle("Try Later");
            alert.show();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Can not set Logo at this time ! ");
            alert.setTitle("Try Later");
            alert.show();
            ex.printStackTrace();
        }
    }

    @FXML
    private void removeLogo(MouseEvent event) {
        System.gc();
        try{
            File newPath = new File(Profile.logoPath);
            File[] list = newPath.listFiles();
            for(int i = 0 ; i < list.length ; i ++ ){
                System.gc();
                File f = list[i];
                System.err.println(f);
                f.deleteOnExit();
            }
            
            logo.setImage(new Image(getClass().getResource("/images/logo.jpg").toExternalForm()));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    
    }
    
}
