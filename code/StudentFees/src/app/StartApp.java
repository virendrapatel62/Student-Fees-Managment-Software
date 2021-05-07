
package app;

import controllers.LoginFXMLController;
import databaseHandler.Reader;
import databaseHandler.Save;
import emailsender.DataSender;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.Batch;
import models.InstalmentFee;
import models.MonthlyFee;
import models.Student;
import prefrences.Password;
import prefrences.Profile;
import prefrences.Theme;

public class StartApp extends Application {
    FXMLLoader loginLoader = null;
    FXMLLoader splashLoader = null;
    Parent login;
    Parent splash;
    Scene scene;        
    @Override
    public void start(Stage sta) throws Exception {
        try {
            
            loginLoader = new FXMLLoader(getClass().getResource("/FXML/LoginFXML.fxml"));
            splashLoader  = new FXMLLoader(getClass().getResource("/FXML/SplashScreen.fxml"));
             login = loginLoader.load();
             splash = splashLoader.load();
            
             Stage stage = new Stage(StageStyle.TRANSPARENT);
            scene = new Scene(splash);
            stage.setScene(scene);
            
            stage.setFullScreenExitHint("Student Fees Management");
            stage.setMaximized(true);
            stage.show();
            
            FadeTransition fadeout = new FadeTransition(Duration.millis(2000), splash);
            fadeout.setToValue(0.5);
            fadeout.setFromValue(1.0);
            
            fadeout.play();
            
            fadeout.setOnFinished((s) -> {
                stage.close();
                
//                if(Profile.isNew()){
//                    showTermAndCondition();
//                    return ;
//                }
//                
                if (new Password().isPasswordSetted()) {
                    
                    Stage stt = new Stage();
                    FadeTransition fadein = new FadeTransition(Duration.millis(1000), login);
                    fadein.setToValue(1.0);
                     fadein.setFromValue(0);
                    scene = new Scene(login);
                    scene.getStylesheets().add(getClass().getResource(Theme.getLoginCss()).toExternalForm());

                    stt.setScene(scene);
                    stt.show();
                    fadein.play();
                } else {
                    LoginFXMLController con = ((LoginFXMLController) (loginLoader.getController()));
                    con.show();
                }

            });
            
                       
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void showTermAndCondition(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/TermsAnsConditionsFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
