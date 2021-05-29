/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static nav.Navigation.isDark;

/**
 *
 * @author Nacho
 */
public class Conecta4 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLSignIn.fxml"));
        
        Scene scene = new Scene(root);
       
        String style = "/view/style.css";
        String styleB = this.getClass().getResource(style).toExternalForm();
        scene.getStylesheets().add(styleB); 
        
        stage.setTitle("Conecta 4");
        stage.setScene(scene);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
