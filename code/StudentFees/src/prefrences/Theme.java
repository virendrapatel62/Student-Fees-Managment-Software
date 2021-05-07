package prefrences;

import java.util.prefs.Preferences;

public class Theme {
    private static Preferences prefs = Preferences.userRoot().node("nvmicrosoftware/themes");
    
    public static int getThemeCode(){
        return prefs.getInt("code", 2);
    }


    public static void setThemeCode(int code){
        prefs.putInt("code", code);
    }
    
    public static String getMainFrameCss(){
        return "/css/theme"+Theme.getThemeCode()+"/mainFrame.css";
    }
    public static String getFeesDetailsCss(){
        return "/css/theme"+Theme.getThemeCode()+"/feesdetails.css";
    }
    public static String getLoginCss(){
        return "/css/theme"+Theme.getThemeCode()+"/loginfxml.css";
    }
    public static String getExportImportProgressCss(){
        return "/css/theme"+Theme.getThemeCode()+"/exportimportprogress.css";
    }
    public static String getMyProfileCss(){
        return "/css/theme"+Theme.getThemeCode()+"/myprofilefxml.css";
    }
    
    public static String getPasswordFxmlCss(){
        return "/css/theme"+Theme.getThemeCode()+"/passwordfxml.css";
    }
    
    public static String getReceiptCss(){
        return "/css/theme"+Theme.getThemeCode()+"/recipt.css";
    }


    public static String getSendMailCss(){
        return "/css/theme"+Theme.getThemeCode()+"/sendemailbox.css";
    }
    
    public static String getDynamicCss(){
        return "/css/dynamicCss.css";
    }
    
    
    
}
