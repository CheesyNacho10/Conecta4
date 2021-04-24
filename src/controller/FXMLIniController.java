/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLIniController implements Initializable {

    @FXML
    private Button BSignIn;
    @FXML
    private Button BSignUp;

    private void handleButtonAction (ActionEvent e) throws IOException {
        
        // To be revised
        if (e.getSource() == BSignIn) {
            Stage stage = (Stage) BSignIn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLSignIn.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
