/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
import model.ApplicationState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Connect4;
import model.Player;
import util.Navigation;

/**
 * FXML Controller class
 *
 * @author Nacho
 */
public class FXMLSignInController extends FXMLBaseController {

    @FXML
    private TextField TFUser;
    @FXML
    private PasswordField PFPass;
    @FXML
    private Hyperlink HLForgetPass;
    @FXML
    private Button BSignIn;
    @FXML
    private Text TError;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void init() {}    
    
    @FXML
    private void navigateToForgotPass(){
        Navigation.navigateToForgotPass((Stage) BSignIn.getScene().getWindow(), getClass());
    }
    
    @FXML
    private void authorizeUser(){
        Player p = getUser(TFUser.getText(), PFPass.getText());
        if(p != null && applicationState.logInPlayer(p)) {
            navigateHome();
        } else {
            TError.setVisible(true);
        }
        
    }
    
    private void navigateHome(){
        Navigation.navigateToHome((Stage) BSignIn.getScene().getWindow(), getClass());
    }
    
    private Player getUser(String nickName, String password) {
        Player p = db.loginPlayer(nickName, password);
        return p;
    }
}
