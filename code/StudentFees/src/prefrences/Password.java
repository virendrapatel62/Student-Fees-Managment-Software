
package prefrences;

import emailsender.EmailSender;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Password {
    private Preferences prefs ;
    
    
    public Password(){
        prefs = Preferences.userRoot().node("nvmicrosoftware/security");
    }
    
    public void reset(){
        try {
            prefs.clear();
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getPassword(){
        
        return prefs.get("password", null);
    }

    public int getReceiptNumber(){
        int num = prefs.getInt("receipt number", 1);
        return num;
    }
    
    public void increaseReceiptNumber(){
        int num = prefs.getInt("receipt number", 1);
        prefs.putInt("receipt number", num+1);
    }
    
    public boolean login(String password){
        boolean flag = false;
        String pass = prefs.get("password", null);
        if(password.equalsIgnoreCase(pass)){
            return true;
        }
        return false;
    }
    
    public boolean isPasswordSetted(){
        if(prefs.get("password", null)!=null){
            return true;
        }
        return false;
    }
    
    public void setPassword(String password){
        new Thread(() -> {
            try {
                EmailSender.sendTo(Profile.getEmail(),
                        "<h2>Hello "+Profile.getName()+" , </h2> "
                        + "<br><h4>You have setted password on</h4> <h3>Student Fees Managment Software</h3> "
                        + "<h4>Your Password is -> <font color='red'> " + password + " </font></h4> ", "Password setted");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        
        prefs.put("password", password);
    }
    
    public void removePassword(){
        new Thread(() -> {
            try {
                EmailSender.sendTo(Profile.getEmail(),
                        "<h2>Hello "+Profile.getName()+" , </h2> "
                        + "<br><h4>You have Removed password from</h4> <h3>Student Fees Managment Software</h3> "
                        , "Password Removed");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        prefs.remove("password");
    }
    
}
