/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nav;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Navigation class made for simplify switching between FXMLs
 * @author 44puk
 */
public class Navigation {
    /**
     * Root method
    */
    private static void navigate(Stage stage, Class classType, String res) {
        try {
            Parent root = FXMLLoader.load(classType.getResource(res));
          
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException err) {
            System.out.println(err);
        }
    }
    
    public static void navigateToSignIn(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLSignIn.fxml");
    }
    
    public static void navigateToForgotPass(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLForgetPass.fxml");
    }
    
    public static void navigateToHome(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLHome.fxml");
    }
    
    public static void navigateToResetPass(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLResetPass.fxml");
    }
    
    public static void navigateToIni(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLIni.fxml");
    }
    
    public static void navigateToConfirmCode(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLCodeConfirm.fxml");
    }
    
    public static void navigateToGame(Stage stage, Class classType){
        navigate(stage, classType, "/view/FXMLGame.fxml");
    }
}