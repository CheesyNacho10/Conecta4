/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.Connect4DAOException;
import credentials.ApplicationState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import nav.Navigation;

/**
 * FXML Controller class
 * Sing in scene where we verify the identity of the user
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
    @FXML
    private Button BSignUp;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void init() {
        //Remove initial focus from TFUser
    }    
    
    @FXML
    private void navigateToForgotPass(){
        Navigation.navigateToForgotPass((Stage) BSignIn.getScene().getWindow(), getClass());
    }
    
    /**
     * When the user hits de button we verify their identity.
     * If it's authorised we move to the home screen
     */
    @FXML
    private void authorizeUser(){
        Player p = getUser(TFUser.getText(), PFPass.getText()); 
        if(p != null && applicationState.logInPlayer(p)) { // Singleton ApplicationState to know if the Player is in the Database
            navigateHome();
        } else {
            TError.setVisible(true);
        }
        
    }
    
    private void navigateHome(){
        Navigation.navigateToHome((Stage) BSignIn.getScene().getWindow(), getClass());
    }
    
    /**
     * Gets the user in form of Player
     * @param nickName Name
     * @param password Password
     * @return Player created
     */
    private Player getUser(String nickName, String password) {
        Player p = db.loginPlayer(nickName, password);
        return p;
    }

    @FXML
    private void navigateToSignUp(ActionEvent event) {
    }
}
