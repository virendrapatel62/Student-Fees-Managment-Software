package prefrences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class Profile {
    private static String email = "email";
    private static String name = "name";
    private static String instituteName = "institute_name";
    private static String address = "address";
    private static String mobile = "mobile";
    public  static String profilePicPath = "images/student_photos";
    public static String logoPath = "images/logo";
    public static String isNew = "isNew";
    

    private static Preferences prefs = Preferences.userRoot().node("nvmicrosoftware/profile");
    
    public Profile() {
        
    }

    public static String getEmail() {
       return  prefs.get(email, "");
    }

    public static String getName() {
        return prefs.get(name, "");
    }

    public static void setInstituteName(String instituteName) {
        prefs.put(Profile.instituteName , instituteName);
    }

    public static void setAddress(String address) {
        prefs.put(Profile.address , address);
    }

    public static void setMobile(String mobile) {
        prefs.put(Profile.mobile , mobile);
    }
    
    

    public static void setEmail(String email){
        prefs.put(Profile.email , email);
    }

    public static String getAddress() {
        if(prefs.get(address, "").trim().isEmpty()){
            return "Your Institute Address";
        }else{
            return prefs.get(address, "");
         }
       
    }

    public static String getInstituteName() {
        if(prefs.get(instituteName, "").trim().isEmpty()){
            return "Your Institute Name";
        }else{
            return prefs.get(instituteName, "");
        }
    }

    public static String getMobile() {
        return prefs.get(mobile, "");
    }
    
    

    public static void setName(String name) {
        prefs.put(Profile.name, name);
    }
    
    public static Image  getLogo() throws Exception{
        File file = new File(logoPath);
        String[] logos = file.list();
        if(logos==null){
           return new Image(Profile.class.getResourceAsStream("/images/logo.jpg"));
        }
        int i = logos.length;
        if(i==0){
            return new Image(Profile.class.getResourceAsStream("/images/logo.jpg"));
        }else{
            return new Image(new FileInputStream(new File(file , "logo"+(i)+".jpg")));
        }
        
    }
    
    public static boolean  isNew(){
        return prefs.getBoolean(isNew, true);
    }
    
    public static void acceptedTermsAndConditions(){
        prefs.putBoolean(isNew, false);
    }
    
    
    
    
       
    
}
