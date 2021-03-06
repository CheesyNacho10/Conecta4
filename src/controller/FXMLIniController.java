/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
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
import model.Connect4;
import nav.Navigation;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLIniController extends FXMLBaseController {

    @FXML
    private Button BSignIn;
    @FXML
    private Button BSignUp;

    @FXML
    private void navigateToSignIn (ActionEvent e) {
        Navigation.navigateToSignIn((Stage) BSignIn.getScene().getWindow(), getClass());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void init() {
        try {
            if(!db.exitsNickName("nickName1")) {
                db.createDemoData(10, 10, 10);
            }
            
        } catch (Connect4DAOException err) {
            System.out.println(err);
        }
        
    }
}
